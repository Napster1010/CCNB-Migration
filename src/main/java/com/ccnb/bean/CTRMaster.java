package com.ccnb.bean;

import javax.persistence.Entity;
import javax.persistence.*;
import java.util.Date;

@Entity(name = "CTRMaster")
@Table(name = "ctr_master")
public class CTRMaster {
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

    @Column(name = "ct_ratio")
    private String ctRatio;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;

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

    public String getCtRatio() {
        return ctRatio;
    }

    public void setCtRatio(String ctRatio) {
        this.ctRatio = ctRatio;
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

}
