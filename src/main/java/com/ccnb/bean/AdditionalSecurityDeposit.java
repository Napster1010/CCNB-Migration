package com.ccnb.bean;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity(name = "AdditionalSecurityDeposit")
@Table(name = "additional_security_deposit")
public class AdditionalSecurityDeposit {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "consumer_no")
    private String consumerNo;

    @Column(name = "average_consumption")
    private BigDecimal averageConsumption;

    @Column(name = "average_bill")
    private BigDecimal averageBill;

    @Column(name = "is_defaulter")
    private boolean isDefaulter;

    @Column(name = "bill_month")
    private String billMonth;

    @Column(name = "security_deposit_demand")
    private BigDecimal securityDepositDemand;

    @Column(name = "period")
    private int period;

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

    @Column(name = "required_security_deposit")
    private BigDecimal requiredSecurityDeposit;

    @Column(name = "existing_security_deposit")
    private BigDecimal existingSecurityDeposit;
    
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

    
    public BigDecimal getAverageConsumption() {
        if (this.averageConsumption != null) {
            return new BigDecimal(String.valueOf(this.averageConsumption.doubleValue()));
        }
        return averageConsumption;
    }

    
    public void setAverageConsumption(BigDecimal averageConsumption) {
        this.averageConsumption = averageConsumption;
    }

    
    public BigDecimal getAverageBill() {
        if (this.averageBill != null) {
            return new BigDecimal(String.valueOf(this.averageBill.doubleValue()));
        }
        return averageBill;
    }

    
    public void setAverageBill(BigDecimal averageBill) {
        this.averageBill = averageBill;
    }

    
    public boolean isDefaulter() {
        return isDefaulter;
    }

    
    public void setDefaulter(boolean defaulter) {
        isDefaulter = defaulter;
    }

    
    public String getBillMonth() {
        return billMonth;
    }

    
    public void setBillMonth(String billMonth) {
        this.billMonth = billMonth;
    }

    
    public BigDecimal getSecurityDepositDemand() {
        if (this.securityDepositDemand != null) {
            return new BigDecimal(String.valueOf(this.securityDepositDemand.doubleValue()));
        }
        return securityDepositDemand;
    }

    
    public void setSecurityDepositDemand(BigDecimal securityDepositDemand) {
        this.securityDepositDemand = securityDepositDemand;
    }

    
    public int getPeriod() {
        return period;
    }

    
    public void setPeriod(int period) {
        this.period = period;
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


	public BigDecimal getRequiredSecurityDeposit() {
		return requiredSecurityDeposit;
	}


	public void setRequiredSecurityDeposit(BigDecimal requiredSecurityDeposit) {
		this.requiredSecurityDeposit = requiredSecurityDeposit;
	}


	public BigDecimal getExistingSecurityDeposit() {
		return existingSecurityDeposit;
	}


	public void setExistingSecurityDeposit(BigDecimal existingSecurityDeposit) {
		this.existingSecurityDeposit = existingSecurityDeposit;
	}
}
