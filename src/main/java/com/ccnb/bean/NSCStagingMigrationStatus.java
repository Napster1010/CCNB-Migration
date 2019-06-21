package com.ccnb.bean;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name="NSCStagingMigrationStatus")
@Table(name="nsc_staging_migration_status")
public class NSCStagingMigrationStatus {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	private long nsc_staging_id;
	
	private String status;
	
	private String location_code;
	
	private String old_cons_no;
	
	private String generated_consumer_no;
	
	private String remark;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date created_on;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date last_updated_on;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getNsc_staging_id() {
		return nsc_staging_id;
	}

	public void setNsc_staging_id(long nsc_staging_id) {
		this.nsc_staging_id = nsc_staging_id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLocation_code() {
		return location_code;
	}

	public void setLocation_code(String location_code) {
		this.location_code = location_code;
	}

	public String getOld_cons_no() {
		return old_cons_no;
	}

	public void setOld_cons_no(String old_cons_no) {
		this.old_cons_no = old_cons_no;
	}

	public String getGenerated_consumer_no() {
		return generated_consumer_no;
	}

	public void setGenerated_consumer_no(String generated_consumer_no) {
		this.generated_consumer_no = generated_consumer_no;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCreated_on() {
		return created_on;
	}

	public void setCreated_on(Date created_on) {
		this.created_on = created_on;
	}

	public Date getLast_updated_on() {
		return last_updated_on;
	}

	public void setLast_updated_on(Date last_updated_on) {
		this.last_updated_on = last_updated_on;
	}
	
}
