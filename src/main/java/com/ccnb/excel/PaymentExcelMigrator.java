package com.ccnb.excel;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.ccnb.bean.CCNBPayment;
import com.ccnb.util.PathUtil;
import com.monitorjbl.xlsx.StreamingReader;

public class PaymentExcelMigrator {
	public static void main(String[] args) throws Exception{
        
		final String paymentDirectoryName = "PAYMENT";
		SimpleDateFormat ccnbDateFormat = new SimpleDateFormat("dd-MMM-yy");

		//For creating an exception Text File
		File file = new File(PathUtil.baseExceptionFolder + "PaymentExcelMigrationExceptionLog.txt");
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
		
		//define the path of directory containing all the excel files to process
		final String paymentDirectoryPath = PathUtil.baseExcelFolder + paymentDirectoryName;
		File paymentDirectory = new File(paymentDirectoryPath);		

		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();    	
    	Session session = sessionFactory.openSession();
    	
		long startTime = System.currentTimeMillis();
		
		//Map containing summary about each excel file processed
		Map<String, Pair<Long, Long>> excelSummary = new HashMap<>();		

		for(File excel: paymentDirectory.listFiles()) {
			if(excel.isFile()) {
		    	InputStream is = new FileInputStream(excel);
		    	Workbook workbook = StreamingReader.builder()
		    	        .rowCacheSize(100)    
		    	        .bufferSize(4096)     
		    	        .open(is);    	  

		    	long recordCount=0, exceptionCount=0;
		    	System.out.println("Excel File" + excel.getName() +" opened successfully!!");		    	
		    	
		    	Sheet sheet = workbook.getSheetAt(0);    	

		    	for(Row r: sheet)
		    	{
		        	CCNBPayment ccnbPayment = new CCNBPayment();
		        	session.clear();
		    		    		
		    		if(r.getRowNum()==0)
		    			continue;    			
		    		
		    			System.out.println();

		    			for(Cell c: r)
		        		{
		    				String cellValue = (c.getStringCellValue()==null)?"":c.getStringCellValue().trim();
		    				if(cellValue.isEmpty())
		    					cellValue = "0";
		        			//For setting values of all columns of the current row into bean object
		        			switch(c.getColumnIndex())
		        			{
			        			case 0: ccnbPayment.setSource(cellValue);break;
			        			
			        			case 1: ccnbPayment.setOnline(strToBool(cellValue));
			        			
			        			case 2: ccnbPayment.setLocationCode(cellValue);break;
			        			
			        			case 3: ccnbPayment.setConsumerNo(cellValue);break;
			        			
			        			case 4: ccnbPayment.setPunchingDate((cellValue.equals("0"))?null:ccnbDateFormat.parse(cellValue));break;
			        			
			        			case 5: ccnbPayment.setPayDate((cellValue.equals("0"))?null:ccnbDateFormat.parse(cellValue));break;
			        			
			        			case 6: ccnbPayment.setAmount(cellValue);break;
			        			
			        			case 7: ccnbPayment.setPayMode(cellValue);break;
			        			
			        			case 8: ccnbPayment.setPayWindow(cellValue);break;
			        			
			        			case 9: ccnbPayment.setCacNo(cellValue);break;
			        			
			        			case 10: ccnbPayment.setDeleted(false);break;
			        			
			        			case 11: ccnbPayment.setPosted(true);break;
			        			
			        			case 12: ccnbPayment.setPostingBillMonth(cellValue);break;
			        			
			        			case 13: ccnbPayment.setPostingDate((cellValue.equals("0"))?null:ccnbDateFormat.parse(cellValue));break;
		        			}        			
		        			System.out.print(cellValue+" ");
		        		}  
		        		
		        		//Saving the created bean Object
		        		try
		        		{
		        			ccnbPayment.setMigrated(false);
		        			session.beginTransaction();
			        		session.flush();
			    			session.save(ccnbPayment);
			    			session.getTransaction().commit();
			    			++recordCount;	    	
		        		}
		        		catch(Exception e)
		        		{
		    				++exceptionCount;				
		    				writer.println();
		    				writer.println("***********EXCEPTION NUMBER " + exceptionCount + "***********" + "FILE NAME: " + excel.getName() + "***********" + "Occured on: " + new Date());
		    				writer.println("***********LOCATION CODE: " + ccnbPayment.getLocationCode() + "***" + "CONSUMER NUMBER: " + ccnbPayment.getConsumerNo() + "***********");
		    				writer.println("Root cause : ");
		    				e.printStackTrace(writer);				
		        			e.printStackTrace();
		        			session.getTransaction().rollback();
		        			continue;
		        		}		        		        		    		
		    	}    					
		    	//add an entry in the summary map for the file
		    	excelSummary.put(excel.getName(), Pair.of(recordCount, exceptionCount));
				is.close();
				workbook.close();		    
			}
		}
		
		session.close();
		
		long endTime = System.currentTimeMillis();
		long seconds = (endTime - startTime)/1000;
		long minutes = seconds/60;
		seconds -= minutes*60;

		System.out.println();
    	System.out.println();
    	System.out.println("MIGRATION OF PAYMENT_EXCEL SUCCESSFULLY DONE!!!!");
    	System.out.println();
    	System.out.println("SUMMARY:\n");
    	//calculate total records inserted and the total no. of exceptions
    	long totalRecordsInserted=0, totalExceptions=0;    	
    	for(Map.Entry<String, Pair<Long, Long>> fileSummary: excelSummary.entrySet()) {
    		System.out.println("File Name: " + fileSummary.getKey() + ", " + 
    				"Records successfully inserted: " + fileSummary.getValue().getLeft() + ", " + 
    				"Excpetions: " + fileSummary.getValue().getRight());
    		totalRecordsInserted += fileSummary.getValue().getLeft();
    		totalExceptions += fileSummary.getValue().getRight();
    	}    	
    	System.out.println("\n Total Records inserted: " + totalRecordsInserted);
    	System.out.println("Total Exceptions caught: " + totalExceptions);    	

		System.out.println("Time Elapsed: " + minutes + " Minutes " + seconds + " Seconds");	
		session.close();
		sessionFactory.close();
		writer.close();
	}

	private static boolean strToBool(String str) {
    	if("TRUE".equals(str))
    		return true;
    	else
    		return false;
    }
}
