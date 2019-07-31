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
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.ccnb.bean.AgricultureBill6Monthly;
import com.ccnb.bean.CCNBAgricultureBill6Monthly;
import com.ccnb.util.PathUtil;
import com.monitorjbl.xlsx.StreamingReader;

public class AgricultureBill6MonthlyExcelMigrator {
	public static void main(String[] args) throws Exception {

		// For creating an exception Text File
		long exceptionCount = 0;
		File file = new File(PathUtil.baseExceptionFolder + "AgricultureBill6MonthlyExcelMigrationExceptionLog.txt");
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

		File excel = new File(PathUtil.baseExcelFolder + "bill_ag_3424624.xlsx");
		InputStream is = new FileInputStream(excel);
		Workbook workbook = StreamingReader.builder().rowCacheSize(100).bufferSize(4096).open(is);
		System.out.println("Excel File opened successfully!!");

		Sheet sheet = workbook.getSheetAt(0);

		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

		Session session = null;

		BigInteger big = BigInteger.ZERO;

		session = sessionFactory.openSession();
		long startTime = System.currentTimeMillis();

		SimpleDateFormat ccnbDateFormat = new SimpleDateFormat("dd-MMM-yy");
		SimpleDateFormat ngbBillMonthDateFormat = new SimpleDateFormat("MMM-yyyy");

		for (Row r : sheet) {
			CCNBAgricultureBill6Monthly ccnbBill = new CCNBAgricultureBill6Monthly();
			session.clear();
			try {
				if (r.getRowNum() == 0)
					continue;

				System.out.println();
				for (Cell c : r) {
					String cellValue = (c.getStringCellValue() == null) ? "" : c.getStringCellValue().trim();
					if (cellValue.isEmpty())
						cellValue = "0";
					// For setting values of all columns of the current row into bean object
					switch (c.getColumnIndex()) {

					case 5:
						if (cellValue != null)
							ccnbBill.setConsumerNo(cellValue);
						break;

					case 6:
						if (cellValue != null)
							ccnbBill.setLoad(new BigDecimal(cellValue));
						break;

					case 8:
						if (cellValue != null)
							ccnbBill.setSecurityDeposit(new BigDecimal(cellValue));
						break;

					case 13:
						if (cellValue != null)
							ccnbBill.setIssueDate(ccnbDateFormat.parse(cellValue));
						break;

					case 18:
						if (cellValue != null)
							ccnbBill.setActualBill(new BigDecimal(cellValue));
						break;

					case 19:
						if (cellValue != null)
							ccnbBill.setSubsidy(new BigDecimal(cellValue));
						break;

					case 20:
						if (cellValue != null)
							ccnbBill.setCurrentBill(new BigDecimal(cellValue));
						break;

					case 21:
						if (cellValue != null)
							ccnbBill.setCapacitorSurcharge(new BigDecimal(cellValue));
						break;

					case 23:
						if (cellValue != null)
							ccnbBill.setArrear(new BigDecimal(cellValue));
						break;

					case 24:
						if (cellValue != null)
							ccnbBill.setOldArrearInstallment(new BigDecimal(cellValue));
						break;

					case 25:
						if (cellValue != null) {
							final BigDecimal arrear = ccnbBill.getArrear() == null ? BigDecimal.ZERO : ccnbBill.getArrear();
							ccnbBill.setArrear(arrear.add(new BigDecimal(cellValue)));
						}
						break;

					case 27:
						if (cellValue != null)
							ccnbBill.setSurcharge(new BigDecimal(cellValue));
						break;

					case 28:
						if (cellValue != null)
							ccnbBill.setNetBill(new BigDecimal(cellValue));
						break;

					case 29:
						if (cellValue != null)
							ccnbBill.setDueDate(ccnbDateFormat.parse(cellValue));
						if (ccnbBill.getDueDate() != null) {
							Calendar cal = Calendar.getInstance();
							cal.setTime(ccnbBill.getDueDate());
							cal.add(Calendar.DAY_OF_MONTH, -3);
							ccnbBill.setChequeDueDate(cal.getTime());
						}
						break;

					case 38:
						if (cellValue != null)
							ccnbBill.setCreatedOn(ccnbDateFormat.parse(cellValue));
						break;

					case 40:
						if (cellValue != null)
							ccnbBill.setStartBillMonth(cellValue);

						if (ccnbBill.getStartBillMonth() != null) {
							final Date monthDate = ngbBillMonthDateFormat.parse(ccnbBill.getStartBillMonth());
							Calendar cal = Calendar.getInstance();
							cal.setTime(monthDate);
							cal.add(Calendar.MONTH, 5);
							ccnbBill.setEndBillMonth(ngbBillMonthDateFormat.format(cal.getTime()).toUpperCase());
						}
						break;

					case 46:
						if (cellValue != null)
							ccnbBill.setEnergyCharge(new BigDecimal(cellValue));
						break;

					case 47:
						if (cellValue != null)
							ccnbBill.setFixedCharge(new BigDecimal(cellValue));
						break;
					}
					System.out.print(cellValue + " ");
				}

				ccnbBill.setUnit(BigDecimal.ZERO);
				ccnbBill.setFca(BigDecimal.ZERO);
				ccnbBill.setSdInterest(BigDecimal.ZERO);
				ccnbBill.setOldArrearInstallmentSubsidy(BigDecimal.ZERO);
				ccnbBill.setCumulativeSurcharge(BigDecimal.ZERO);
				ccnbBill.setSurchargeArrear(BigDecimal.ZERO);
				ccnbBill.setMigrated(false);

				// Saving the created bean Object

				session.beginTransaction();
				session.flush();
				session.save(ccnbBill);
				session.getTransaction().commit();
				big = big.add(BigInteger.ONE);
			} catch (Exception e) {
				++exceptionCount;
				writer.println();
				writer.println("***********EXCEPTION NUMBER " + exceptionCount + "***********" + "Occured on: " + new Date());
				writer.println("************CCNB BILL: " + ccnbBill);
				writer.println("Root cause : ");
				e.printStackTrace(writer);
				e.printStackTrace();
				session.getTransaction().rollback();
				continue;
			}

		}
		session.close();

		long endTime = System.currentTimeMillis();
		long seconds = (endTime - startTime) / 1000;
		long minutes = seconds / 60;
		seconds -= minutes * 60;

		System.out.println();
		System.out.println();
		System.out.println("MIGRATION OF AGRICULTURE_6_MONTHLY_BILL_EXCEL SUCCESSFULLY DONE!!!!");
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
