package com.ccnb.excel;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.ccnb.bean.CCNBSchedule;
import com.ccnb.util.PathUtil;
import com.monitorjbl.xlsx.StreamingReader;

public class ScheduleExcelMigrator {

    public static void main(String[] args) throws Exception
    {        
		//For creating an exception Text File
		long exceptionCount=0;
		File file = new File(PathUtil.baseExceptionFolder + "ScheduleExcelMigrationExceptionLog.txt");
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
		
		File excel = new File(PathUtil.baseExcelFolder + "bill_cyc_sch.xlsx");
    	InputStream is = new FileInputStream(excel);
    	Workbook workbook = StreamingReader.builder()
    	        .rowCacheSize(100)    
    	        .bufferSize(4096)     
    	        .open(is);    	  
    	System.out.println("Excel File opened successfully!!");
    	
    	
    	Sheet sheet = workbook.getSheetAt(0);
    	
    	SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    	
    	Session session = null;
    	
    	BigInteger big = BigInteger.ZERO;
    		
    	session = sessionFactory.openSession();
		long startTime = System.currentTimeMillis();
		
		SimpleDateFormat ccnbDateFormat = new SimpleDateFormat("dd-MMM-yy");
		SimpleDateFormat billMonthFormat = new SimpleDateFormat("MMM-yyyy");

    	for(Row r: sheet)
    	{
        	CCNBSchedule ccnbSchedule = new CCNBSchedule();
        	session.clear();
    		    		
    		if(r.getRowNum()==0)
    			continue;    			
    		
    			System.out.println();

    			for(Cell c: r)
        		{
    				String cellValue = (c.getStringCellValue()==null)?"":c.getStringCellValue().trim();

    				//For setting values of all columns of the current row into bean object
        			switch(c.getColumnIndex())
        			{
	        			case 0: ccnbSchedule.setGroupNo(cellValue);break;
	        			
	        			case 1: ccnbSchedule.setStartReadingDate((cellValue.isEmpty()?null:ccnbDateFormat.parse(cellValue)));break;
	        			
	        			case 3: ccnbSchedule.setCashUpto((cellValue.isEmpty()?null:ccnbDateFormat.parse(cellValue)));break;
	        			
	        			case 4: ccnbSchedule.setDueDate((cellValue.isEmpty()?null:ccnbDateFormat.parse(cellValue)));break;
	        				        				        			
	        			case 7: ccnbSchedule.setBillMonth((cellValue.isEmpty()?null:billMonthFormat.format(ccnbDateFormat.parse(cellValue)).toUpperCase()));break;

	        			case 10: ccnbSchedule.setStatus(cellValue);break;
	        			
	        			case 11: ccnbSchedule.setEndReadingDate((cellValue.isEmpty()?null:ccnbDateFormat.parse(cellValue)));break;
	        			
	        			case 12: ccnbSchedule.setChequeDueDate((cellValue.isEmpty()?null:ccnbDateFormat.parse(cellValue)));break;
        			}        			
        			System.out.print(c.getStringCellValue()+" ");
        		}  
        		System.out.println();
        		//Saving the created bean Object
        		try
        		{
        			ccnbSchedule.setMigrated(false);
        			session.beginTransaction();
	        		session.flush();
	    			session.save(ccnbSchedule);
	    			session.getTransaction().commit();
	    			big = big.add(BigInteger.ONE);	    	
        		}
        		catch(Exception e)
        		{
    				++exceptionCount;				
    				writer.println();
    				writer.println("***********EXCEPTION NUMBER " + exceptionCount + "***********" + "Occured on: " + new Date());
    				writer.println("***********GROUP NUMBER: " + ccnbSchedule.getGroupNo() + "***********");
    				writer.println("Root cause : ");
    				e.printStackTrace(writer);				
        			e.printStackTrace();
        			session.getTransaction().rollback();
        			continue;
        		}
        		        		    		
    	}    	
		session.close();
		
		long endTime = System.currentTimeMillis();
		long seconds = (endTime - startTime)/1000;
		long minutes = seconds/60;
		seconds -= minutes*60;

		System.out.println();
    	System.out.println();
    	System.out.println("MIGRATION OF CCNB_SCHEDULE SUCCESSFULLY DONE!!!!");
    	System.out.println();
    	System.out.println(big.toString() + " ROWS SUCCESSFULLY INSERTED INTO THE DATABASE!!!!");    	    	
		System.out.println(exceptionCount + " EXCEPTIONS CAUGHT !! PLEASE REFER EXCEPTION LOG FOR MORE DETAILS!!");		
		System.out.println("Time Elapsed: " + minutes + " Minutes " + seconds + " Seconds");	
		session.close();
		sessionFactory.close();
		is.close();
		writer.close();
		workbook.close();		
    }        
}
