package com.ccnb.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "CCNBBillMafi")
@Table(name = "ccnb_bill_mafi")
public class CCNBBillMafi {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	@Column(name = "consumerNo")
	private String consumerNo;
	
	@Column(name = "enrollmentId")
	private String enrollmentId;
	
	@Column(name = "yojnaDate")
	private Date yojnaDate;
	
	@Column(name = "principalArrear")
	private String principalArrear;
	
	@Column(name = "totalArrear")
	private String totalArrear;
	
	@Column(name = "surchargeArrear")
	private String surchargeArrear;
	
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

	public String getEnrollmentId() {
		return enrollmentId;
	}

	public void setEnrollmentId(String enrollmentId) {
		this.enrollmentId = enrollmentId;
	}

	public Date getYojnaDate() {
		return yojnaDate;
	}

	public void setYojnaDate(Date yojnaDate) {
		this.yojnaDate = yojnaDate;
	}

	public String getPrincipalArrear() {
		return principalArrear;
	}

	public void setPrincipalArrear(String principalArrear) {
		this.principalArrear = principalArrear;
	}

	public String getTotalArrear() {
		return totalArrear;
	}

	public void setTotalArrear(String totalArrear) {
		this.totalArrear = totalArrear;
	}

	public String getSurchargeArrear() {
		return surchargeArrear;
	}

	public void setSurchargeArrear(String surchargeArrear) {
		this.surchargeArrear = surchargeArrear;
	}

	public boolean isMigrated() {
		return migrated;
	}

	public void setMigrated(boolean migrated) {
		this.migrated = migrated;
	}	
}
