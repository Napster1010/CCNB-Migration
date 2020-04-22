package com.ccnb.migration;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

import org.apache.commons.lang3.tuple.Pair;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.ccnb.bean.Bill;
import com.ccnb.bean.CCNBNSCStagingMigration;
import com.ccnb.bean.ConsumerNoMaster;
import com.ccnb.bean.Payment;

public class PDCBillGeneratorWorker implements Callable<Pair<Long, Long>> {
	private final SessionFactory sessionFactory;
	private final List<CCNBNSCStagingMigration> pdcConsumers;
	private final PrintWriter exceptionWriter;
	private final String latestBillMonth;
	private final SimpleDateFormat billMonthFormat = new SimpleDateFormat("MMM-yyyy");
	
	public PDCBillGeneratorWorker(SessionFactory sessionFactory, List<CCNBNSCStagingMigration> pdcConsumers,
			String latestBillMonth, PrintWriter exceptionWriter) {
		this.sessionFactory = sessionFactory;
		this.pdcConsumers = pdcConsumers;
		this.exceptionWriter = exceptionWriter;		
		this.latestBillMonth = latestBillMonth;
	}

	@Override
	public Pair<Long, Long> call() throws Exception {
		System.out.println("New thread started!");
		System.out.println("Processing " + pdcConsumers.size() + " no. of consumers");
		Session session = sessionFactory.openSession();
		Query<ConsumerNoMaster> consumerNoMasterQuery;
		long recordCount=0, exceptionCount=0;
		
		for (CCNBNSCStagingMigration currentRecord : pdcConsumers) {
			try {
				long runningCount=0;
				session.clear();
				session.beginTransaction();
				session.flush();

				// retrieve consumer no
				consumerNoMasterQuery = session.createQuery("from ConsumerNoMaster where oldServiceNoOne = :consNo", ConsumerNoMaster.class);
				consumerNoMasterQuery.setParameter("consNo", currentRecord.getOld_cons_no());

				ConsumerNoMaster consumerNoMaster = consumerNoMasterQuery.uniqueResult();
				if (consumerNoMaster == null)
					throw new Exception("NGB consumer number not found!!");

				BigDecimal totalOutstanding = new BigDecimal(currentRecord.getTotal_outstanding());

				// get pdc date
				Date pdcDate = currentRecord.getPdcDate();

				// outstanding amount
				Long currentOutstanding = Long.parseLong(totalOutstanding.toString());

				if (pdcDate != null) {
					// start with the latest bill month
					Date counterDate = billMonthFormat.parse(latestBillMonth);
					Calendar counterCalendar = Calendar.getInstance();
					counterCalendar.setTime(counterDate);

					// for inserting 12 bills for the consumer
					for (int i = 1; i <= 12; i++) {
						// check for payments and keep creating bills in decreasing order of bill months
						// (keep in mind the pdc date)
						if (pdcDate.before(counterDate)) {

							Bill bill = new Bill();
							initializeBillInstance(bill);

							bill.setLocationCode(consumerNoMaster.getLocationCode());
							bill.setGroupNo(consumerNoMaster.getGroupNo());
							bill.setReadingDiaryNo(consumerNoMaster.getReadingDiaryNo());
							bill.setConsumerNo(consumerNoMaster.getConsumerNo());
							bill.setBillMonth(billMonthFormat.format(counterDate).toUpperCase());
							bill.setBillDate(pdcDate);
							bill.setDueDate(pdcDate);
							bill.setChequeDueDate(pdcDate);

							if (totalOutstanding.compareTo(BigDecimal.ZERO) == 0) {
								bill.setArrear(BigDecimal.ZERO);
								bill.setCumulativeSurcharge(BigDecimal.ZERO);
								bill.setNetBill(BigDecimal.ZERO);
								bill.setPristineNetBill(BigDecimal.ZERO);
							} else {
								bill.setArrear(new BigDecimal(currentOutstanding));
								bill.setCumulativeSurcharge(new BigDecimal(currentRecord.getPend_surcharge()));
								bill.setNetBill(bill.getArrear());
								bill.setPristineNetBill(bill.getArrear());
							}
							bill.setPristineCurrentBill(bill.getCurrentBill());

							session.save(bill);
							++runningCount;

							String currentBillMonth = billMonthFormat.format(counterDate).toUpperCase();
							Long paymentAmount = getPaymentForMonth(session, currentBillMonth,
									consumerNoMaster.getConsumerNo());
							currentOutstanding += paymentAmount;
						} else {
							break;							
						}

						counterCalendar.add(Calendar.MONTH, -1);
						counterDate = counterCalendar.getTime();
					}
				} else {
					Bill bill = new Bill();
					initializeBillInstance(bill);

					bill.setLocationCode(consumerNoMaster.getLocationCode());
					bill.setGroupNo(consumerNoMaster.getGroupNo());
					bill.setReadingDiaryNo(consumerNoMaster.getReadingDiaryNo());
					bill.setConsumerNo(consumerNoMaster.getConsumerNo());
					bill.setBillMonth(latestBillMonth);
					bill.setBillDate(new Date());
					bill.setDueDate(new Date());
					bill.setChequeDueDate(new Date());
					
					if (totalOutstanding.compareTo(BigDecimal.ZERO) == 0) {
						bill.setArrear(BigDecimal.ZERO);
						bill.setCumulativeSurcharge(BigDecimal.ZERO);
						bill.setNetBill(BigDecimal.ZERO);
						bill.setPristineNetBill(BigDecimal.ZERO);
					} else {
						bill.setArrear(totalOutstanding);
						bill.setCumulativeSurcharge(totalOutstanding);
						bill.setNetBill(totalOutstanding);
						bill.setPristineNetBill(totalOutstanding);
					}
					
					bill.setPristineCurrentBill(bill.getCurrentBill());
					session.save(bill);
					++runningCount;
				}

				session.getTransaction().commit();
				recordCount += runningCount;
			} catch (Exception e) {
				++exceptionCount;
				synchronized (exceptionWriter) {
					exceptionWriter.println();
					exceptionWriter.println("***********EXCEPTION NUMBER " + exceptionCount + "***********" + "Occured on: " + new Date());
					exceptionWriter.println("***********CONSUMER NUMBER: " + currentRecord.getOld_cons_no() + "***********");
					exceptionWriter.println("Root cause : ");
					e.printStackTrace(exceptionWriter);					
				}
				e.printStackTrace();
				session.getTransaction().rollback();
			}
		}

		session.close();
		return Pair.of(recordCount, exceptionCount);
	}

	private static Long getPaymentForMonth(Session session, String billMonth, String consumerNo) {
		Long paymentAmount = 0L;

		Query<Payment> paymentQuery = session
				.createQuery("from Payment where consumerNo=:consNo and postingBillMonth=:billMonth", Payment.class);
		paymentQuery.setParameter("consNo", consumerNo);
		paymentQuery.setParameter("billMonth", billMonth);
		List<Payment> payments = paymentQuery.list();

		for (Payment payment : payments)
			paymentAmount += payment.getAmount();

		return paymentAmount;
	}

	private static void initializeBillInstance(Bill bill) {
		bill.setCurrentRead(BigDecimal.ZERO);
		bill.setPreviousRead(BigDecimal.ZERO);
		bill.setDifference(BigDecimal.ZERO);
		bill.setMf(BigDecimal.ONE);
		bill.setMeteredUnit(BigDecimal.ZERO);
		bill.setAssessment(BigDecimal.ZERO);
		bill.setTotalUnit(BigDecimal.ZERO);
		bill.setGmcUnit(BigDecimal.ZERO);
		bill.setBilledUnit(BigDecimal.ZERO);
		bill.setBilledMD(BigDecimal.ZERO);
		bill.setBilledPF(BigDecimal.ZERO);
		bill.setLoadFactor(BigDecimal.ZERO);
		bill.setFixedCharge(BigDecimal.ZERO);
		bill.setAdditionalFixedCharges1(BigDecimal.ZERO);
		bill.setAdditionalFixedCharges2(BigDecimal.ZERO);
		bill.setEnergyCharge(BigDecimal.ZERO);
		bill.setFcaCharge(BigDecimal.ZERO);
		bill.setElectricityDuty(BigDecimal.ZERO);
		bill.setPristineElectricityDuty(BigDecimal.ZERO);
		bill.setMeterRent(BigDecimal.ZERO);
		bill.setPfCharge(BigDecimal.ZERO);
		bill.setWeldingTransformerSurcharge(BigDecimal.ZERO);
		bill.setLoadFactorIncentive(BigDecimal.ZERO);
		bill.setSdInterest(BigDecimal.ZERO);
		bill.setCcbAdjustment(BigDecimal.ZERO);
		bill.setLockCredit(BigDecimal.ZERO);
		bill.setOtherAdjustment(BigDecimal.ZERO);
		bill.setEmployeeRebate(BigDecimal.ZERO);
		bill.setOnlinePaymentRebate(BigDecimal.ZERO);
		bill.setPrepaidMeterRebate(BigDecimal.ZERO);
		bill.setPromptPaymentIncentive(BigDecimal.ZERO);
		bill.setAdvancePaymentIncentive(BigDecimal.ZERO);
		bill.setDemandSideIncentive(BigDecimal.ZERO);
		bill.setSubsidy(BigDecimal.ZERO);
		bill.setCurrentBill(BigDecimal.ZERO);
		bill.setSurchargeDemanded(BigDecimal.ZERO);
		bill.setCurrentBillSurcharge(BigDecimal.ZERO);
		bill.setAsdBilled(BigDecimal.ZERO);
		bill.setAsdArrear(BigDecimal.ZERO);
		bill.setAsdInstallment(BigDecimal.ZERO);
		bill.setXrayFixedCharge(BigDecimal.ZERO);
		bill.setCurrentBillSurcharge(BigDecimal.ZERO);
		bill.setBillTypeCode("PDC");
		bill.setDeleted(false);

		bill.setCreatedBy("CCNB_MIG");
		bill.setCreatedOn(new Date());
	}
}
