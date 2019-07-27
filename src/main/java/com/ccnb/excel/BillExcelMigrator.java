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

import com.ccnb.bean.CCNBBill;
import com.ccnb.bean.CCNBNSCStagingMigration;
import com.ccnb.util.PathUtil;
import com.monitorjbl.xlsx.StreamingReader;

public class BillExcelMigrator {
	public static void main(String[] args) throws Exception{
        
		//For creating an exception Text File
		long exceptionCount=0;
		File file = new File(PathUtil.baseExceptionFolder + "BillExcelMigrationExceptionLog.txt");
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
		
		File excel = new File(PathUtil.baseExcelFolder + "bill_3424624.xlsx");
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
		SimpleDateFormat ccnbDateFormat2 = new SimpleDateFormat("yyyy-MM-dd");

    	for(Row r: sheet)
    	{
        	CCNBBill ccnbBill = new CCNBBill();
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
	        			case 0: ccnbBill.setConsumerNo(cellValue);break;
	        			
	        			case 1: ccnbBill.setLocationCode(cellValue);break;
	        			
	        			case 2: ccnbBill.setGroupNo(cellValue);break;
	        			
	        			case 3: ccnbBill.setReadingDiaryNo(cellValue);break;
	        			
	        			case 4: ccnbBill.setBillMonth(cellValue);break;
	        			
	        			case 5: ccnbBill.setBillTypeCode(cellValue);break;
	        			
	        			case 6: ccnbBill.setBillDate((cellValue=="0"?null:ccnbDateFormat.parse(cellValue)));break;
	        			
	        			case 7: ccnbBill.setDueDate((cellValue=="0"?null:ccnbDateFormat.parse(cellValue)));break;
	        			
	        			case 8: ccnbBill.setChequeDueDate((cellValue=="0"?null:ccnbDateFormat.parse(cellValue)));break;
	        			
	        			case 9: ccnbBill.setCurrentReadDate((cellValue=="0"?null:ccnbDateFormat.parse(cellValue)));break;
	        			
	        			case 10: ccnbBill.setCurrentRead(cellValue);break;
	        			
	        			case 11: ccnbBill.setPreviousRead(cellValue);break;
	        			
	        			case 12: ccnbBill.setDifference(cellValue);break;
	        			
	        			case 13: ccnbBill.setMf(cellValue);break;
	        			
	        			case 14: ccnbBill.setMeteredUnit(cellValue);break;
	        			
	        			case 15: ccnbBill.setAssessment(cellValue);break;
	        			
	        			case 16: ccnbBill.setTotalUnit(cellValue);break; 
	        				
	        			case 17: ccnbBill.setGmcUnit(cellValue);break;
	        				
	        			case 18: ccnbBill.setBilledUnit(cellValue);break;
	        			
	        			case 19: ccnbBill.setBilledMD(cellValue);break;
	        			
	        			case 20: ccnbBill.setBilledPF(cellValue);break;
	        			
	        			case 21: ccnbBill.setLoadFactor(cellValue);break;
	        			
	        			case 22: ccnbBill.setFixedCharge(cellValue);break;
	        			
	        			case 23: ccnbBill.setAdditionalFixedCharges1(cellValue);break;
	        			
	        			case 24: ccnbBill.setAdditionalFixedCharges2(cellValue);break;
	        			
	        			case 25: ccnbBill.setXrayFixedCharge(cellValue);break;
	        			
	        			case 26: ccnbBill.setEnergyCharge(cellValue);break;
	        			
	        			case 27: ccnbBill.setFcaCharge(cellValue);break;
	        			
	        			case 28: ccnbBill.setPristineElectricityDuty(cellValue);break;
	        			
	        			case 29: ccnbBill.setElectricityDuty(cellValue);break;
	        			
	        			case 30: ccnbBill.setMeterRent(cellValue);break;
	        			
	        			case 31: ccnbBill.setPfCharge(cellValue);break;
	        			
	        			case 32: ccnbBill.setWeldingTransformerSurcharge(cellValue);break;
	        			
	        			case 33: ccnbBill.setLoadFactorIncentive(cellValue);break;
	        			
	        			case 34: ccnbBill.setSdInterest(cellValue);break;
	        			
	        			case 35: ccnbBill.setCcbAdjustment(cellValue);break;
	        			
	        			case 36: ccnbBill.setLockCredit(cellValue);break;
	        			
	        			case 37: ccnbBill.setOtherAdjustment(cellValue);break;
	        			
	        			case 38: ccnbBill.setEmployeeRebate(cellValue);break;
	        			
	        			case 39: ccnbBill.setOnlinePaymentRebate(cellValue);break;
	        			
	        			case 40: ccnbBill.setPrepaidMeterRebate(cellValue);break;
	        			
	        			case 41: ccnbBill.setPromptPaymentIncentive(cellValue);break;
	        			
	        			case 42: ccnbBill.setAdvancePaymentIncentive(cellValue);break;
	        			
	        			case 43: ccnbBill.setDemandSideIncentive(cellValue);break;
	        			
	        			case 44: ccnbBill.setSubsidy(cellValue);break;
	        			
	        			case 45: ccnbBill.setPristineCurrentBill(cellValue);break;
	        			
	        			case 46: ccnbBill.setCurrentBill(cellValue);break;
	        			
	        			case 47: ccnbBill.setArrear(cellValue);break;
	        			
	        			case 48: ccnbBill.setCumulativeSurcharge(cellValue);break;
	        			
	        			case 49: ccnbBill.setSurchargeDemanded(cellValue);break;
	        			
	        			case 50: ccnbBill.setPristineNetBill(cellValue);break;
	        			
	        			case 51: ccnbBill.setNetBill(cellValue);break;
	        			
	        			case 52: ccnbBill.setCurrentBillSurcharge(cellValue);break;
	        			
	        			case 53: ccnbBill.setAsdBilled(cellValue);break;
	        			
	        			case 54: ccnbBill.setAsdArrear(cellValue);break;
	        			
	        			case 55: ccnbBill.setAsdInstallment(cellValue);break;
        			}        			
        			System.out.print(cellValue+" ");
        		}  
        		
        		//Saving the created bean Object
        		try
        		{
        			if(ccnbBill.getBilledMD()==null)
        				ccnbBill.setBilledMD("0");
        			ccnbBill.setMigrated(false);
        			session.beginTransaction();
	        		session.flush();
	    			session.save(ccnbBill);
	    			session.getTransaction().commit();
	    			big = big.add(BigInteger.ONE);	    	
        		}
        		catch(Exception e)
        		{
    				++exceptionCount;				
    				writer.println();
    				writer.println("***********EXCEPTION NUMBER " + exceptionCount + "***********" + "Occured on: " + new Date());
    				writer.println("***********LOCATION CODE: " + ccnbBill.getLocationCode() + "***" + "CONSUMER NUMBER: " + ccnbBill.getConsumerNo() + "***********");
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
    	System.out.println("MIGRATION OF BILL_EXCEL SUCCESSFULLY DONE!!!!");
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
