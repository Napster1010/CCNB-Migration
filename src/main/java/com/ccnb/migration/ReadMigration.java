package com.ccnb.migration;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.ccnb.util.PathUtil;

public class ReadMigration {
	
	private static final int noOfThreads = 3;

	public static void main(String[] args) throws Exception {

		//For creating a exception Text File
		long exceptionCount=0, recordCount=0;
		File file = new File(PathUtil.baseExceptionFolder + "ReadMigrationExceptionLog.txt");
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
		
		Query<String> billMonthQuery = session.createQuery("select distinct(billMonth) from CCNBRead");
		List<String> billMonths = billMonthQuery.list();
		
		List<Thread> runningThreads = new ArrayList<>();
		List<ReadMigrationWorker> workerObjects = new ArrayList<>();
		
		int noOfBillMonths = billMonths.size();
		
		//divide the months equally amongst all threads
		int temp = noOfBillMonths, curIndex=0, leftThreads=noOfThreads;		
		while(temp>0) {
			int div = temp/leftThreads;
			if(div>0) {
				
				ReadMigrationWorker worker = new ReadMigrationWorker(sessionFactory, curIndex, curIndex+div-1, billMonths, writer);
				Thread workerThread = new Thread(worker);
				workerThread.start();
				
				runningThreads.add(workerThread);
				workerObjects.add(worker);
				
				temp -= div;
				curIndex += div;
			}
			--leftThreads;
		}
		
		for(Thread th: runningThreads) {
			th.join();
		}
		
		for(ReadMigrationWorker worker: workerObjects) {
			recordCount += worker.getRecordCount();
			exceptionCount += worker.getExceptionCount();
		}
		
		
		long endTime = System.currentTimeMillis();
		long totTime = endTime - startTime;
		long seconds = (endTime - startTime)/1000;
		long minutes = seconds/60;
		seconds -= minutes*60;
		
		System.out.println("MIGRATION FROM CCNB_READ SUCCESSFULLY DONE !!");
		//System.out.println(unmigratedRecords.size() + " ROWS WERE RETRIEVED");
		System.out.println(recordCount + " ROWS SUCCESSFULLY MIGRATED !!");		
		System.out.println(exceptionCount + " EXCEPTIONS CAUGHT !! PLEASE REFER EXCEPTION LOG FOR MORE DETAILS!!");		
		System.out.println("Time Elapsed: " + minutes + " Minutes " + seconds + " Seconds");				
		
		session.close();
		sessionFactory.close();
		writer.close();		
	}		
}
