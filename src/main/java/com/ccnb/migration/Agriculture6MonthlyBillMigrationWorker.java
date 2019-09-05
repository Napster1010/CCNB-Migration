package com.ccnb.migration;

import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.ccnb.bean.AgricultureBill6Monthly;
import com.ccnb.bean.CCNBAgricultureBill6Monthly;

public class Agriculture6MonthlyBillMigrationWorker implements Runnable {

	private SessionFactory sessionFactory;
	private int startIndex, endIndex;
	private List<String> billMonths;
	private PrintWriter writer;

	private long recordCount;
	private long exceptionCount;

	public Agriculture6MonthlyBillMigrationWorker(SessionFactory sessionFactory, int startIndex, int endIndex, List<String> billMonths, PrintWriter writer) {
		this.sessionFactory = sessionFactory;
		this.startIndex = startIndex;
		this.endIndex = endIndex;
		this.billMonths = billMonths;
		this.writer = writer;
	}

	public void run() {
		Session session = sessionFactory.openSession();

		Query<String> consumerNoMasterQuery;
		Query<CCNBAgricultureBill6Monthly> billQuery;

		for (int i = startIndex; i <= endIndex; i++) {
			String currentBillMonth = billMonths.get(i);
			billQuery = session.createQuery("from CCNBAgricultureBill6Monthly where startBillMonth=? and migrated=false");
			billQuery.setParameter(0, currentBillMonth);
			List<CCNBAgricultureBill6Monthly> unmigratedRecords = billQuery.list();

			for (CCNBAgricultureBill6Monthly currentRecord : unmigratedRecords) {
				try {
					session.clear();
					session.beginTransaction();
					session.flush();

					// retrieve consumer no
					consumerNoMasterQuery = session.createQuery("select consumerNo from ConsumerNoMaster where oldServiceNoOne = ?");
					consumerNoMasterQuery.setParameter(0, currentRecord.getConsumerNo());

					String consumerNo = consumerNoMasterQuery.uniqueResult();
					if (consumerNo == null)
						throw new Exception("NGB consumer number not found!!");

					AgricultureBill6Monthly bill = new AgricultureBill6Monthly();
					bill.setConsumerNo(consumerNo);
					bill.setStartBillMonth(currentRecord.getStartBillMonth());
					bill.setEndBillMonth(currentRecord.getEndBillMonth());
					bill.setIssueDate(currentRecord.getIssueDate());
					bill.setDueDate(currentRecord.getDueDate());
					bill.setChequeDueDate(currentRecord.getChequeDueDate());
					bill.setLoad(currentRecord.getLoad());
					bill.setUnit(currentRecord.getUnit());
					bill.setEnergyCharge(currentRecord.getEnergyCharge());
					bill.setFixedCharge(currentRecord.getFixedCharge());
					bill.setFca(currentRecord.getFca());
					bill.setCapacitorSurcharge(currentRecord.getCapacitorSurcharge());
					bill.setActualBill(currentRecord.getActualBill());
					bill.setSubsidy(currentRecord.getSubsidy());
					bill.setCurrentBill(currentRecord.getCurrentBill());
					bill.setSecurityDeposit(currentRecord.getSecurityDeposit());
					bill.setSdInterest(currentRecord.getSdInterest());
					bill.setArrear(currentRecord.getArrear());
					bill.setOldArrearInstallment(currentRecord.getOldArrearInstallment());
					bill.setOldArrearInstallmentSubsidy(currentRecord.getOldArrearInstallmentSubsidy());
					bill.setSurcharge(currentRecord.getSurcharge());
					bill.setCumulativeSurcharge(currentRecord.getCumulativeSurcharge());
					bill.setNetBill(currentRecord.getNetBill());
					bill.setSurchargeArrear(currentRecord.getSurchargeArrear());
					bill.setCreatedBy("CCNB_MIG");
					bill.setCreatedOn(new Date());
					bill.setUpdatedBy("CCNB_MIG");
					bill.setUpdatedOn(new Date());

					session.save(bill);
					currentRecord.setMigrated(true);
					session.update(currentRecord);
					session.getTransaction().commit();
					++recordCount;
				} catch (Exception e) {
					++exceptionCount;
					writer.println();
					writer.println("***********EXCEPTION NUMBER " + exceptionCount + "***********" + "THREAD NUMBER " + Thread.currentThread().getName() + "***********" + "Occured on: " + new Date());
					writer.println("************BILL: " + currentRecord);
					writer.println("Root cause : ");
					e.printStackTrace(writer);
					e.printStackTrace();
					session.getTransaction().rollback();
				}
			}
		}

		session.close();
	}

	public long getExceptionCount() {
		return exceptionCount;
	}

	public long getRecordCount() {
		return recordCount;
	}

}
