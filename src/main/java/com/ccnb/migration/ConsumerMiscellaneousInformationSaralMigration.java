package com.ccnb.migration;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.ccnb.bean.CCNBNSCStagingMigration;
import com.ccnb.bean.ConsumerMiscellaneousInformation;
import com.ccnb.bean.SaralScheme;
import com.ccnb.util.PathUtil;

public class ConsumerMiscellaneousInformationSaralMigration {

	public static void main(String[] args) throws Exception {
		// For creating a exception Text File
		long exceptionCount = 0, recordCount = 0;
		File file = new File(PathUtil.baseExceptionFolder + "ConsumerMiscellaneousInformationSaralMigrationExceptionLog.txt");
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
		final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		
		long startTime = System.currentTimeMillis();		

		Query<CCNBNSCStagingMigration> ccnbSaralQuery = session.createQuery("from CCNBNSCStagingMigration where old_trf_catg like 'LV1%' and saral_migrated=false");
		List<CCNBNSCStagingMigration> unmigratedSaralRecords = ccnbSaralQuery.list();

		for (CCNBNSCStagingMigration currentRecord : unmigratedSaralRecords) {
			try {
				session.beginTransaction();
				boolean isSaral = false;

				// retrieve consumer no
				Query<String> consumerNoMasterQuery = session.createQuery("select consumerNo from ConsumerNoMaster where oldServiceNoOne = ?");
				consumerNoMasterQuery.setParameter(0, currentRecord.getOld_cons_no());

				String consumerNo = consumerNoMasterQuery.uniqueResult();
				if (consumerNo == null)
					throw new Exception("NGB consumer number not found!!");

				if (currentRecord.isSaral()) {
					ConsumerMiscellaneousInformation consumerMiscellaneousInformation = new ConsumerMiscellaneousInformation();
					consumerMiscellaneousInformation.setConsumerNo(consumerNo);
					consumerMiscellaneousInformation.setPropertyName("is_saral");
					consumerMiscellaneousInformation.setPropertyValue("Y");
					consumerMiscellaneousInformation.setEffectiveStartDate(new Date());
					consumerMiscellaneousInformation.setEffectiveEndDate(dateFormat.parse("01-01-2050"));
					consumerMiscellaneousInformation.setIsActive(true);
					consumerMiscellaneousInformation.setCreatedBy("CCNB_MIG");
					consumerMiscellaneousInformation.setCreatedOn(new Date());
					session.save(consumerMiscellaneousInformation);
					isSaral = true;
				}

				if ((currentRecord.getShramikNo() != null && currentRecord.getShramikNo().trim().length() > 0) || (currentRecord.getIsKarmkaar() != null && currentRecord.getIsKarmkaar().trim().length() > 0)) {
					final boolean isKarmkar = currentRecord.getIsKarmkaar() != null && currentRecord.getIsKarmkaar().trim().length() >= 3;
					String labourNo = currentRecord.getIsKarmkaar();
					if (isKarmkar) {
						labourNo = labourNo.trim();
						ConsumerMiscellaneousInformation consumerMiscellaneousInformation = new ConsumerMiscellaneousInformation();
						consumerMiscellaneousInformation.setConsumerNo(consumerNo);
						consumerMiscellaneousInformation.setPropertyName("is_karmkar");
						consumerMiscellaneousInformation.setPropertyValue("Y");
						consumerMiscellaneousInformation.setEffectiveStartDate(new Date());
						consumerMiscellaneousInformation.setEffectiveEndDate(dateFormat.parse("01-01-2050"));
						consumerMiscellaneousInformation.setIsActive(true);
						consumerMiscellaneousInformation.setCreatedBy("CCNB_MIG");
						consumerMiscellaneousInformation.setCreatedOn(new Date());
						consumerMiscellaneousInformation.setUpdatedBy("CCNB_MIG");
						consumerMiscellaneousInformation.setUpdatedOn(new Date());
						session.save(consumerMiscellaneousInformation);
					}
					
					labourNo = (currentRecord.getShramikNo() != null && currentRecord.getShramikNo().trim().length()>=3)? currentRecord.getShramikNo().trim() : labourNo;
					//labourNo = (labourNo == null || labourNo.length() == 0) ? currentRecord.getShramikNo() : labourNo;
					if (labourNo != null && labourNo.trim().length() >= 3) {
						ConsumerMiscellaneousInformation consumerMiscellaneousInformation = new ConsumerMiscellaneousInformation();
						consumerMiscellaneousInformation.setConsumerNo(consumerNo);
						consumerMiscellaneousInformation.setPropertyName("is_labour");
						consumerMiscellaneousInformation.setPropertyValue("Y");
						consumerMiscellaneousInformation.setEffectiveStartDate(new Date());
						consumerMiscellaneousInformation.setEffectiveEndDate(dateFormat.parse("01-01-2050"));
						consumerMiscellaneousInformation.setIsActive(true);
						consumerMiscellaneousInformation.setCreatedBy("CCNB_MIG");
						consumerMiscellaneousInformation.setCreatedOn(new Date());
						consumerMiscellaneousInformation.setUpdatedBy("CCNB_MIG");
						consumerMiscellaneousInformation.setUpdatedOn(new Date());
						session.save(consumerMiscellaneousInformation);

						consumerMiscellaneousInformation = new ConsumerMiscellaneousInformation();
						consumerMiscellaneousInformation.setConsumerNo(consumerNo);
						consumerMiscellaneousInformation.setPropertyName("labour_registration_no");
						consumerMiscellaneousInformation.setPropertyValue(labourNo);
						consumerMiscellaneousInformation.setEffectiveStartDate(new Date());
						consumerMiscellaneousInformation.setEffectiveEndDate(dateFormat.parse("01-01-2050"));
						consumerMiscellaneousInformation.setIsActive(true);
						consumerMiscellaneousInformation.setCreatedBy("CCNB_MIG");
						consumerMiscellaneousInformation.setCreatedOn(new Date());
						consumerMiscellaneousInformation.setUpdatedBy("CCNB_MIG");
						consumerMiscellaneousInformation.setUpdatedOn(new Date());
						session.save(consumerMiscellaneousInformation);

						if (!currentRecord.isSaral()) {
							consumerMiscellaneousInformation = new ConsumerMiscellaneousInformation();
							consumerMiscellaneousInformation.setConsumerNo(consumerNo);
							consumerMiscellaneousInformation.setPropertyName("is_saral");
							consumerMiscellaneousInformation.setPropertyValue("Y");
							consumerMiscellaneousInformation.setEffectiveStartDate(new Date());
							consumerMiscellaneousInformation.setEffectiveEndDate(dateFormat.parse("01-01-2050"));
							consumerMiscellaneousInformation.setIsActive(false);
							consumerMiscellaneousInformation.setCreatedBy("CCNB_MIG");
							consumerMiscellaneousInformation.setCreatedOn(new Date());
							consumerMiscellaneousInformation.setUpdatedBy("CCNB_MIG");
							consumerMiscellaneousInformation.setUpdatedOn(new Date());
							session.save(consumerMiscellaneousInformation);
							isSaral = true;
						}
					}
				}
				if (isSaral) {
					SaralScheme saralScheme = new SaralScheme();
					saralScheme.setConsumerNo(consumerNo);
					saralScheme.setAsdArrear(BigDecimal.ZERO);
					saralScheme.setUrjasApplicationNo("");
					saralScheme.setPosted(true);
					saralScheme.setCreatedBy("CCNB_MIG");
					saralScheme.setCreatedOn(new Date());
					saralScheme.setUpdatedBy("CCNB_MIG");
					saralScheme.setUpdatedOn(new Date());
					session.save(saralScheme);
				}

				currentRecord.setSaralMigrated(true);
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

		long endTime = System.currentTimeMillis();
		long totTime = endTime - startTime;
		long seconds = (endTime - startTime) / 1000;
		long minutes = seconds / 60;
		seconds -= minutes * 60;

		System.out.println("MIGRATION FROM CCNB_ASD SUCCESSFULLY DONE !!");
		System.out.println(unmigratedSaralRecords.size() + " ROWS WERE RETRIEVED");
		System.out.println(recordCount + " ROWS SUCCESSFULLY MIGRATED !!");
		System.out.println(exceptionCount + " EXCEPTIONS CAUGHT !! PLEASE REFER EXCEPTION LOG FOR MORE DETAILS!!");
		System.out.println("Time Elapsed: " + minutes + " Minutes " + seconds + " Seconds");

		session.close();
		sessionFactory.close();
		writer.close();
	}

}
