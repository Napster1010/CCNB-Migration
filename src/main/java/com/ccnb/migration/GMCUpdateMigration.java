package com.ccnb.migration;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.ccnb.bean.CCNBGMCUpdate;
import com.ccnb.bean.GMCAccounting;
import com.ccnb.util.PathUtil;

public class GMCUpdateMigration {


	public static void main(String[] args) throws Exception {
		// For creating a exception Text File
		long exceptionCount = 0, recordCount = 0;
		File file = new File(PathUtil.baseExceptionFolder + "GMCUpdateMigrationExceptionLog.txt");
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
		final SimpleDateFormat billMonthFormat = new SimpleDateFormat("MMM-yyyy");
		ArrayList<String> billMonths = new ArrayList<>();
		addBillMonths(billMonths, billMonthFormat);
		long startTime = System.currentTimeMillis();
		
		Query<String> distinctConsumerQuery = session.createQuery("select distinct(consumerNo) from CCNBGMCUpdate");
		List<String> consumerNos = distinctConsumerQuery.list();
		
		for(String cons: consumerNos) {
			System.out.println();
			try {

				session.clear();
				session.beginTransaction();
				session.flush();

				// retrieve consumer no
				Query<String> consumerNoMasterQuery = session.createQuery("select consumerNo from ConsumerNoMaster where oldServiceNoOne = ?");
				consumerNoMasterQuery.setParameter(0, cons);

				String consumerNo = consumerNoMasterQuery.uniqueResult();
				if (consumerNo == null)
					throw new Exception("NGB consumer number not found!!");
				
				//retrieve gmc row
				Query<GMCAccounting> gmcAccountingQuery = session.createQuery("from GMCAccounting where consumerNo=?");
				gmcAccountingQuery.setParameter(0, consumerNo);
				GMCAccounting gmcAccounting = gmcAccountingQuery.uniqueResult();
				if(gmcAccounting==null)
					throw new Exception("Couldn't find a record in ngb's gmc_accounting table for the consumer!!");

				//retrieve gmc_update row
				Query<CCNBGMCUpdate> ccnbGmcQuery = session.createQuery("from CCNBGMCUpdate where migrated=false and consumerNo=? and billMonth IN(?1)");
				ccnbGmcQuery.setParameter(0, cons);
				ccnbGmcQuery.setParameter(1, billMonths);
				List<CCNBGMCUpdate> gmcUpdateRecords = ccnbGmcQuery.list();
				
				//get the latest record from the list
				CCNBGMCUpdate latestGmcUpdate = getLatestGmc(gmcUpdateRecords, billMonthFormat);

				String tariffCode = latestGmcUpdate.getTariffCode().trim();
				BigDecimal sanctionedLoad = new BigDecimal(latestGmcUpdate.getSanctionedLoad());

				//total gmc units
				BigDecimal totalGmcUnits = null;
				
				//Check tariff
				if(tariffCode.equals("LV2")) {
					
					if(sanctionedLoad.compareTo(new BigDecimal(10))<=0 && latestGmcUpdate.getSanctionedLoadUnit().equals("KW")) {
						
						//variable to store sanctioned load sum 
						BigDecimal sanctionedLoadSum = BigDecimal.ZERO;
						
						for(CCNBGMCUpdate ccnbgmcUpdate: gmcUpdateRecords) {
							sanctionedLoadSum = sanctionedLoadSum.add(new BigDecimal(ccnbgmcUpdate.getSanctionedLoad()));
						}
						
						if("URBAN".equals(latestGmcUpdate.getPremiseType()))
							totalGmcUnits = sanctionedLoadSum.multiply(new BigDecimal(20)); 								
						else if("RURAL".equals(latestGmcUpdate.getPremiseType()))
							totalGmcUnits = sanctionedLoadSum.multiply(new BigDecimal(15));
						else
							throw new Exception("Invalid premise type!!");
													
					}else if(sanctionedLoad.compareTo(new BigDecimal(10))>0 && latestGmcUpdate.getSanctionedLoadUnit().equals("KW")) {
						
						//variable to store contract demand sum 
						BigDecimal contractDemandSum = BigDecimal.ZERO;
						
						for(CCNBGMCUpdate ccnbgmcUpdate: gmcUpdateRecords) {
							if(BigDecimal.ZERO.compareTo(new BigDecimal(ccnbgmcUpdate.getContractDemand()))==0)
								contractDemandSum = contractDemandSum.add(new BigDecimal(ccnbgmcUpdate.getSanctionedLoad()));
							else
								contractDemandSum = contractDemandSum.add(new BigDecimal(ccnbgmcUpdate.getContractDemand()));
						}						
						if("URBAN".equals(latestGmcUpdate.getPremiseType()))
							totalGmcUnits = contractDemandSum.multiply(new BigDecimal(20)); 								
						else if("RURAL".equals(latestGmcUpdate.getPremiseType()))
							totalGmcUnits = contractDemandSum.multiply(new BigDecimal(15));
						else
							throw new Exception("Invalid premise type!!");
													
					}else
						throw new Exception("Sanctioned load unit is not KW !!");
				}else if(tariffCode.equals("LV4")) {
					
					//variable to store contract demand sum 
					BigDecimal contractDemandSum = BigDecimal.ZERO;
					
					for(CCNBGMCUpdate ccnbgmcUpdate: gmcUpdateRecords) {
						if(BigDecimal.ZERO.compareTo(new BigDecimal(ccnbgmcUpdate.getContractDemand()))==0)
							contractDemandSum = contractDemandSum.add(new BigDecimal(ccnbgmcUpdate.getSanctionedLoad()));
						else
							contractDemandSum = contractDemandSum.add(new BigDecimal(ccnbgmcUpdate.getContractDemand()));
					}						
					if("URBAN".equals(latestGmcUpdate.getPremiseType()))
						totalGmcUnits = contractDemandSum.multiply(new BigDecimal(20)); 								
					else if("RURAL".equals(latestGmcUpdate.getPremiseType()))
						totalGmcUnits = contractDemandSum.multiply(new BigDecimal(10));
					else
						throw new Exception("Invalid premise type!!");
																	
				}else
					throw new Exception("Tariff code other than LV2 or LV4 found !!");				
				
				//update gmc row with new gmc value
				updateGmcBean(gmcAccounting, totalGmcUnits);
				
				session.update(gmcAccounting);
				
				//set migrated flag as true for all retrieved records
				for(CCNBGMCUpdate ccnbgmcUpdate: gmcUpdateRecords) {
					ccnbgmcUpdate.setMigrated(true);
					session.update(ccnbgmcUpdate);
				}
				
				session.getTransaction().commit();
				++recordCount;				
			}catch (Exception e) {
				++exceptionCount;
				writer.println();
				writer.println("***********EXCEPTION NUMBER " + exceptionCount + "***********" + "Occured on: " + new Date());
				writer.println("***********CONSUMER NUMBER " + cons);
				writer.println("Root cause : ");
				e.printStackTrace(writer);
				e.printStackTrace();
				session.getTransaction().rollback();
			}
		}		

		long endTime = System.currentTimeMillis();
		long seconds = (endTime - startTime) / 1000;
		long minutes = seconds / 60;
		seconds -= minutes * 60;

		System.out.println("MIGRATION FROM CCNB_GMC_UPDATE SUCCESSFULLY DONE !!");
		System.out.println(recordCount + " ROWS SUCCESSFULLY MIGRATED !!");
		System.out.println(exceptionCount + " EXCEPTIONS CAUGHT !! PLEASE REFER EXCEPTION LOG FOR MORE DETAILS!!");
		System.out.println("Time Elapsed: " + minutes + " Minutes " + seconds + " Seconds");

		session.close();
		sessionFactory.close();
		writer.close();
	}
	
	private static void addBillMonths(ArrayList<String> arr, SimpleDateFormat billMonthFormat) throws Exception{
		Date startDate = billMonthFormat.parse("APR-2019");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDate);
		
		while(calendar.get(Calendar.MONTH)!=3 || calendar.get(Calendar.YEAR)!=2020) {
			arr.add(billMonthFormat.format(calendar.getTime()).toUpperCase());
			calendar.add(Calendar.MONTH, 1);
		}
	}
	
	private static CCNBGMCUpdate getLatestGmc(List<CCNBGMCUpdate> list, SimpleDateFormat billMonthFormat) throws Exception{
		CCNBGMCUpdate latestGmcUpdate = null;
		Date maxDate = null;
		for(CCNBGMCUpdate record: list) {
			Date parsedMonth = billMonthFormat.parse(record.getBillMonth());
			if(maxDate==null || (parsedMonth.compareTo(maxDate)>0)) {
				maxDate = parsedMonth;
				latestGmcUpdate = record;
			}				
		}
		
		return latestGmcUpdate;
	}
	
	private static void updateGmcBean(GMCAccounting gmcAccounting, BigDecimal totalGmcUnits) {
		gmcAccounting.setMinimumCumulative(totalGmcUnits);
		gmcAccounting.setHigherOfActualMinimumCumulative((gmcAccounting.getActualCumulativeConsumption().compareTo(gmcAccounting.getMinimumCumulative())<=0 ? gmcAccounting.getMinimumCumulative() : gmcAccounting.getActualCumulativeConsumption()));								
		gmcAccounting.setToBeBilled(gmcAccounting.getHigherOfActualMinimumCumulative().subtract(gmcAccounting.getAlreadyBilled()));		
	}
}
