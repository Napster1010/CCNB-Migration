package com.ccnb.bean;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name="CCNBNSCStagingMigration")
@Table(name="ccnb_nsc_staging_migration")
public class CCNBNSCStagingMigration {
	
	  @Id	
	  @GeneratedValue(strategy=GenerationType.IDENTITY)	
	  private long id;

	  private Date connection_date;
	  
	  private String consumer_name;
	  
	  private String consumer_name_h;
	  
	  private String relative_name;
	  
	  private String relation;
	  
	  private boolean is_bpl;
	  
	  private String bpl_no;
	  
	  private String category;
	  
	  private boolean is_employee;
	  
	  private String employee_company;
	  
	  private String employee_no;
	  
	  private String address_1;
	  
	  private String address_2;
	  
	  private String address_3;
	  
	  private String address_1_h;
	  
	  private String address_2_h;
	  
	  private String address_3_h;
	  
	  private String primary_mobile_no;
	  
	  private String alternate_mobile_no;
	  
	  private String aadhaar_no;
	  
	  private String pan;
	  
	  private String bank_account_no;
	  
	  private String bank_account_holder_name;
	  
	  private String bank_name;
	  
	  private String ifsc;
	  
	  private String email_address;
	  
	  private String tariff_category;
	  
	  private String connection_type;
	  
	  private String metering_status;
	  
	  private String premise_type;
	  
	  private BigDecimal sanctioned_load;
	  
	  private String sanctioned_load_unit;
	  
	  private BigDecimal contract_demand;
	  
	  private String contract_demand_unit;
	  
	  private boolean is_seasonal;
	  
	  private Date season_start_date;
	  
	  private Date season_end_date;
	  
	  private String season_start_bill_month;
	  
	  private String season_end_bill_month;
	  
	  private String purpose_of_installation;
	  
	  private String tariff_code;
	  
	  private long sub_category_code;
	  
	  private String phase;
	  
	  private Date tc_start_date;
	  
	  private Date tc_end_date;
	  
	  private boolean is_government;
	  
	  private String government_type;
	  
	  private BigDecimal plot_size;
	  
	  private String plot_size_unit;
	  
	  private String location_code;
	  
	  private boolean is_xray;
	  
	  private BigDecimal xray_load;
	  
	  private long no_of_dental_xray_machine;
	  
	  private long no_of_single_phase_xray_machine;
	  
	  private long no_of_three_phase_xray_machine;
	  
	  private boolean is_welding_transformer_surcharge;
	  
	  private boolean is_capacitor_surcharge;
	  
	  private boolean is_demandside;
	  
	  private String isi_motor_type;
	  
	  private String dtr_name;
	  
	  private String pole_no;
	  
	  private String pole_latitude;
	  
	  private String pole_longitude;
	  
	  private String feeder_name;
	  
	  private long pole_distance;
	  
	  private String area_status;
	  
	  private String group_no;
	  
	  private String reading_diary_no;
	  
	  private String neighbour_connection_no;
	  
	  private Date survey_date;
	  
	  private String meter_identifier;
	  
	  private BigDecimal start_read;
	  
	  private boolean has_ctr;
	  
	  private String ctr_identifier;
	  
	  private BigDecimal ctr_overall_mf;
	  	  
	  private Date meter_installation_date;
	  
	  private String meter_installer_name;
	  
	  private String meter_installer_designation;
	  
	  private boolean has_modem;
	  
	  private String modem_no;
	  
	  private String sim_no;
	  
	  private Date date_of_registration;
	  
	  private BigDecimal registration_fee_amount;
	  
	  private String registration_fee_amount_mr_no;
	  
	  private BigDecimal security_deposit_amount;
	  
	  private String security_deposit_amount_mr_no;
	  
	  private String portal_name;
	  
	  private String portal_reference_no;
	  
	  @Temporal(TemporalType.TIMESTAMP)
	  private Date created_on;
	  
	  private String old_cons_no;
	  
	  private String old_gr_no;	  
	  
	  private String old_rd_no;
	  
	  private String old_trf_catg;
	  
	  private String old_rev_catg;
	  
	  private String total_outstanding;
	  
	  private String prev_arrear;
	  
	  private String pend_surcharge;
	  
	  private String curr_surcharge;
	  
	  private String coll_surcharge;	  
	  
	  private String pfl_bill_amount;
	  
	  private String mf;
	  
	  private boolean is_beneficiary;
	  
	  private String nrev_catg;
	  
	  private long purpose_of_installation_id;
	  
	  private String sd_enhance_cd;
	  
	  private String dishnrd_chq_flg;

	  private String status;
	  
	  @Column(name = "is_affiliated")
	  private boolean isAffiliated;
	
	  @Column(name = "is_affiliated_consumer")
	  private boolean isAffiliatedConsumer;
	  
      @Column(name = "affiliated_consumer_no")
	  private String affiliatedConsumerNo;
      
      @Column(name = "is_saral")
      private boolean isSaral;
      
      @Column(name = "is_bill_mafi")
      private boolean isBillMafi;
      
      @Column(name = "shramik_no")
      private String shramikNo;
      
      @Column(name = "purpose_of_installation_cd")
      private String purposeOfInstallationCD;
      
      @Column(name = "ccnb_purpose_of_installation")
	  private String ccnbPurposeOfInstallation;
	  
      @Column(name = "meter_type")
	  private String meterType;
	  
      @Column(name = "meter_capacity")
	  private String meterCapacity;
	  
      @Column(name = "pdc_date")
	  private Date pdcDate;
      
      @Column(name = "created_by")
      private String createdBy;

      @Column(name = "migrated")
      private boolean migrated;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getConnection_date() {
		return connection_date;
	}

	public void setConnection_date(Date connection_date) {
		this.connection_date = connection_date;
	}

	public String getConsumer_name() {
		return consumer_name;
	}

	public void setConsumer_name(String consumer_name) {
		this.consumer_name = consumer_name;
	}

	public String getConsumer_name_h() {
		return consumer_name_h;
	}

	public void setConsumer_name_h(String consumer_name_h) {
		this.consumer_name_h = consumer_name_h;
	}

	public String getRelative_name() {
		return relative_name;
	}

	public void setRelative_name(String relative_name) {
		this.relative_name = relative_name;
	}

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	public boolean isIs_bpl() {
		return is_bpl;
	}

	public void setIs_bpl(boolean is_bpl) {
		this.is_bpl = is_bpl;
	}

	public String getBpl_no() {
		return bpl_no;
	}

	public void setBpl_no(String bpl_no) {
		this.bpl_no = bpl_no;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public boolean isIs_employee() {
		return is_employee;
	}

	public void setIs_employee(boolean is_employee) {
		this.is_employee = is_employee;
	}

	public String getEmployee_company() {
		return employee_company;
	}

	public void setEmployee_company(String employee_company) {
		this.employee_company = employee_company;
	}

	public String getEmployee_no() {
		return employee_no;
	}

	public void setEmployee_no(String employee_no) {
		this.employee_no = employee_no;
	}

	public String getAddress_1() {
		return address_1;
	}

	public void setAddress_1(String address_1) {
		this.address_1 = address_1;
	}

	public String getAddress_2() {
		return address_2;
	}

	public void setAddress_2(String address_2) {
		this.address_2 = address_2;
	}

	public String getAddress_3() {
		return address_3;
	}

	public void setAddress_3(String address_3) {
		this.address_3 = address_3;
	}

	public String getAddress_1_h() {
		return address_1_h;
	}

	public void setAddress_1_h(String address_1_h) {
		this.address_1_h = address_1_h;
	}

	public String getAddress_2_h() {
		return address_2_h;
	}

	public void setAddress_2_h(String address_2_h) {
		this.address_2_h = address_2_h;
	}

	public String getAddress_3_h() {
		return address_3_h;
	}

	public void setAddress_3_h(String address_3_h) {
		this.address_3_h = address_3_h;
	}

	public String getPrimary_mobile_no() {
		return primary_mobile_no;
	}

	public void setPrimary_mobile_no(String primary_mobile_no) {
		this.primary_mobile_no = primary_mobile_no;
	}

	public String getAlternate_mobile_no() {
		return alternate_mobile_no;
	}

	public void setAlternate_mobile_no(String alternate_mobile_no) {
		this.alternate_mobile_no = alternate_mobile_no;
	}

	public String getAadhaar_no() {
		return aadhaar_no;
	}

	public void setAadhaar_no(String aadhaar_no) {
		this.aadhaar_no = aadhaar_no;
	}

	public String getPan() {
		return pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	public String getBank_account_no() {
		return bank_account_no;
	}

	public void setBank_account_no(String bank_account_no) {
		this.bank_account_no = bank_account_no;
	}

	public String getBank_account_holder_name() {
		return bank_account_holder_name;
	}

	public void setBank_account_holder_name(String bank_account_holder_name) {
		this.bank_account_holder_name = bank_account_holder_name;
	}

	public String getBank_name() {
		return bank_name;
	}

	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}

	public String getIfsc() {
		return ifsc;
	}

	public void setIfsc(String ifsc) {
		this.ifsc = ifsc;
	}

	public String getEmail_address() {
		return email_address;
	}

	public void setEmail_address(String email_address) {
		this.email_address = email_address;
	}

	public String getTariff_category() {
		return tariff_category;
	}

	public void setTariff_category(String tariff_category) {
		this.tariff_category = tariff_category;
	}

	public String getConnection_type() {
		return connection_type;
	}

	public void setConnection_type(String connection_type) {
		this.connection_type = connection_type;
	}

	public String getMetering_status() {
		return metering_status;
	}

	public void setMetering_status(String metering_status) {
		this.metering_status = metering_status;
	}

	public String getPremise_type() {
		return premise_type;
	}

	public void setPremise_type(String premise_type) {
		this.premise_type = premise_type;
	}

	public BigDecimal getSanctioned_load() {
		return sanctioned_load;
	}

	public void setSanctioned_load(BigDecimal sanctioned_load) {
		this.sanctioned_load = sanctioned_load;
	}

	public String getSanctioned_load_unit() {
		return sanctioned_load_unit;
	}

	public void setSanctioned_load_unit(String sanctioned_load_unit) {
		this.sanctioned_load_unit = sanctioned_load_unit;
	}

	public BigDecimal getContract_demand() {
		return contract_demand;
	}

	public void setContract_demand(BigDecimal contract_demand) {
		this.contract_demand = contract_demand;
	}

	public String getContract_demand_unit() {
		return contract_demand_unit;
	}

	public void setContract_demand_unit(String contract_demand_unit) {
		this.contract_demand_unit = contract_demand_unit;
	}

	public boolean isIs_seasonal() {
		return is_seasonal;
	}

	public void setIs_seasonal(boolean is_seasonal) {
		this.is_seasonal = is_seasonal;
	}

	public Date getSeason_start_date() {
		return season_start_date;
	}

	public void setSeason_start_date(Date season_start_date) {
		this.season_start_date = season_start_date;
	}

	public Date getSeason_end_date() {
		return season_end_date;
	}

	public void setSeason_end_date(Date season_end_date) {
		this.season_end_date = season_end_date;
	}

	public String getSeason_start_bill_month() {
		return season_start_bill_month;
	}

	public void setSeason_start_bill_month(String season_start_bill_month) {
		this.season_start_bill_month = season_start_bill_month;
	}

	public String getSeason_end_bill_month() {
		return season_end_bill_month;
	}

	public void setSeason_end_bill_month(String season_end_bill_month) {
		this.season_end_bill_month = season_end_bill_month;
	}

	public String getPurpose_of_installation() {
		return purpose_of_installation;
	}

	public void setPurpose_of_installation(String purpose_of_installation) {
		this.purpose_of_installation = purpose_of_installation;
	}

	public String getTariff_code() {
		return tariff_code;
	}

	public void setTariff_code(String tariff_code) {
		this.tariff_code = tariff_code;
	}

	public long getSub_category_code() {
		return sub_category_code;
	}

	public void setSub_category_code(long sub_category_code) {
		this.sub_category_code = sub_category_code;
	}

	public String getPhase() {
		return phase;
	}

	public void setPhase(String phase) {
		this.phase = phase;
	}

	public Date getTc_start_date() {
		return tc_start_date;
	}

	public void setTc_start_date(Date tc_start_date) {
		this.tc_start_date = tc_start_date;
	}

	public Date getTc_end_date() {
		return tc_end_date;
	}

	public void setTc_end_date(Date tc_end_date) {
		this.tc_end_date = tc_end_date;
	}

	public boolean isIs_government() {
		return is_government;
	}

	public void setIs_government(boolean is_government) {
		this.is_government = is_government;
	}

	public String getGovernment_type() {
		return government_type;
	}

	public void setGovernment_type(String government_type) {
		this.government_type = government_type;
	}

	public BigDecimal getPlot_size() {
		return plot_size;
	}

	public void setPlot_size(BigDecimal plot_size) {
		this.plot_size = plot_size;
	}

	public String getPlot_size_unit() {
		return plot_size_unit;
	}

	public void setPlot_size_unit(String plot_size_unit) {
		this.plot_size_unit = plot_size_unit;
	}

	public String getLocation_code() {
		return location_code;
	}

	public void setLocation_code(String location_code) {
		this.location_code = location_code;
	}

	public boolean isIs_xray() {
		return is_xray;
	}

	public void setIs_xray(boolean is_xray) {
		this.is_xray = is_xray;
	}

	public BigDecimal getXray_load() {
		return xray_load;
	}

	public void setXray_load(BigDecimal xray_load) {
		this.xray_load = xray_load;
	}

	public long getNo_of_dental_xray_machine() {
		return no_of_dental_xray_machine;
	}

	public void setNo_of_dental_xray_machine(long no_of_dental_xray_machine) {
		this.no_of_dental_xray_machine = no_of_dental_xray_machine;
	}

	public long getNo_of_single_phase_xray_machine() {
		return no_of_single_phase_xray_machine;
	}

	public void setNo_of_single_phase_xray_machine(long no_of_single_phase_xray_machine) {
		this.no_of_single_phase_xray_machine = no_of_single_phase_xray_machine;
	}

	public long getNo_of_three_phase_xray_machine() {
		return no_of_three_phase_xray_machine;
	}

	public void setNo_of_three_phase_xray_machine(long no_of_three_phase_xray_machine) {
		this.no_of_three_phase_xray_machine = no_of_three_phase_xray_machine;
	}

	public boolean isIs_welding_transformer_surcharge() {
		return is_welding_transformer_surcharge;
	}

	public void setIs_welding_transformer_surcharge(boolean is_welding_transformer_surcharge) {
		this.is_welding_transformer_surcharge = is_welding_transformer_surcharge;
	}

	public boolean isIs_capacitor_surcharge() {
		return is_capacitor_surcharge;
	}

	public void setIs_capacitor_surcharge(boolean is_capacitor_surcharge) {
		this.is_capacitor_surcharge = is_capacitor_surcharge;
	}

	public boolean isIs_demandside() {
		return is_demandside;
	}

	public void setIs_demandside(boolean is_demandside) {
		this.is_demandside = is_demandside;
	}

	public String getIsi_motor_type() {
		return isi_motor_type;
	}

	public void setIsi_motor_type(String isi_motor_type) {
		this.isi_motor_type = isi_motor_type;
	}

	public String getDtr_name() {
		return dtr_name;
	}

	public void setDtr_name(String dtr_name) {
		this.dtr_name = dtr_name;
	}

	public String getPole_no() {
		return pole_no;
	}

	public void setPole_no(String pole_no) {
		this.pole_no = pole_no;
	}

	public String getPole_latitude() {
		return pole_latitude;
	}

	public void setPole_latitude(String pole_latitude) {
		this.pole_latitude = pole_latitude;
	}

	public String getPole_longitude() {
		return pole_longitude;
	}

	public void setPole_longitude(String pole_longitude) {
		this.pole_longitude = pole_longitude;
	}

	public String getFeeder_name() {
		return feeder_name;
	}

	public void setFeeder_name(String feeder_name) {
		this.feeder_name = feeder_name;
	}

	public long getPole_distance() {
		return pole_distance;
	}

	public void setPole_distance(long pole_distance) {
		this.pole_distance = pole_distance;
	}

	public String getArea_status() {
		return area_status;
	}

	public void setArea_status(String area_status) {
		this.area_status = area_status;
	}

	public String getGroup_no() {
		return group_no;
	}

	public void setGroup_no(String group_no) {
		this.group_no = group_no;
	}

	public String getReading_diary_no() {
		return reading_diary_no;
	}

	public void setReading_diary_no(String reading_diary_no) {
		this.reading_diary_no = reading_diary_no;
	}

	public String getNeighbour_connection_no() {
		return neighbour_connection_no;
	}

	public void setNeighbour_connection_no(String neighbour_connection_no) {
		this.neighbour_connection_no = neighbour_connection_no;
	}

	public Date getSurvey_date() {
		return survey_date;
	}

	public void setSurvey_date(Date survey_date) {
		this.survey_date = survey_date;
	}

	public String getMeter_identifier() {
		return meter_identifier;
	}

	public void setMeter_identifier(String meter_identifier) {
		this.meter_identifier = meter_identifier;
	}

	public BigDecimal getStart_read() {
		return start_read;
	}

	public void setStart_read(BigDecimal start_read) {
		this.start_read = start_read;
	}

	public boolean isHas_ctr() {
		return has_ctr;
	}

	public void setHas_ctr(boolean has_ctr) {
		this.has_ctr = has_ctr;
	}

	public String getCtr_identifier() {
		return ctr_identifier;
	}

	public void setCtr_identifier(String ctr_identifier) {
		this.ctr_identifier = ctr_identifier;
	}

	public BigDecimal getCtr_overall_mf() {
		return ctr_overall_mf;
	}

	public void setCtr_overall_mf(BigDecimal ctr_overall_mf) {
		this.ctr_overall_mf = ctr_overall_mf;
	}

	public Date getMeter_installation_date() {
		return meter_installation_date;
	}

	public void setMeter_installation_date(Date meter_installation_date) {
		this.meter_installation_date = meter_installation_date;
	}

	public String getMeter_installer_name() {
		return meter_installer_name;
	}

	public void setMeter_installer_name(String meter_installer_name) {
		this.meter_installer_name = meter_installer_name;
	}

	public String getMeter_installer_designation() {
		return meter_installer_designation;
	}

	public void setMeter_installer_designation(String meter_installer_designation) {
		this.meter_installer_designation = meter_installer_designation;
	}

	public boolean isHas_modem() {
		return has_modem;
	}

	public void setHas_modem(boolean has_modem) {
		this.has_modem = has_modem;
	}

	public String getModem_no() {
		return modem_no;
	}

	public void setModem_no(String modem_no) {
		this.modem_no = modem_no;
	}

	public String getSim_no() {
		return sim_no;
	}

	public void setSim_no(String sim_no) {
		this.sim_no = sim_no;
	}

	public Date getDate_of_registration() {
		return date_of_registration;
	}

	public void setDate_of_registration(Date date_of_registration) {
		this.date_of_registration = date_of_registration;
	}

	public BigDecimal getRegistration_fee_amount() {
		return registration_fee_amount;
	}

	public void setRegistration_fee_amount(BigDecimal registration_fee_amount) {
		this.registration_fee_amount = registration_fee_amount;
	}

	public String getRegistration_fee_amount_mr_no() {
		return registration_fee_amount_mr_no;
	}

	public void setRegistration_fee_amount_mr_no(String registration_fee_amount_mr_no) {
		this.registration_fee_amount_mr_no = registration_fee_amount_mr_no;
	}

	public BigDecimal getSecurity_deposit_amount() {
		return security_deposit_amount;
	}

	public void setSecurity_deposit_amount(BigDecimal security_deposit_amount) {
		this.security_deposit_amount = security_deposit_amount;
	}

	public String getSecurity_deposit_amount_mr_no() {
		return security_deposit_amount_mr_no;
	}

	public void setSecurity_deposit_amount_mr_no(String security_deposit_amount_mr_no) {
		this.security_deposit_amount_mr_no = security_deposit_amount_mr_no;
	}

	public String getPortal_name() {
		return portal_name;
	}

	public void setPortal_name(String portal_name) {
		this.portal_name = portal_name;
	}

	public String getPortal_reference_no() {
		return portal_reference_no;
	}

	public void setPortal_reference_no(String portal_reference_no) {
		this.portal_reference_no = portal_reference_no;
	}

	public Date getCreated_on() {
		return created_on;
	}

	public void setCreated_on(Date created_on) {
		this.created_on = created_on;
	}

	public String getOld_cons_no() {
		return old_cons_no;
	}

	public void setOld_cons_no(String old_cons_no) {
		this.old_cons_no = old_cons_no;
	}

	public String getOld_gr_no() {
		return old_gr_no;
	}

	public void setOld_gr_no(String old_gr_no) {
		this.old_gr_no = old_gr_no;
	}

	public String getOld_rd_no() {
		return old_rd_no;
	}

	public void setOld_rd_no(String old_rd_no) {
		this.old_rd_no = old_rd_no;
	}

	public String getOld_trf_catg() {
		return old_trf_catg;
	}

	public void setOld_trf_catg(String old_trf_catg) {
		this.old_trf_catg = old_trf_catg;
	}

	public String getOld_rev_catg() {
		return old_rev_catg;
	}

	public void setOld_rev_catg(String old_rev_catg) {
		this.old_rev_catg = old_rev_catg;
	}

	public String getTotal_outstanding() {
		return total_outstanding;
	}

	public void setTotal_outstanding(String total_outstanding) {
		this.total_outstanding = total_outstanding;
	}

	public String getPrev_arrear() {
		return prev_arrear;
	}

	public void setPrev_arrear(String prev_arrear) {
		this.prev_arrear = prev_arrear;
	}

	public String getPend_surcharge() {
		return pend_surcharge;
	}

	public void setPend_surcharge(String pend_surcharge) {
		this.pend_surcharge = pend_surcharge;
	}

	public String getCurr_surcharge() {
		return curr_surcharge;
	}

	public void setCurr_surcharge(String curr_surcharge) {
		this.curr_surcharge = curr_surcharge;
	}

	public String getColl_surcharge() {
		return coll_surcharge;
	}

	public void setColl_surcharge(String coll_surcharge) {
		this.coll_surcharge = coll_surcharge;
	}

	public String getPfl_bill_amount() {
		return pfl_bill_amount;
	}

	public void setPfl_bill_amount(String pfl_bill_amount) {
		this.pfl_bill_amount = pfl_bill_amount;
	}

	public String getMf() {
		return mf;
	}

	public void setMf(String mf) {
		this.mf = mf;
	}

	public boolean isIs_beneficiary() {
		return is_beneficiary;
	}

	public void setIs_beneficiary(boolean is_beneficiary) {
		this.is_beneficiary = is_beneficiary;
	}

	public String getNrev_catg() {
		return nrev_catg;
	}

	public void setNrev_catg(String nrev_catg) {
		this.nrev_catg = nrev_catg;
	}

	public long getPurpose_of_installation_id() {
		return purpose_of_installation_id;
	}

	public void setPurpose_of_installation_id(long purpose_of_installation_id) {
		this.purpose_of_installation_id = purpose_of_installation_id;
	}

	public String getSd_enhance_cd() {
		return sd_enhance_cd;
	}

	public void setSd_enhance_cd(String sd_enhance_cd) {
		this.sd_enhance_cd = sd_enhance_cd;
	}

	public String getDishnrd_chq_flg() {
		return dishnrd_chq_flg;
	}

	public void setDishnrd_chq_flg(String dishnrd_chq_flg) {
		this.dishnrd_chq_flg = dishnrd_chq_flg;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isAffiliated() {
		return isAffiliated;
	}

	public void setAffiliated(boolean isAffiliated) {
		this.isAffiliated = isAffiliated;
	}

	public boolean isAffiliatedConsumer() {
		return isAffiliatedConsumer;
	}

	public void setAffiliatedConsumer(boolean isAffiliatedConsumer) {
		this.isAffiliatedConsumer = isAffiliatedConsumer;
	}

	public String getAffiliatedConsumerNo() {
		return affiliatedConsumerNo;
	}

	public void setAffiliatedConsumerNo(String affiliatedConsumerNo) {
		this.affiliatedConsumerNo = affiliatedConsumerNo;
	}

	public boolean isSaral() {
		return isSaral;
	}

	public void setSaral(boolean isSaral) {
		this.isSaral = isSaral;
	}

	public boolean isBillMafi() {
		return isBillMafi;
	}

	public void setBillMafi(boolean isBillMafi) {
		this.isBillMafi = isBillMafi;
	}

	public String getShramikNo() {
		return shramikNo;
	}

	public void setShramikNo(String shramikNo) {
		this.shramikNo = shramikNo;
	}

	public String getPurposeOfInstallationCD() {
		return purposeOfInstallationCD;
	}

	public void setPurposeOfInstallationCD(String purposeOfInstallationCD) {
		this.purposeOfInstallationCD = purposeOfInstallationCD;
	}

	public String getCcnbPurposeOfInstallation() {
		return ccnbPurposeOfInstallation;
	}

	public void setCcnbPurposeOfInstallation(String ccnbPurposeOfInstallation) {
		this.ccnbPurposeOfInstallation = ccnbPurposeOfInstallation;
	}

	public String getMeterType() {
		return meterType;
	}

	public void setMeterType(String meterType) {
		this.meterType = meterType;
	}

	public String getMeterCapacity() {
		return meterCapacity;
	}

	public void setMeterCapacity(String meterCapacity) {
		this.meterCapacity = meterCapacity;
	}

	public Date getPdcDate() {
		return pdcDate;
	}

	public void setPdcDate(Date pdcDate) {
		this.pdcDate = pdcDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public boolean isMigrated() {
		return migrated;
	}

	public void setMigrated(boolean migrated) {
		this.migrated = migrated;
	}

	@Override
	public String toString() {
		return "CCNBNSCStagingMigration [id=" + id + ", connection_date=" + connection_date + ", consumer_name="
				+ consumer_name + ", consumer_name_h=" + consumer_name_h + ", relative_name=" + relative_name
				+ ", relation=" + relation + ", is_bpl=" + is_bpl + ", bpl_no=" + bpl_no + ", category=" + category
				+ ", is_employee=" + is_employee + ", employee_company=" + employee_company + ", employee_no="
				+ employee_no + ", address_1=" + address_1 + ", address_2=" + address_2 + ", address_3=" + address_3
				+ ", address_1_h=" + address_1_h + ", address_2_h=" + address_2_h + ", address_3_h=" + address_3_h
				+ ", primary_mobile_no=" + primary_mobile_no + ", alternate_mobile_no=" + alternate_mobile_no
				+ ", aadhaar_no=" + aadhaar_no + ", pan=" + pan + ", bank_account_no=" + bank_account_no
				+ ", bank_account_holder_name=" + bank_account_holder_name + ", bank_name=" + bank_name + ", ifsc="
				+ ifsc + ", email_address=" + email_address + ", tariff_category=" + tariff_category
				+ ", connection_type=" + connection_type + ", metering_status=" + metering_status + ", premise_type="
				+ premise_type + ", sanctioned_load=" + sanctioned_load + ", sanctioned_load_unit="
				+ sanctioned_load_unit + ", contract_demand=" + contract_demand + ", contract_demand_unit="
				+ contract_demand_unit + ", is_seasonal=" + is_seasonal + ", season_start_date=" + season_start_date
				+ ", season_end_date=" + season_end_date + ", season_start_bill_month=" + season_start_bill_month
				+ ", season_end_bill_month=" + season_end_bill_month + ", purpose_of_installation="
				+ purpose_of_installation + ", tariff_code=" + tariff_code + ", sub_category_code=" + sub_category_code
				+ ", phase=" + phase + ", tc_start_date=" + tc_start_date + ", tc_end_date=" + tc_end_date
				+ ", is_government=" + is_government + ", government_type=" + government_type + ", plot_size="
				+ plot_size + ", plot_size_unit=" + plot_size_unit + ", location_code=" + location_code + ", is_xray="
				+ is_xray + ", xray_load=" + xray_load + ", no_of_dental_xray_machine=" + no_of_dental_xray_machine
				+ ", no_of_single_phase_xray_machine=" + no_of_single_phase_xray_machine
				+ ", no_of_three_phase_xray_machine=" + no_of_three_phase_xray_machine
				+ ", is_welding_transformer_surcharge=" + is_welding_transformer_surcharge + ", is_capacitor_surcharge="
				+ is_capacitor_surcharge + ", is_demandside=" + is_demandside + ", isi_motor_type=" + isi_motor_type
				+ ", dtr_name=" + dtr_name + ", pole_no=" + pole_no + ", pole_latitude=" + pole_latitude
				+ ", pole_longitude=" + pole_longitude + ", feeder_name=" + feeder_name + ", pole_distance="
				+ pole_distance + ", area_status=" + area_status + ", group_no=" + group_no + ", reading_diary_no="
				+ reading_diary_no + ", neighbour_connection_no=" + neighbour_connection_no + ", survey_date="
				+ survey_date + ", meter_identifier=" + meter_identifier + ", start_read=" + start_read + ", has_ctr="
				+ has_ctr + ", ctr_identifier=" + ctr_identifier + ", ctr_overall_mf=" + ctr_overall_mf
				+ ", meter_installation_date=" + meter_installation_date + ", meter_installer_name="
				+ meter_installer_name + ", meter_installer_designation=" + meter_installer_designation + ", has_modem="
				+ has_modem + ", modem_no=" + modem_no + ", sim_no=" + sim_no + ", date_of_registration="
				+ date_of_registration + ", registration_fee_amount=" + registration_fee_amount
				+ ", registration_fee_amount_mr_no=" + registration_fee_amount_mr_no + ", security_deposit_amount="
				+ security_deposit_amount + ", security_deposit_amount_mr_no=" + security_deposit_amount_mr_no
				+ ", portal_name=" + portal_name + ", portal_reference_no=" + portal_reference_no + ", created_on="
				+ created_on + ", old_cons_no=" + old_cons_no + ", old_gr_no=" + old_gr_no + ", old_rd_no=" + old_rd_no
				+ ", old_trf_catg=" + old_trf_catg + ", old_rev_catg=" + old_rev_catg + ", total_outstanding="
				+ total_outstanding + ", prev_arrear=" + prev_arrear + ", pend_surcharge=" + pend_surcharge
				+ ", curr_surcharge=" + curr_surcharge + ", coll_surcharge=" + coll_surcharge + ", pfl_bill_amount="
				+ pfl_bill_amount + ", mf=" + mf + ", is_beneficiary=" + is_beneficiary + ", nrev_catg=" + nrev_catg
				+ ", purpose_of_installation_id=" + purpose_of_installation_id + ", sd_enhance_cd=" + sd_enhance_cd
				+ ", dishnrd_chq_flg=" + dishnrd_chq_flg + ", status=" + status + ", isAffiliated=" + isAffiliated
				+ ", isAffiliatedConsumer=" + isAffiliatedConsumer + ", affiliatedConsumerNo=" + affiliatedConsumerNo
				+ ", isSaral=" + isSaral + ", isBillMafi=" + isBillMafi + ", shramikNo=" + shramikNo
				+ ", purposeOfInstallationCD=" + purposeOfInstallationCD + ", ccnbPurposeOfInstallation="
				+ ccnbPurposeOfInstallation + ", meterType=" + meterType + ", meterCapacity=" + meterCapacity
				+ ", pdcDate=" + pdcDate + ", createdBy=" + createdBy + ", migrated=" + migrated + "]";
	}      
}


