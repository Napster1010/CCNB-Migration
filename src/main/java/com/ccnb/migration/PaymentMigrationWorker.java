package com.ccnb.migration;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.ccnb.bean.CCNBPayment;
import com.ccnb.bean.ConsumerNoMaster;
import com.ccnb.bean.Payment;
import com.ccnb.bean.ReadMaster;
import com.ccnb.bean.ReadMasterKW;
import com.ccnb.bean.ReadMasterPF;

public class PaymentMigrationWorker implements Runnable {
	
	private SessionFactory sessionFactory;
	private int startIndex, endIndex;	
	private List<String> billMonths;
	private PrintWriter writer;
	
	private long recordCount;
	private long exceptionCount;
	
	public PaymentMigrationWorker(SessionFactory sessionFactory, int startIndex, int endIndex, List<String> billMonths, PrintWriter writer) {
		this.sessionFactory = sessionFactory;
		this.startIndex = startIndex;
		this.endIndex = endIndex;
		this.billMonths = billMonths;
		this.writer = writer;
	}
	
	public void run() {
		Session session = sessionFactory.openSession();
		
		Query<ConsumerNoMaster> consumerNoMasterQuery;
		Query<CCNBPayment> readQuery;
		Query<Integer> migrationStatus;
		
		for(int i=startIndex; i<=endIndex; i++) {
			String currentBillMonth = billMonths.get(i);
			readQuery = session.createQuery("from CCNBPayment where postingBillMonth=? and migrated=false");
			readQuery.setParameter(0, currentBillMonth);
			List<CCNBPayment> unmigratedRecords = readQuery.list();
			
			for(CCNBPayment currentRecord: unmigratedRecords) {
				try {
					session.clear();
					session.beginTransaction();
					session.flush();
					
					//retrieve consumer no
					consumerNoMasterQuery = session.createQuery("from ConsumerNoMaster where oldServiceNoOne = ?");
					consumerNoMasterQuery.setParameter(0, currentRecord.getConsumerNo());
					
					ConsumerNoMaster consumerNoMaster = consumerNoMasterQuery.uniqueResult();
					if(consumerNoMaster==null)
						throw new Exception("NGB consumer number not found!!");

					Payment payment = new Payment();
					
					payment.setSource(currentRecord.getSource());
					payment.setOnline(currentRecord.isOnline());
					payment.setLocationCode(currentRecord.getLocationCode());
					payment.setConsumerNo(consumerNoMaster.getConsumerNo());
					payment.setPunchingDate(currentRecord.getPunchingDate());
					payment.setPayDate(currentRecord.getPayDate());
					payment.setAmount(Long.parseLong(currentRecord.getAmount()));
					payment.setPayWindow(currentRecord.getPayWindow());
					payment.setCacNo(currentRecord.getCacNo());
					payment.setDeleted(currentRecord.isDeleted());
					payment.setPosted(currentRecord.isPosted());
					payment.setPostingBillMonth(currentRecord.getPostingBillMonth());
					payment.setPostingDate(currentRecord.getPostingDate());
					payment.setCreatedBy("CCNB_MIG");
					payment.setCreatedOn(new Date());
					payment.setUpdatedBy("CCNB_MIG");
					payment.setUpdatedOn(new Date());

					if("CASH".equals(currentRecord.getPayMode()) || "DD".equals(currentRecord.getPayMode()) || "EPYT".equals(currentRecord.getPayMode()) || "RTGS".equals(currentRecord.getPayMode()))
						payment.setPayMode("CASH");
					else if("CHEQ".equals(currentRecord.getPayMode()))
						payment.setPayMode("CHEQUE");
					else
						throw new Exception("Invalid pay mode!");
					
					session.save(payment);					

					//Update the migrated flag
					currentRecord.setMigrated(true);
					session.update(currentRecord);					
					session.getTransaction().commit();
					++recordCount;
				}catch(Exception e) {
                    ++exceptionCount;              
                    writer.println();
                    writer.println("***********EXCEPTION NUMBER " + exceptionCount + "***********" + "THREAD NUMBER " + Thread.currentThread().getName() + "***********" + "Occured on: " + new Date());
                    writer.println("***********CONSUMER NUMBER: " + currentRecord.getConsumerNo() + "***" + "BILL MONTH: " + currentBillMonth + "***********");
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
