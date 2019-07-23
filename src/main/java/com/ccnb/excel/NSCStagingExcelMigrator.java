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
import org.hibernate.query.Query;

import com.ccnb.bean.CCNBNSCStagingMigration;
import com.ccnb.util.PathUtil;
import com.monitorjbl.xlsx.StreamingReader;

public class NSCStagingExcelMigrator {

    public static void main(String[] args) throws Exception
    {        
		//For creating an exception Text File
		long exceptionCount=0;
		File file = new File(PathUtil.baseExceptionFolder + "NSCStagingExcelMigrationExceptionLog.txt");
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
		
		File excel = new File(PathUtil.baseExcelFolder + "nsc_3424624.xlsx");
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
        	CCNBNSCStagingMigration ccnbNscStagingMigration = new CCNBNSCStagingMigration();
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
	        			case 0: ccnbNscStagingMigration.setConnection_date((cellValue=="")?null:ccnbDateFormat.parse(cellValue));break;
	        			
	        			case 1: ccnbNscStagingMigration.setConsumer_name(cellValue);break;
	        			
	        			case 2: ccnbNscStagingMigration.setConsumer_name_h(cellValue);break;
	        			
	        			case 3: ccnbNscStagingMigration.setRelative_name(cellValue);break;
	        			
	        			case 4: ccnbNscStagingMigration.setRelation(cellValue);break;
	        			
	        			case 5: ccnbNscStagingMigration.setBpl_no(cellValue);break;
	        			
	        			case 6: ccnbNscStagingMigration.setCategory(cellValue);break;
	        			
	        			case 7: ccnbNscStagingMigration.setEmployee_company(cellValue);break;
	        			
	        			case 8: ccnbNscStagingMigration.setEmployee_no(cellValue);break;
	        			
	        			case 9: ccnbNscStagingMigration.setAddress_1(cellValue);break;
	        			
	        			case 10: ccnbNscStagingMigration.setAddress_2(cellValue);break;
	        			
	        			case 11: ccnbNscStagingMigration.setAddress_3(cellValue);break;
	        			
	        			case 12: ccnbNscStagingMigration.setAddress_1_h(cellValue);break;
	        			
	        			case 13: ccnbNscStagingMigration.setAddress_2_h(cellValue);break;
	        			
	        			case 14: ccnbNscStagingMigration.setAddress_3_h(cellValue);break;
	        			
	        			case 15:
	        				if(cellValue.length()==10)
	        					ccnbNscStagingMigration.setPrimary_mobile_no(cellValue);
	        				break;
	        			
	        			case 16: 
	        				if(cellValue.length()==10)
	        					ccnbNscStagingMigration.setAlternate_mobile_no(cellValue);	        					
	        				break;
	        				
	        			case 17:
	        				if(cellValue.length()==12)
	        					ccnbNscStagingMigration.setAadhaar_no(cellValue);	        			
	        				break;	        			
	        				
	        			case 18: ccnbNscStagingMigration.setPan(cellValue);break;
	        			
	        			case 19: ccnbNscStagingMigration.setBank_account_no(cellValue);break;
	        			
	        			case 20: ccnbNscStagingMigration.setBank_account_holder_name(cellValue);break;
	        			
	        			case 21: ccnbNscStagingMigration.setBank_name(cellValue);break;
	        			
	        			case 22: ccnbNscStagingMigration.setIfsc(cellValue);break;
	        			
	        			case 23: ccnbNscStagingMigration.setEmail_address(cellValue);break;
	        			
	        			case 24: ccnbNscStagingMigration.setTariff_category(cellValue);break;
	        			
	        			case 25: ccnbNscStagingMigration.setConnection_type(cellValue);break;
	        			
	        			case 26: ccnbNscStagingMigration.setMetering_status(cellValue);break;
	        			
	        			case 27: ccnbNscStagingMigration.setPremise_type(cellValue);break;
	        			
	        			case 28: ccnbNscStagingMigration.setSanctioned_load((cellValue=="")?BigDecimal.ZERO:new BigDecimal(cellValue));break;
	        			
	        			case 29: ccnbNscStagingMigration.setSanctioned_load_unit(cellValue);break;
	        			
	        			case 30: ccnbNscStagingMigration.setContract_demand((cellValue=="")?BigDecimal.ZERO:new BigDecimal(cellValue));break;
	        			
	        			case 31: ccnbNscStagingMigration.setContract_demand_unit(cellValue);break;
	        			
	        			case 32: ccnbNscStagingMigration.setSeason_start_date((cellValue=="")?null:ccnbDateFormat2.parse(cellValue));break;
	        			
	        			case 33: ccnbNscStagingMigration.setSeason_end_date((cellValue=="")?null:ccnbDateFormat2.parse(cellValue));break;
	        			
	        			case 34: ccnbNscStagingMigration.setSeason_start_bill_month(cellValue);break;
	        			
	        			case 35: ccnbNscStagingMigration.setSeason_end_bill_month(cellValue);break;
	        			
	        			case 36: ccnbNscStagingMigration.setPurpose_of_installation(cellValue);break;
	        			
	        			case 37: ccnbNscStagingMigration.setTariff_code(cellValue);break;
	        			
	        			case 38: ccnbNscStagingMigration.setSub_category_code((cellValue=="")?0:Long.parseLong(cellValue));break;
	        			
	        			case 39: ccnbNscStagingMigration.setPhase(cellValue);break;
	        			
	        			case 40: ccnbNscStagingMigration.setTc_start_date((cellValue=="")?null:ccnbDateFormat1.parse(cellValue));break;
	        			
	        			case 41: ccnbNscStagingMigration.setTc_end_date((cellValue=="")?null:ccnbDateFormat1.parse(cellValue));break;
	        			
	        			case 42: ccnbNscStagingMigration.setGovernment_type(cellValue);break;
	        			
	        			case 43: ccnbNscStagingMigration.setPlot_size((cellValue=="")?BigDecimal.ZERO:new BigDecimal(cellValue));break;
	        			
	        			case 44: ccnbNscStagingMigration.setPlot_size_unit(cellValue);break;
	        			
	        			case 45: ccnbNscStagingMigration.setLocation_code(cellValue);break;
	        			
	        			case 46: ccnbNscStagingMigration.setXray_load((cellValue=="")?BigDecimal.ZERO:new BigDecimal(cellValue));break;
	        			
	        			case 47: ccnbNscStagingMigration.setNo_of_dental_xray_machine((cellValue=="")?0:Long.parseLong(cellValue));break;
	        			
	        			case 48: ccnbNscStagingMigration.setNo_of_single_phase_xray_machine((cellValue=="")?0:Long.parseLong(cellValue));break;
	        			
	        			case 49: ccnbNscStagingMigration.setNo_of_three_phase_xray_machine((cellValue=="")?0:Long.parseLong(cellValue));break;
	        			
	        			case 50: ccnbNscStagingMigration.setIsi_motor_type(cellValue);break;
	        			
	        			case 51: ccnbNscStagingMigration.setDtr_name(cellValue);break;
	        			
	        			case 52: ccnbNscStagingMigration.setPole_no(cellValue);break;
	        			
	        			case 53: ccnbNscStagingMigration.setPole_latitude(cellValue);break;
	        			
	        			case 54: ccnbNscStagingMigration.setPole_longitude(cellValue);break;
	        			
	        			case 55: ccnbNscStagingMigration.setFeeder_name(cellValue);break;
	        			
	        			case 56: ccnbNscStagingMigration.setPole_distance((cellValue=="")?0:Long.parseLong(cellValue));break;
	        			
	        			case 57: ccnbNscStagingMigration.setArea_status(cellValue);break;
	        			
	        			case 58: ccnbNscStagingMigration.setGroup_no(cellValue);break;
	        			
	        			case 59: ccnbNscStagingMigration.setReading_diary_no(cellValue);break;
	        			
	        			case 60: ccnbNscStagingMigration.setNeighbour_connection_no(cellValue);break;
	        			
	        			case 61: ccnbNscStagingMigration.setSurvey_date((cellValue=="")?null:ccnbDateFormat.parse(cellValue));break;
	        			
	        			case 62: ccnbNscStagingMigration.setMeter_identifier(cellValue);break;
	        			
	        			case 63: ccnbNscStagingMigration.setStart_read((cellValue=="")?BigDecimal.ZERO:new BigDecimal(cellValue));break;
	        			
	        			case 64: ccnbNscStagingMigration.setCtr_identifier(cellValue);break;
	        			
	        			case 65: ccnbNscStagingMigration.setCtr_overall_mf((cellValue=="")?BigDecimal.ZERO:new BigDecimal(cellValue));break;
	        			
	        			case 66: ccnbNscStagingMigration.setMeter_installation_date((cellValue=="")?null:ccnbDateFormat.parse(cellValue));break;
	        			
	        			case 67: ccnbNscStagingMigration.setMeter_installer_name(cellValue);break;
	        			
	        			case 68: ccnbNscStagingMigration.setMeter_installer_designation(cellValue);break;
	        			
	        			case 69: ccnbNscStagingMigration.setModem_no(cellValue);break;
	        			
	        			case 70: ccnbNscStagingMigration.setSim_no(cellValue);break;
	        			
	        			case 71: ccnbNscStagingMigration.setDate_of_registration((cellValue=="")?null:ccnbDateFormat.parse(cellValue));break;
	        			
	        			case 72: ccnbNscStagingMigration.setRegistration_fee_amount((cellValue=="")?BigDecimal.ZERO:new BigDecimal(cellValue));break;
	        			
	        			case 73: ccnbNscStagingMigration.setRegistration_fee_amount_mr_no(cellValue);break;
	        			
	        			case 74: ccnbNscStagingMigration.setSecurity_deposit_amount((cellValue=="")?BigDecimal.ZERO:new BigDecimal(cellValue));break;
	        			
	        			case 75: ccnbNscStagingMigration.setSecurity_deposit_amount_mr_no(cellValue);break;
	        			
	        			case 76: ccnbNscStagingMigration.setPortal_name(cellValue);break;
	        			
	        			case 77: ccnbNscStagingMigration.setPortal_reference_no(cellValue);break;
	        			
	        			case 78: ccnbNscStagingMigration.setCreated_on((cellValue=="")?null:ccnbDateFormat.parse(cellValue));break;
	        			
	        			case 79:
	        				String consNo = cellValue;
        					for(int i=consNo.length();i<10;i++)
        						consNo = "0" + consNo;
	        				ccnbNscStagingMigration.setOld_cons_no(consNo);	        				
	        				break;
	        			
	        			case 80: ccnbNscStagingMigration.setOld_gr_no(cellValue);break;
	        			
	        			case 81: ccnbNscStagingMigration.setOld_rd_no(cellValue);break;
	        			
	        			case 82: ccnbNscStagingMigration.setOld_trf_catg(cellValue);break;
	        			
	        			case 83: ccnbNscStagingMigration.setOld_rev_catg(cellValue);break;
	        			
	        			case 84: ccnbNscStagingMigration.setTotal_outstanding(cellValue);break;
	        			
	        			case 85: ccnbNscStagingMigration.setPrev_arrear(cellValue);break;
	        			
	        			case 86: ccnbNscStagingMigration.setPend_surcharge(cellValue);break;
	        			
	        			case 87: ccnbNscStagingMigration.setCurr_surcharge(cellValue);break;
	        			
	        			case 88: ccnbNscStagingMigration.setColl_surcharge(cellValue);break;
	        			
	        			case 89: ccnbNscStagingMigration.setPfl_bill_amount(cellValue);break;
	        			
	        			case 90: ccnbNscStagingMigration.setMf(cellValue);break;
	        			
	        			case 91: ccnbNscStagingMigration.setNrev_catg(cellValue);break;
	        			
	        			case 92: ccnbNscStagingMigration.setPurpose_of_installation_id((cellValue=="")?0:Long.parseLong(cellValue));break;
	        			
	        			case 93: 
	        				if("YES".equals(cellValue))
	        					ccnbNscStagingMigration.setSd_enhance_cd("Y");
	        				else
	        					ccnbNscStagingMigration.setSd_enhance_cd("N");
	        			
	        			case 94: ccnbNscStagingMigration.setDishnrd_chq_flg(cellValue);break;
	        			
	        			case 95: ccnbNscStagingMigration.setStatus(cellValue);break;
	        			
	        			case 96: 
	        				if(strToBool(cellValue))
	        					ccnbNscStagingMigration.setIs_bpl(true);
	        				else
	        					ccnbNscStagingMigration.setIs_bpl(false);
	        				break;
	        			
	        			case 97: 
	        				if(strToBool(cellValue))
	        					ccnbNscStagingMigration.setIs_employee(true);
	        				else
	        					ccnbNscStagingMigration.setIs_employee(false);
	        				break;

	        			case 98:
	        				if(strToBool(cellValue))
	        					ccnbNscStagingMigration.setIs_seasonal(true);
	        				else
	        					ccnbNscStagingMigration.setIs_seasonal(false);
	        				break;
	        			
	        			case 99:
	        				if(strToBool(cellValue))
	        					ccnbNscStagingMigration.setIs_government(true);
	        				else
	        					ccnbNscStagingMigration.setIs_government(false);
	        				break;
	        			
	        			case 100:
	        				if(strToBool(cellValue))
	        					ccnbNscStagingMigration.setIs_xray(true);
	        				else
	        					ccnbNscStagingMigration.setIs_xray(false);
	        				break;
	        			
	        			case 101:
	        				if(strToBool(cellValue))
	        					ccnbNscStagingMigration.setIs_welding_transformer_surcharge(true);
	        				else
	        					ccnbNscStagingMigration.setIs_welding_transformer_surcharge(false);
	        				break;
	        			
	        			case 102:
	        				if(strToBool(cellValue))
	        					ccnbNscStagingMigration.setIs_capacitor_surcharge(true);
	        				else
	        					ccnbNscStagingMigration.setIs_capacitor_surcharge(false);	        			
	        				break;
	        			
	        			case 103:
	        				if(strToBool(cellValue))
	        					ccnbNscStagingMigration.setIs_demandside(true);
	        				else
	        					ccnbNscStagingMigration.setIs_demandside(false);	        				
	        				break;
	        			
	        			case 104:
	        				if(strToBool(cellValue))
	        					ccnbNscStagingMigration.setIs_beneficiary(true);
	        				else
	        					ccnbNscStagingMigration.setIs_beneficiary(false);	        					        				
	        				break;
	        			
	        			case 105:
	        				if(strToBool(cellValue))
	        					ccnbNscStagingMigration.setHas_ctr(true);
	        				else
	        					ccnbNscStagingMigration.setHas_ctr(false);	        			
	        				break;
	        			
	        			case 106: ccnbNscStagingMigration.setAffiliatedConsumerNo(cellValue);break;
	        			
	        			case 107: ccnbNscStagingMigration.setCreatedBy("CCNB_MIG");break;
	        			
	        			case 108:
	        				if(strToBool(cellValue))
	        					ccnbNscStagingMigration.setAffiliatedConsumer(true);
	        				else
	        					ccnbNscStagingMigration.setAffiliatedConsumer(false);	        			
	        				break;
	        			
	        			case 109:
	        				if(strToBool(cellValue))
	        					ccnbNscStagingMigration.setHas_modem(true);
	        				else
	        					ccnbNscStagingMigration.setHas_modem(false);	        			
	        				break;

	        			case 110:
	        				if(strToBool(cellValue))
	        					ccnbNscStagingMigration.setAffiliated(true);
	        				else
	        					ccnbNscStagingMigration.setAffiliated(false);	        			
	        				break;
	        				
	        			case 111:
	        				if(strToBool(cellValue))
	        					ccnbNscStagingMigration.setSaral(true);
	        				else
	        					ccnbNscStagingMigration.setSaral(false);	        			
	        				break;

	        			case 112:
	        				if(strToBool(cellValue))
	        					ccnbNscStagingMigration.setBillMafi(true);
	        				else
	        					ccnbNscStagingMigration.setBillMafi(false);        			
	        				break;
	        			
	        			case 113: ccnbNscStagingMigration.setShramikNo(cellValue);break;
	        			
	        			case 114: ccnbNscStagingMigration.setPurposeOfInstallationCD(cellValue);break;
	        			
	        			case 115: ccnbNscStagingMigration.setCcnbPurposeOfInstallation(cellValue);break;
	        			
	        			case 116: ccnbNscStagingMigration.setMeterType(cellValue);break;
	        			
	        			case 117: ccnbNscStagingMigration.setMeterCapacity(cellValue);break;
	        			
	        			case 118: ccnbNscStagingMigration.setPdcDate((cellValue=="")?null:ccnbDateFormat.parse(cellValue));break;	        			

	        			case 119: 
	        				if(strToBool(cellValue))
	        					ccnbNscStagingMigration.setPdcSaActive(true);
	        				else
	        					ccnbNscStagingMigration.setPdcSaActive(false);	        				
	        				break;
	        				
	        			case 120: ccnbNscStagingMigration.setMeterCtrRatio(cellValue);break;
	        			
	        			case 121: ccnbNscStagingMigration.setMeterRent(cellValue);break;
	        			
	        			case 122: ccnbNscStagingMigration.setSaType(cellValue);break;
	        			
	        			case 123: ccnbNscStagingMigration.setLastBillMonth(cellValue);break;
	        			
	        			case 124: ccnbNscStagingMigration.setLastBillDate((cellValue=="")?null:ccnbDateFormat.parse(cellValue));break;

	        			case 125: ccnbNscStagingMigration.setIsKarmkaar(cellValue);break;

        			}        			
        			System.out.print(cellValue+" ");
        		}  
        		System.out.println();
        		//Saving the created bean Object
        		try
        		{
        			ccnbNscStagingMigration.setMigrated(false);
        			session.beginTransaction();
	        		session.flush();
	    			session.save(ccnbNscStagingMigration);
	    			session.getTransaction().commit();
	    			big = big.add(BigInteger.ONE);	    	
        		}
        		catch(Exception e)
        		{
    				++exceptionCount;				
    				writer.println();
    				writer.println("***********EXCEPTION NUMBER " + exceptionCount + "***********" + "Occured on: " + new Date());
    				writer.println("***********LOCATION CODE: " + ccnbNscStagingMigration.getLocation_code() + "***" + "CONSUMER NUMBER: " + ccnbNscStagingMigration.getOld_cons_no() + "***********");
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
    	System.out.println("MIGRATION OF CCNB_NSC_STAGING_MIGRATION SUCCESSFULLY DONE!!!!");
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
