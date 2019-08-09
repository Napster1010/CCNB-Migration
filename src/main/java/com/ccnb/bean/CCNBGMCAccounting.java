package com.ccnb.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "CCNBGMCAccounting")
@Table(name = "ccnb_gmc_accounting")
public class CCNBGMCAccounting {

	    @Id
	    @Column(name= "id")
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private long id;

	    @Column(name = "bill_month")
	    private String billMonth;

	    @Column(name = "consumer_no" )
	    private String consumerNo;

	    @Column(name = "tariff_code")
	    private String tariffCode;

	    @Column(name = "premise_type")
	    private String premiseType;

	    @Column(name = "sanctioned_load")
	    private String sanctionedLoad;

	    @Column(name = "sanctioned_load_unit")
	    private String sanctionedLoadUnit;

	    @Column(name = "contract_demand")
	    private String contractDemand;

	    @Column(name = "contract_demand_unit")
	    private String contractDemandUnit;

	    @Column(name = "crnt_consumption")
	    private String crntConsumption;

	    @Column(name = "sumMeteredUnit")
	    private String sumMeteredUnit;

	    @Column(name = "sum_assessment")
	    private String sumAssessment;

	    @Column(name = "sum_total_unit")
	    private String sumTotalUnit;

	    @Column(name = "sum_billed_unit")
	    private String sumBilledUnit;

	    @Column(name = "sum_gmc_unit")
	    private String sumGmcUnit;
	 
	    @Column(name = "already_billed")
	    private String alreadyBilled;
	    
	    @Column(name = "migrated")
	    private boolean migrated;

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

		public String getConsumerNo() {
			return consumerNo;
		}

		public void setConsumerNo(String consumerNo) {
			this.consumerNo = consumerNo;
		}

		public String getTariffCode() {
			return tariffCode;
		}

		public void setTariffCode(String tariffCode) {
			this.tariffCode = tariffCode;
		}

		public String getPremiseType() {
			return premiseType;
		}

		public void setPremiseType(String premiseType) {
			this.premiseType = premiseType;
		}

		public String getSanctionedLoad() {
			return sanctionedLoad;
		}

		public void setSanctionedLoad(String sanctionedLoad) {
			this.sanctionedLoad = sanctionedLoad;
		}

		public String getSanctionedLoadUnit() {
			return sanctionedLoadUnit;
		}

		public void setSanctionedLoadUnit(String sanctionedLoadUnit) {
			this.sanctionedLoadUnit = sanctionedLoadUnit;
		}

		public String getContractDemand() {
			return contractDemand;
		}

		public void setContractDemand(String contractDemand) {
			this.contractDemand = contractDemand;
		}

		public String getContractDemandUnit() {
			return contractDemandUnit;
		}

		public void setContractDemandUnit(String contractDemandUnit) {
			this.contractDemandUnit = contractDemandUnit;
		}

		public String getCrntConsumption() {
			return crntConsumption;
		}

		public void setCrntConsumption(String crntConsumption) {
			this.crntConsumption = crntConsumption;
		}

		public String getSumMeteredUnit() {
			return sumMeteredUnit;
		}

		public void setSumMeteredUnit(String sumMeteredUnit) {
			this.sumMeteredUnit = sumMeteredUnit;
		}

		public String getSumAssessment() {
			return sumAssessment;
		}

		public void setSumAssessment(String sumAssessment) {
			this.sumAssessment = sumAssessment;
		}

		public String getSumTotalUnit() {
			return sumTotalUnit;
		}

		public void setSumTotalUnit(String sumTotalUnit) {
			this.sumTotalUnit = sumTotalUnit;
		}

		public String getSumBilledUnit() {
			return sumBilledUnit;
		}

		public void setSumBilledUnit(String sumBilledUnit) {
			this.sumBilledUnit = sumBilledUnit;
		}

		public String getSumGmcUnit() {
			return sumGmcUnit;
		}

		public void setSumGmcUnit(String sumGmcUnit) {
			this.sumGmcUnit = sumGmcUnit;
		}

		public String getAlreadyBilled() {
			return alreadyBilled;
		}

		public void setAlreadyBilled(String alreadyBilled) {
			this.alreadyBilled = alreadyBilled;
		}

		public boolean isMigrated() {
			return migrated;
		}

		public void setMigrated(boolean migrated) {
			this.migrated = migrated;
		}	    
}
