package com.ccnb.migration;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.ccnb.bean.Bill;
import com.ccnb.bean.CCNBBill;
import com.ccnb.bean.ConsumerNoMaster;

public class BillMigrationWorker implements Runnable{
	
	private SessionFactory sessionFactory;
	private int startIndex, endIndex;	
	private List<String> billMonths;
	private PrintWriter writer;
	
	private long recordCount;
	private long exceptionCount;
	
	public BillMigrationWorker(SessionFactory sessionFactory, int startIndex, int endIndex, List<String> billMonths, PrintWriter writer) {
		this.sessionFactory = sessionFactory;
		this.startIndex = startIndex;
		this.endIndex = endIndex;
		this.billMonths = billMonths;
		this.writer = writer;
	}
	
	public void run() {
		Session session = sessionFactory.openSession();
		
		Query<ConsumerNoMaster> consumerNoMasterQuery;
		Query<CCNBBill> billQuery;
		Query<Integer> migrationStatus;
		
		for(int i=startIndex; i<=endIndex; i++) {
			String currentBillMonth = billMonths.get(i);
			billQuery = session.createQuery("from CCNBBill where billMonth=? and migrated=false");
			billQuery.setParameter(0, currentBillMonth);
			List<CCNBBill> unmigratedRecords = billQuery.list();
			
			for(CCNBBill currentRecord: unmigratedRecords) {				
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

					Bill bill = new Bill();
					initializeInstance(bill);
					
					bill.setConsumerNo(consumerNoMaster.getConsumerNo());
					bill.setLocationCode(currentRecord.getLocationCode());
					bill.setGroupNo(consumerNoMaster.getGroupNo());
					bill.setReadingDiaryNo(consumerNoMaster.getReadingDiaryNo());
					bill.setBillMonth(currentRecord.getBillMonth());
					bill.setBillDate(currentRecord.getBillDate());
					bill.setDueDate(currentRecord.getDueDate());
					bill.setChequeDueDate(currentRecord.getChequeDueDate());
					bill.setCurrentReadDate(currentRecord.getCurrentReadDate());
					bill.setCurrentRead(new BigDecimal(currentRecord.getCurrentRead()));
					bill.setPreviousRead(new BigDecimal(currentRecord.getPreviousRead()));
					bill.setDifference(new BigDecimal(currentRecord.getDifference()));
					bill.setMf(new BigDecimal(currentRecord.getMf()));
					bill.setMeteredUnit(new BigDecimal(currentRecord.getMeteredUnit()));
					bill.setAssessment(new BigDecimal(currentRecord.getAssessment()));
					bill.setTotalUnit(new BigDecimal(currentRecord.getTotalUnit()));
					bill.setGmcUnit(new BigDecimal(currentRecord.getGmcUnit()));
					bill.setBilledUnit(new BigDecimal(currentRecord.getBilledUnit()));
					bill.setBilledMD(new BigDecimal(currentRecord.getBilledMD()));
					bill.setBilledPF(new BigDecimal(currentRecord.getBilledPF()));
					bill.setLoadFactor(new BigDecimal(currentRecord.getLoadFactor()));
					bill.setFixedCharge(new BigDecimal(currentRecord.getFixedCharge()));
					bill.setAdditionalFixedCharges1(new BigDecimal(currentRecord.getAdditionalFixedCharges1()));
					bill.setAdditionalFixedCharges2(new BigDecimal(currentRecord.getAdditionalFixedCharges2()));
					bill.setXrayFixedCharge(new BigDecimal(currentRecord.getXrayFixedCharge()));
					bill.setEnergyCharge(new BigDecimal(currentRecord.getEnergyCharge()));
					bill.setFcaCharge(new BigDecimal(currentRecord.getFcaCharge()));
					bill.setPristineElectricityDuty(new BigDecimal(currentRecord.getPristineElectricityDuty()));
					bill.setElectricityDuty(new BigDecimal(currentRecord.getElectricityDuty()));
					bill.setMeterRent(new BigDecimal(currentRecord.getMeterRent()));
					bill.setPfCharge(new BigDecimal(currentRecord.getPfCharge()));
					bill.setWeldingTransformerSurcharge(new BigDecimal(currentRecord.getWeldingTransformerSurcharge()));
					bill.setLoadFactorIncentive(new BigDecimal(currentRecord.getLoadFactorIncentive()));
					bill.setSdInterest(new BigDecimal(currentRecord.getSdInterest()));
					bill.setCcbAdjustment(new BigDecimal(currentRecord.getCcbAdjustment()));
					bill.setLockCredit(new BigDecimal(currentRecord.getLockCredit()));
					bill.setOtherAdjustment(new BigDecimal(currentRecord.getOtherAdjustment()));
					bill.setEmployeeRebate(new BigDecimal(currentRecord.getEmployeeRebate()));
					bill.setOnlinePaymentRebate(new BigDecimal(currentRecord.getOnlinePaymentRebate()));
					bill.setPrepaidMeterRebate(new BigDecimal(currentRecord.getPrepaidMeterRebate()));
					bill.setPromptPaymentIncentive(new BigDecimal(currentRecord.getPromptPaymentIncentive()));
					bill.setAdvancePaymentIncentive(new BigDecimal(currentRecord.getAdvancePaymentIncentive()));
					bill.setDemandSideIncentive(new BigDecimal(currentRecord.getDemandSideIncentive()));
					bill.setSubsidy(new BigDecimal(currentRecord.getSubsidy()));
					bill.setPristineCurrentBill(new BigDecimal(currentRecord.getPristineCurrentBill()));
					bill.setCurrentBill(new BigDecimal(currentRecord.getCurrentBill()));
					bill.setArrear(new BigDecimal(currentRecord.getArrear()));
					bill.setCumulativeSurcharge(new BigDecimal(currentRecord.getCumulativeSurcharge()));
					bill.setSurchargeDemanded(new BigDecimal(currentRecord.getSurchargeDemanded()));
					bill.setPristineNetBill(new BigDecimal(currentRecord.getPristineNetBill()));
					bill.setNetBill(new BigDecimal(currentRecord.getNetBill()));
					bill.setCurrentBillSurcharge(new BigDecimal(currentRecord.getCurrentBillSurcharge()));
					bill.setAsdBilled(new BigDecimal(currentRecord.getAsdBilled()));
					bill.setAsdArrear(new BigDecimal(currentRecord.getAsdArrear()));
					bill.setAsdInstallment(new BigDecimal(currentRecord.getAsdInstallment()));			
					
					bill.setCreatedBy("CCNB_MIG");
					bill.setCreatedOn(new Date());
					bill.setUpdatedBy("CCNB_MIG");
					bill.setUpdatedOn(new Date());
					
					session.save(bill);

					//Update the migrated flag				
					migrationStatus = session.createQuery("update CCNBBill set migrated=true where id=?");
					migrationStatus.setParameter(0, currentRecord.getId());
					migrationStatus.executeUpdate();
					
					session.getTransaction().commit();
					++recordCount;
				}catch(Exception e) {
                    ++exceptionCount;              
                    writer.println();
                    writer.println("***********EXCEPTION NUMBER " + exceptionCount + "***********" + "THREAD NUMBER " + Thread.currentThread().getName() + "***********" + "Occured on: " + new Date());
                    writer.println("***********LOCATION CODE: " + currentRecord.getLocationCode() + "***" + "CONSUMER NUMBER: " + currentRecord.getConsumerNo() + "***" + "BILL MONTH: " + currentBillMonth + "***********");
                    writer.println("Root cause : ");
                    e.printStackTrace(writer);
                    e.printStackTrace();
                    session.getTransaction().rollback();                                					
				}
			}			
		}
		
		session.close();		
	}

    private void initializeInstance(Bill bill)
    {
        bill.setBillTypeCode("MIG");       
        bill.setDeleted(false);
    }
    
    public long getExceptionCount() {
    	return exceptionCount;    
    }
    
    public long getRecordCount() {
    	return recordCount;
    }        
}
