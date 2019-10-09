package com.ccnb.bean;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "Schedule")
@Table(name = "schedule")
public class Schedule {

    @Id
    @Column(name= "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "group_no" )
    private String groupNo;

    @Column(name = "bill_month")
    private String billMonth;

    @Column(name = "bill_status")
    private String billStatus;

    @Column(name = "submitted")
    private String submitted;

    @Column(name = "r15_status")
    private String r15Status;

    @Column(name = "cheque_due_date")
    @Temporal(TemporalType.DATE)
    private Date chequeDueDate;

    @Column(name = "due_date")
    @Temporal(TemporalType.DATE)
    private Date dueDate;

    @Column(name= "bill_date")
    @Temporal(TemporalType.DATE)
    private Date billDate;

    @Column(name = "start_reading_date")
    @Temporal(TemporalType.DATE)
    private Date startReadingDate;

    @Column(name = "end_reading_date")
    @Temporal(TemporalType.DATE)
    private Date endReadingDate;

    @Column(name = "cash_upto")
    private Date cashUpto;

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

    public String getGroupNo() {
        return groupNo;
    }

    public void setGroupNo(String groupNo) {
        this.groupNo = groupNo;
    }

    public String getBillMonth() {
        return billMonth;
    }

    public void setBillMonth(String billMonth) {
        this.billMonth = billMonth;
    }

    public String getBillStatus() {
        return billStatus;
    }

    public void setBillStatus(String billStatus) {
        this.billStatus = billStatus;
    }

    public String getSubmitted() {
        return submitted;
    }

    public void setSubmitted(String submitted) {
        this.submitted = submitted;
    }

    public String getR15Status() {
        return r15Status;
    }

    public void setR15Status(String r15Status) {
        this.r15Status = r15Status;
    }

    public Date getChequeDueDate() {
        return chequeDueDate;
    }

    public void setChequeDueDate(Date chequeDueDate) {
        this.chequeDueDate = chequeDueDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getBillDate() {
        return billDate;
    }

    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }

    public Date getStartReadingDate() {
        return startReadingDate;
    }

    public void setStartReadingDate(Date startReadingDate) {
        this.startReadingDate = startReadingDate;
    }

    public Date getCashUpto() {
        return cashUpto;
    }

    public void setCashUpto(Date cashUpto) {
        this.cashUpto = cashUpto;
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

    public Date getEndReadingDate() {
        return endReadingDate;
    }

    public void setEndReadingDate(Date endReadingDate) {
        this.endReadingDate = endReadingDate;
    }
    
}

