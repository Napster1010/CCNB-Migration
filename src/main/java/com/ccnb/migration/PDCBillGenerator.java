package com.ccnb.migration;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.ccnb.bean.AdditionalSecurityDeposit;
import com.ccnb.bean.AdditionalSecurityDepositInstallment;
import com.ccnb.bean.Bill;
import com.ccnb.bean.CCNBAsd;
import com.ccnb.bean.CCNBNSCStagingMigration;
import com.ccnb.bean.ConsumerNoMaster;
import com.ccnb.bean.Payment;
import com.ccnb.util.PathUtil;

public class PDCBillGenerator {

	public static void main(String[] args) throws Exception {
		//For creating a exception Text File
		long exceptionCount=0, recordCount=0;
		File file = new File(PathUtil.baseExceptionFolder + "PDCBillGeneratorExceptionLog.txt");
		FileWriter fw=null;
		BufferedWriter bw = null;
		PrintWriter writer = null;
		try	
		{
			if(file.exists()==false)
				file.createNewFile();
			else
			{
				file.delete();
				file.createNewFile();
			}
			fw = new FileWriter(file,true);
			bw = new BufferedWriter(fw);
			writer = new PrintWriter(bw);
		}
		catch(Exception e){}

		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		long startTime = System.currentTimeMillis();
		
		final SimpleDateFormat billMonthFormat = new SimpleDateFormat("MMM-yyyy");
		Query<CCNBNSCStagingMigration> pdcQuery = session.createQuery("from CCNBNSCStagingMigration where status='D'");
		List<CCNBNSCStagingMigration> pdcConsumers = pdcQuery.list();
		
		Query<ConsumerNoMaster> consumerNoMasterQuery;
		
		//get latest bill month from bill table
		String latestBillMonth = getLatestBillMonth(session);
		if(latestBillMonth==null)
			throw new Exception("No bill was found in bill table !!");
							
		for(CCNBNSCStagingMigration currentRecord: pdcConsumers) {
			try {
				session.beginTransaction();
				
				//retrieve consumer no
				consumerNoMasterQuery = session.createQuery("from ConsumerNoMaster where oldServiceNoOne = :consNo");
				consumerNoMasterQuery.setParameter("consNo", currentRecord.getOld_cons_no());
				
				ConsumerNoMaster consumerNoMaster = consumerNoMasterQuery.uniqueResult();
				if(consumerNoMaster==null)
					throw new Exception("NGB consumer number not found!!");

				BigDecimal totalOutstanding = new BigDecimal(currentRecord.getTotal_outstanding());
				BigDecimal securityDepositAmount = currentRecord.getSecurity_deposit_amount();
				
				if(!(totalOutstanding.compareTo(BigDecimal.ZERO)==0) || securityDepositAmount.compareTo(BigDecimal.ZERO)<0) {
					
					//get pdc date
					Date pdcDate = currentRecord.getPdcDate();				
					
					//outstanding amount
					Long currentOutstanding = Long.parseLong(totalOutstanding.toString());
					
					if(pdcDate!=null) {
						//start with the latest bill month
						Date counterDate = billMonthFormat.parse(latestBillMonth);
						Calendar counterCalendar = Calendar.getInstance();
						counterCalendar.setTime(counterDate);
						
						//for inserting 12 bills for the consumer
						for(int i=1;i<=12;i++) {							
							//check for payments and keep creating bills in decreasing order of bill months (keep in mind the pdc date)
							if(pdcDate.before(counterDate)) {
								
								Bill bill = new Bill();
								initializeInstance(bill);
								
								bill.setLocationCode(consumerNoMaster.getLocationCode());
								bill.setGroupNo(consumerNoMaster.getGroupNo());
								bill.setReadingDiaryNo(consumerNoMaster.getReadingDiaryNo());
								bill.setConsumerNo(consumerNoMaster.getConsumerNo());
								bill.setBillMonth(billMonthFormat.format(counterDate).toUpperCase());
								bill.setBillDate(pdcDate);
								bill.setDueDate(pdcDate);
								bill.setChequeDueDate(pdcDate);
								bill.setArrear(new BigDecimal(currentOutstanding));
								bill.setCumulativeSurcharge(new BigDecimal(currentRecord.getPend_surcharge()));
								bill.setNetBill(bill.getArrear());
								bill.setPristineNetBill(bill.getArrear());																
								
								session.save(bill);
								++recordCount;
								
								String currentBillMonth = billMonthFormat.format(counterDate).toUpperCase();
								Long paymentAmount = getPaymentForMonth(session, currentBillMonth, consumerNoMaster.getConsumerNo());
								currentOutstanding += paymentAmount;
							}else 
								break;								
							
							counterCalendar.add(Calendar.MONTH, -1);
							counterDate = counterCalendar.getTime();
						}						
					}else {
						Bill bill = new Bill();
						initializeInstance(bill);
						
						bill.setLocationCode(consumerNoMaster.getLocationCode());
						bill.setGroupNo(consumerNoMaster.getGroupNo());
						bill.setReadingDiaryNo(consumerNoMaster.getReadingDiaryNo());
						bill.setConsumerNo(consumerNoMaster.getConsumerNo());
						bill.setBillMonth(latestBillMonth);
						bill.setBillDate(new Date());
						bill.setDueDate(new Date());
						bill.setChequeDueDate(new Date());
						bill.setArrear(totalOutstanding);
						bill.setCumulativeSurcharge(totalOutstanding);
						bill.setNetBill(totalOutstanding);
						bill.setPristineNetBill(totalOutstanding);																
						
						session.save(bill);
						++recordCount;						
					}					
				}									
				session.getTransaction().commit();
			}catch(Exception e) {				
				++exceptionCount;				
				writer.println();
				writer.println("***********EXCEPTION NUMBER " + exceptionCount + "***********" + "Occured on: " + new Date());
				writer.println("***********CONSUMER NUMBER: " + currentRecord.getOld_cons_no() + "***********");
				writer.println("Root cause : ");
				e.printStackTrace(writer);
				e.printStackTrace();
				session.getTransaction().rollback();				
			}			
		}
		
		long endTime = System.currentTimeMillis();
		long totTime = endTime - startTime;
		long seconds = (endTime - startTime)/1000;
		long minutes = seconds/60;
		seconds -= minutes*60;
		
		System.out.println("PDC SUCCESSFULLY GENERATED!!");
		System.out.println(recordCount + " BILLS CREATED !!");		
		System.out.println(exceptionCount + " EXCEPTIONS CAUGHT !! PLEASE REFER EXCEPTION LOG FOR MORE DETAILS!!");		
		System.out.println("Time Elapsed: " + minutes + " Minutes " + seconds + " Seconds");				
		
		session.close();
		sessionFactory.close();
		writer.close();		
	}
	
	private static Long getPaymentForMonth(Session session, String billMonth, String consumerNo) {
		Long paymentAmount = 0L;
		
		Query<Payment> paymentQuery = session.createQuery("from Payment where consumerNo=:consNo and postingBillMonth=:billMonth");
		paymentQuery.setParameter("consNo", consumerNo);
		paymentQuery.setParameter("billMonth", billMonth);
		List<Payment> payments = paymentQuery.list();
		
		for(Payment payment: payments) 
			paymentAmount += payment.getAmount();
				
		return paymentAmount;
	}
	
	private static String getLatestBillMonth(Session session) {
		String billMonth=null;
		
		Query<Bill> latestBillQuery = session.createQuery("from Bill where billTypeCode='MIG' order by id DESC");
		latestBillQuery.setMaxResults(1);
		
		Bill latestBill = latestBillQuery.uniqueResult();
		if(latestBill!=null)
			billMonth = latestBill.getBillMonth();
		
		return billMonth;
	}

    private static void initializeInstance(Bill bill)
    {
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
