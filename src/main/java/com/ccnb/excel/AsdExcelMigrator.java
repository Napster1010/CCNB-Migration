package com.ccnb.excel;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
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

import com.ccnb.bean.CCNBAsd;
import com.ccnb.bean.CCNBNSCStagingMigration;
import com.ccnb.util.PathUtil;
import com.monitorjbl.xlsx.StreamingReader;

import javassist.bytecode.analysis.Util;

public class AsdExcelMigrator {
	public static void main(String[] args) throws Exception {
        
		//For creating an exception Text File
		long exceptionCount=0;
		File file = new File(PathUtil.baseExceptionFolder + "AsdExcelMigrationExceptionLog.txt");
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
		
		File excel = new File(PathUtil.baseExcelFolder + "ASD_DTL.xlsx");
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
		
    	for(Row r: sheet)
    	{
        	CCNBAsd ccnbAsd = new CCNBAsd();
        	session.clear();
    		    		
    		if(r.getRowNum()==0)
    			continue;    			
    		
    			System.out.println();

    			for(Cell c: r)
        		{
    				String cellValue = c.getStringCellValue().trim();
        			//For setting values of all columns of the current row into bean object
        			switch(c.getColumnIndex())
        			{
	        			case 0: ccnbAsd.setConsumerNo(c.getStringCellValue().trim());break;
	        			
	        			case 1: ccnbAsd.setAverageConsumption(c.getStringCellValue().trim());break;
	        			
	        			case 2: ccnbAsd.setAverageBill(c.getStringCellValue());break;
	        			
	        			case 3:
	        					ccnbAsd.setDefaulter(strToBool(c.getStringCellValue().trim()));
	        				break;
	        			
	        			case 4: ccnbAsd.setBillMonth(c.getStringCellValue().trim());break;
	        			
	        			case 5: ccnbAsd.setRequiredSecurityDeposit(c.getStringCellValue().trim());break;
	        			
	        			case 6: ccnbAsd.setExistingSecurityDeposit(c.getStringCellValue().trim());break;
	        			
	        			case 7: ccnbAsd.setSecurityDepositDemand(c.getStringCellValue().trim());break;
	        			
	        			case 8: ccnbAsd.setInstallment1(c.getStringCellValue().trim());break;
	        			
	        			case 9: ccnbAsd.setInstallment2(c.getStringCellValue().trim());break;
	        			
	        			case 10: ccnbAsd.setInstallment3(c.getStringCellValue().trim());break;
	        			
	        			case 11: ccnbAsd.setPeriod(c.getStringCellValue().trim());break;
        			}        			
        			System.out.print(cellValue+" ");
        		}  
        		System.out.println();
        		//Saving the created bean Object
        		try
        		{
        			session.beginTransaction();
	        		session.flush();
	    			session.save(ccnbAsd);
	    			session.getTransaction().commit();
	    			big = big.add(BigInteger.ONE);	    	
        		}
        		catch(Exception e)
        		{
    				++exceptionCount;				
    				writer.println();
    				writer.println("***********EXCEPTION NUMBER " + exceptionCount + "***********" + "Occured on: " + new Date());
    				writer.println("***********CONSUMER NUMBER: " + ccnbAsd.getConsumerNo() + "***********");
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
    	System.out.println("MIGRATION OF CCNB_ASD SUCCESSFULLY DONE!!!!");
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

	private static boolean strToBool(String str) {
    	if("TRUE".equals(str))
    		return true;
    	else
    		return false;
    }
}
