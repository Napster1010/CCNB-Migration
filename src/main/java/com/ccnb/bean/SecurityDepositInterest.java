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

@Entity(name="SecurityDepositInterest")
@Table(name="security_deposit_interest")
public class SecurityDepositInterest {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private long id;

	@Column(name = "consumer_no")
	private String consumerNo;
	
	@Column(name = "calculation_start_date")
	private Date calculationStartDate;
	
	@Column(name = "calculation_end_date")
	private Date calculationEndDate;
	
	@Column(name = "bill_month")
	private String billMonth;
	
	@Column(name = "amount")
	private BigDecimal amount;
	
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
	
	
	
	public String getConsumerNo() {
		return consumerNo;
	}

	public void setConsumerNo(String consumerNo) {
		this.consumerNo = consumerNo;
	}

	public Date getCalculationStartDate() {
		return calculationStartDate;
	}

	public void setCalculationStartDate(Date calculationStartDate) {
		this.calculationStartDate = calculationStartDate;
	}

	public Date getCalculationEndDate() {
		return calculationEndDate;
	}

	public void setCalculationEndDate(Date calculationEndDate) {
		this.calculationEndDate = calculationEndDate;
	}

	public String getBillMonth() {
		return billMonth;
	}

	public void setBillMonth(String billMonth) {
		this.billMonth = billMonth;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	
}
