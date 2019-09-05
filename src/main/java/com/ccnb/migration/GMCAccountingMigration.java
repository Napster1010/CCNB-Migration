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

import com.ccnb.bean.CCNBGMCAccounting;
import com.ccnb.bean.CCNBNSCStagingMigration;
import com.ccnb.bean.ConsumerMiscellaneousInformation;
import com.ccnb.bean.GMCAccounting;
import com.ccnb.bean.SaralScheme;
import com.ccnb.util.PathUtil;

public class GMCAccountingMigration {


	public static void main(String[] args) throws Exception {
		// For creating a exception Text File
		long exceptionCount = 0, recordCount = 0;
		File file = new File(PathUtil.baseExceptionFolder + "GMCMigrationExceptionLog.txt");
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
		final SimpleDateFormat billMonthFormat = new SimpleDateFormat("MMM-yyyy");
		
		long startTime = System.currentTimeMillis();		

		Query<CCNBGMCAccounting> ccnbGmcQuery = session.createQuery("from CCNBGMCAccounting where migrated=false");
		List<CCNBGMCAccounting> unmigratedRecords = ccnbGmcQuery.list();

		for (CCNBGMCAccounting currentRecord : unmigratedRecords) {
			try {
				session.clear();
				session.beginTransaction();
				session.flush();

				GMCAccounting gmcAccounting = new GMCAccounting();

				// retrieve consumer no
				Query<String> consumerNoMasterQuery = session.createQuery("select consumerNo from ConsumerNoMaster where oldServiceNoOne = ?");
				consumerNoMasterQuery.setParameter(0, currentRecord.getConsumerNo());

				String consumerNo = consumerNoMasterQuery.uniqueResult();
				if (consumerNo == null)
					throw new Exception("NGB consumer number not found!!");

				gmcAccounting.setConsumerNo(consumerNo);
				gmcAccounting.setCurrentBillMonth(currentRecord.getBillMonth());
				gmcAccounting.setReadType("NORMAL");
				gmcAccounting.setCurrentConsumption(new BigDecimal(currentRecord.getCrntConsumption()));
				gmcAccounting.setActualCumulativeConsumption(new BigDecimal(currentRecord.getSumTotalUnit()));
				gmcAccounting.setMinimumCumulative(new BigDecimal(currentRecord.getSumBilledUnit()));				
				gmcAccounting.setHigherOfActualMinimumCumulative((gmcAccounting.getActualCumulativeConsumption().compareTo(gmcAccounting.getMinimumCumulative())<=0 ? gmcAccounting.getMinimumCumulative() : gmcAccounting.getActualCumulativeConsumption()));								
				gmcAccounting.setAlreadyBilled(new BigDecimal(currentRecord.getAlreadyBilled()));
				gmcAccounting.setToBeBilled(gmcAccounting.getHigherOfActualMinimumCumulative().subtract(gmcAccounting.getAlreadyBilled()));
				initializeZero(gmcAccounting);
				
				//prev bill month logic
				Date billMonthDate = billMonthFormat.parse(gmcAccounting.getCurrentBillMonth());
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(billMonthDate);
				calendar.add(Calendar.MONTH, -1);
				
				Date newBillMonthDate = calendar.getTime();
				String prevBillMonth = billMonthFormat.format(newBillMonthDate).toUpperCase();
				gmcAccounting.setPreviousMonth(prevBillMonth);
				
				session.save(gmcAccounting);				
				currentRecord.setMigrated(true);
				session.update(currentRecord);

				session.getTransaction().commit();
				++recordCount;
			} catch (Exception e) {
				++exceptionCount;
				writer.println();
				writer.println("***********EXCEPTION NUMBER " + exceptionCount + "***********" + "Occured on: " + new Date());
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

		System.out.println("MIGRATION FROM CCNB_GMC_ACCOUNTING SUCCESSFULLY DONE !!");
		System.out.println(unmigratedRecords.size() + " ROWS WERE RETRIEVED");
		System.out.println(recordCount + " ROWS SUCCESSFULLY MIGRATED !!");
		System.out.println(exceptionCount + " EXCEPTIONS CAUGHT !! PLEASE REFER EXCEPTION LOG FOR MORE DETAILS!!");
		System.out.println("Time Elapsed: " + minutes + " Minutes " + seconds + " Seconds");

		session.close();
		sessionFactory.close();
		writer.close();
	}
	
	private static void initializeZero(GMCAccounting gmcAccounting) {
		gmcAccounting.setPreviousConsumption(BigDecimal.ZERO);
		gmcAccounting.setPreviousActualCumulativeConsumption(BigDecimal.ZERO);
		gmcAccounting.setPreviousMinimumCumulative(BigDecimal.ZERO);
		gmcAccounting.setPreviousHigherOfActualMinimumCumulative(BigDecimal.ZERO);
		gmcAccounting.setPreviousAlreadyBilled(BigDecimal.ZERO);
		gmcAccounting.setPreviousToBeBilled(BigDecimal.ZERO);		
	}
}
