package com.ccnb.migration;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.lang3.tuple.Pair;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.ccnb.bean.CCNBNSCStagingMigration;
import com.ccnb.util.PathUtil;
import com.google.common.collect.Lists;

public class PDCBillGenerator {
	private static final int noOfPartitions = 10;
	
	public static void main(String[] args) throws Exception {
		// For creating a exception Text File
		long exceptionCount = 0, recordCount = 0;
		File file = new File(PathUtil.baseExceptionFolder + "PDCBillGeneratorExceptionLog.txt");
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

		Query<CCNBNSCStagingMigration> pdcQuery = session.createQuery("from CCNBNSCStagingMigration where status='D'", CCNBNSCStagingMigration.class);
		List<CCNBNSCStagingMigration> pdcConsumers = pdcQuery.list();

		// get latest bill month from bill table
		String latestBillMonth = getLatestBillMonth(session);
		System.out.println("GOT BILL MONTH: " + latestBillMonth);
		if (latestBillMonth == null)
			throw new Exception("No bill was found in bill table !!");
		
		//find size of each sublist based on the number of partitions
		int subListSize = (int)Math.ceil(((double)pdcConsumers.size())/noOfPartitions);
		
		//divide the list into multiple sublists depending on the number of partitions
		List<List<CCNBNSCStagingMigration>> pdcConsumersSublists = Lists.partition(pdcConsumers, subListSize);
		
		//create an executor service
		final ExecutorService executorService = Executors.newFixedThreadPool(10);				
		
		//create a callable object for all the tasks and add them to the list
		List<Callable<Pair<Long, Long>>> workerCallables = new ArrayList<>();
		for(List<CCNBNSCStagingMigration> pdcConsumerSublist: pdcConsumersSublists) {
			workerCallables.add(new PDCBillGeneratorWorker(sessionFactory, pdcConsumerSublist, latestBillMonth, writer));					
		}
		
		//submit all the callables to executor service and get the future objects for each
		List<Future<Pair<Long, Long>>> futures = executorService.invokeAll(workerCallables);
		
		//block till all the tasks have finished execution and get the results from each
		int i=0;				
		System.out.println();
		for(Future<Pair<Long, Long>> future: futures) {
			Pair<Long, Long> threadSummary = future.get();
			System.out.print("Thread " + ++i + "=> ");
			System.out.print("Records inserted: " + threadSummary.getLeft() + ", ");
			System.out.println("Exceptions caught: " + threadSummary.getRight());
			recordCount += threadSummary.getLeft();
			exceptionCount += threadSummary.getRight();
		}
		
		//shutdown the executor service
		executorService.shutdown();
		
		long endTime = System.currentTimeMillis();
		long seconds = (endTime - startTime) / 1000;
		long minutes = seconds / 60;
		seconds -= minutes * 60;

		System.out.println("\nPDC BILLS SUCCESSFULLY GENERATED!!");
		System.out.println(recordCount + " BILLS CREATED !!");
		System.out.println(exceptionCount + " EXCEPTIONS CAUGHT !! PLEASE REFER EXCEPTION LOG FOR MORE DETAILS!!");
		System.out.println("Time Elapsed: " + minutes + " Minutes " + seconds + " Seconds");

		session.close();
		sessionFactory.close();
		writer.close();
	}
	
	private static String getLatestBillMonth(Session session) {
		String billMonth = null;

		Query<String> billMonthQuery = session.createQuery("select distinct(currentBillMonth) from GMCAccounting", String.class);
		billMonth = billMonthQuery.uniqueResult();
		
		return billMonth;
	}
}
