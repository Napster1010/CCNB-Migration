package com.ccnb.tariffmigration;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.StringTokenizer;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.ccnb.bean.CcnbNgbTariffMapping;
import com.ccnb.util.PathUtil;
import com.monitorjbl.xlsx.StreamingReader;

public class TariffMigration {
	public static void main(String[] args) throws Exception{
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		File excel = new File(PathUtil.baseExcelFolder + "Purpose_of_Instalation_Modi_Vr_1.xlsx");
    	InputStream is = new FileInputStream(excel);
    	Workbook workbook = StreamingReader.builder()
    	        .rowCacheSize(100)    
    	        .bufferSize(4096)     
    	        .open(is);    	  
    	System.out.println("Excel File opened successfully!!");
		
    	Sheet sheet = workbook.getSheetAt(1);
    	
    	for(Row row: sheet) {
    		if(row.getRowNum()==0)
    			continue;
    		
    		String ccnbTariff="", ccnbCharTypeCd="", ccnbCharVal="", ccnbVersion="", ccnbOwnerFlag="", ccnbPurpose="", ngbPurposeId="", ngbTariffCode="", ngbPurposeOfInstallation="", ngbUrbanSubcategoryCode1="", ngbRuralSubcategoryCode1="", ngbUrbanSubcategoryCode2="", ngbRuralSubcategoryCode2="";   
    		for(Cell cell: row) {
    			switch (cell.getColumnIndex()) {
				case 0:
					ccnbTariff = cell.getStringCellValue();
					break;

				case 1:					
					ccnbCharTypeCd = cell.getStringCellValue();
					break;

				case 2:				
					ccnbCharVal = cell.getStringCellValue();
					break;

				case 3:					
					ccnbVersion = cell.getStringCellValue();
					break;

				case 4:					
					ccnbOwnerFlag = cell.getStringCellValue();
					break;

				case 5:					
					ccnbPurpose = cell.getStringCellValue();
					break;
    			
				case 6:					
					ngbPurposeId = cell.getStringCellValue();
					break;
					
				case 7:					
					ngbTariffCode = cell.getStringCellValue();
					break;
					
				case 8:					
					ngbPurposeOfInstallation = cell.getStringCellValue();
					break;
					
				case 9:					
					ngbUrbanSubcategoryCode1 = cell.getStringCellValue();
					break;
					
				case 10:					
					ngbRuralSubcategoryCode1 = cell.getStringCellValue();
					break;
					
				case 11:					
					ngbUrbanSubcategoryCode2 = cell.getStringCellValue();
					break;
					
				case 12:					
					ngbRuralSubcategoryCode2 = cell.getStringCellValue();
					break;
    			}
    			System.out.print(cell.getStringCellValue() + " ");
    		}
    		System.out.println();
    		
    		StringTokenizer tokenizer = new StringTokenizer(ccnbTariff, ",");
    		while(tokenizer.hasMoreTokens()) {
    			String curTariff = tokenizer.nextToken().trim();
    			if(!"".equals(curTariff)) {
        			CcnbNgbTariffMapping tariffMapping = new CcnbNgbTariffMapping();
        			tariffMapping.setCcnbTariff(curTariff);
        			tariffMapping.setCcnbCharTypeCd(ccnbCharTypeCd);
        			tariffMapping.setCcnbCharVal(ccnbCharVal);
        			tariffMapping.setCcnbVersion(ccnbVersion);
        			tariffMapping.setCcnbOwnerFlag(ccnbOwnerFlag);
        			tariffMapping.setCcnbPurpose(ccnbPurpose);
        			tariffMapping.setNgbPurposeId(ngbPurposeId);
        			tariffMapping.setNgbTariffCode(ngbTariffCode);
        			tariffMapping.setNgbPurposeOfInstallation(ngbPurposeOfInstallation);
        			tariffMapping.setNgbUrbanSubcategory1(ngbUrbanSubcategoryCode1);
        			tariffMapping.setNgbRuralSubcategory1(ngbRuralSubcategoryCode1);
        			tariffMapping.setNgbUrbanSubcategory2(ngbUrbanSubcategoryCode2);
        			tariffMapping.setNgbRuralSubcategory2(ngbRuralSubcategoryCode2);
        			session.save(tariffMapping);
        			session.clear();    		    				
    			}
       		}
    	}
    	
    	session.close();
    	sessionFactory.close();
    	workbook.close();
    	is.close();    	
	}
}







