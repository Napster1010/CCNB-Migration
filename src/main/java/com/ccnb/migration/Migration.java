package com.ccnb.migration;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.ccnb.bean.CCNBNSCStagingMigration;
import com.ccnb.bean.CTRMaster;
import com.ccnb.bean.CcnbNgbTariffMapping;
import com.ccnb.bean.MeterMaster;
import com.ccnb.bean.NSCStagingMigration;
import com.ccnb.bean.NSCStagingMigrationStatus;
import com.ccnb.bean.Zone;

public class Migration {

	public static void main(String[] args) {

		//For creating a exception Text File
		long exceptionCount=0, recordCount=0;
		File file = new File("C:\\Users\\Napster\\Documents\\ccnb_migration_excel\\Exception_Log\\CcnbMigrationExceptionLog.txt");
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
		
		Query<CCNBNSCStagingMigration> ccnbNscStagingMigrationQuery = session.createQuery("from CCNBNSCStagingMigration where migrated=false");
		List<CCNBNSCStagingMigration> unmigratedRecords = ccnbNscStagingMigrationQuery.list();
		
		Query<CcnbNgbTariffMapping> tariffMappingQuery = session.createQuery("from CcnbNgbTariffMapping");
		List<CcnbNgbTariffMapping> tariffMappings = tariffMappingQuery.list(); 
		
		SimpleDateFormat ccnbDateFormat = new SimpleDateFormat("dd-MM-yyyy");

		Query<String> zoneQuery;
		Query<Integer> migrationStatus;

		for(CCNBNSCStagingMigration currentRecord: unmigratedRecords) {
			try {
				session.beginTransaction();
				session.flush();
				
				if(basicNullCheck(currentRecord)) 
					throw new Exception("One or more mandatory fields were found to be blank");				
				
				//Create a new object
				NSCStagingMigration nscStagingMigration = new NSCStagingMigration();
				NSCStagingMigrationStatus nscStagingMigrationStatus = new NSCStagingMigrationStatus();
				
				nscStagingMigration.setPremise_type(currentRecord.getPremise_type());
				
				CcnbNgbTariffMapping tariffMapping = getTariffMapping(tariffMappings, currentRecord.getOld_trf_catg().trim(), currentRecord.getPurposeOfInstallationCD().trim(), currentRecord.getCcnbPurposeOfInstallation().trim());
				nscStagingMigration.setPremise_type(currentRecord.getPremise_type());
				
				nscStagingMigration.setIs_employee(currentRecord.isIs_employee());
				
				//Connection date is same as application date
				if(currentRecord.getConnection_date()==null)
					nscStagingMigration.setApplication_date(ccnbDateFormat.parse("10-10-2010"));
				else
					nscStagingMigration.setApplication_date(currentRecord.getConnection_date());
				
				if("Y".equals(currentRecord.getMetering_status().trim())) {
					nscStagingMigration.setMetering_status("METERED");

					MeterMaster meterMaster = new MeterMaster();
					
					String make="", serialNo="";
					if(currentRecord.getMeter_identifier()!=null && !currentRecord.getMeter_identifier().trim().isEmpty()) {
						String ccnbIdentifier = currentRecord.getMeter_identifier();
						int meterMakeIndex = getMeterMakeIndex(ccnbIdentifier);
						make = ccnbIdentifier.substring(0, meterMakeIndex+1);
						serialNo = ccnbIdentifier.substring(meterMakeIndex+1);
						
					}else {
						make = "MIG";
						serialNo = currentRecord.getOld_cons_no();
					}
					
					meterMaster.setIdentifier(make.concat(serialNo));
					meterMaster.setMake(make);
					meterMaster.setSerialNo(serialNo);
					meterMaster.setMeterOwner("DISCOM");
					meterMaster.setPrepaid(false);
					meterMaster.setCreatedBy("CCNB_MIG");
					meterMaster.setCreateOn(new Date());						
				
					nscStagingMigration.setMeter_identifier(meterMaster.getIdentifier());
					
					if(currentRecord.getMeter_installation_date()==null)
						nscStagingMigration.setMeter_installation_date(nscStagingMigration.getApplication_date());
					else
						nscStagingMigration.setMeter_installation_date(currentRecord.getMeter_installation_date());
					
					nscStagingMigration.setMeter_installer_name("MIGRATION");
					nscStagingMigration.setMeter_installer_designation("MIG");
					
					//Save meter
					session.save(meterMaster);
				}
				else if("N".equals(currentRecord.getMetering_status().trim()))
					nscStagingMigration.setMetering_status("UNMETERED");
				
				nscStagingMigration.setPurpose_of_installation(tariffMapping.getNgbPurposeOfInstallation());
				
				nscStagingMigration.setPurpose_of_installation_id(Long.parseLong(tariffMapping.getNgbPurposeId()));
				
				if("SC/ST".equals(currentRecord.getCategory().trim()))
					nscStagingMigration.setCategory("ST");
				else if("NOT DISCLOSED".equals(currentRecord.getCategory().trim()))
					nscStagingMigration.setCategory("GENERAL");
				else
					nscStagingMigration.setCategory(currentRecord.getCategory().trim());

				nscStagingMigration.setTariff_code(tariffMapping.getNgbTariffCode());
				
				nscStagingMigration.setLocation_code(currentRecord.getLocation_code());
				
				nscStagingMigration.setOld_cons_no(currentRecord.getOld_cons_no());

				zoneQuery = session.createQuery("select shortCode from Zone where code = ?");
				zoneQuery.setParameter(0, currentRecord.getLocation_code().trim());
				String shortCode = (String)zoneQuery.uniqueResult();
				if(shortCode!=null) {
					String groupNo = currentRecord.getOld_gr_no().trim().substring(2);
					nscStagingMigration.setGroup_no(shortCode.trim().concat(groupNo));
				}
				
				nscStagingMigration.setOld_rd_no(currentRecord.getOld_rd_no());
				
				nscStagingMigration.setReading_diary_no(currentRecord.getOld_rd_no());
				
				nscStagingMigration.setAffiliated(currentRecord.isAffiliated());
				
				nscStagingMigration.setAffiliatedConsumer(currentRecord.isAffiliatedConsumer());
				
				nscStagingMigration.setOld_gr_no(currentRecord.getOld_gr_no());
				
				nscStagingMigration.setConsumer_name(currentRecord.getConsumer_name());
				
				nscStagingMigration.setAddress_1(currentRecord.getAddress_1());
				
				nscStagingMigration.setAddress_2(currentRecord.getAddress_2());
				
				nscStagingMigration.setAddress_3(currentRecord.getAddress_3());
				
				nscStagingMigration.setOld_trf_catg(currentRecord.getOld_trf_catg());
				
				nscStagingMigration.setOld_rev_catg(currentRecord.getOld_rev_catg());
				
				nscStagingMigration.setSanctioned_load(currentRecord.getSanctioned_load());
				
				nscStagingMigration.setSanctioned_load_unit(currentRecord.getSanctioned_load_unit());
				
				nscStagingMigration.setSd_enhance_cd(currentRecord.getSd_enhance_cd());

				if("I".equals(currentRecord.getDishnrd_chq_flg().trim()))
					nscStagingMigration.setDishnrd_chq_flg("N");
				else if("A".equals(currentRecord.getDishnrd_chq_flg().trim()))
					nscStagingMigration.setDishnrd_chq_flg("Y");

				nscStagingMigration.setContract_demand_unit(currentRecord.getContract_demand_unit());

				if(currentRecord.getOld_trf_catg().trim().startsWith("LV4") && "HP".equals(currentRecord.getContract_demand_unit().trim())) {
					BigDecimal contractDemand = currentRecord.getContract_demand().multiply(new BigDecimal(0.746));
					contractDemand = contractDemand.setScale(3, BigDecimal.ROUND_HALF_UP);
					
					nscStagingMigration.setContract_demand(contractDemand);
					nscStagingMigration.setContract_demand_unit("KW");
				}else
					nscStagingMigration.setContract_demand(currentRecord.getContract_demand());
				
				if(currentRecord.getOld_trf_catg().startsWith("LV2")) {
					if(currentRecord.getSanctioned_load().compareTo(new BigDecimal(10))>0 && currentRecord.getSanctioned_load().compareTo(currentRecord.getContract_demand())<0)
						throw new Exception("Sanctioned load can't be less than the contract demand!");
				}else if(currentRecord.getOld_trf_catg().startsWith("LV4")) {
					BigDecimal sanctionedLoad = currentRecord.getSanctioned_load().multiply(new BigDecimal(0.746));
					sanctionedLoad = sanctionedLoad.setScale(3, BigDecimal.ROUND_HALF_UP);
					if(sanctionedLoad.compareTo(currentRecord.getContract_demand())<0)
						throw new Exception("Sanctioned load can't be less than the contract demand!");
				}
				
				nscStagingMigration.setTotal_outstanding(currentRecord.getTotal_outstanding());
				
				nscStagingMigration.setPrev_arrear(currentRecord.getPrev_arrear());
				
				if(Double.parseDouble(currentRecord.getPend_surcharge())<0)
					nscStagingMigration.setPend_surcharge("0");
				else
					nscStagingMigration.setPend_surcharge(currentRecord.getPend_surcharge());
				
				nscStagingMigration.setCurr_surcharge(currentRecord.getCurr_surcharge());
				
				nscStagingMigration.setColl_surcharge(currentRecord.getColl_surcharge());
				
				nscStagingMigration.setPfl_bill_amount(currentRecord.getPfl_bill_amount());
				
				nscStagingMigration.setMf(currentRecord.getMf());
				
				nscStagingMigration.setNrev_catg(currentRecord.getNrev_catg());
				
				if(currentRecord.getPrimary_mobile_no()!=null) {
					if(currentRecord.getPrimary_mobile_no().length()==10)
						nscStagingMigration.setPrimary_mobile_no(currentRecord.getPrimary_mobile_no());					
					else if(currentRecord.getPrimary_mobile_no().length()==12 || currentRecord.getPrimary_mobile_no().length()==13) {
						if(currentRecord.getPrimary_mobile_no().startsWith("+91"))
							nscStagingMigration.setPrimary_mobile_no(currentRecord.getPrimary_mobile_no().substring(3));
						else if(currentRecord.getPrimary_mobile_no().startsWith("91"))
							nscStagingMigration.setPrimary_mobile_no(currentRecord.getPrimary_mobile_no().substring(2));
					}
				}
				
				nscStagingMigration.setIs_bpl(currentRecord.isIs_bpl());
				
				if(currentRecord.isIs_bpl()) {
					if(currentRecord.getBpl_no().trim().isEmpty())
						nscStagingMigration.setBpl_no("MIG".concat(currentRecord.getOld_cons_no()));
					else
						nscStagingMigration.setBpl_no(currentRecord.getBpl_no());					
				}
				
				if(currentRecord.getAadhaar_no()!=null && currentRecord.getAadhaar_no().length()==12)
					nscStagingMigration.setAadhaar_no(currentRecord.getAadhaar_no());
				
				nscStagingMigration.setConsumer_name_h(currentRecord.getConsumer_name_h());
				
				nscStagingMigration.setAddress_1_h(currentRecord.getAddress_1_h());
				
				nscStagingMigration.setAddress_2_h(currentRecord.getAddress_2_h());
				
				nscStagingMigration.setAddress_3_h(currentRecord.getAddress_3_h());
				
				nscStagingMigration.setIs_beneficiary(currentRecord.isIs_beneficiary());
				
				//Tariff Category
				nscStagingMigration.setTariff_category(currentRecord.getOld_trf_catg().substring(0, 3));

				if(currentRecord.getOld_trf_catg().contains("T") && "PERMANENT".equals(currentRecord.getConnection_type().trim()))
					nscStagingMigration.setConnection_type("TEMPORARY");
				else					
					nscStagingMigration.setConnection_type(currentRecord.getConnection_type());
				
				nscStagingMigration.setIs_seasonal(currentRecord.isIs_seasonal());
				
				nscStagingMigration.setPhase(currentRecord.getPhase());
				
				nscStagingMigration.setIs_government(currentRecord.isIs_government());
				
				nscStagingMigration.setPlot_size(currentRecord.getPlot_size());
				
				nscStagingMigration.setPlot_size_unit(currentRecord.getPlot_size_unit());
				
				nscStagingMigration.setIs_xray(currentRecord.isIs_xray());
				
				nscStagingMigration.setNo_of_dental_xray_machine(currentRecord.getNo_of_dental_xray_machine());
				
				nscStagingMigration.setXray_load(currentRecord.getXray_load());
				
				nscStagingMigration.setNo_of_single_phase_xray_machine(currentRecord.getNo_of_single_phase_xray_machine());
				
				nscStagingMigration.setNo_of_three_phase_xray_machine(currentRecord.getNo_of_three_phase_xray_machine());
				
				nscStagingMigration.setIs_welding_transformer_surcharge(currentRecord.isIs_welding_transformer_surcharge());
				
				nscStagingMigration.setIs_capacitor_surcharge(currentRecord.isIs_capacitor_surcharge());
				
				nscStagingMigration.setIs_demandside(currentRecord.isIs_demandside());
				
				nscStagingMigration.setHas_ctr(currentRecord.isHas_ctr());
				
				if(currentRecord.isHas_ctr()) {
					//Create a new CTR
					String meterCapacity, ctRatio;
					CTRMaster ctrMaster = new CTRMaster();
					
					if(currentRecord.getCtr_overall_mf().equals("2"))
					{
						meterCapacity = "100/5";
						ctRatio = "200/5";
					}
					else if(currentRecord.getCtr_overall_mf().equals("1.5"))
					{
						meterCapacity = "100/5";
						ctRatio = "150/5";					
					}
					else if(currentRecord.getCtr_overall_mf().equals("3"))
					{
						meterCapacity = "100/5";
						ctRatio = "300/5";					
					}
					else if(currentRecord.getCtr_overall_mf().equals("4"))
					{
						meterCapacity = "100/5";
						ctRatio = "400/5";					
					}
					else if(currentRecord.getCtr_overall_mf().equals("5"))
					{
						meterCapacity = "100/5";
						ctRatio = "500/5";					
					}
					else if(currentRecord.getCtr_overall_mf().equals("15"))
					{
						meterCapacity = "-/5";
						ctRatio = "75/5";					
					}
					else if(currentRecord.getCtr_overall_mf().equals("20"))
					{
						meterCapacity = "-/5";
						ctRatio = "100/5";					
					}
					else if(currentRecord.getCtr_overall_mf().equals("40"))
					{
						meterCapacity = "-/5";
						ctRatio = "200/5";					
					}
					else if(currentRecord.getCtr_overall_mf().equals("60"))
					{
						meterCapacity = "-/5";
						ctRatio = "300/5";					
					}
					else if(currentRecord.getCtr_overall_mf().equals("80"))
					{
						meterCapacity = "-/5";
						ctRatio = "400/5";					
					}
					else
						throw new Exception("Invalid MF !!");
					
					ctrMaster.setMake("MIG");
					ctrMaster.setCtRatio(ctRatio);
					ctrMaster.setCreatedBy("CCNB_MIG");
					ctrMaster.setCreatedOn(new Date());
					ctrMaster.setSerialNo(currentRecord.getOld_cons_no());
					ctrMaster.setIdentifier("MIGCTR".concat(currentRecord.getOld_cons_no()));					
					
					if(currentRecord.getCtr_overall_mf().compareTo(BigDecimal.ZERO)==0)
						throw new Exception("CTR overall MF can't be 0!");
										
					nscStagingMigration.setCtr_identifier(ctrMaster.getIdentifier());
					nscStagingMigration.setCtr_overall_mf(currentRecord.getCtr_overall_mf());
				
					//Save CTR
					session.save(ctrMaster);
				}
				
				nscStagingMigration.setHas_modem(currentRecord.isHas_modem());
				
				//Forcefully set the positive security deposit to zero and convert the negative security deposit to positive
				if(currentRecord.getSecurity_deposit_amount().compareTo(BigDecimal.ZERO)>0)
					nscStagingMigration.setSecurity_deposit_amount(BigDecimal.ZERO);
				else
					nscStagingMigration.setSecurity_deposit_amount(currentRecord.getSecurity_deposit_amount().abs());

				nscStagingMigration.setPortal_name("CCNB MIGRATION");
				
				nscStagingMigration.setPortal_reference_no("CCNB_MIG 1");
				
				nscStagingMigration.setCreated_on(new Date());
				
				if("LEGAL".equals(currentRecord.getArea_status().trim()))
					nscStagingMigration.setArea_status("AUTHORIZED");
				else
					nscStagingMigration.setArea_status("UNAUTHORIZED");
				
				if("C".equals(currentRecord.getStatus().trim()))
					nscStagingMigration.setStatus("ACTIVE");
				else if("D".equals(currentRecord.getStatus().trim()))
					nscStagingMigration.setStatus("INACTIVE");
				else
					throw new Exception("Invalid status!");
				
				//Subcategory code only handeled for domestic as of now
				if(currentRecord.getOld_trf_catg().trim().startsWith("LV1.1")) {
					if(currentRecord.getSanctioned_load().compareTo(new BigDecimal(0.1))>0)
						throw new Exception("Load can't be greater than 0.1 for LV1.1 !");
				}
				
				if(currentRecord.getOld_trf_catg().trim().startsWith("LV1")) {
					
					if(!"KW".equals(currentRecord.getSanctioned_load_unit()))
						throw new Exception("Invalid sanctioned load unit for LV1 tariff category !!");

					if("URBAN".equals(currentRecord.getPremise_type()))
						nscStagingMigration.setSub_category_code(Long.parseLong(tariffMapping.getNgbUrbanSubcategory1()));
					else if("RURAL".equals(currentRecord.getPremise_type()))
						nscStagingMigration.setSub_category_code(Long.parseLong(tariffMapping.getNgbRuralSubcategory1()));
					
				}else if(currentRecord.getOld_trf_catg().trim().startsWith("LV3")) {
					if("CM_IPMPW".equals(currentRecord.getSaType()) || "CM_BPMPW".equals(currentRecord.getSaType()) || "CM_JPMPW".equals(currentRecord.getSaType())) {
						/////////////////////////////////////////////////////////////
					}
				}else if(currentRecord.getOld_trf_catg().trim().startsWith("LV2")) {
					
					if(!"KW".equals(currentRecord.getSanctioned_load_unit()))
						throw new Exception("Invalid sanctioned load unit for LV2 tariff category !!");

					if(currentRecord.getSanctioned_load().compareTo(new BigDecimal(10))<=0) {
						if("URBAN".equals(currentRecord.getPremise_type()))
							nscStagingMigration.setSub_category_code(Long.parseLong(tariffMapping.getNgbUrbanSubcategory1()));
						else if("RURAL".equals(currentRecord.getPremise_type()))
							nscStagingMigration.setSub_category_code(Long.parseLong(tariffMapping.getNgbRuralSubcategory1()));						
						
					}else {
						
						if(currentRecord.getContract_demand().compareTo(BigDecimal.ZERO)==0)
							throw new Exception("Contract demand can't be 0 !");
						if("URBAN".equals(currentRecord.getPremise_type()))
							nscStagingMigration.setSub_category_code(Long.parseLong(tariffMapping.getNgbUrbanSubcategory2()));
						else if("RURAL".equals(currentRecord.getPremise_type()))
							nscStagingMigration.setSub_category_code(Long.parseLong(tariffMapping.getNgbRuralSubcategory2()));												
						
					}						
				}else if(currentRecord.getOld_trf_catg().trim().startsWith("LV4")) {
					
					if(!"HP".equals(currentRecord.getSanctioned_load_unit()))
						throw new Exception("Invalid sanctioned load unit for LV4 tariff category !!");
					if(currentRecord.getContract_demand().compareTo(BigDecimal.ZERO)==0)
						throw new Exception("Contract demand can't be 0 !");
					
					if("URBAN".equals(currentRecord.getPremise_type()))
						nscStagingMigration.setSub_category_code(Long.parseLong(tariffMapping.getNgbUrbanSubcategory2()));
					else if("RURAL".equals(currentRecord.getPremise_type()))
						nscStagingMigration.setSub_category_code(Long.parseLong(tariffMapping.getNgbRuralSubcategory2()));					
					
				}
				
				//save nsc staging migration object
				session.save(nscStagingMigration);
				
				//Create staging status object
				nscStagingMigrationStatus.setStatus("PENDING");
				nscStagingMigrationStatus.setCreated_on(new Date());
				nscStagingMigrationStatus.setLast_updated_on(new Date());
				nscStagingMigrationStatus.setLocation_code(currentRecord.getLocation_code());
				nscStagingMigrationStatus.setOld_cons_no(currentRecord.getOld_cons_no());
				nscStagingMigrationStatus.setNsc_staging_id(nscStagingMigration.getId());

				//save nsc staging migration status object
				session.save(nscStagingMigrationStatus);
				
				//commit
				session.getTransaction().commit();
				
				//Update the migrated flag
				session.beginTransaction();
				
				migrationStatus = session.createQuery("update CCNBNSCStagingMigration set migrated=true where id=?");
				migrationStatus.setParameter(0, currentRecord.getId());
				migrationStatus.executeUpdate();
				session.getTransaction().commit();
				
				++recordCount;
			}catch(Exception e) {
				++exceptionCount;				
				writer.println();
				writer.println("***********EXCEPTION NUMBER " + exceptionCount + "***********" + "Occured on: " + new Date());
				writer.println("***********CONSUMER NUMBER: " + currentRecord.getOld_cons_no() + "***********");
				writer.println("Root cause : ");
				e.printStackTrace(writer);
				e.printStackTrace();
				session.getTransaction().rollback();
			}
		}
		
		long endTime = System.currentTimeMillis();
		long totTime = endTime - startTime;
		long seconds = (endTime - startTime)/1000;
		long minutes = seconds/60;
		seconds -= minutes*60;
		
		System.out.println("MIGRATION FROM CCNB_NSC_STAGING_MIGRATION TO NSC_STAGING_MIGRATION SUCCESSFULLY DONE !!");
		System.out.println(unmigratedRecords.size() + " ROWS WERE RETRIEVED");
		System.out.println(recordCount + " ROWS SUCCESSFULLY MIGRATED !!");		
		System.out.println(exceptionCount + " EXCEPTIONS CAUGHT !! PLEASE REFER EXCEPTION LOG FOR MORE DETAILS!!");		
		System.out.println("Time Elapsed: " + minutes + " Minutes " + seconds + " Seconds");				
		
		session.close();
		sessionFactory.close();
		writer.close();		
	}
	
	private static CcnbNgbTariffMapping getTariffMapping(List<CcnbNgbTariffMapping> tariffMappings, String ccnbTariff, String ccnbCharTypeCd, String ccnbCharVal) {
		CcnbNgbTariffMapping reqMapping = null;
		for(CcnbNgbTariffMapping tariffMapping: tariffMappings) {
			if(tariffMapping.getCcnbTariff().trim().equals(ccnbTariff) && tariffMapping.getCcnbCharTypeCd().trim().equals(ccnbCharTypeCd) && tariffMapping.getCcnbCharVal().trim().equals(ccnbCharVal))
				reqMapping = tariffMapping;			
		}
		return reqMapping;
	}

	private static boolean basicNullCheck(CCNBNSCStagingMigration currentRecord) {
		boolean check = currentRecord.getConsumer_name().trim().trim().isEmpty() ||
						currentRecord.getCategory().trim().trim().isEmpty() || 
						currentRecord.getConnection_type().trim().isEmpty() ||
						currentRecord.getMetering_status().trim().isEmpty() || currentRecord.getPremise_type().trim().isEmpty() ||
						currentRecord.getSanctioned_load().compareTo(BigDecimal.ZERO)==0 ||
						currentRecord.getSanctioned_load_unit().trim().isEmpty() || 						
						currentRecord.getCcnbPurposeOfInstallation().trim().isEmpty() ||
						currentRecord.getLocation_code().trim().isEmpty() || 
						currentRecord.getOld_cons_no().trim().isEmpty() ||  
						currentRecord.getOld_gr_no().trim().isEmpty() || 
						currentRecord.getOld_rd_no().trim().isEmpty() ||
						currentRecord.getOld_trf_catg().trim().isEmpty() || 
						currentRecord.getPurposeOfInstallationCD().trim().isEmpty() ||
						currentRecord.getStatus().trim().isEmpty() ||
						(currentRecord.isIs_employee() && currentRecord.getEmployee_no().trim().isEmpty()) ||
						(currentRecord.isIs_seasonal() && (currentRecord.getSeason_start_date()==null || currentRecord.getSeason_end_date()==null || currentRecord.getSeason_start_bill_month().trim().isEmpty() || currentRecord.getSeason_end_bill_month().trim().isEmpty()));
		
		return check;
	}
	
	private static int getMeterMakeIndex(String identifier) {
		int index=-1;
		for(int i=0;i<identifier.length();i++) {
			if(Character.isAlphabetic(identifier.charAt(i)))
				++index;
			else
				break;
		}
		return index;
	}
	
}
