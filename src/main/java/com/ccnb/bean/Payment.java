package com.ccnb.bean;


import javax.persistence.*;
import java.util.Date;

@Entity(name = "Payment")
@Table(name = "payment")
public class Payment {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "source")
    private String source;

    @Column(name = "is_online")
    private boolean isOnline;

    @Column(name = "location_code")
    private String locationCode;

    @Column(name = "consumer_no")
    private String consumerNo;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "punching_date")
    private Date punchingDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "pay_date")
    private Date payDate;

    @Column(name = "amount")
    private long amount;

    @Column(name = "pay_mode")
    private String payMode;

    @Column(name = "pay_window")
    private String payWindow;

    @Column(name = "cac_no")
    private String cacNo;

    @Column(name = "deleted")
    private boolean deleted;

    @Column(name = "posted")
    private boolean posted;

    @Column(name = "posting_bill_month")
    private String postingBillMonth;

    @Temporal(TemporalType.DATE)
    @Column(name = "posting_date")
    private Date postingDate;

    @Column(name = "created_by")
    private String createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_on")
    private Date createdOn;

    @Column(name = "updated_by")
    private String updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_on")
    private Date updatedOn;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    public String getConsumerNo() {
        return consumerNo;
    }

    public void setConsumerNo(String consumerNo) {
        this.consumerNo = consumerNo;
    }

    public Date getPunchingDate() {
        return punchingDate;
    }

    public void setPunchingDate(Date punchingDate) {
        this.punchingDate = punchingDate;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getPayMode() {
        return payMode;
    }

    public void setPayMode(String payMode) {
        this.payMode = payMode;
    }

    public String getPayWindow() {
        return payWindow;
    }

    public void setPayWindow(String payWindow) {
        this.payWindow = payWindow;
    }

    public String getCacNo() {
        return cacNo;
    }

    public void setCacNo(String cacNo) {
        this.cacNo = cacNo;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean isPosted() {
        return posted;
    }

    public void setPosted(boolean posted) {
        this.posted = posted;
    }

    public String getPostingBillMonth() {
        return postingBillMonth;
    }

    public void setPostingBillMonth(String postingBillMonth) {
        this.postingBillMonth = postingBillMonth;
    }

    public Date getPostingDate() {
        return postingDate;
    }

    public void setPostingDate(Date postingDate) {
        this.postingDate = postingDate;
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
}
