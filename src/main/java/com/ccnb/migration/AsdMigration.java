package com.ccnb.migration;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.ccnb.bean.AdditionalSecurityDeposit;
import com.ccnb.bean.AdditionalSecurityDepositInstallment;
import com.ccnb.bean.CCNBAsd;
import com.ccnb.bean.CCNBNSCStagingMigration;
import com.ccnb.bean.ConsumerNoMaster;
import com.ccnb.util.PathUtil;

public class AsdMigration {

	public static void main(String[] args) throws Exception {
		//For creating a exception Text File
		long exceptionCount=0, recordCount=0;
		File file = new File(PathUtil.baseExceptionFolder + "AsdMigrationExceptionLog.txt");
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
		
		Query<CCNBAsd> ccnbAsdQuery = session.createQuery("from CCNBAsd where migrated=false");
		List<CCNBAsd> unmigratedRecords = ccnbAsdQuery.list();
		
		Query<CCNBNSCStagingMigration> ccnbNscStagingQuery;
		
		Query<String> consumerNoMasterQuery;
		Query<Integer> migrationStatus;
		
		for(CCNBAsd currentRecord: unmigratedRecords) {
			try {
				session.beginTransaction();
				
				//retrieve consumer no
				consumerNoMasterQuery = session.createQuery("select consumerNo from ConsumerNoMaster where oldServiceNoOne = ?");
				consumerNoMasterQuery.setParameter(0, currentRecord.getConsumerNo());
				
				String consumerNo = consumerNoMasterQuery.uniqueResult();
				if(consumerNo==null)
					throw new Exception("NGB consumer number not found!!");
				
				//Create ASD row
				AdditionalSecurityDeposit additionalSecurityDeposit = new AdditionalSecurityDeposit();
				additionalSecurityDeposit.setConsumerNo(consumerNo);
				additionalSecurityDeposit.setAverageConsumption(new BigDecimal(currentRecord.getAverageConsumption()));
				additionalSecurityDeposit.setAverageBill(new BigDecimal(currentRecord.getAverageBill()));
				additionalSecurityDeposit.setDefaulter(currentRecord.isDefaulter());
				additionalSecurityDeposit.setBillMonth(currentRecord.getBillMonth());
				additionalSecurityDeposit.setRequiredSecurityDeposit(new BigDecimal(currentRecord.getRequiredSecurityDeposit()));
				additionalSecurityDeposit.setExistingSecurityDeposit(new BigDecimal(currentRecord.getExistingSecurityDeposit()));
				additionalSecurityDeposit.setSecurityDepositDemand(new BigDecimal(currentRecord.getSecurityDepositDemand()));
				additionalSecurityDeposit.setPeriod(Integer.parseInt(currentRecord.getPeriod()));
				additionalSecurityDeposit.setCreatedBy("CCNB_MIG");
				additionalSecurityDeposit.setCreatedOn(new Date());
				additionalSecurityDeposit.setUpdatedBy("CCNB_MIG");
				additionalSecurityDeposit.setUpdatedOn(new Date());
				
				session.save(additionalSecurityDeposit);
				
				ccnbNscStagingQuery = session.createQuery("from CCNBNSCStagingMigration where old_cons_no=?");
				ccnbNscStagingQuery.setParameter(0, currentRecord.getConsumerNo());
				
				CCNBNSCStagingMigration ccnbnscStagingMigration = ccnbNscStagingQuery.uniqueResult();
				if(ccnbnscStagingMigration==null)
					throw new Exception("No record found for the consumer in ccnb_nsc_staging_migration !!");								
				
				//Create ASD installment rows
				for(int i=1;i<=3;i++) {
					AdditionalSecurityDepositInstallment additionalSecurityDepositInstallment = new AdditionalSecurityDepositInstallment();
					additionalSecurityDepositInstallment.setAdditionalSecurityDepositId(additionalSecurityDeposit.getId());
					switch(i) {
					case 1:
						additionalSecurityDepositInstallment.setInstallmentAmount(new BigDecimal(currentRecord.getInstallment1()));
						additionalSecurityDepositInstallment.setBillMonth("JUL-2019");
						break;
					case 2:
						additionalSecurityDepositInstallment.setInstallmentAmount(new BigDecimal(currentRecord.getInstallment2()));
						additionalSecurityDepositInstallment.setBillMonth("AUG-2019");
						break;
					case 3:
						additionalSecurityDepositInstallment.setInstallmentAmount(new BigDecimal(currentRecord.getInstallment3()));
						additionalSecurityDepositInstallment.setBillMonth("SEP-2019");
						break;					
					}

					/////////////////////////////////////////Set posted and deleted as false//////////////////////////////////////////////////////					
					if(ccnbnscStagingMigration.isIs_employee() || ccnbnscStagingMigration.isSaral() || ccnbnscStagingMigration.getOld_trf_catg().contains("T") || ccnbnscStagingMigration.getOld_trf_catg().trim().equals("LV1.2N"))
						additionalSecurityDepositInstallment.setDeleted(true);
					else
						additionalSecurityDepositInstallment.setDeleted(false);
					
					additionalSecurityDepositInstallment.setPosted(false);
					session.save(additionalSecurityDepositInstallment);					
				}
				
				//Update the migrated flag				
				migrationStatus = session.createQuery("update CCNBAsd set migrated=true where id=?");
				migrationStatus.setParameter(0, currentRecord.getId());
				migrationStatus.executeUpdate();
				session.getTransaction().commit();

				++recordCount;
			}catch(Exception e) {
				++exceptionCount;				
				writer.println();
				writer.println("***********EXCEPTION NUMBER " + exceptionCount + "***********" + "Occured on: " + new Date());
				writer.println("***********CONSUMER NUMBER: " + currentRecord.getConsumerNo() + "***********");
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
		
		System.out.println("MIGRATION FROM CCNB_ASD SUCCESSFULLY DONE !!");
		System.out.println(unmigratedRecords.size() + " ROWS WERE RETRIEVED");
		System.out.println(recordCount + " ROWS SUCCESSFULLY MIGRATED !!");		
		System.out.println(exceptionCount + " EXCEPTIONS CAUGHT !! PLEASE REFER EXCEPTION LOG FOR MORE DETAILS!!");		
		System.out.println("Time Elapsed: " + minutes + " Minutes " + seconds + " Seconds");				
		
		session.close();
		sessionFactory.close();
		writer.close();		
	}

}
