package com.ccnb.bean;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "CCNBBill")
@Table(name = "ccnb_bill")
public class CCNBBill{

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "location_code")
    private String locationCode;

    @Column(name = "group_no")
    private String groupNo;

    @Column(name = "reading_diary_no")
    private String readingDiaryNo;

    @Column(name = "consumer_no")
    private String consumerNo;

    @Column(name = "bill_month")
    private String billMonth;

    @Column(name = "bill_date")
    @Temporal(TemporalType.DATE)
    private Date billDate;

    @Column(name = "due_date")
    @Temporal(TemporalType.DATE)
    private Date dueDate;

    @Column(name = "cheque_due_date")
    @Temporal(TemporalType.DATE)
    private Date chequeDueDate;

    @Column(name = "current_read_date")
    @Temporal(TemporalType.DATE)
    private Date currentReadDate;

    @Column(name = "current_read")
    private String currentRead;

    @Column(name = "previous_read")
    private String previousRead;

    @Column(name = "difference")
    private String difference;

    @Column(name = "mf")
    private String mf;

    @Column(name = "metered_unit")
    private String meteredUnit;

    @Column(name = "assessment")
    private String assessment;

    @Column(name = "total_unit")
    private String totalUnit;

    @Column(name = "gmc_unit")
    private String gmcUnit;

    @Column(name = "billed_unit")
    private String billedUnit;

    @Column(name = "billed_md")
    private String billedMD;

    @Column(name = "billed_pf")
    private String billedPF;

    @Column(name = "load_factor")
    private String loadFactor;

    @Column(name = "fixed_charge")
    private String fixedCharge;

    @Column(name = "additional_fixed_charges1")
    private String additionalFixedCharges1;

    @Column(name = "additional_fixed_charges2")
    private String additionalFixedCharges2;

    @Column(name = "energy_charge")
    private String energyCharge;

    @Column(name = "fca_charge")
    private String fcaCharge;

    @Column(name = "electricity_duty")
    private String electricityDuty;

    @Column(name = "meter_rent")
    private String meterRent;

    @Column(name = "pf_charge")
    private String pfCharge;

    @Column(name = "welding_transformer_surcharge")
    private String weldingTransformerSurcharge;

    @Column(name = "load_factor_incentive")
    private String loadFactorIncentive;

    @Column(name = "sd_interest")
    private String sdInterest;

    @Column(name = "ccb_adjustment")
    private String ccbAdjustment;

    @Column(name = "lock_credit")
    private String lockCredit;

    @Column(name = "other_adjustment")
    private String otherAdjustment;

    @Column(name = "employee_rebate")
    private String employeeRebate;

    @Column(name = "online_payment_rebate")
    private String onlinePaymentRebate;

    @Column(name = "prepaid_meter_rebate")
    private String prepaidMeterRebate;

    @Column(name = "prompt_payment_incentive")
    private String promptPaymentIncentive;

    @Column(name = "advance_payment_incentive")
    private String advancePaymentIncentive;

    @Column(name = "demand_side_incentive")
    private String demandSideIncentive;

    @Column(name = "subsidy")
    private String subsidy;

    @Column(name = "pristine_current_bill")
    private String pristineCurrentBill;

    @Column(name = "current_bill")
    private String currentBill;

    @Column(name = "arrear")
    private String arrear;

    @Column(name = "cumulative_surcharge")
    private String cumulativeSurcharge;

    @Column(name = "surcharge_demanded")
    private String surchargeDemanded;

    @Column(name = "net_bill")
    private String netBill;

    @Column(name = "asd_billed")
    private String asdBilled;

    @Column(name = "asd_arrear")
    private String asdArrear;

    @Column(name = "asd_installment")
    private String asdInstallment;

    @Column(name = "pristine_electricity_duty")
    private String pristineElectricityDuty;

    @Column(name = "pristine_net_bill")
    private String pristineNetBill;

    @Column(name = "current_bill_surcharge")
    private String currentBillSurcharge;

    @Column(name = "bill_type_code")
    private String billTypeCode;

    @Column(name = "xray_fixed_charge")
    private String xrayFixedCharge;
    
    @Column(name = "migrated")
    private boolean migrated;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLocationCode() {
		return locationCode;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	public String getGroupNo() {
		return groupNo;
	}

	public void setGroupNo(String groupNo) {
		this.groupNo = groupNo;
	}

	public String getReadingDiaryNo() {
		return readingDiaryNo;
	}

	public void setReadingDiaryNo(String readingDiaryNo) {
		this.readingDiaryNo = readingDiaryNo;
	}

	public String getConsumerNo() {
		return consumerNo;
	}

	public void setConsumerNo(String consumerNo) {
		this.consumerNo = consumerNo;
	}

	public String getBillMonth() {
		return billMonth;
	}

	public void setBillMonth(String billMonth) {
		this.billMonth = billMonth;
	}

	public Date getBillDate() {
		return billDate;
	}

	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Date getChequeDueDate() {
		return chequeDueDate;
	}

	public void setChequeDueDate(Date chequeDueDate) {
		this.chequeDueDate = chequeDueDate;
	}

	public Date getCurrentReadDate() {
		return currentReadDate;
	}

	public void setCurrentReadDate(Date currentReadDate) {
		this.currentReadDate = currentReadDate;
	}

	public String getCurrentRead() {
		return currentRead;
	}

	public void setCurrentRead(String currentRead) {
		this.currentRead = currentRead;
	}

	public String getPreviousRead() {
		return previousRead;
	}

	public void setPreviousRead(String previousRead) {
		this.previousRead = previousRead;
	}

	public String getDifference() {
		return difference;
	}

	public void setDifference(String difference) {
		this.difference = difference;
	}

	public String getMf() {
		return mf;
	}

	public void setMf(String mf) {
		this.mf = mf;
	}

	public String getMeteredUnit() {
		return meteredUnit;
	}

	public void setMeteredUnit(String meteredUnit) {
		this.meteredUnit = meteredUnit;
	}

	public String getAssessment() {
		return assessment;
	}

	public void setAssessment(String assessment) {
		this.assessment = assessment;
	}

	public String getTotalUnit() {
		return totalUnit;
	}

	public void setTotalUnit(String totalUnit) {
		this.totalUnit = totalUnit;
	}

	public String getGmcUnit() {
		return gmcUnit;
	}

	public void setGmcUnit(String gmcUnit) {
		this.gmcUnit = gmcUnit;
	}

	public String getBilledUnit() {
		return billedUnit;
	}

	public void setBilledUnit(String billedUnit) {
		this.billedUnit = billedUnit;
	}

	public String getBilledMD() {
		return billedMD;
	}

	public void setBilledMD(String billedMD) {
		this.billedMD = billedMD;
	}

	public String getBilledPF() {
		return billedPF;
	}

	public void setBilledPF(String billedPF) {
		this.billedPF = billedPF;
	}

	public String getLoadFactor() {
		return loadFactor;
	}

	public void setLoadFactor(String loadFactor) {
		this.loadFactor = loadFactor;
	}

	public String getFixedCharge() {
		return fixedCharge;
	}

	public void setFixedCharge(String fixedCharge) {
		this.fixedCharge = fixedCharge;
	}

	public String getAdditionalFixedCharges1() {
		return additionalFixedCharges1;
	}

	public void setAdditionalFixedCharges1(String additionalFixedCharges1) {
		this.additionalFixedCharges1 = additionalFixedCharges1;
	}

	public String getAdditionalFixedCharges2() {
		return additionalFixedCharges2;
	}

	public void setAdditionalFixedCharges2(String additionalFixedCharges2) {
		this.additionalFixedCharges2 = additionalFixedCharges2;
	}

	public String getEnergyCharge() {
		return energyCharge;
	}

	public void setEnergyCharge(String energyCharge) {
		this.energyCharge = energyCharge;
	}

	public String getFcaCharge() {
		return fcaCharge;
	}

	public void setFcaCharge(String fcaCharge) {
		this.fcaCharge = fcaCharge;
	}

	public String getElectricityDuty() {
		return electricityDuty;
	}

	public void setElectricityDuty(String electricityDuty) {
		this.electricityDuty = electricityDuty;
	}

	public String getMeterRent() {
		return meterRent;
	}

	public void setMeterRent(String meterRent) {
		this.meterRent = meterRent;
	}

	public String getPfCharge() {
		return pfCharge;
	}

	public void setPfCharge(String pfCharge) {
		this.pfCharge = pfCharge;
	}

	public String getWeldingTransformerSurcharge() {
		return weldingTransformerSurcharge;
	}

	public void setWeldingTransformerSurcharge(String weldingTransformerSurcharge) {
		this.weldingTransformerSurcharge = weldingTransformerSurcharge;
	}

	public String getLoadFactorIncentive() {
		return loadFactorIncentive;
	}

	public void setLoadFactorIncentive(String loadFactorIncentive) {
		this.loadFactorIncentive = loadFactorIncentive;
	}

	public String getSdInterest() {
		return sdInterest;
	}

	public void setSdInterest(String sdInterest) {
		this.sdInterest = sdInterest;
	}

	public String getCcbAdjustment() {
		return ccbAdjustment;
	}

	public void setCcbAdjustment(String ccbAdjustment) {
		this.ccbAdjustment = ccbAdjustment;
	}

	public String getLockCredit() {
		return lockCredit;
	}

	public void setLockCredit(String lockCredit) {
		this.lockCredit = lockCredit;
	}

	public String getOtherAdjustment() {
		return otherAdjustment;
	}

	public void setOtherAdjustment(String otherAdjustment) {
		this.otherAdjustment = otherAdjustment;
	}

	public String getEmployeeRebate() {
		return employeeRebate;
	}

	public void setEmployeeRebate(String employeeRebate) {
		this.employeeRebate = employeeRebate;
	}

	public String getOnlinePaymentRebate() {
		return onlinePaymentRebate;
	}

	public void setOnlinePaymentRebate(String onlinePaymentRebate) {
		this.onlinePaymentRebate = onlinePaymentRebate;
	}

	public String getPrepaidMeterRebate() {
		return prepaidMeterRebate;
	}

	public void setPrepaidMeterRebate(String prepaidMeterRebate) {
		this.prepaidMeterRebate = prepaidMeterRebate;
	}

	public String getPromptPaymentIncentive() {
		return promptPaymentIncentive;
	}

	public void setPromptPaymentIncentive(String promptPaymentIncentive) {
		this.promptPaymentIncentive = promptPaymentIncentive;
	}

	public String getAdvancePaymentIncentive() {
		return advancePaymentIncentive;
	}

	public void setAdvancePaymentIncentive(String advancePaymentIncentive) {
		this.advancePaymentIncentive = advancePaymentIncentive;
	}

	public String getDemandSideIncentive() {
		return demandSideIncentive;
	}

	public void setDemandSideIncentive(String demandSideIncentive) {
		this.demandSideIncentive = demandSideIncentive;
	}

	public String getSubsidy() {
		return subsidy;
	}

	public void setSubsidy(String subsidy) {
		this.subsidy = subsidy;
	}

	public String getPristineCurrentBill() {
		return pristineCurrentBill;
	}

	public void setPristineCurrentBill(String pristineCurrentBill) {
		this.pristineCurrentBill = pristineCurrentBill;
	}

	public String getCurrentBill() {
		return currentBill;
	}

	public void setCurrentBill(String currentBill) {
		this.currentBill = currentBill;
	}

	public String getArrear() {
		return arrear;
	}

	public void setArrear(String arrear) {
		this.arrear = arrear;
	}

	public String getCumulativeSurcharge() {
		return cumulativeSurcharge;
	}

	public void setCumulativeSurcharge(String cumulativeSurcharge) {
		this.cumulativeSurcharge = cumulativeSurcharge;
	}

	public String getSurchargeDemanded() {
		return surchargeDemanded;
	}

	public void setSurchargeDemanded(String surchargeDemanded) {
		this.surchargeDemanded = surchargeDemanded;
	}

	public String getNetBill() {
		return netBill;
	}

	public void setNetBill(String netBill) {
		this.netBill = netBill;
	}

	public String getAsdBilled() {
		return asdBilled;
	}

	public void setAsdBilled(String asdBilled) {
		this.asdBilled = asdBilled;
	}

	public String getAsdArrear() {
		return asdArrear;
	}

	public void setAsdArrear(String asdArrear) {
		this.asdArrear = asdArrear;
	}

	public String getAsdInstallment() {
		return asdInstallment;
	}

	public void setAsdInstallment(String asdInstallment) {
		this.asdInstallment = asdInstallment;
	}

	public String getPristineElectricityDuty() {
		return pristineElectricityDuty;
	}

	public void setPristineElectricityDuty(String pristineElectricityDuty) {
		this.pristineElectricityDuty = pristineElectricityDuty;
	}

	public String getPristineNetBill() {
		return pristineNetBill;
	}

	public void setPristineNetBill(String pristineNetBill) {
		this.pristineNetBill = pristineNetBill;
	}

	public String getCurrentBillSurcharge() {
		return currentBillSurcharge;
	}

	public void setCurrentBillSurcharge(String currentBillSurcharge) {
		this.currentBillSurcharge = currentBillSurcharge;
	}

	public String getBillTypeCode() {
		return billTypeCode;
	}

	public void setBillTypeCode(String billTypeCode) {
		this.billTypeCode = billTypeCode;
	}

	public String getXrayFixedCharge() {
		return xrayFixedCharge;
	}

	public void setXrayFixedCharge(String xrayFixedCharge) {
		this.xrayFixedCharge = xrayFixedCharge;
	}

	public boolean isMigrated() {
		return migrated;
	}

	public void setMigrated(boolean migrated) {
		this.migrated = migrated;
	}

	@Override
	public String toString() {
		return "CCNBBill [id=" + id + ", locationCode=" + locationCode + ", groupNo=" + groupNo + ", readingDiaryNo="
				+ readingDiaryNo + ", consumerNo=" + consumerNo + ", billMonth=" + billMonth + ", billDate=" + billDate
				+ ", dueDate=" + dueDate + ", chequeDueDate=" + chequeDueDate + ", currentReadDate=" + currentReadDate
				+ ", currentRead=" + currentRead + ", previousRead=" + previousRead + ", difference=" + difference
				+ ", mf=" + mf + ", meteredUnit=" + meteredUnit + ", assessment=" + assessment + ", totalUnit="
				+ totalUnit + ", gmcUnit=" + gmcUnit + ", billedUnit=" + billedUnit + ", billedMD=" + billedMD
				+ ", billedPF=" + billedPF + ", loadFactor=" + loadFactor + ", fixedCharge=" + fixedCharge
				+ ", additionalFixedCharges1=" + additionalFixedCharges1 + ", additionalFixedCharges2="
				+ additionalFixedCharges2 + ", energyCharge=" + energyCharge + ", fcaCharge=" + fcaCharge
				+ ", electricityDuty=" + electricityDuty + ", meterRent=" + meterRent + ", pfCharge=" + pfCharge
				+ ", weldingTransformerSurcharge=" + weldingTransformerSurcharge + ", loadFactorIncentive="
				+ loadFactorIncentive + ", sdInterest=" + sdInterest + ", ccbAdjustment=" + ccbAdjustment
				+ ", lockCredit=" + lockCredit + ", otherAdjustment=" + otherAdjustment + ", employeeRebate="
				+ employeeRebate + ", onlinePaymentRebate=" + onlinePaymentRebate + ", prepaidMeterRebate="
				+ prepaidMeterRebate + ", promptPaymentIncentive=" + promptPaymentIncentive
				+ ", advancePaymentIncentive=" + advancePaymentIncentive + ", demandSideIncentive="
				+ demandSideIncentive + ", subsidy=" + subsidy + ", pristineCurrentBill=" + pristineCurrentBill
				+ ", currentBill=" + currentBill + ", arrear=" + arrear + ", cumulativeSurcharge=" + cumulativeSurcharge
				+ ", surchargeDemanded=" + surchargeDemanded + ", netBill=" + netBill + ", asdBilled=" + asdBilled
				+ ", asdArrear=" + asdArrear + ", asdInstallment=" + asdInstallment + ", pristineElectricityDuty="
				+ pristineElectricityDuty + ", pristineNetBill=" + pristineNetBill + ", currentBillSurcharge="
				+ currentBillSurcharge + ", billTypeCode=" + billTypeCode + ", xrayFixedCharge=" + xrayFixedCharge
				+ ", migrated=" + migrated + "]";
	}       
}
