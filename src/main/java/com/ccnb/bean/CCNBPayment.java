package com.ccnb.bean;


import javax.persistence.*;
import java.util.Date;

@Entity(name = "CCNBPayment")
@Table(name = "ccnb_payment")
public class CCNBPayment {

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
    private String amount;

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

    @Column(name = "migrated")
    private boolean migrated;

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

	public void setOnline(boolean isOnline) {
		this.isOnline = isOnline;
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

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
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

	public boolean isMigrated() {
		return migrated;
	}

	public void setMigrated(boolean migrated) {
		this.migrated = migrated;
	}
}
