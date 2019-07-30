package com.ccnb.migration;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.ccnb.bean.Bill;
import com.ccnb.bean.CCNBRead;
import com.ccnb.bean.ReadMaster;
import com.ccnb.bean.ReadMasterKW;
import com.ccnb.bean.ReadMasterPF;

public class ReadMigrationWorker implements Runnable {
	
	private SessionFactory sessionFactory;
	private int startIndex, endIndex;	
	private List<String> billMonths;
	private PrintWriter writer;
	
	private long recordCount;
	private long exceptionCount;
	
	public ReadMigrationWorker(SessionFactory sessionFactory, int startIndex, int endIndex, List<String> billMonths, PrintWriter writer) {
		this.sessionFactory = sessionFactory;
		this.startIndex = startIndex;
		this.endIndex = endIndex;
		this.billMonths = billMonths;
		this.writer = writer;
	}
	
	public void run() {
		Session session = sessionFactory.openSession();
		
		Query<String> consumerNoMasterQuery;
		Query<CCNBRead> readQuery;
		Query<Integer> migrationStatus;
		
		for(int i=startIndex; i<=endIndex; i++) {
			String currentBillMonth = billMonths.get(i);
			readQuery = session.createQuery("from CCNBRead where billMonth=? and migrated=false");
			readQuery.setParameter(0, currentBillMonth);
			List<CCNBRead> unmigratedRecords = readQuery.list();
			
			for(CCNBRead currentRecord: unmigratedRecords) {
				try {
					session.beginTransaction();
					
					//retrieve consumer no
					consumerNoMasterQuery = session.createQuery("select consumerNo from ConsumerNoMaster where oldServiceNoOne = ?");
					consumerNoMasterQuery.setParameter(0, currentRecord.getConsumerNo());
					
					String consumerNo = consumerNoMasterQuery.uniqueResult();
					if(consumerNo==null)
						throw new Exception("NGB consumer number not found!!");

					ReadMaster readMaster = new ReadMaster();
					
					readMaster.setBillMonth(currentRecord.getBillMonth());
					readMaster.setGroupNo(currentRecord.getGroupNo());
					readMaster.setReadingDiaryNo(currentRecord.getReadingDiaryNo());
					readMaster.setConsumerNo(consumerNo);
					readMaster.setMeterIdentifier((currentRecord.getMeterIdentifier()==null || currentRecord.getMeterIdentifier().equals("0"))?null:currentRecord.getMeterIdentifier());
					readMaster.setReadingDate(currentRecord.getReadingDate());					
					
					if("ASSESSED_UNIT".equals(currentRecord.getReadingType()))
						readMaster.setReadingType("ASSESSMENT");
					else if("METER_REPLACED".equals(currentRecord.getReadingType()) || "NORMAL".equals(currentRecord.getReadingType()))
						readMaster.setReadingType("NORMAL");
					else if("PFL".equals(currentRecord.getReadingType()))
						readMaster.setReadingType("PFL");					
					else
						throw new Exception("Invalid reading type");
					
					if("ASSESSMENT".equals(readMaster.getReadingType()))
						readMaster.setMeterStatus("DEFECTIVE");
					else
						readMaster.setMeterStatus("WORKING");
					
					readMaster.setReplacementFlag("NR");
					readMaster.setSource("MIG");
					readMaster.setReading(new BigDecimal(currentRecord.getReading()));
					readMaster.setDifference(new BigDecimal(currentRecord.getDifference()));
					readMaster.setMf((currentRecord.getMf()==null || currentRecord.getMf().equals("0"))?BigDecimal.ONE:new BigDecimal(currentRecord.getMf()));
					readMaster.setConsumption(new BigDecimal(currentRecord.getConsumption()));
					readMaster.setAssessment(new BigDecimal(currentRecord.getAssessment()));
					readMaster.setPropagatedAssessment(new BigDecimal(currentRecord.getPropagatedAssessment()));
					readMaster.setTotalConsumption(new BigDecimal(currentRecord.getTotalConsumption()));
					
					readMaster.setCreatedBy("MIG");
					readMaster.setCreatedOn(new Date());
					readMaster.setUpdatedBy("MIG");
					readMaster.setUpdatedOn(new Date());
					readMaster.setUsedOnBill(true);

					session.save(readMaster);

					BigDecimal billingDemand = new BigDecimal(currentRecord.getBillingDemand());
					BigDecimal billingPf = new BigDecimal(currentRecord.getBillingPf());
					
					if(billingDemand.compareTo(BigDecimal.ZERO)>0 || billingPf.compareTo(BigDecimal.ZERO)>0) {
						//insert into readMasterPf and readMasterKw
						ReadMasterKW readMasterKW = new ReadMasterKW();
						ReadMasterPF readMasterPF = new ReadMasterPF();
						
						readMasterKW.setMeterMD(new BigDecimal(currentRecord.getMeterMd()));
						readMasterKW.setMultipliedMD(new BigDecimal(currentRecord.getMultipliedMd()));
						readMasterKW.setBillingDemand(billingDemand);
						readMasterKW.setCreatedBy("MIG");
						readMasterKW.setCreatedOn(new Date());
						readMasterKW.setUpdatedBy("MIG");
						readMasterKW.setUpdatedOn(new Date());
						readMasterKW.setReadMasterId(readMaster.getId());
						
						readMasterPF.setMeterPF(new BigDecimal(currentRecord.getMeterPf()));
						readMasterPF.setBillingPF(new BigDecimal(currentRecord.getMeterPf()));
						readMasterPF.setCreatedBy("MIG");
						readMasterPF.setCreatedOn(new Date());
						readMasterPF.setUpdatedBy("MIG");
						readMasterPF.setUpdatedOn(new Date());
						readMasterPF.setReadMasterId(readMaster.getId());
						
						session.save(readMasterKW);
						session.save(readMasterPF);						
					}

					//Update the migrated flag				
					migrationStatus = session.createQuery("update CCNBRead set migrated=true where id=?");
					migrationStatus.setParameter(0, currentRecord.getId());
					migrationStatus.executeUpdate();
					
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
