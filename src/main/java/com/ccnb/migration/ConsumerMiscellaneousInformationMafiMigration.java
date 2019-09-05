package com.ccnb.migration;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.ccnb.bean.CCNBNSCStagingMigration;
import com.ccnb.bean.ConsumerMiscellaneousInformation;
import com.ccnb.util.PathUtil;

public class ConsumerMiscellaneousInformationMafiMigration {

	public static void main(String[] args) throws Exception {
		// For creating a exception Text File
		long exceptionCount = 0, recordCount = 0;
		File file = new File(PathUtil.baseExceptionFolder + "ConsumerMiscellaneousInformationMigrationMafiExceptionLog.txt");
		FileWriter fw = null;
		BufferedWriter bw = null;
		PrintWriter writer = null;
		try {
			if (file.exists() == false)
				file.createNewFile();
			else {
				file.delete();
				file.createNewFile();
			}
			fw = new FileWriter(file, true);
			bw = new BufferedWriter(fw);
			writer = new PrintWriter(bw);
		} catch (Exception e) {
		}

		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		long startTime = System.currentTimeMillis();

		Query<CCNBNSCStagingMigration> ccnbMafiQuery = session.createQuery("from CCNBNSCStagingMigration where old_trf_catg like 'LV1%' and is_bill_mafi=true and bill_mafi_migrated=false");
		List<CCNBNSCStagingMigration> unmigratedMafiRecords = ccnbMafiQuery.list();

		for (CCNBNSCStagingMigration currentRecord : unmigratedMafiRecords) {
			try {
				session.clear();
				session.beginTransaction();
				session.flush();

				// retrieve consumer no
				Query<String> consumerNoMasterQuery = session.createQuery("select consumerNo from ConsumerNoMaster where oldServiceNoOne = ?");
				consumerNoMasterQuery.setParameter(0, currentRecord.getOld_cons_no());

				String consumerNo = consumerNoMasterQuery.uniqueResult();
				if (consumerNo == null)
					throw new Exception("NGB consumer number not found!!");

				if (currentRecord.isBillMafi()) {
					ConsumerMiscellaneousInformation consumerMiscellaneousInformation = new ConsumerMiscellaneousInformation();
					consumerMiscellaneousInformation.setConsumerNo(consumerNo);
					consumerMiscellaneousInformation.setPropertyName("is_bill_mafi");
					consumerMiscellaneousInformation.setPropertyValue("Y");
					consumerMiscellaneousInformation.setEffectiveStartDate(new Date());
					consumerMiscellaneousInformation.setEffectiveEndDate(new Date());
					consumerMiscellaneousInformation.setIsActive(true);
					consumerMiscellaneousInformation.setCreatedBy("CCNB_MIG");
					consumerMiscellaneousInformation.setCreatedOn(new Date());
					consumerMiscellaneousInformation.setUpdatedBy("CCNB_MIG");
					consumerMiscellaneousInformation.setUpdatedOn(new Date());
					session.save(consumerMiscellaneousInformation);
				}

				currentRecord.setBillMafiMigrated(true);
				session.update(currentRecord);
				session.getTransaction().commit();
				++recordCount;
			} catch (Exception e) {
				++exceptionCount;
				writer.println();
				writer.println("***********EXCEPTION NUMBER " + exceptionCount + "***********" + "Occured on: " + new Date());
				writer.println("***********NSC STAGING: " + currentRecord);
				writer.println("Root cause : ");
				e.printStackTrace(writer);
				e.printStackTrace();
				session.getTransaction().rollback();
			}
		}

		try {
			session.beginTransaction();
			Query<Integer> migrationStatus = session.createQuery("update CCNBNSCStagingMigration set bill_mafi_migrated=true where is_bill_mafi=false");
			int count = migrationStatus.executeUpdate();
			session.getTransaction().commit();
			System.out.println(count + " ROWS BLINDLY UPDATED!!");
		} catch (Exception e) {
			writer.println();
			writer.println("***********EXCEPTION WHILE UPDATING BILL MAFI FALSE RECORDS " + exceptionCount + "***********" + "Occured on: " + new Date());
			writer.println("Root cause : ");
			e.printStackTrace(writer);
			e.printStackTrace();
			session.getTransaction().rollback();
		}

		long endTime = System.currentTimeMillis();
		long totTime = endTime - startTime;
		long seconds = (endTime - startTime) / 1000;
		long minutes = seconds / 60;
		seconds -= minutes * 60;

		System.out.println("MIGRATION FROM CCNB_ASD SUCCESSFULLY DONE !!");
		System.out.println(unmigratedMafiRecords.size() + " ROWS WERE RETRIEVED");
		System.out.println(recordCount + " ROWS SUCCESSFULLY MIGRATED !!");
		System.out.println(exceptionCount + " EXCEPTIONS CAUGHT !! PLEASE REFER EXCEPTION LOG FOR MORE DETAILS!!");
		System.out.println("Time Elapsed: " + minutes + " Minutes " + seconds + " Seconds");

		session.close();
		sessionFactory.close();
		writer.close();
	}

}
