package com.ccnb.migration;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.ccnb.bean.BillMafiScheme;
import com.ccnb.bean.CCNBBillMafi;
import com.ccnb.bean.GMCAccounting;
import com.ccnb.util.PathUtil;

public class BillMafiMigrator {



	public static void main(String[] args) throws Exception {
		// For creating a exception Text File
		long exceptionCount = 0, recordCount = 0;
		File file = new File(PathUtil.baseExceptionFolder + "BillMafiMigrationExceptionLog.txt");
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
		
		SimpleDateFormat billMonthFormat = new SimpleDateFormat("MMM-yyyy");

		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();		
		
		long startTime = System.currentTimeMillis();		

		Query<CCNBBillMafi> ccnbBillMafiQuery = session.createQuery("from CCNBBillMafi where migrated=false");
		List<CCNBBillMafi> unmigratedSaralRecords = ccnbBillMafiQuery.list();

		for (CCNBBillMafi currentRecord : unmigratedSaralRecords) {
			try {
				session.beginTransaction();
				BillMafiScheme billMafiScheme = new BillMafiScheme();

				// retrieve consumer no
				Query<String> consumerNoMasterQuery = session.createQuery("select consumerNo from ConsumerNoMaster where oldServiceNoOne = ?");
				consumerNoMasterQuery.setParameter(0, currentRecord.getConsumerNo());

				String consumerNo = consumerNoMasterQuery.uniqueResult();
				if (consumerNo == null)
					throw new Exception("NGB consumer number not found!!");
				
				billMafiScheme.setUrjasApplicationNo(currentRecord.getEnrollmentId());
				billMafiScheme.setConsumerNo(consumerNo);
				billMafiScheme.setAppliedOn(currentRecord.getYojnaDate());
				billMafiScheme.setArrear(new BigDecimal(currentRecord.getPrincipalArrear()));
				BigDecimal half = billMafiScheme.getArrear().divide(new BigDecimal("2")).setScale(2, RoundingMode.HALF_UP);
				billMafiScheme.setExemptedByDiscom(half);
				billMafiScheme.setSubsidyByGovernment(half);
				billMafiScheme.setAsdArrear(BigDecimal.ZERO);
				billMafiScheme.setCumulativeSurcharge(new BigDecimal(currentRecord.getSurchargeArrear()));
				billMafiScheme.setSurchargeDemanded(BigDecimal.ZERO);								
				billMafiScheme.setPostedBillMonth(billMonthFormat.format(billMafiScheme.getAppliedOn()).toUpperCase());
				billMafiScheme.setPosted(true);
				billMafiScheme.setCreatedBy("CCNB_MIG");
				billMafiScheme.setCreatedOn(new Date());
				billMafiScheme.setUpdatedBy("CCNB_MIG");
				billMafiScheme.setUpdatedOn(new Date());								
				
				session.save(billMafiScheme);			
				
				currentRecord.setMigrated(true);
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

		System.out.println("MIGRATION FROM CCNB_BILL_MAFI SUCCESSFULLY DONE !!");
		System.out.println(unmigratedSaralRecords.size() + " ROWS WERE RETRIEVED");
		System.out.println(recordCount + " ROWS SUCCESSFULLY MIGRATED !!");
		System.out.println(exceptionCount + " EXCEPTIONS CAUGHT !! PLEASE REFER EXCEPTION LOG FOR MORE DETAILS!!");
		System.out.println("Time Elapsed: " + minutes + " Minutes " + seconds + " Seconds");

		session.close();
		sessionFactory.close();
		writer.close();
	}
}
