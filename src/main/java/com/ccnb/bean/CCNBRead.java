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

@Entity(name = "CCNBRead")
@Table(name = "ccnb_read")
public class CCNBRead {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "bill_month")
    private String billMonth;

    @Column(name = "group_no")
    private String groupNo;

    @Column(name = "reading_diary_no")
    private String readingDiaryNo;

    @Column(name = "consumer_no")
    private String consumerNo;

    @Column(name = "meter_identifier")
    private String meterIdentifier;

    @Column(name = "reading_date")
    @Temporal(TemporalType.DATE)
    private Date readingDate;

    @Column(name = "reading_type")
    private String readingType;

    @Column(name = "meter_status")
    private String meterStatus;

    @Column(name = "replacement_flag")
    private boolean replacementFlag;

    @Column(name = "source")
    private String source;

    @Column(name = "reading")
    private String reading;

    @Column(name = "difference")
    private String difference;

    @Column(name = "mf")
    private String mf;

    @Column(name = "consumption")
    private String consumption;

    @Column(name = "assessment")
    private String assessment;

    @Column(name = "propagated_assessment")
    private String propagatedAssessment;

    @Column(name = "total_consumption")
    private String totalConsumption;
    
    @Column(name = "meter_md")
    private String meterMd;
    
    @Column(name = "multiplied_md")
    private String multipliedMd;
    
    @Column(name = "billing_demand")
    private String billingDemand;
    
    @Column(name = "meter_pf")
    private String meterPf;
    
    @Column(name = "billing_pf")
    private String billingPf;

    @Column(name = "used_on_bill")
    private boolean usedOnBill;

    private boolean migrated;
    
    public boolean getMigrated() {
    	return migrated;
    }
    
    public void setMigrated(boolean migrated) {
    	this.migrated = migrated;
    }
    
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getBillMonth() {
		return billMonth;
	}

	public void setBillMonth(String billMonth) {
		this.billMonth = billMonth;
	}

	public String getGroupNo() {
		return groupNo;
	}

	public void setGroupNo(String groupNo) {
		this.groupNo = groupNo;
	}

	public String getReadingDiaryNo() {
		return readingDiaryNo;
	}

	public void setReadingDiaryNo(String readingDiaryNo) {
		this.readingDiaryNo = readingDiaryNo;
	}

	public String getConsumerNo() {
		return consumerNo;
	}

	public void setConsumerNo(String consumerNo) {
		this.consumerNo = consumerNo;
	}

	public String getMeterIdentifier() {
		return meterIdentifier;
	}

	public void setMeterIdentifier(String meterIdentifier) {
		this.meterIdentifier = meterIdentifier;
	}

	public Date getReadingDate() {
		return readingDate;
	}

	public void setReadingDate(Date readingDate) {
		this.readingDate = readingDate;
	}

	public String getReadingType() {
		return readingType;
	}

	public void setReadingType(String readingType) {
		this.readingType = readingType;
	}

	public String getMeterStatus() {
		return meterStatus;
	}

	public void setMeterStatus(String meterStatus) {
		this.meterStatus = meterStatus;
	}

	public boolean getReplacementFlag() {
		return replacementFlag;
	}

	public void setReplacementFlag(boolean replacementFlag) {
		this.replacementFlag = replacementFlag;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getReading() {
		return reading;
	}

	public void setReading(String reading) {
		this.reading = reading;
	}

	public String getDifference() {
		return difference;
	}

	public void setDifference(String difference) {
		this.difference = difference;
	}

	public String getMf() {
		return mf;
	}

	public void setMf(String mf) {
		this.mf = mf;
	}

	public String getConsumption() {
		return consumption;
	}

	public void setConsumption(String consumption) {
		this.consumption = consumption;
	}

	public String getAssessment() {
		return assessment;
	}

	public void setAssessment(String assessment) {
		this.assessment = assessment;
	}

	public String getPropagatedAssessment() {
		return propagatedAssessment;
	}

	public void setPropagatedAssessment(String propagatedAssessment) {
		this.propagatedAssessment = propagatedAssessment;
	}

	public String getTotalConsumption() {
		return totalConsumption;
	}

	public void setTotalConsumption(String totalConsumption) {
		this.totalConsumption = totalConsumption;
	}

	public String getMeterMd() {
		return meterMd;
	}

	public void setMeterMd(String meterMd) {
		this.meterMd = meterMd;
	}

	public String getMultipliedMd() {
		return multipliedMd;
	}

	public void setMultipliedMd(String multipliedMd) {
		this.multipliedMd = multipliedMd;
	}

	public String getBillingDemand() {
		return billingDemand;
	}

	public void setBillingDemand(String billingDemand) {
		this.billingDemand = billingDemand;
	}

	public String getMeterPf() {
		return meterPf;
	}

	public void setMeterPf(String meterPf) {
		this.meterPf = meterPf;
	}

	public String getBillingPf() {
		return billingPf;
	}

	public void setBillingPf(String billingPf) {
		this.billingPf = billingPf;
	}

	public boolean isUsedOnBill() {
		return usedOnBill;
	}

	public void setUsedOnBill(boolean usedOnBill) {
		this.usedOnBill = usedOnBill;
	}

	@Override
	public String toString() {
		return "CCNBRead [id=" + id + ", billMonth=" + billMonth + ", groupNo=" + groupNo + ", readingDiaryNo="
				+ readingDiaryNo + ", consumerNo=" + consumerNo + ", meterIdentifier=" + meterIdentifier
				+ ", readingDate=" + readingDate + ", readingType=" + readingType + ", meterStatus=" + meterStatus
				+ ", replacementFlag=" + replacementFlag + ", source=" + source + ", reading=" + reading
				+ ", difference=" + difference + ", mf=" + mf + ", consumption=" + consumption + ", assessment="
				+ assessment + ", propagatedAssessment=" + propagatedAssessment + ", totalConsumption="
				+ totalConsumption + ", meterMd=" + meterMd + ", multipliedMd=" + multipliedMd + ", billingDemand="
				+ billingDemand + ", meterPf=" + meterPf + ", billingPf=" + billingPf + ", usedOnBill=" + usedOnBill
				+ "]";
	}    
}
