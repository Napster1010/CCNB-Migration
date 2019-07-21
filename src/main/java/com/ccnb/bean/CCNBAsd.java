package com.ccnb.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="CCNBAsd")
@Table(name="ccnb_asd")
public class CCNBAsd {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "consumer_no")
	private String consumerNo;
	
	@Column(name = "average_consumption")
	private String averageConsumption;
	
	@Column(name = "average_bill")
	private String averageBill;

	@Column(name = "is_defaulter")
	private boolean isDefaulter;
	
	@Column(name = "bill_month")
	private String billMonth;

	@Column(name = "required_security_deposit")	
	private String requiredSecurityDeposit;
	
	@Column(name = "existing_security_deposit")
	private String existingSecurityDeposit;
	
	@Column(name = "security_deposit_demand")
	private String securityDepositDemand;
	
	@Column(name = "installment_1")
	private String installment1;
	
	@Column(name = "installment_2")
	private String installment2;
	
	@Column(name = "installment_3")
	private String installment3;
	
	@Column(name = "period")
	private String period;
	
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

	public String getAverageConsumption() {
		return averageConsumption;
	}

	public void setAverageConsumption(String averageConsumption) {
		this.averageConsumption = averageConsumption;
	}

	public String getAverageBill() {
		return averageBill;
	}

	public void setAverageBill(String averageBill) {
		this.averageBill = averageBill;
	}

	public boolean isDefaulter() {
		return isDefaulter;
	}

	public void setDefaulter(boolean isDefaulter) {
		this.isDefaulter = isDefaulter;
	}

	public String getBillMonth() {
		return billMonth;
	}

	public void setBillMonth(String billMonth) {
		this.billMonth = billMonth;
	}

	public String getRequiredSecurityDeposit() {
		return requiredSecurityDeposit;
	}

	public void setRequiredSecurityDeposit(String requiredSecurityDeposit) {
		this.requiredSecurityDeposit = requiredSecurityDeposit;
	}

	public String getExistingSecurityDeposit() {
		return existingSecurityDeposit;
	}

	public void setExistingSecurityDeposit(String existingSecurityDeposit) {
		this.existingSecurityDeposit = existingSecurityDeposit;
	}

	public String getSecurityDepositDemand() {
		return securityDepositDemand;
	}

	public void setSecurityDepositDemand(String securityDepositDemand) {
		this.securityDepositDemand = securityDepositDemand;
	}

	public String getInstallment1() {
		return installment1;
	}

	public void setInstallment1(String installment1) {
		this.installment1 = installment1;
	}

	public String getInstallment2() {
		return installment2;
	}

	public void setInstallment2(String installment2) {
		this.installment2 = installment2;
	}

	public String getInstallment3() {
		return installment3;
	}

	public void setInstallment3(String installment3) {
		this.installment3 = installment3;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public boolean isMigrated() {
		return migrated;
	}

	public void setMigrated(boolean migrated) {
		this.migrated = migrated;
	}

	@Override
	public String toString() {
		return "CCNBAsd [id=" + id + ", consumerNo=" + consumerNo + ", averageConsumption=" + averageConsumption
				+ ", averageBill=" + averageBill + ", isDefaulter=" + isDefaulter + ", billMonth=" + billMonth
				+ ", requiredSecurityDeposit=" + requiredSecurityDeposit + ", existingSecurityDeposit="
				+ existingSecurityDeposit + ", securityDepositDemand=" + securityDepositDemand + ", installment1="
				+ installment1 + ", installment2=" + installment2 + ", installment3=" + installment3 + ", period="
				+ period + ", migrated=" + migrated + "]";
	}	
}
