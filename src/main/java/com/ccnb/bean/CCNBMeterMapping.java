package com.ccnb.bean;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "CCNBMeterMapping")
@Table(name = "ccnb_meter_mapping")
public class CCNBMeterMapping {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;	
	
	@Column(name = "ccnb_meter_capacity")
	private String ccnbMeterCapacity;
	
	@Column(name = "meter_code")
	private String meterCode;
	
	@Column(name = "meter_rent")
	private BigDecimal meterRent;
	
	@Column(name = "meter_capacity")
	private String meterCapacity;
	
	@Column(name = "meter_phase")
	private String meterPhase;
	
	@Column(name = "meter_description")
	private String meterDescription;

	public String getCcnbMeterCapacity() {
		return ccnbMeterCapacity;
	}

	public void setCcnbMeterCapacity(String ccnbMeterCapacity) {
		this.ccnbMeterCapacity = ccnbMeterCapacity;
	}

	public String getMeterCode() {
		return meterCode;
	}

	public void setMeterCode(String meterCode) {
		this.meterCode = meterCode;
	}

	public BigDecimal getMeterRent() {
		return meterRent;
	}

	public void setMeterRent(BigDecimal meterRent) {
		this.meterRent = meterRent;
	}

	public String getMeterCapacity() {
		return meterCapacity;
	}

	public void setMeterCapacity(String meterCapacity) {
		this.meterCapacity = meterCapacity;
	}

	public String getMeterPhase() {
		return meterPhase;
	}

	public void setMeterPhase(String meterPhase) {
		this.meterPhase = meterPhase;
	}

	public String getMeterDescription() {
		return meterDescription;
	}

	public void setMeterDescription(String meterDescription) {
		this.meterDescription = meterDescription;
	}

	@Override
	public String toString() {
		return "CCNBMeterMapping [ccnbMeterCapacity=" + ccnbMeterCapacity + ", meterCode=" + meterCode + ", meterRent="
				+ meterRent + ", meterCapacity=" + meterCapacity + ", meterPhase=" + meterPhase + ", meterDescription="
				+ meterDescription + "]";
	}	
}
