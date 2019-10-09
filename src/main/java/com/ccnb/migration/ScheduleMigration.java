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

import com.ccnb.bean.CCNBSchedule;
import com.ccnb.bean.Schedule;
import com.ccnb.util.PathUtil;

public class ScheduleMigration {
	public static void main(String[] args) throws Exception {
		//For creating a exception Text File
		long exceptionCount=0, recordCount=0;
		File file = new File(PathUtil.baseExceptionFolder + "ScheduleMigrationExceptionLog.txt");
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
		
		Query<CCNBSchedule> ccnbScheduleQuery = session.createQuery("from CCNBSchedule where migrated=false and status='B'");
		List<CCNBSchedule> unmigratedRecords = ccnbScheduleQuery.list();
		
		Query<String> consumerNoMasterQuery;
		Query<Integer> migrationStatus;
		
		for(CCNBSchedule currentRecord: unmigratedRecords) {
			try {
				session.clear();
				session.beginTransaction();
				session.flush();
				
				//retrieve consumer no
				consumerNoMasterQuery = session.createQuery("select groupNo from ConsumerNoMaster where oldGroupNo = ?");
				consumerNoMasterQuery.setParameter(0, currentRecord.getGroupNo());
				consumerNoMasterQuery.setMaxResults(1);
				
				String newGroupNo = consumerNoMasterQuery.uniqueResult();
				if(newGroupNo==null)
					throw new Exception("NGB group number not found!!");
				
				//Create Schedule row
				Schedule schedule = new Schedule();
				schedule.setGroupNo(newGroupNo);
				schedule.setBillMonth(currentRecord.getBillMonth());
				schedule.setBillStatus("COMPLETED");
				schedule.setSubmitted("Y");
				schedule.setR15Status("FROZEN");
				schedule.setDueDate(currentRecord.getDueDate());
				schedule.setBillDate(currentRecord.getCashUpto());
				schedule.setStartReadingDate(currentRecord.getStartReadingDate());
				schedule.setCashUpto(currentRecord.getCashUpto());
				schedule.setCreatedBy("CCNB_MIG");
				schedule.setCreatedOn(new Date());
				schedule.setEndReadingDate(currentRecord.getEndReadingDate());
				schedule.setChequeDueDate(currentRecord.getChequeDueDate());

				session.save(schedule);
				
				//Update the migrated flag				
				migrationStatus = session.createQuery("update CCNBSchedule set migrated=true where id=?");
				migrationStatus.setParameter(0, currentRecord.getId());
				migrationStatus.executeUpdate();
				session.getTransaction().commit();

				++recordCount;
			}catch(Exception e) {
				++exceptionCount;				
				writer.println();
				writer.println("***********EXCEPTION NUMBER " + exceptionCount + "***********" + "Occured on: " + new Date());
				writer.println("***********CCNB GROUP NUMBER: " + currentRecord.getGroupNo() + "***********");
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
		
		System.out.println("MIGRATION FROM CCNB_SCHEDULE SUCCESSFULLY DONE !!");
		System.out.println(unmigratedRecords.size() + " ROWS WERE RETRIEVED");
		System.out.println(recordCount + " ROWS SUCCESSFULLY MIGRATED !!");		
		System.out.println(exceptionCount + " EXCEPTIONS CAUGHT !! PLEASE REFER EXCEPTION LOG FOR MORE DETAILS!!");		
		System.out.println("Time Elapsed: " + minutes + " Minutes " + seconds + " Seconds");				
		
		session.close();
		sessionFactory.close();
		writer.close();		
	}
}
