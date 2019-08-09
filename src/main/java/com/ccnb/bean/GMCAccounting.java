package com.ccnb.bean;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "GMCAccounting")
@Table(name = "gmc_accounting")
public class GMCAccounting {

	    @Id
	    @Column(name= "id")
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private long id;

	    @Column(name = "consumer_no" )
	    private String consumerNo;

	    @Column(name = "current_bill_month")
	    private String currentBillMonth;

	    @Column(name = "read_type")
	    private String readType;

	    @Column(name = "current_consumption")
	    private BigDecimal currentConsumption;

	    @Column(name = "actual_cumulative_consumption")
	    private BigDecimal actualCumulativeConsumption;

	    @Column(name = "minimum_cumulative")
	    private BigDecimal minimumCumulative;

	    @Column(name = "higher_of_actual_minimum_cumulative")
	    private BigDecimal higherOfActualMinimumCumulative;

	    @Column(name = "already_billed")
	    private BigDecimal alreadyBilled;

	    @Column(name = "to_be_billed")
	    private BigDecimal toBeBilled;

	    @Column(name = "previous_month")
	    private String previousMonth;

	    @Column(name = "previous_read_type")
	    private String previousReadType;

	    @Column(name = "previous_consumption")
	    private BigDecimal previousConsumption;

	    @Column(name = "previous_actual_cumulative_consumption")
	    private BigDecimal previousActualCumulativeConsumption;

	    @Column(name = "previous_minimum_cumulative")
	    private BigDecimal previousMinimumCumulative;

	    @Column(name = "previous_higher_of_actual_minimum_cumulative")
	    private BigDecimal previousHigherOfActualMinimumCumulative;

	    @Column(name = "previous_already_billed")
	    private BigDecimal previousAlreadyBilled;

	    @Column(name = "previous_to_be_billed")
	    private BigDecimal previousToBeBilled;

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

	    public String getCurrentBillMonth() {
	        return currentBillMonth;
	    }

	    public void setCurrentBillMonth(String currentBillMonth) {
	        this.currentBillMonth = currentBillMonth;
	    }

	    public String getReadType() {
	        return readType;
	    }

	    public void setReadType(String readType) {
	        this.readType = readType;
	    }

	    public BigDecimal getCurrentConsumption() {
	        if(this.currentConsumption != null){
	            return new BigDecimal(String.valueOf(this.currentConsumption.doubleValue()));
	        }
	        return currentConsumption;
	    }

	    public void setCurrentConsumption(BigDecimal currentConsumption) {
	        this.currentConsumption = currentConsumption;
	    }

	    public BigDecimal getActualCumulativeConsumption() {
	        if(this.actualCumulativeConsumption != null){
	            return new BigDecimal(String.valueOf(this.actualCumulativeConsumption.doubleValue()));
	        }
	        return actualCumulativeConsumption;
	    }

	    public void setActualCumulativeConsumption(BigDecimal actualCumulativeConsumption) {
	        this.actualCumulativeConsumption = actualCumulativeConsumption;
	    }

	    public BigDecimal getMinimumCumulative() {
	        if(this.minimumCumulative != null){
	            return new BigDecimal(String.valueOf(this.minimumCumulative.doubleValue()));
	        }
	        return minimumCumulative;
	    }

	    public void setMinimumCumulative(BigDecimal minimumCumulative) {
	        this.minimumCumulative = minimumCumulative;
	    }

	    public BigDecimal getHigherOfActualMinimumCumulative() {
	        if(this.higherOfActualMinimumCumulative != null){
	            return new BigDecimal(String.valueOf(this.higherOfActualMinimumCumulative.doubleValue()));
	        }
	        return higherOfActualMinimumCumulative;
	    }

	    public void setHigherOfActualMinimumCumulative(BigDecimal higherOfActualMinimumCumulative) {
	        this.higherOfActualMinimumCumulative = higherOfActualMinimumCumulative;
	    }

	    public BigDecimal getAlreadyBilled() {
	        if(this.alreadyBilled != null){
	            return new BigDecimal(String.valueOf(this.alreadyBilled.doubleValue()));
	        }
	        return alreadyBilled;
	    }

	    public void setAlreadyBilled(BigDecimal alreadyBilled) {
	        this.alreadyBilled = alreadyBilled;
	    }

	    public BigDecimal getToBeBilled() {
	        if(this.toBeBilled != null){
	            return new BigDecimal(String.valueOf(this.toBeBilled.doubleValue()));
	        }
	        return toBeBilled;
	    }

	    public void setToBeBilled(BigDecimal toBeBilled) {
	        this.toBeBilled = toBeBilled;
	    }

	    public String getPreviousMonth() {
	        return previousMonth;
	    }

	    public void setPreviousMonth(String previousMonth) {
	        this.previousMonth = previousMonth;
	    }

	    public String getPreviousReadType() {
	        return previousReadType;
	    }

	    public void setPreviousReadType(String previousReadType) {
	        this.previousReadType = previousReadType;
	    }

	    public BigDecimal getPreviousConsumption() {
	        if(this.previousConsumption != null){
	            return new BigDecimal(String.valueOf(this.previousConsumption.doubleValue()));
	        }
	        return previousConsumption;
	    }

	    public void setPreviousConsumption(BigDecimal previousConsumption) {
	        this.previousConsumption = previousConsumption;
	    }

	    public BigDecimal getPreviousActualCumulativeConsumption() {
	        if(this.previousActualCumulativeConsumption != null){
	            return new BigDecimal(String.valueOf(this.previousActualCumulativeConsumption.doubleValue()));
	        }
	        return previousActualCumulativeConsumption;
	    }

	    public void setPreviousActualCumulativeConsumption(BigDecimal previousActualCumulativeConsumption) {
	        this.previousActualCumulativeConsumption = previousActualCumulativeConsumption;
	    }

	    public BigDecimal getPreviousMinimumCumulative() {
	        if(this.previousMinimumCumulative != null){
	            return new BigDecimal(String.valueOf(this.previousMinimumCumulative.doubleValue()));
	        }
	        return previousMinimumCumulative;
	    }

	    public void setPreviousMinimumCumulative(BigDecimal previousMinimumCumulative) {
	        this.previousMinimumCumulative = previousMinimumCumulative;
	    }

	    public BigDecimal getPreviousHigherOfActualMinimumCumulative() {
	        if(this.previousHigherOfActualMinimumCumulative != null){
	            return new BigDecimal(String.valueOf(this.previousHigherOfActualMinimumCumulative.doubleValue()));
	        }
	        return previousHigherOfActualMinimumCumulative;
	    }

	    public void setPreviousHigherOfActualMinimumCumulative(BigDecimal previousHigherOfActualMinimumCumulative) {
	        this.previousHigherOfActualMinimumCumulative = previousHigherOfActualMinimumCumulative;
	    }

	    public BigDecimal getPreviousAlreadyBilled() {
	        if(this.previousAlreadyBilled != null){
	            return new BigDecimal(String.valueOf(this.previousAlreadyBilled.doubleValue()));
	        }
	        return previousAlreadyBilled;
	    }

	    public void setPreviousAlreadyBilled(BigDecimal previousAlreadyBilled) {
	        this.previousAlreadyBilled = previousAlreadyBilled;
	    }

	    public BigDecimal getPreviousToBeBilled() {
	        if(this.previousToBeBilled != null){
	            return new BigDecimal(String.valueOf(this.previousToBeBilled.doubleValue()));
	        }
	        return previousToBeBilled;
	    }

	    public void setPreviousToBeBilled(BigDecimal previousToBeBilled) {
	        this.previousToBeBilled = previousToBeBilled;
	    }


}
