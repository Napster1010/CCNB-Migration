package com.ccnb.bean;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity(name = "Bill")
@Table(name = "bill")
public class Bill{

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
    private BigDecimal currentRead;

    @Column(name = "previous_read")
    private BigDecimal previousRead;

    @Column(name = "difference")
    private BigDecimal difference;

    @Column(name = "mf")
    private BigDecimal mf;

    @Column(name = "metered_unit")
    private BigDecimal meteredUnit;

    @Column(name = "assessment")
    private BigDecimal assessment;

    @Column(name = "total_unit")
    private BigDecimal totalUnit;

    @Column(name = "gmc_unit")
    private BigDecimal gmcUnit;

    @Column(name = "billed_unit")
    private BigDecimal billedUnit;

    @Column(name = "billed_md")
    private BigDecimal billedMD;

    @Column(name = "billed_pf")
    private BigDecimal billedPF;

    @Column(name = "load_factor")
    private BigDecimal loadFactor;

    @Column(name = "fixed_charge")
    private BigDecimal fixedCharge;

    @Column(name = "additional_fixed_charges1")
    private BigDecimal additionalFixedCharges1;

    @Column(name = "additional_fixed_charges2")
    private BigDecimal additionalFixedCharges2;

    @Column(name = "energy_charge")
    private BigDecimal energyCharge;

    @Column(name = "fca_charge")
    private BigDecimal fcaCharge;

    @Column(name = "electricity_duty")
    private BigDecimal electricityDuty;

    @Column(name = "meter_rent")
    private BigDecimal meterRent;

    @Column(name = "pf_charge")
    private BigDecimal pfCharge;

    @Column(name = "welding_transformer_surcharge")
    private BigDecimal weldingTransformerSurcharge;

    @Column(name = "load_factor_incentive")
    private BigDecimal loadFactorIncentive;

    @Column(name = "sd_interest")
    private BigDecimal sdInterest;

    @Column(name = "ccb_adjustment")
    private BigDecimal ccbAdjustment;

    @Column(name = "lock_credit")
    private BigDecimal lockCredit;

    @Column(name = "other_adjustment")
    private BigDecimal otherAdjustment;

    @Column(name = "employee_rebate")
    private BigDecimal employeeRebate;

    @Column(name = "online_payment_rebate")
    private BigDecimal onlinePaymentRebate;

    @Column(name = "prepaid_meter_rebate")
    private BigDecimal prepaidMeterRebate;

    @Column(name = "prompt_payment_incentive")
    private BigDecimal promptPaymentIncentive;

    @Column(name = "advance_payment_incentive")
    private BigDecimal advancePaymentIncentive;

    @Column(name = "demand_side_incentive")
    private BigDecimal demandSideIncentive;

    @Column(name = "subsidy")
    private BigDecimal subsidy;

    @Column(name = "pristine_current_bill")
    private BigDecimal pristineCurrentBill;

    @Column(name = "current_bill")
    private BigDecimal currentBill;

    @Column(name = "arrear")
    private BigDecimal arrear;

    @Column(name = "cumulative_surcharge")
    private BigDecimal cumulativeSurcharge;

    @Column(name = "surcharge_demanded")
    private BigDecimal surchargeDemanded;

    @Column(name = "net_bill")
    private BigDecimal netBill;

    @Column(name = "asd_billed")
    private BigDecimal asdBilled;

    @Column(name = "asd_arrear")
    private BigDecimal asdArrear;

    @Column(name = "asd_installment")
    private BigDecimal asdInstallment;

    @Column(name = "deleted")
    private boolean deleted;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedOn;

    @Column(name = "pristine_electricity_duty")
    private BigDecimal pristineElectricityDuty;

    @Column(name = "pristine_net_bill")
    private BigDecimal pristineNetBill;

    @Column(name = "current_bill_surcharge")
    private BigDecimal currentBillSurcharge;

    @Column(name = "bill_type_code")
    private String billTypeCode;

    @Column(name = "xray_fixed_charge")
    private BigDecimal xrayFixedCharge;

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

    
    public BigDecimal getCurrentRead() {
        if(this.currentRead != null){
            return new BigDecimal(String.valueOf(this.currentRead.doubleValue()));
        }
        return currentRead;
    }

    
    public void setCurrentRead(BigDecimal currentRead) {
        this.currentRead = currentRead;
    }

    
    public BigDecimal getPreviousRead() {
        if(this.previousRead != null){
            return new BigDecimal(String.valueOf(this.previousRead.doubleValue()));
        }
        return previousRead;
    }

    
    public void setPreviousRead(BigDecimal previousRead) {
        this.previousRead = previousRead;
    }

    
    public BigDecimal getDifference() {
        if(this.difference != null){
            return new BigDecimal(String.valueOf(this.difference.doubleValue()));
        }
        return difference;
    }

    
    public void setDifference(BigDecimal difference) {
        this.difference = difference;
    }

    
    public BigDecimal getMf() {
        if(this.mf != null){
            return new BigDecimal(String.valueOf(this.mf.doubleValue()));
        }
        return mf;
    }

    
    public void setMf(BigDecimal mf) {
        this.mf = mf;
    }

    
    public BigDecimal getMeteredUnit() {
        if(this.meteredUnit != null){
            double temp = this.meteredUnit.doubleValue();
            return new BigDecimal(String.valueOf(temp));
        }
        return meteredUnit;
    }

    
    public void setMeteredUnit(BigDecimal meteredUnit) {
        this.meteredUnit = meteredUnit;
    }

    
    public BigDecimal getAssessment() {
        if(this.assessment != null){
            return new BigDecimal(String.valueOf(this.assessment.doubleValue()));
        }
        return assessment;
    }

    
    public void setAssessment(BigDecimal assessment) {
        this.assessment = assessment;
    }

    
    public BigDecimal getTotalUnit() {
        if(this.totalUnit != null){
            double temp = this.totalUnit.doubleValue();
            return new BigDecimal(String.valueOf(temp));
        }
        return totalUnit;
    }

    
    public void setTotalUnit(BigDecimal totalUnit) {
        this.totalUnit = totalUnit;
    }

    
    public BigDecimal getGmcUnit() {
        if(this.gmcUnit != null){
            double temp = this.gmcUnit.doubleValue();
            return new BigDecimal(String.valueOf(temp));
        }
        return gmcUnit;
    }

    
    public void setGmcUnit(BigDecimal gmcUnit) {
        this.gmcUnit = gmcUnit;
    }

    
    public BigDecimal getBilledUnit() {
        if(this.billedUnit != null){
            double temp = this.billedUnit.doubleValue();
            return new BigDecimal(String.valueOf(temp));
        }
        return billedUnit;
    }

    
    public void setBilledUnit(BigDecimal billedUnit) {
        this.billedUnit = billedUnit;
    }

    
    public BigDecimal getBilledMD() {
        if(this.billedMD != null){
            double temp = this.billedMD.doubleValue();
            return new BigDecimal(String.valueOf(temp));
        }
        return billedMD;
    }

    
    public void setBilledMD(BigDecimal billedMD) {
        this.billedMD = billedMD;
    }

    
    public BigDecimal getBilledPF() {
        if(this.billedPF != null){
            double temp = this.billedPF.doubleValue();
            return new BigDecimal(String.valueOf(temp));
        }
        return billedPF;
    }

    
    public void setBilledPF(BigDecimal billedPF) {
        this.billedPF = billedPF;
    }

    
    public BigDecimal getLoadFactor() {
        if(this.loadFactor != null){
            double temp = this.loadFactor.doubleValue();
            return new BigDecimal(String.valueOf(temp));
        }
        return loadFactor;
    }

    
    public void setLoadFactor(BigDecimal loadFactor) {
        this.loadFactor = loadFactor;
    }

    
    public BigDecimal getFixedCharge() {
        if(this.fixedCharge != null){
            double temp = this.fixedCharge.doubleValue();
            return new BigDecimal(String.valueOf(temp));
        }
        return fixedCharge;
    }

    
    public void setFixedCharge(BigDecimal fixedCharge) {
        this.fixedCharge = fixedCharge;
    }

    
    public BigDecimal getAdditionalFixedCharges1() {
        if(this.additionalFixedCharges1 != null){
            double temp = this.additionalFixedCharges1.doubleValue();
            return new BigDecimal(String.valueOf(temp));
        }
        return additionalFixedCharges1;
    }

    
    public void setAdditionalFixedCharges1(BigDecimal additionalFixedCharges1) {
        this.additionalFixedCharges1 = additionalFixedCharges1;
    }

    
    public BigDecimal getAdditionalFixedCharges2() {
        if(this.additionalFixedCharges2 != null){
            double temp = this.additionalFixedCharges2.doubleValue();
            return new BigDecimal(String.valueOf(temp));
        }
        return additionalFixedCharges2;
    }

    
    public void setAdditionalFixedCharges2(BigDecimal additionalFixedCharges2) {
        this.additionalFixedCharges2 = additionalFixedCharges2;
    }

    
    public BigDecimal getEnergyCharge() {
        if(this.energyCharge != null){
            double temp = this.energyCharge.doubleValue();
            return new BigDecimal(String.valueOf(temp));
        }
        return energyCharge;
    }

    
    public void setEnergyCharge(BigDecimal energyCharge) {
        this.energyCharge = energyCharge;
    }

    
    public BigDecimal getFcaCharge() {
        if(this.fcaCharge != null){
            double temp = this.fcaCharge.doubleValue();
            return new BigDecimal(String.valueOf(temp));
        }
        return fcaCharge;
    }

    
    public void setFcaCharge(BigDecimal fcaCharge) {
        this.fcaCharge = fcaCharge;
    }

    
    public BigDecimal getElectricityDuty() {
        if(this.electricityDuty != null){
            double temp = this.electricityDuty.doubleValue();
            return new BigDecimal(String.valueOf(temp));
        }
        return electricityDuty;
    }

    
    public void setElectricityDuty(BigDecimal electricityDuty) {
        this.electricityDuty = electricityDuty;
    }

    
    public BigDecimal getMeterRent() {
        if(this.meterRent != null){
            double temp = this.meterRent.doubleValue();
            return new BigDecimal(String.valueOf(temp));
        }
        return meterRent;
    }

    
    public void setMeterRent(BigDecimal meterRent) {
        this.meterRent = meterRent;
    }

    public BigDecimal getPfCharge() {
        if(this.pfCharge != null){
            double temp = this.pfCharge.doubleValue();
            return new BigDecimal(String.valueOf(temp));
        }
        return pfCharge;
    }

    public void setPfCharge(BigDecimal pfCharge) {
        this.pfCharge = pfCharge;
    }

    
    public BigDecimal getWeldingTransformerSurcharge() {
        if(this.weldingTransformerSurcharge != null){
            double temp = this.weldingTransformerSurcharge.doubleValue();
            return new BigDecimal(String.valueOf(temp));
        }
        return weldingTransformerSurcharge;
    }

    
    public void setWeldingTransformerSurcharge(BigDecimal weldingTransformerSurcharge) {
        this.weldingTransformerSurcharge = weldingTransformerSurcharge;
    }

    
    public BigDecimal getLoadFactorIncentive() {
        if(this.loadFactorIncentive != null){
            double temp = this.loadFactorIncentive.doubleValue();
            return new BigDecimal(String.valueOf(temp));
        }
        return loadFactorIncentive;
    }

    
    public void setLoadFactorIncentive(BigDecimal loadFactorIncentive) {
        this.loadFactorIncentive = loadFactorIncentive;
    }

    
    public BigDecimal getSdInterest() {
        if(this.sdInterest != null){
            double temp = this.sdInterest.doubleValue();
            return new BigDecimal(String.valueOf(temp));
        }
        return sdInterest;
    }

    
    public void setSdInterest(BigDecimal sdInterest) {
        this.sdInterest = sdInterest;
    }

    
    public BigDecimal getCcbAdjustment() {
        if(this.ccbAdjustment != null){
            double temp = this.ccbAdjustment.doubleValue();
            return new BigDecimal(String.valueOf(temp));
        }
        return ccbAdjustment;
    }

    
    public void setCcbAdjustment(BigDecimal ccbAdjustment) {
        this.ccbAdjustment = ccbAdjustment;
    }

    
    public BigDecimal getLockCredit() {
        if(this.lockCredit != null){
            double temp = this.lockCredit.doubleValue();
            return new BigDecimal(String.valueOf(temp));
        }
        return lockCredit;
    }

    
    public void setLockCredit(BigDecimal lockCredit) {
        this.lockCredit = lockCredit;
    }

    
    public BigDecimal getOtherAdjustment() {
        if(this.otherAdjustment != null){
            double temp = this.otherAdjustment.doubleValue();
            return new BigDecimal(String.valueOf(temp));
        }
        return otherAdjustment;
    }

    
    public void setOtherAdjustment(BigDecimal otherAdjustment) {
        this.otherAdjustment = otherAdjustment;
    }

    
    public BigDecimal getEmployeeRebate() {
        if(this.employeeRebate != null){
            double temp = this.employeeRebate.doubleValue();
            return new BigDecimal(String.valueOf(temp));
        }
        return employeeRebate;
    }

    
    public void setEmployeeRebate(BigDecimal employeeRebate) {
        this.employeeRebate = employeeRebate;
    }

    
    public BigDecimal getOnlinePaymentRebate() {
        if(this.onlinePaymentRebate != null){
            double temp = this.onlinePaymentRebate.doubleValue();
            return new BigDecimal(String.valueOf(temp));
        }
        return onlinePaymentRebate;
    }

    
    public void setOnlinePaymentRebate(BigDecimal onlinePaymentRebate) {
        this.onlinePaymentRebate = onlinePaymentRebate;
    }

    
    public BigDecimal getPrepaidMeterRebate() {
        if(this.prepaidMeterRebate != null){
            double temp = this.prepaidMeterRebate.doubleValue();
            return new BigDecimal(String.valueOf(temp));
        }
        return prepaidMeterRebate;
    }

    
    public void setPrepaidMeterRebate(BigDecimal prepaidMeterRebate) {
        this.prepaidMeterRebate = prepaidMeterRebate;
    }

    
    public BigDecimal getPromptPaymentIncentive() {
        if(this.promptPaymentIncentive != null){
            double temp = this.promptPaymentIncentive.doubleValue();
            return new BigDecimal(String.valueOf(temp));
        }
        return promptPaymentIncentive;
    }

    
    public void setPromptPaymentIncentive(BigDecimal promptPaymentIncentive) {
        this.promptPaymentIncentive = promptPaymentIncentive;
    }

    
    public BigDecimal getAdvancePaymentIncentive() {
        if(this.advancePaymentIncentive != null){
            double temp = this.advancePaymentIncentive.doubleValue();
            return new BigDecimal(String.valueOf(temp));
        }
        return advancePaymentIncentive;
    }

    
    public void setAdvancePaymentIncentive(BigDecimal advancePaymentIncentive) {
        this.advancePaymentIncentive = advancePaymentIncentive;
    }

    
    public BigDecimal getDemandSideIncentive() {
        if(this.demandSideIncentive != null){
            double temp = this.demandSideIncentive.doubleValue();
            return new BigDecimal(String.valueOf(temp));
        }
        return demandSideIncentive;
    }

    
    public void setDemandSideIncentive(BigDecimal demandSideIncentive) {
        this.demandSideIncentive = demandSideIncentive;
    }

    
    public BigDecimal getSubsidy() {
        if(this.subsidy != null){
            double temp = this.subsidy.doubleValue();
            return new BigDecimal(String.valueOf(temp));
        }
        return subsidy;
    }

    
    public void setSubsidy(BigDecimal subsidy) {
        this.subsidy = subsidy;
    }

    
    public BigDecimal getPristineCurrentBill() {
        if(this.pristineCurrentBill != null){
            return new BigDecimal(String.valueOf(this.pristineCurrentBill.doubleValue()));
        }
        return pristineCurrentBill;
    }

    
    public void setPristineCurrentBill(BigDecimal pristineCurrentBill) {
        this.pristineCurrentBill = pristineCurrentBill;
    }

    
    public BigDecimal getCurrentBill() {
        if(this.currentBill != null){
            double temp = this.currentBill.doubleValue();
            return new BigDecimal(String.valueOf(temp));
        }
        return currentBill;
    }

    
    public void setCurrentBill(BigDecimal currentBill) {
        this.currentBill = currentBill;
    }

    
    public BigDecimal getArrear() {
        if(this.arrear != null){
            double temp = this.arrear.doubleValue();
            return new BigDecimal(String.valueOf(temp));
        }
        return arrear;
    }

    
    public void setArrear(BigDecimal arrear) {
        this.arrear = arrear;
    }

    
    public BigDecimal getCumulativeSurcharge() {
        if(this.cumulativeSurcharge != null){
            double temp = this.cumulativeSurcharge.doubleValue();
            return new BigDecimal(String.valueOf(temp));
        }
        return cumulativeSurcharge;
    }

    
    public void setCumulativeSurcharge(BigDecimal cumulativeSurcharge) {
        this.cumulativeSurcharge = cumulativeSurcharge;
    }

    
    public BigDecimal getSurchargeDemanded() {
        if(this.surchargeDemanded != null){
            double temp = this.surchargeDemanded.doubleValue();
            return new BigDecimal(String.valueOf(temp));
        }
        return surchargeDemanded;
    }

    
    public void setSurchargeDemanded(BigDecimal surchargeDemanded) {
        this.surchargeDemanded = surchargeDemanded;
    }

    
    public BigDecimal getNetBill() {
        if(this.netBill != null){
            double temp = this.netBill.doubleValue();
            return new BigDecimal(String.valueOf(temp));
        }
        return netBill;
    }

    
    public void setNetBill(BigDecimal netBill) {
        this.netBill = netBill;
    }

    
    public BigDecimal getAsdBilled() {
        if(this.asdBilled != null){
            double temp = this.asdBilled.doubleValue();
            return new BigDecimal(String.valueOf(temp));
        }
        return asdBilled;
    }

    
    public void setAsdBilled(BigDecimal asdBilled) {
        this.asdBilled = asdBilled;
    }

    
    public BigDecimal getAsdArrear() {
        if(this.asdArrear != null){
            double temp = this.asdArrear.doubleValue();
            return new BigDecimal(String.valueOf(temp));
        }
        return asdArrear;
    }

    
    public void setAsdArrear(BigDecimal asdArrear) {
        this.asdArrear = asdArrear;
    }

    
    public BigDecimal getAsdInstallment() {
        if(this.asdInstallment != null){
            double temp = this.asdInstallment.doubleValue();
            return new BigDecimal(String.valueOf(temp));
        }
        return asdInstallment;
    }

    
    public void setAsdInstallment(BigDecimal asdInstallment) {
        this.asdInstallment = asdInstallment;
    }

    
    public boolean isDeleted() {
        return deleted;
    }

    
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    
    public String getCreatedBy() {
        return createdBy;
    }

    
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    
    public Date getCreatedOn() {
        return createdOn;
    }

    
    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    
    public String getUpdatedBy() {
        return updatedBy;
    }

    
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    
    public Date getUpdatedOn() {
        return updatedOn;
    }

    
    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    
    public BigDecimal getPristineElectricityDuty() {
        if(this.pristineElectricityDuty != null){
            double temp = this.pristineElectricityDuty.doubleValue();
            return new BigDecimal(String.valueOf(temp));
        }
        return pristineElectricityDuty;
    }

    
    public void setPristineElectricityDuty(BigDecimal pristineElectricityDuty) {
        this.pristineElectricityDuty = pristineElectricityDuty;
    }

    
    public BigDecimal getPristineNetBill() {
        if(this.pristineNetBill != null){
            double temp = this.pristineNetBill.doubleValue();
            return new BigDecimal(String.valueOf(temp));
        }
        return pristineNetBill;
    }

    
    public void setPristineNetBill(BigDecimal pristineNetBill) {
        this.pristineNetBill = pristineNetBill;
    }

    
    public BigDecimal getCurrentBillSurcharge() {
        if(this.currentBillSurcharge != null){
            double temp = this.currentBillSurcharge.doubleValue();
            return new BigDecimal(String.valueOf(temp));
        }
        return currentBillSurcharge;
    }

    
    public void setCurrentBillSurcharge(BigDecimal currentBillSurcharge) {
        this.currentBillSurcharge = currentBillSurcharge;
    }
    
    public String getBillTypeCode() {
        return billTypeCode;
    }

    
    public void setBillTypeCode(String billTypeCode) {
        this.billTypeCode = billTypeCode;
    }

    
    public BigDecimal getXrayFixedCharge() {
        if(this.xrayFixedCharge != null){
            double temp = this.xrayFixedCharge.doubleValue();
            return new BigDecimal(String.valueOf(temp));
        }
        return xrayFixedCharge;
    }

    
    public void setXrayFixedCharge(BigDecimal xrayFixedCharge) {
        this.xrayFixedCharge = xrayFixedCharge;
    }

	@Override
	public String toString() {
		return "Bill [id=" + id + ", locationCode=" + locationCode + ", groupNo=" + groupNo + ", readingDiaryNo="
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
				+ ", asdArrear=" + asdArrear + ", asdInstallment=" + asdInstallment + ", deleted=" + deleted
				+ ", createdBy=" + createdBy + ", createdOn=" + createdOn + ", updatedBy=" + updatedBy + ", updatedOn="
				+ updatedOn + ", pristineElectricityDuty=" + pristineElectricityDuty + ", pristineNetBill="
				+ pristineNetBill + ", currentBillSurcharge=" + currentBillSurcharge + ", billTypeCode=" + billTypeCode
				+ ", xrayFixedCharge=" + xrayFixedCharge + "]";
	}

}
