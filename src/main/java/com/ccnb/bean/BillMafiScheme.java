package com.ccnb.bean;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity(name = "BillMafiScheme")
@Table(name = "bill_mafi_scheme")
public class BillMafiScheme{

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "urjas_application_no")
    private String urjasApplicationNo;

    @Column(name = "consumer_no")
    private String consumerNo;

    @Column(name = "applied_on")
    private Date appliedOn;

    @Column(name = "asd_arrear")
    private BigDecimal asdArrear;

    @Column(name = "arrear")
    private BigDecimal arrear;

    @Column(name = "cumulative_surcharge")
    private BigDecimal cumulativeSurcharge;

    @Column(name = "surcharge_demanded")
    private BigDecimal surchargeDemanded;

    @Column(name = "exempted_by_discom")
    private BigDecimal exemptedByDiscom;

    @Column(name = "subsidy_by_government")
    private BigDecimal subsidyByGovernment;

    @Column(name = "posted_bill_month")
    private String postedBillMonth;

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

    public Date getAppliedOn() {
        return appliedOn;
    }

    public void setAppliedOn(Date appliedOn) {
        this.appliedOn = appliedOn;
    }

    public BigDecimal getAsdArrear() {
        if(this.asdArrear != null){
            return new BigDecimal(String.valueOf(this.asdArrear.doubleValue()));
        }
        return asdArrear;
    }

    public void setAsdArrear(BigDecimal asdArrear) {
        this.asdArrear = asdArrear;
    }

    public BigDecimal getArrear() {
        if(this.arrear != null){
        return new BigDecimal(String.valueOf(this.arrear.doubleValue()));
    }
        return arrear;
    }

    public void setArrear(BigDecimal arrear) {
        this.arrear = arrear;
    }

    public BigDecimal getCumulativeSurcharge() {
        if(this.cumulativeSurcharge != null){
            return new BigDecimal(String.valueOf(this.cumulativeSurcharge.doubleValue()));
        }
        return cumulativeSurcharge;
    }

    public void setCumulativeSurcharge(BigDecimal cumulativeSurcharge) {
        this.cumulativeSurcharge = cumulativeSurcharge;
    }

    public BigDecimal getSurchargeDemanded() {
        if(this.surchargeDemanded != null){
            return new BigDecimal(String.valueOf(this.surchargeDemanded.doubleValue()));
        }
        return surchargeDemanded;
    }

    public void setSurchargeDemanded(BigDecimal surchargeDemanded) {
        this.surchargeDemanded = surchargeDemanded;
    }

    public BigDecimal getExemptedByDiscom() {
        if(this.exemptedByDiscom != null){
            return new BigDecimal(String.valueOf(this.exemptedByDiscom.doubleValue()));
        }
        return exemptedByDiscom;
    }

    public void setExemptedByDiscom(BigDecimal exemptedByDiscom) {

        this.exemptedByDiscom = exemptedByDiscom;
    }

    public BigDecimal getSubsidyByGovernment() {
        if(this.subsidyByGovernment != null){
            return new BigDecimal(String.valueOf(this.subsidyByGovernment.doubleValue()));
        }
        return subsidyByGovernment;
    }

    public void setSubsidyByGovernment(BigDecimal subsidyByGovernment) {
        this.subsidyByGovernment = subsidyByGovernment;
    }

    public String getUrjasApplicationNo() {
        return urjasApplicationNo;
    }

    public void setUrjasApplicationNo(String urjasApplicationNo) {
        this.urjasApplicationNo = urjasApplicationNo;
    }

    public String getPostedBillMonth() {
        return postedBillMonth;
    }

    public void setPostedBillMonth(String postedBillMonth) {
        this.postedBillMonth = postedBillMonth;
    }

    public boolean isPosted() {
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
        return "BillMafiScheme{" +
                "id=" + id +
                ", urjasApplicationNo='" + urjasApplicationNo + '\'' +
                ", consumerNo='" + consumerNo + '\'' +
                ", appliedOn=" + appliedOn +
                ", asdArrear=" + asdArrear +
                ", arrear=" + arrear +
                ", cumulativeSurcharge=" + cumulativeSurcharge +
                ", surchargeDemanded=" + surchargeDemanded +
                ", exemptedByDiscom=" + exemptedByDiscom +
                ", subsidyByGovernment=" + subsidyByGovernment +
                ", postedBillMonth='" + postedBillMonth + '\'' +
                ", posted=" + posted +
                ", createdBy='" + createdBy + '\'' +
                ", createdOn=" + createdOn +
                ", updatedBy='" + updatedBy + '\'' +
                ", updatedOn=" + updatedOn +
                '}';
    }
}
