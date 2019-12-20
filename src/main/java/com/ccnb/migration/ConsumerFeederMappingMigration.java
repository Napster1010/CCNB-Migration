package com.ccnb.migration;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.ccnb.bean.ConsumerFeederMapping;
import com.ccnb.bean.ConsumerMiscellaneousInformation;
import com.ccnb.util.PathUtil;

public class ConsumerFeederMappingMigration {
	public static void main(String[] args) throws Exception {
		// For creating a exception Text File
		long exceptionCount = 0, recordCount = 0;
		File file = new File(PathUtil.baseExceptionFolder + "ConsumerFeederMappingMigrationExceptionLog.txt");
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

		Query<ConsumerFeederMapping> consumerFeederMappingQuery = session.createQuery("from ConsumerFeederMapping where migrated=false");
		List<ConsumerFeederMapping> unmigratedRecords = consumerFeederMappingQuery.list();
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

		for (ConsumerFeederMapping currentRecord : unmigratedRecords) {
			try {
				session.clear();
				session.beginTransaction();
				session.flush();

				// retrieve consumer no
				Query<String> consumerNoMasterQuery = session.createQuery("select consumerNo from ConsumerNoMaster where oldServiceNoOne = ?");
				consumerNoMasterQuery.setParameter(0, currentRecord.getOldConsumerNo());

				String consumerNo = consumerNoMasterQuery.uniqueResult();
				if (consumerNo == null)
					throw new Exception("NGB consumer number not found!!");

				ConsumerMiscellaneousInformation consumerMiscellaneousInformation = new ConsumerMiscellaneousInformation();
				consumerMiscellaneousInformation.setConsumerNo(consumerNo);
				consumerMiscellaneousInformation.setPropertyName("feeder_code");
				consumerMiscellaneousInformation.setPropertyValue(currentRecord.getFeederCode());
				consumerMiscellaneousInformation.setEffectiveStartDate(new Date());
				consumerMiscellaneousInformation.setEffectiveEndDate(dateFormat.parse("01-01-2050"));
				consumerMiscellaneousInformation.setIsActive(true);
				consumerMiscellaneousInformation.setCreatedBy("CCNB_MIG");
				consumerMiscellaneousInformation.setCreatedOn(new Date());
				consumerMiscellaneousInformation.setUpdatedBy("CCNB_MIG");
				consumerMiscellaneousInformation.setUpdatedOn(new Date());

				session.save(consumerMiscellaneousInformation);

				currentRecord.setMigrated(true);
				session.update(currentRecord);
				
				session.getTransaction().commit();
				++recordCount;
			} catch (Exception e) {
				++exceptionCount;
				writer.println();
				writer.println("***********EXCEPTION NUMBER: " + exceptionCount + "***********" + "CONSUMER NUMBER: " + currentRecord.getOldConsumerNo() + "***********" + "Occured on: " + new Date());
				writer.println("Root cause : ");
				e.printStackTrace(writer);
				e.printStackTrace();
				session.getTransaction().rollback();
			}
		}

		long endTime = System.currentTimeMillis();
		long totTime = endTime - startTime;
		long seconds = (endTime - startTime) / 1000;
		long minutes = seconds / 60;
		seconds -= minutes * 60;

		System.out.println("MIGRATION FROM CONSUMER_FEEDER_MAPPING SUCCESSFULLY DONE !!");
		System.out.println(unmigratedRecords.size() + " ROWS WERE RETRIEVED");
		System.out.println(recordCount + " ROWS SUCCESSFULLY MIGRATED !!");
		System.out.println(exceptionCount + " EXCEPTIONS CAUGHT !! PLEASE REFER EXCEPTION LOG FOR MORE DETAILS!!");
		System.out.println("Time Elapsed: " + minutes + " Minutes " + seconds + " Seconds");

		session.close();
		sessionFactory.close();
		writer.close();
	}
}
