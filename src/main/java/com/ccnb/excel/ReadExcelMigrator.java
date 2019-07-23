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
import com.ccnb.util.PathUtil;
import com.monitorjbl.xlsx.StreamingReader;

public class ReadExcelMigrator {

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
		
		File excel = new File(PathUtil.baseExcelFolder + "NSC_3424804_1706.xlsx");
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
        	CCNBNSCStagingMigration ccnbNscStagingMigration = new CCNBNSCStagingMigration();
        	session.clear();
    		    		
    		if(r.getRowNum()==0)
    			continue;    			
    		
    			System.out.println();

    			for(Cell c: r)
        		{
        			//For setting values of all columns of the current row into bean object
        			switch(c.getColumnIndex())
        			{
	        			case 0: ccnbNscStagingMigration.setConnection_date((c.getStringCellValue()=="")?null:ccnbDateFormat.parse(c.getStringCellValue()));break;
	        			
	        			case 1: ccnbNscStagingMigration.setConsumer_name(c.getStringCellValue());break;
	        			
	        			case 2: ccnbNscStagingMigration.setConsumer_name_h(c.getStringCellValue());break;
	        			
	        			case 3: ccnbNscStagingMigration.setRelative_name(c.getStringCellValue());break;
	        			
	        			case 4: ccnbNscStagingMigration.setRelation(c.getStringCellValue());break;
	        			
	        			case 5: ccnbNscStagingMigration.setBpl_no(c.getStringCellValue());break;
	        			
	        			case 6: ccnbNscStagingMigration.setCategory(c.getStringCellValue());break;
	        			
	        			case 7: ccnbNscStagingMigration.setEmployee_company(c.getStringCellValue());break;
	        			
	        			case 8: ccnbNscStagingMigration.setEmployee_no(c.getStringCellValue());break;
	        			
	        			case 9: ccnbNscStagingMigration.setAddress_1(c.getStringCellValue());break;
	        			
	        			case 10: ccnbNscStagingMigration.setAddress_2(c.getStringCellValue());break;
	        			
	        			case 11: ccnbNscStagingMigration.setAddress_3(c.getStringCellValue());break;
	        			
	        			case 12: ccnbNscStagingMigration.setAddress_1_h(c.getStringCellValue());break;
	        			
	        			case 13: ccnbNscStagingMigration.setAddress_2_h(c.getStringCellValue());break;
	        			
	        			case 14: ccnbNscStagingMigration.setAddress_3_h(c.getStringCellValue());break;
	        			
	        			case 15:
	        				if(c.getStringCellValue().length()==10)
	        					ccnbNscStagingMigration.setPrimary_mobile_no(c.getStringCellValue());
	        				break;
	        			
	        			case 16: 
	        				if(c.getStringCellValue().length()==10)
	        					ccnbNscStagingMigration.setAlternate_mobile_no(c.getStringCellValue());	        					
	        				break;
	        				
	        			case 17:
	        				if(c.getStringCellValue().length()==12)
	        					ccnbNscStagingMigration.setAadhaar_no(c.getStringCellValue());	        			
	        				break;	        			
	        				
	        			case 18: ccnbNscStagingMigration.setPan(c.getStringCellValue());break;
	        			
	        			case 19: ccnbNscStagingMigration.setBank_account_no(c.getStringCellValue());break;
	        			
	        			case 20: ccnbNscStagingMigration.setBank_account_holder_name(c.getStringCellValue());break;
	        			
	        			case 21: ccnbNscStagingMigration.setBank_name(c.getStringCellValue());break;
	        			
	        			case 22: ccnbNscStagingMigration.setIfsc(c.getStringCellValue());break;
	        			
	        			case 23: ccnbNscStagingMigration.setEmail_address(c.getStringCellValue());break;
	        			
	        			case 24: ccnbNscStagingMigration.setTariff_category(c.getStringCellValue());break;
	        			
	        			case 25: ccnbNscStagingMigration.setConnection_type(c.getStringCellValue());break;
	        			
	        			case 26: ccnbNscStagingMigration.setMetering_status(c.getStringCellValue());break;
	        			
	        			case 27: ccnbNscStagingMigration.setPremise_type(c.getStringCellValue());break;
	        			
	        			case 28: ccnbNscStagingMigration.setSanctioned_load((c.getStringCellValue()=="")?BigDecimal.ZERO:new BigDecimal(c.getStringCellValue()));break;
	        			
	        			case 29: ccnbNscStagingMigration.setSanctioned_load_unit(c.getStringCellValue());break;
	        			
	        			case 30: ccnbNscStagingMigration.setContract_demand((c.getStringCellValue()=="")?BigDecimal.ZERO:new BigDecimal(c.getStringCellValue()));break;
	        			
	        			case 31: ccnbNscStagingMigration.setContract_demand_unit(c.getStringCellValue());break;
	        			
	        			case 32: ccnbNscStagingMigration.setSeason_start_date((c.getStringCellValue()=="")?null:ccnbDateFormat.parse(c.getStringCellValue()));break;
	        			
	        			case 33: ccnbNscStagingMigration.setSeason_end_date((c.getStringCellValue()=="")?null:ccnbDateFormat.parse(c.getStringCellValue()));break;
	        			
	        			case 34: ccnbNscStagingMigration.setSeason_start_bill_month(c.getStringCellValue());break;
	        			
	        			case 35: ccnbNscStagingMigration.setSeason_end_bill_month(c.getStringCellValue());break;
	        			
	        			case 36: ccnbNscStagingMigration.setPurpose_of_installation(c.getStringCellValue());break;
	        			
	        			case 37: ccnbNscStagingMigration.setTariff_code(c.getStringCellValue());break;
	        			
	        			case 38: ccnbNscStagingMigration.setSub_category_code((c.getStringCellValue()=="")?0:Long.parseLong(c.getStringCellValue()));break;
	        			
	        			case 39: ccnbNscStagingMigration.setPhase(c.getStringCellValue());break;
	        			
	        			case 40: ccnbNscStagingMigration.setTc_start_date((c.getStringCellValue()=="")?null:ccnbDateFormat1.parse(c.getStringCellValue()));break;
	        			
	        			case 41: ccnbNscStagingMigration.setTc_end_date((c.getStringCellValue()=="")?null:ccnbDateFormat1.parse(c.getStringCellValue()));break;
	        			
	        			case 42: ccnbNscStagingMigration.setGovernment_type(c.getStringCellValue());break;
	        			
	        			case 43: ccnbNscStagingMigration.setPlot_size((c.getStringCellValue()=="")?BigDecimal.ZERO:new BigDecimal(c.getStringCellValue()));break;
	        			
	        			case 44: ccnbNscStagingMigration.setPlot_size_unit(c.getStringCellValue());break;
	        			
	        			case 45: ccnbNscStagingMigration.setLocation_code(c.getStringCellValue());break;
	        			
	        			case 46: ccnbNscStagingMigration.setXray_load((c.getStringCellValue()=="")?BigDecimal.ZERO:new BigDecimal(c.getStringCellValue()));break;
	        			
	        			case 47: ccnbNscStagingMigration.setNo_of_dental_xray_machine((c.getStringCellValue()=="")?0:Long.parseLong(c.getStringCellValue()));break;
	        			
	        			case 48: ccnbNscStagingMigration.setNo_of_single_phase_xray_machine((c.getStringCellValue()=="")?0:Long.parseLong(c.getStringCellValue()));break;
	        			
	        			case 49: ccnbNscStagingMigration.setNo_of_three_phase_xray_machine((c.getStringCellValue()=="")?0:Long.parseLong(c.getStringCellValue()));break;
	        			
	        			case 50: ccnbNscStagingMigration.setIsi_motor_type(c.getStringCellValue());break;
	        			
	        			case 51: ccnbNscStagingMigration.setDtr_name(c.getStringCellValue());break;
	        			
	        			case 52: ccnbNscStagingMigration.setPole_no(c.getStringCellValue());break;
	        			
	        			case 53: ccnbNscStagingMigration.setPole_latitude(c.getStringCellValue());break;
	        			
	        			case 54: ccnbNscStagingMigration.setPole_longitude(c.getStringCellValue());break;
	        			
	        			case 55: ccnbNscStagingMigration.setFeeder_name(c.getStringCellValue());break;
	        			
	        			case 56: ccnbNscStagingMigration.setPole_distance((c.getStringCellValue()=="")?0:Long.parseLong(c.getStringCellValue()));break;
	        			
	        			case 57: ccnbNscStagingMigration.setArea_status(c.getStringCellValue());break;
	        			
	        			case 58: ccnbNscStagingMigration.setGroup_no(c.getStringCellValue());break;
	        			
	        			case 59: ccnbNscStagingMigration.setReading_diary_no(c.getStringCellValue());break;
	        			
	        			case 60: ccnbNscStagingMigration.setNeighbour_connection_no(c.getStringCellValue());break;
	        			
	        			case 61: ccnbNscStagingMigration.setSurvey_date((c.getStringCellValue()=="")?null:ccnbDateFormat.parse(c.getStringCellValue()));break;
	        			
	        			case 62: ccnbNscStagingMigration.setMeter_identifier(c.getStringCellValue());break;
	        			
	        			case 63: ccnbNscStagingMigration.setStart_read((c.getStringCellValue()=="")?BigDecimal.ZERO:new BigDecimal(c.getStringCellValue()));break;
	        			
	        			case 64: ccnbNscStagingMigration.setCtr_identifier(c.getStringCellValue());break;
	        			
	        			case 65: ccnbNscStagingMigration.setCtr_overall_mf((c.getStringCellValue()=="")?BigDecimal.ZERO:new BigDecimal(c.getStringCellValue()));break;
	        			
	        			case 66: ccnbNscStagingMigration.setMeter_installation_date((c.getStringCellValue()=="")?null:ccnbDateFormat.parse(c.getStringCellValue()));break;
	        			
	        			case 67: ccnbNscStagingMigration.setMeter_installer_name(c.getStringCellValue());break;
	        			
	        			case 68: ccnbNscStagingMigration.setMeter_installer_designation(c.getStringCellValue());break;
	        			
	        			case 69: ccnbNscStagingMigration.setModem_no(c.getStringCellValue());break;
	        			
	        			case 70: ccnbNscStagingMigration.setSim_no(c.getStringCellValue());break;
	        			
	        			case 71: ccnbNscStagingMigration.setDate_of_registration((c.getStringCellValue()=="")?null:ccnbDateFormat.parse(c.getStringCellValue()));break;
	        			
	        			case 72: ccnbNscStagingMigration.setRegistration_fee_amount((c.getStringCellValue()=="")?BigDecimal.ZERO:new BigDecimal(c.getStringCellValue()));break;
	        			
	        			case 73: ccnbNscStagingMigration.setRegistration_fee_amount_mr_no(c.getStringCellValue());break;
	        			
	        			case 74: ccnbNscStagingMigration.setSecurity_deposit_amount((c.getStringCellValue()=="")?BigDecimal.ZERO:new BigDecimal(c.getStringCellValue()));break;
	        			
	        			case 75: ccnbNscStagingMigration.setSecurity_deposit_amount_mr_no(c.getStringCellValue());break;
	        			
	        			case 76: ccnbNscStagingMigration.setPortal_name(c.getStringCellValue());break;
	        			
	        			case 77: ccnbNscStagingMigration.setPortal_reference_no(c.getStringCellValue());break;
	        			
	        			case 78: ccnbNscStagingMigration.setCreated_on((c.getStringCellValue()=="")?null:ccnbDateFormat.parse(c.getStringCellValue()));break;
	        			
	        			case 79: ccnbNscStagingMigration.setOld_cons_no(c.getStringCellValue());break;
	        			
	        			case 80: ccnbNscStagingMigration.setOld_gr_no(c.getStringCellValue());break;
	        			
	        			case 81: ccnbNscStagingMigration.setOld_rd_no(c.getStringCellValue());break;
	        			
	        			case 82: ccnbNscStagingMigration.setOld_trf_catg(c.getStringCellValue());break;
	        			
	        			case 83: ccnbNscStagingMigration.setOld_rev_catg(c.getStringCellValue());break;
	        			
	        			case 84: ccnbNscStagingMigration.setTotal_outstanding(c.getStringCellValue());break;
	        			
	        			case 85: ccnbNscStagingMigration.setPrev_arrear(c.getStringCellValue());break;
	        			
	        			case 86: ccnbNscStagingMigration.setPend_surcharge(c.getStringCellValue());break;
	        			
	        			case 87: ccnbNscStagingMigration.setCurr_surcharge(c.getStringCellValue());break;
	        			
	        			case 88: ccnbNscStagingMigration.setColl_surcharge(c.getStringCellValue());break;
	        			
	        			case 89: ccnbNscStagingMigration.setPfl_bill_amount(c.getStringCellValue());break;
	        			
	        			case 90: ccnbNscStagingMigration.setMf(c.getStringCellValue());break;
	        			
	        			case 91: ccnbNscStagingMigration.setNrev_catg(c.getStringCellValue());break;
	        			
	        			case 92: ccnbNscStagingMigration.setPurpose_of_installation_id((c.getStringCellValue()=="")?0:Long.parseLong(c.getStringCellValue()));break;
	        			
	        			case 93: 
	        				if("YES".equals(c.getStringCellValue()))
	        					ccnbNscStagingMigration.setSd_enhance_cd("Y");
	        				else
	        					ccnbNscStagingMigration.setSd_enhance_cd("N");
	        			
	        			case 94: ccnbNscStagingMigration.setDishnrd_chq_flg(c.getStringCellValue());break;
	        			
	        			case 95: ccnbNscStagingMigration.setStatus(c.getStringCellValue());break;
	        			
	        			case 96: 
	        				if(strToBool(c.getStringCellValue()))
	        					ccnbNscStagingMigration.setIs_bpl(true);
	        				else
	        					ccnbNscStagingMigration.setIs_bpl(false);
	        				break;
	        			
	        			case 97: 
	        				if(strToBool(c.getStringCellValue()))
	        					ccnbNscStagingMigration.setIs_employee(true);
	        				else
	        					ccnbNscStagingMigration.setIs_employee(false);
	        				break;

	        			case 98:
	        				if(strToBool(c.getStringCellValue()))
	        					ccnbNscStagingMigration.setIs_seasonal(true);
	        				else
	        					ccnbNscStagingMigration.setIs_seasonal(false);
	        				break;
	        			
	        			case 99:
	        				if(strToBool(c.getStringCellValue()))
	        					ccnbNscStagingMigration.setIs_government(true);
	        				else
	        					ccnbNscStagingMigration.setIs_government(false);
	        				break;
	        			
	        			case 100:
	        				if(strToBool(c.getStringCellValue()))
	        					ccnbNscStagingMigration.setIs_xray(true);
	        				else
	        					ccnbNscStagingMigration.setIs_xray(false);
	        				break;
	        			
	        			case 101:
	        				if(strToBool(c.getStringCellValue()))
	        					ccnbNscStagingMigration.setIs_welding_transformer_surcharge(true);
	        				else
	        					ccnbNscStagingMigration.setIs_welding_transformer_surcharge(false);
	        				break;
	        			
	        			case 102:
	        				if(strToBool(c.getStringCellValue()))
	        					ccnbNscStagingMigration.setIs_capacitor_surcharge(true);
	        				else
	        					ccnbNscStagingMigration.setIs_capacitor_surcharge(false);	        			
	        				break;
	        			
	        			case 103:
	        				if(strToBool(c.getStringCellValue()))
	        					ccnbNscStagingMigration.setIs_demandside(true);
	        				else
	        					ccnbNscStagingMigration.setIs_demandside(false);	        				
	        				break;
	        			
	        			case 104:
	        				if(strToBool(c.getStringCellValue()))
	        					ccnbNscStagingMigration.setIs_beneficiary(true);
	        				else
	        					ccnbNscStagingMigration.setIs_beneficiary(false);	        					        				
	        				break;
	        			
	        			case 105:
	        				if(strToBool(c.getStringCellValue()))
	        					ccnbNscStagingMigration.setHas_ctr(true);
	        				else
	        					ccnbNscStagingMigration.setHas_ctr(false);	        			
	        				break;
	        			
	        			case 106: ccnbNscStagingMigration.setAffiliatedConsumerNo(c.getStringCellValue());break;
	        			
	        			case 107: ccnbNscStagingMigration.setCreatedBy("CCNB_MIG");break;
	        			
	        			case 108:
	        				if(strToBool(c.getStringCellValue()))
	        					ccnbNscStagingMigration.setAffiliatedConsumer(true);
	        				else
	        					ccnbNscStagingMigration.setAffiliatedConsumer(false);	        			
	        				break;
	        			
	        			case 109:
	        				if(strToBool(c.getStringCellValue()))
	        					ccnbNscStagingMigration.setHas_modem(true);
	        				else
	        					ccnbNscStagingMigration.setHas_modem(false);	        			
	        				break;

	        			case 110:
	        				if(strToBool(c.getStringCellValue()))
	        					ccnbNscStagingMigration.setAffiliated(true);
	        				else
	        					ccnbNscStagingMigration.setAffiliated(false);	        			
	        				break;
	        				
	        			case 111:
	        				if(strToBool(c.getStringCellValue()))
	        					ccnbNscStagingMigration.setSaral(true);
	        				else
	        					ccnbNscStagingMigration.setSaral(false);	        			
	        				break;

	        			case 112:
	        				if(strToBool(c.getStringCellValue()))
	        					ccnbNscStagingMigration.setBillMafi(true);
	        				else
	        					ccnbNscStagingMigration.setBillMafi(false);        			
	        				break;
	        			
	        			case 113: ccnbNscStagingMigration.setShramikNo(c.getStringCellValue());break;
	        			
	        			case 114: ccnbNscStagingMigration.setPurposeOfInstallationCD(c.getStringCellValue());break;
	        			
	        			case 115: ccnbNscStagingMigration.setCcnbPurposeOfInstallation(c.getStringCellValue());break;
	        			
	        			case 116: ccnbNscStagingMigration.setMeterType(c.getStringCellValue());break;
	        			
	        			case 117: ccnbNscStagingMigration.setMeterCapacity(c.getStringCellValue());break;
	        			
	        			case 118: ccnbNscStagingMigration.setPdcDate((c.getStringCellValue()=="")?null:ccnbDateFormat.parse(c.getStringCellValue()));break;	        			
        			}        			
        			System.out.print(c.getStringCellValue()+" ");
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
