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

@Table(name = "saral_scheme")
@Entity(name = "SaralScheme")
public class SaralScheme {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "consumer_no")
	private String consumerNo;

	@Column(name = "urjas_application_no")
	private String urjasApplicationNo;

	@Column(name = "asd_arrear")
	private BigDecimal asdArrear;

	@Column(name = "posted")
	private boolean posted;

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

	public String getUrjasApplicationNo() {
		return urjasApplicationNo;
	}

	public void setUrjasApplicationNo(String urjasApplicationNo) {
		this.urjasApplicationNo = urjasApplicationNo;
	}

	public BigDecimal getAsdArrear() {
		if (this.asdArrear != null) {
			return new BigDecimal(String.valueOf(this.asdArrear.doubleValue()));
		}
		return asdArrear;
	}

	public void setAsdArrear(BigDecimal asdArrear) {
		this.asdArrear = asdArrear;
	}

	public boolean getPosted() {
		return posted;
	}

	public void setPosted(boolean posted) {
		this.posted = posted;
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

	@Override
	public String toString() {
		return "SaralScheme{" + "id=" + id + ", consumerNo='" + consumerNo + '\'' + ", urjasApplicationNo='" + urjasApplicationNo + '\'' + ", asdArrear=" + asdArrear + '}';
	}
}