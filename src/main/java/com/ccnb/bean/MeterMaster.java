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

@Entity(name = "MeterMaster")
@Table(name = "meter_master")
public class MeterMaster {
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "id")
	    private long id;

	    @Column(name = "identifier")
	    private String identifier;

	    @Column(name = "serial_no")
	    private String serialNo;

	    @Column(name = "make")
	    private String make;

	    @Column(name = "meter_owner")
	    private String meterOwner;

	    @Column(name = "capacity")
	    private String capacity;

	    @Column(name = "ctr")
	    private String ctr;

	    @Column(name = "mf")
	    private BigDecimal mf;

	    @Column(name = "description")
	    private String description;

	    @Column(name = "phase")
	    private String phase;

	    @Column(name = "code")
	    private String code;

	    @Column(name = "is_prepaid")
	    private boolean isPrepaid;

	    @Column(name = "history_no")
	    private String historyNo;

	    @Column(name = "created_by")
	    private String createdBy;

	    @Column(name = "created_on")
	    @Temporal(TemporalType.TIMESTAMP)
	    private Date createOn;

	    public long getId() {
	        return id;
	    }

	    public void setId(long id) {
	        this.id = id;
	    }

	    public String getIdentifier() {
	        return identifier;
	    }

	    public void setIdentifier(String identifier) {
	        this.identifier = identifier;
	    }

	    public String getSerialNo() {
	        return serialNo;
	    }

	    public void setSerialNo(String serialNo) {
	        this.serialNo = serialNo;
	    }

	    public String getMake() {
	        return make;
	    }

	    public void setMake(String make) {
	        this.make = make;
	    }

	    public String getMeterOwner() {
	        return meterOwner;
	    }

	    public void setMeterOwner(String meterOwner) {
	        this.meterOwner = meterOwner;
	    }

	    public String getCapacity() {
	        return capacity;
	    }

	    public void setCapacity(String capacity) {
	        this.capacity = capacity;
	    }

	    public String getCtr() {
	        return ctr;
	    }

	    public void setCtr(String ctr) {
	        this.ctr = ctr;
	    }

	    public BigDecimal getMf() {
	        return mf;
	    }

	    public void setMf(BigDecimal mf) {
	        this.mf = mf;
	    }

	    public String getDescription() {
	        return description;
	    }

	    public void setDescription(String description) {
	        this.description = description;
	    }

	    public String getPhase() {
	        return phase;
	    }

	    public void setPhase(String phase) {
	        this.phase = phase;
	    }

	    public String getCode() {
	        return code;
	    }

	    public void setCode(String code) {
	        this.code = code;
	    }

	    public String getHistoryNo() {
	        return historyNo;
	    }

	    public void setHistoryNo(String historyNo) {
	        this.historyNo = historyNo;
	    }

	    public String getCreatedBy() {
	        return createdBy;
	    }

	    public void setCreatedBy(String createdBy) {
	        this.createdBy = createdBy;
	    }

	    public Date getCreateOn() {
	        return createOn;
	    }

	    public void setCreateOn(Date createOn) {
	        this.createOn = createOn;
	    }

		public boolean isPrepaid() {
			return isPrepaid;
		}

		public void setPrepaid(boolean isPrepaid) {
			this.isPrepaid = isPrepaid;
		}

}
