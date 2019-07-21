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

@Entity(name = "ConsumerNoMaster")
@Table(name = "consumer_no_master")
public class ConsumerNoMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "location_code")
    private String locationCode;

    @Column(name = "consumer_no")
    private String consumerNo;

    @Column(name = "status")
    private String status;

    @Column(name = "old_location_code")
    private String oldLocationCode;

    @Column(name = "old_service_no_one")
    private String oldServiceNoOne;

    @Column(name = "old_service_no_two")
    private String oldServiceNoTwo;

    @Column(name = "group_no")
    private String groupNo;
    
    @Column(name = "old_group_no")
    private String oldGroupNo;

    @Column(name = "reading_diary_no")
    private String readingDiaryNo;
    
    @Column(name = "old_reading_diary_no")
    private String oldReadingDiaryNo;
    
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOldLocationCode() {
        return oldLocationCode;
    }

    public void setOldLocationCode(String oldLocationCode) {
        this.oldLocationCode = oldLocationCode;
    }

    public String getOldServiceNoOne() {
        return oldServiceNoOne;
    }

    public void setOldServiceNoOne(String oldServiceNoOne) {
        this.oldServiceNoOne = oldServiceNoOne;
    }

    public String getOldServiceNoTwo() {
        return oldServiceNoTwo;
    }

    public void setOldServiceNoTwo(String oldServiceNoTwo) {
        this.oldServiceNoTwo = oldServiceNoTwo;
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

    public String getGroupNo() {
        return groupNo;
    }

    public void setGroupNo(String groupNo) {
        this.groupNo = groupNo;
    }

    @Override
    public String toString() {
        return "ConsumerNoMaster [id=" + id + ", locationCode=" + locationCode + ", consumerNo=" + consumerNo + ", status=" + status + ", oldLocationCode=" + oldLocationCode + ", oldServiceNoOne=" + oldServiceNoOne + ", oldServiceNoTwo=" + oldServiceNoTwo + ", createdBy=" + createdBy + ", createdOn=" + createdOn + ", updatedBy=" + updatedBy + ", updatedOn=" + updatedOn + "]";
    }

	public String getReadingDiaryNo() {
		return readingDiaryNo;
	}

	public void setReadingDiaryNo(String readingDiaryNo) {
		this.readingDiaryNo = readingDiaryNo;
	}

	public String getOldGroupNo() {
		return oldGroupNo;
	}

	public void setOldGroupNo(String oldGroupNo) {
		this.oldGroupNo = oldGroupNo;
	}

	public String getOldReadingDiaryNo() {
		return oldReadingDiaryNo;
	}

	public void setOldReadingDiaryNo(String oldReadingDiaryNo) {
		this.oldReadingDiaryNo = oldReadingDiaryNo;
	}

}
