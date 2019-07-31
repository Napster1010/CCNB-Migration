package com.ccnb.bean;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Table(name = "ccnb_agriculture_bill_6monthly")
@Entity(name = "CCNBAgricultureBill6Monthly")
public class CCNBAgricultureBill6Monthly {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@Column(name = "consumer_no")
	private String consumerNo;

	@Column(name = "start_bill_month")
	private String startBillMonth;

	@Column(name = "end_bill_month")
	private String endBillMonth;

	@Column(name = "issue_date")
	@Temporal(TemporalType.DATE)
	private Date issueDate;

	@Column(name = "due_date")
	@Temporal(TemporalType.DATE)
	private Date dueDate;

	@Column(name = "cheque_due_date")
	@Temporal(TemporalType.DATE)
	private Date chequeDueDate;

	@Column(name = "load")
	private BigDecimal load;

	@Column(name = "unit")
	private BigDecimal unit;

	@Column(name = "energy_charge")
	private BigDecimal energyCharge;

	@Column(name = "fixed_charge")
	private BigDecimal fixedCharge;

	@Column(name = "fca")
	private BigDecimal fca;

	@Column(name = "capacitor_surcharge")
	private BigDecimal capacitorSurcharge;

	@Column(name = "actual_bill")
	private BigDecimal actualBill;

	@Column(name = "subsidy")
	private BigDecimal subsidy;

	@Column(name = "current_bill")
	private BigDecimal currentBill;

	@Column(name = "security_deposit")
	private BigDecimal securityDeposit;

	@Column(name = "sd_interest")
	private BigDecimal sdInterest;

	@Column(name = "arrear")
	private BigDecimal arrear;

	@Column(name = "old_arrear_installment")
	private BigDecimal oldArrearInstallment;

	@Column(name = "old_arrear_installment_subsidy")
	private BigDecimal oldArrearInstallmentSubsidy;

	@Column(name = "surcharge")
	private BigDecimal surcharge;

	@Column(name = "cumulative_surcharge")
	private BigDecimal cumulativeSurcharge;

	@Column(name = "net_bill")
	private BigDecimal netBill;

	@Column(name = "surcharge_arrear")
	private BigDecimal surchargeArrear;

	@Column(name = "created_on")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdOn;

	@Column(name = "migrated")
	private boolean migrated;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getConsumerNo() {
		return consumerNo;
	}

	public void setConsumerNo(String consumerNo) {
		this.consumerNo = consumerNo;
	}

	public String getStartBillMonth() {
		return startBillMonth;
	}

	public void setStartBillMonth(String startBillMonth) {
		this.startBillMonth = startBillMonth;
	}

	public String getEndBillMonth() {
		return endBillMonth;
	}

	public void setEndBillMonth(String endBillMonth) {
		this.endBillMonth = endBillMonth;
	}

	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
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

	public BigDecimal getLoad() {
		if (this.load != null) {
			return new BigDecimal(String.valueOf(this.load.doubleValue()));
		}
		return load;
	}

	public void setLoad(BigDecimal load) {
		this.load = load;
	}

	public BigDecimal getUnit() {
		if (this.unit != null) {
			return new BigDecimal(String.valueOf(this.unit.doubleValue()));
		}
		return unit;
	}

	public void setUnit(BigDecimal unit) {
		this.unit = unit;
	}

	public BigDecimal getEnergyCharge() {
		if (this.energyCharge != null) {
			return new BigDecimal(String.valueOf(this.energyCharge.doubleValue()));
		}
		return energyCharge;
	}

	public void setEnergyCharge(BigDecimal energyCharge) {
		this.energyCharge = energyCharge;
	}

	public BigDecimal getFixedCharge() {
		if (this.fixedCharge != null) {
			return new BigDecimal(String.valueOf(this.fixedCharge.doubleValue()));
		}
		return fixedCharge;
	}

	public void setFixedCharge(BigDecimal fixedCharge) {
		this.fixedCharge = fixedCharge;
	}

	public BigDecimal getFca() {
		if (this.fca != null) {
			return new BigDecimal(String.valueOf(this.fca.doubleValue()));
		}
		return fca;
	}

	public void setFca(BigDecimal fca) {
		this.fca = fca;
	}

	public BigDecimal getCapacitorSurcharge() {
		if (this.capacitorSurcharge != null) {
			return new BigDecimal(String.valueOf(this.capacitorSurcharge.doubleValue()));
		}
		return capacitorSurcharge;
	}

	public void setCapacitorSurcharge(BigDecimal capacitorSurcharge) {
		this.capacitorSurcharge = capacitorSurcharge;
	}

	public BigDecimal getActualBill() {
		if (this.actualBill != null) {
			return new BigDecimal(String.valueOf(this.actualBill.doubleValue()));
		}
		return actualBill;
	}

	public void setActualBill(BigDecimal actualBill) {
		this.actualBill = actualBill;
	}

	public BigDecimal getSubsidy() {
		if (this.subsidy != null) {
			return new BigDecimal(String.valueOf(this.subsidy.doubleValue()));
		}
		return subsidy;
	}

	public void setSubsidy(BigDecimal subsidy) {
		this.subsidy = subsidy;
	}

	public BigDecimal getCurrentBill() {
		if (this.currentBill != null) {
			return new BigDecimal(String.valueOf(this.currentBill.doubleValue()));
		}
		return currentBill;
	}

	public void setCurrentBill(BigDecimal currentBill) {
		this.currentBill = currentBill;
	}

	public BigDecimal getSecurityDeposit() {
		if (this.securityDeposit != null) {
			return new BigDecimal(String.valueOf(this.securityDeposit.doubleValue()));
		}
		return securityDeposit;
	}

	public void setSecurityDeposit(BigDecimal securityDeposit) {
		this.securityDeposit = securityDeposit;
	}

	public BigDecimal getSdInterest() {
		if (this.sdInterest != null) {
			return new BigDecimal(String.valueOf(this.sdInterest.doubleValue()));
		}
		return sdInterest;
	}

	public void setSdInterest(BigDecimal sdInterest) {
		this.sdInterest = sdInterest;
	}

	public BigDecimal getArrear() {
		if (this.arrear != null) {
			return new BigDecimal(String.valueOf(this.arrear.doubleValue()));
		}
		return arrear;
	}

	public void setArrear(BigDecimal arrear) {
		this.arrear = arrear;
	}

	public BigDecimal getOldArrearInstallment() {
		if (this.oldArrearInstallment != null) {
			return new BigDecimal(String.valueOf(this.oldArrearInstallment.doubleValue()));
		}
		return oldArrearInstallment;
	}

	public void setOldArrearInstallment(BigDecimal oldArrearInstallment) {
		this.oldArrearInstallment = oldArrearInstallment;
	}

	public BigDecimal getOldArrearInstallmentSubsidy() {
		if (this.oldArrearInstallmentSubsidy != null) {
			return new BigDecimal(String.valueOf(this.oldArrearInstallmentSubsidy.doubleValue()));
		}
		return oldArrearInstallmentSubsidy;
	}

	public void setOldArrearInstallmentSubsidy(BigDecimal oldArrearInstallmentSubsidy) {
		this.oldArrearInstallmentSubsidy = oldArrearInstallmentSubsidy;
	}

	public BigDecimal getSurcharge() {
		if (this.surcharge != null) {
			return new BigDecimal(String.valueOf(this.surcharge.doubleValue()));
		}
		return surcharge;
	}

	public void setSurcharge(BigDecimal surcharge) {
		this.surcharge = surcharge;
	}

	public BigDecimal getCumulativeSurcharge() {
		if (this.cumulativeSurcharge != null) {
			return new BigDecimal(String.valueOf(this.cumulativeSurcharge.doubleValue()));
		}
		return cumulativeSurcharge;
	}

	public void setCumulativeSurcharge(BigDecimal cumulativeSurcharge) {
		this.cumulativeSurcharge = cumulativeSurcharge;
	}

	public BigDecimal getNetBill() {
		if (this.netBill != null) {
			return new BigDecimal(String.valueOf(this.netBill.doubleValue()));
		}
		return netBill;
	}

	public void setNetBill(BigDecimal netBill) {
		this.netBill = netBill;
	}

	public BigDecimal getSurchargeArrear() {
		if (this.surchargeArrear != null) {
			return new BigDecimal(String.valueOf(this.surchargeArrear.doubleValue()));
		}
		return surchargeArrear;
	}

	public void setSurchargeArrear(BigDecimal surchargeArrear) {
		this.surchargeArrear = surchargeArrear;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public boolean isMigrated() {
		return migrated;
	}

	public void setMigrated(boolean migrated) {
		this.migrated = migrated;
	}

	@Override
	public String toString() {
		return "CCNBAgricultureBill6Monthly [id=" + id + ", consumerNo=" + consumerNo + ", startBillMonth=" + startBillMonth + ", endBillMonth=" + endBillMonth + ", issueDate=" + issueDate + ", dueDate=" + dueDate + ", chequeDueDate=" + chequeDueDate + ", load=" + load + ", unit=" + unit
		        + ", energyCharge=" + energyCharge + ", fixedCharge=" + fixedCharge + ", fca=" + fca + ", capacitorSurcharge=" + capacitorSurcharge + ", actualBill=" + actualBill + ", subsidy=" + subsidy + ", currentBill=" + currentBill + ", securityDeposit=" + securityDeposit + ", sdInterest="
		        + sdInterest + ", arrear=" + arrear + ", oldArrearInstallment=" + oldArrearInstallment + ", oldArrearInstallmentSubsidy=" + oldArrearInstallmentSubsidy + ", surcharge=" + surcharge + ", cumulativeSurcharge=" + cumulativeSurcharge + ", netBill=" + netBill + ", surchargeArrear="
		        + surchargeArrear + ", createdOn=" + createdOn + ", migrated=" + migrated + "]";
	}
}
