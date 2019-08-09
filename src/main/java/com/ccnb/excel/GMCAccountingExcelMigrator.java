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
import java.util.StringTokenizer;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.ccnb.bean.CCNBGMCAccounting;
import com.ccnb.bean.CCNBRead;
import com.ccnb.util.PathUtil;
import com.monitorjbl.xlsx.StreamingReader;

public class GMCAccountingExcelMigrator {

    public static void main(String[] args) throws Exception
    {        
		//For creating an exception Text File
		long exceptionCount=0;
		File file = new File(PathUtil.baseExceptionFolder + "GmcExcelMigrationExceptionLog.txt");
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
		
		File excel = new File(PathUtil.baseExcelFolder + "GMC_3424624.xlsx");
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
        	CCNBGMCAccounting ccnbGmcAccounting = new CCNBGMCAccounting();
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
	        			case 0: ccnbGmcAccounting.setBillMonth(cellValue);
	        			
	        			case 1: ccnbGmcAccounting.setConsumerNo(cellValue);break;
	        			
	        			case 2: ccnbGmcAccounting.setTariffCode(cellValue);break;
	        			
	        			case 3: ccnbGmcAccounting.setPremiseType(cellValue);break;
	        			
	        			case 4: ccnbGmcAccounting.setSanctionedLoad(cellValue);break;
	        			
	        			case 5: ccnbGmcAccounting.setSanctionedLoadUnit(cellValue);break;
	        			
	        			case 6: 
	        				if("0".equals(cellValue)) {
	        					ccnbGmcAccounting.setContractDemand("0");
	        					ccnbGmcAccounting.setContractDemandUnit("KW");
	        				}else {
	        					StringTokenizer tokenizer = new StringTokenizer(cellValue, " ");
	        					ccnbGmcAccounting.setContractDemand(tokenizer.nextToken());
	        					ccnbGmcAccounting.setContractDemandUnit(tokenizer.nextToken());
	        				}
	        			
	        				break;
	        			
	        			case 7: ccnbGmcAccounting.setCrntConsumption(cellValue);break;
	        			
	        			case 8: ccnbGmcAccounting.setSumMeteredUnit(cellValue);break;
	        			
	        			case 9: ccnbGmcAccounting.setSumAssessment(cellValue);break;
	        			
	        			case 10: ccnbGmcAccounting.setSumTotalUnit(cellValue);break;
	        			
	        			case 11: ccnbGmcAccounting.setSumBilledUnit(cellValue);break;
	        			
	        			case 12: ccnbGmcAccounting.setSumGmcUnit(cellValue);break;
	        			
	        			case 13: ccnbGmcAccounting.setAlreadyBilled(cellValue);break;
        			}        			
        			System.out.print(c.getStringCellValue()+" ");
        		}  
        		System.out.println();
        		//Saving the created bean Object
        		try
        		{
        			ccnbGmcAccounting.setMigrated(false);
        			session.beginTransaction();
	        		session.flush();
	    			session.save(ccnbGmcAccounting);
	    			session.getTransaction().commit();
	    			big = big.add(BigInteger.ONE);	    	
        		}
        		catch(Exception e)
        		{
    				++exceptionCount;				
    				writer.println();
    				writer.println("***********EXCEPTION NUMBER " + exceptionCount + "***********" + "Occured on: " + new Date());
    				writer.println("***********CONSUMER NUMBER: " + ccnbGmcAccounting.getConsumerNo() + "***********");
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
    	System.out.println("MIGRATION OF CCNB_GMC_ACCOUNTING SUCCESSFULLY DONE!!!!");
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
