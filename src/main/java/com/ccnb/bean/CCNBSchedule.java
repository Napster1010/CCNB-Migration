package com.ccnb.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name = "CCNBSchedule")
@Table(name = "ccnb_schedule")
public class CCNBSchedule {

    @Id
    @Column(name= "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "group_no" )
    private String groupNo;

    @Column(name = "bill_month")
    private String billMonth;

    @Column(name = "status")
    private String status;

    @Column(name = "cheque_due_date")
    @Temporal(TemporalType.DATE)
    private Date chequeDueDate;

    @Column(name = "due_date")
    @Temporal(TemporalType.DATE)
    private Date dueDate;

    @Column(name = "start_reading_date")
    @Temporal(TemporalType.DATE)
    private Date startReadingDate;

    @Column(name = "end_reading_date")
    @Temporal(TemporalType.DATE)
    private Date endReadingDate;

    @Column(name = "cash_upto")
    private Date cashUpto;
    
    @Column(name = "migrated")
    private boolean migrated;

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

    public Date getEndReadingDate() {
        return endReadingDate;
    }

    public void setEndReadingDate(Date endReadingDate) {
        this.endReadingDate = endReadingDate;
    }

	public boolean isMigrated() {
		return migrated;
	}

	public void setMigrated(boolean migrated) {
		this.migrated = migrated;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
