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

import com.ccnb.bean.SecurityDepositInterest;
import com.ccnb.util.PathUtil;

public class SecurityDepositInterestMigrator {
	public static void main(String[] args) throws Exception {

		//For creating a exception Text File
		long exceptionCount=0, recordCount=0;
		File file = new File(PathUtil.baseExceptionFolder + "SecurityDepositInterestMigrationExceptionLog.txt");
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

		Query<String> consumerNoQuery = session.createQuery("select consumerNo from ConsumerNoMaster", String.class);
		List<String> consumerNos = consumerNoQuery.list();
		
		Query<String> billMonthQuery = session.createQuery("select distinct(currentBillMonth) from GMCAccounting", String.class);
		String billMonth = billMonthQuery.uniqueResult();
		
		for(String consumerNo: consumerNos) {
			session.clear();
			session.beginTransaction();
			session.flush();
			
			//get bill issue date
			Date billIssueDate = getBillIssueDate(session, consumerNo, billMonth);
			if(billIssueDate==null)
				billIssueDate = new Date();			
			
			SecurityDepositInterest securityDepositInterest = new SecurityDepositInterest();
			securityDepositInterest.setConsumerNo(consumerNo);
			securityDepositInterest.setBillMonth(billMonth);
			securityDepositInterest.setCalculationStartDate(billIssueDate);
			securityDepositInterest.setCalculationEndDate(billIssueDate);
			securityDepositInterest.setCreatedBy("CCNB_MIG");
			securityDepositInterest.setCreatedOn(new Date());
			securityDepositInterest.setUpdatedBy("CCNB_MIG");
			securityDepositInterest.setUpdatedOn(new Date());
			securityDepositInterest.setAmount(BigDecimal.ZERO);
			
			session.save(securityDepositInterest);
			session.getTransaction().commit();
			++recordCount;
		}
		
		long endTime = System.currentTimeMillis();
		long seconds = (endTime - startTime)/1000;
		long minutes = seconds/60;
		seconds -= minutes*60;
		
		System.out.println("MIGRATION FROM SECURITY_DEPOSIT_INTEREST SUCCESSFULLY DONE !!");
		System.out.println(consumerNos.size() + " ROWS WERE RETRIEVED");
		System.out.println(recordCount + " ROWS WERE SUCCESSFULLY MIGRATED");
		System.out.println(exceptionCount + " EXCEPTIONS CAUGHT !! PLEASE REFER EXCEPTION LOG FOR MORE DETAILS!!");
		System.out.println("Time Elapsed: " + minutes + " Minutes " + seconds + " Seconds");
		
		session.close();
		sessionFactory.close();
		writer.close();
	}
	
	private static Date getBillIssueDate(Session session, String consumerNo, String billMonth) {
		Query<Date> billIssueDateQuery = session.createQuery("select billDate from Bill where consumerNo=:consumerNo and billMonth=:billMonth and deleted=false", Date.class);
		billIssueDateQuery.setParameter("consumerNo", consumerNo);
		billIssueDateQuery.setParameter("billMonth", billMonth);
		Date billDate = billIssueDateQuery.uniqueResult();
		return billDate;
	}
}