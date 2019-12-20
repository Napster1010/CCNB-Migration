package com.ccnb.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "ConsumerFeederMapping")
@Table(name = "consumer_feeder_mapping")
public class ConsumerFeederMapping {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	@Column(name = "old_consumer_no")
	private String oldConsumerNo;
	
	@Column(name = "old_group_no")
	private String oldGroupNo;
	
	@Column(name = "feeder_id")
	private String feederId;
	
	@Column(name = "feeder_name")
	private String feederName;
	
	@Column(name = "transformer_name")
	private String transformerName;
	
	@Column(name = "feeder_code")	
	private String feederCode;

	@Column(name = "migrated")
	private boolean migrated;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getOldConsumerNo() {
		return oldConsumerNo;
	}

	public void setOldConsumerNo(String oldConsumerNo) {
		this.oldConsumerNo = oldConsumerNo;
	}

	public String getOldGroupNo() {
		return oldGroupNo;
	}

	public void setOldGroupNo(String oldGroupNo) {
		this.oldGroupNo = oldGroupNo;
	}

	public String getFeederId() {
		return feederId;
	}

	public void setFeederId(String feederId) {
		this.feederId = feederId;
	}

	public String getFeederName() {
		return feederName;
	}

	public void setFeederName(String feederName) {
		this.feederName = feederName;
	}

	public String getTransformerName() {
		return transformerName;
	}

	public void setTransformerName(String transformerName) {
		this.transformerName = transformerName;
	}

	public String getFeederCode() {
		return feederCode;
	}

	public void setFeederCode(String feederCode) {
		this.feederCode = feederCode;
	}

	public boolean isMigrated() {
		return migrated;
	}

	public void setMigrated(boolean migrated) {
		this.migrated = migrated;
	}

	@Override
	public String toString() {
		return "ConsumerFeederMapping [id=" + id + ", oldConsumerNo=" + oldConsumerNo + ", oldGroupNo=" + oldGroupNo
				+ ", feederId=" + feederId + ", feederName=" + feederName + ", transformerName=" + transformerName
				+ ", feederCode=" + feederCode + ", migrated=" + migrated + "]";
	}

	public ConsumerFeederMapping(long id, String oldConsumerNo, String oldGroupNo, String feederId, String feederName,
			String transformerName, String feederCode, boolean migrated) {
		super();
		this.id = id;
		this.oldConsumerNo = oldConsumerNo;
		this.oldGroupNo = oldGroupNo;
		this.feederId = feederId;
		this.feederName = feederName;
		this.transformerName = transformerName;
		this.feederCode = feederCode;
		this.migrated = migrated;
	}

	public ConsumerFeederMapping() {
		super();
	}	
}
