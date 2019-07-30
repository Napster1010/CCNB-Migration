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

import com.ccnb.bean.CCNBNSCStagingMigration;
import com.ccnb.bean.CCNBRead;
import com.ccnb.util.PathUtil;
import com.monitorjbl.xlsx.StreamingReader;

public class ReadExcelMigrator {

    public static void main(String[] args) throws Exception
    {        
		//For creating an exception Text File
		long exceptionCount=0;
		File file = new File(PathUtil.baseExceptionFolder + "ReadExcelMigrationExceptionLog.txt");
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
		
		File excel = new File(PathUtil.baseExcelFolder + "3424624_READ_1507.xlsx");
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
		SimpleDateFormat ccnbDateFormat1 = new SimpleDateFormat("dd-MM-yyyy");

    	for(Row r: sheet)
    	{
        	CCNBRead ccnbRead = new CCNBRead();
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
	        			case 0: ccnbRead.setBillMonth(cellValue);break;
	        			
	        			case 1: ccnbRead.setGroupNo(cellValue);break;
	        			
	        			case 2: ccnbRead.setReadingDiaryNo(cellValue);break;
	        			
	        			case 3: ccnbRead.setConsumerNo(cellValue);break;
	        			
	        			case 4: ccnbRead.setMeterIdentifier(cellValue);break;
	        			
	        			case 5: ccnbRead.setReadingDate((cellValue.equals("0")?null:ccnbDateFormat.parse(cellValue)));break;
	        			
	        			case 6: ccnbRead.setReadingType(cellValue);break;
	        			
	        			case 7: ccnbRead.setMeterStatus(cellValue);break;
	        			
	        			case 8: ccnbRead.setReplacementFlag(strToBool(cellValue));break;
	        			
	        			case 9: ccnbRead.setSource(cellValue);break;
	        			
	        			case 10: ccnbRead.setReading(cellValue);break;
	        			
	        			case 11: ccnbRead.setDifference(cellValue);break;
	        			
	        			case 12: ccnbRead.setMf(cellValue);break;
	        			
	        			case 13: ccnbRead.setConsumption(cellValue);break;
	        			
	        			case 14: ccnbRead.setAssessment(cellValue);break;
	        			
	        			case 15: ccnbRead.setPropagatedAssessment(cellValue);break;
	        			
	        			case 16: ccnbRead.setTotalConsumption(cellValue);break;
	        				
	        			case 17: ccnbRead.setMeterMd(cellValue);break;
	        				
	        			case 18: ccnbRead.setMultipliedMd(cellValue);break;
	        			
	        			case 19: ccnbRead.setBillingDemand(cellValue);break;
	        			
	        			case 20: ccnbRead.setMeterPf(cellValue);break;
	        			
	        			case 21: ccnbRead.setBillingPf(cellValue);break;
	        			
        			}        			
        			System.out.print(c.getStringCellValue()+" ");
        		}  
        		System.out.println();
        		//Saving the created bean Object
        		try
        		{
        			ccnbRead.setMigrated(false);
        			session.beginTransaction();
	        		session.flush();
	    			session.save(ccnbRead);
	    			session.getTransaction().commit();
	    			big = big.add(BigInteger.ONE);	    	
        		}
        		catch(Exception e)
        		{
    				++exceptionCount;				
    				writer.println();
    				writer.println("***********EXCEPTION NUMBER " + exceptionCount + "***********" + "Occured on: " + new Date());
    				writer.println("***********CONSUMER NUMBER: " + ccnbRead.getConsumerNo() + "***********");
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
    	System.out.println("MIGRATION OF CCNB_READ SUCCESSFULLY DONE!!!!");
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
