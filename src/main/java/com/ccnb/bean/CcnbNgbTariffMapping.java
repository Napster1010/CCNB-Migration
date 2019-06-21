package com.ccnb.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "CcnbNgbTariffMapping")
@Table(name = "ccnb_ngb_tariff_mapping")
public class CcnbNgbTariffMapping {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "ccnb_tariff")
	private String ccnbTariff;
	
	@Column(name = "ccnb_char_type_cd")
	private String ccnbCharTypeCd;
	
	@Column(name = "ccnb_char_val")
	private String ccnbCharVal;
	
	@Column(name = "ccnb_version")
	private String ccnbVersion;
	
	@Column(name = "ccnb_owner_flag")
	private String ccnbOwnerFlag;
	
	@Column(name = "ccnb_purpose")
	private String ccnbPurpose;
	
	@Column(name = "ngb_purpose_id")
	private String ngbPurposeId;
	
	@Column(name = "ngb_tariff_code")
	private String ngbTariffCode;

	@Column(name = "ngb_purpose_of_installation")
	private String ngbPurposeOfInstallation;
	
	@Column(name = "ngb_urban_subcategory_1")
	private String ngbUrbanSubcategory1;
	
	@Column(name = "ngb_rural_subcategory_1")
	private String ngbRuralSubcategory1;
	
	
	@Column(name = "ngb_urban_subcategory_2")
	private String ngbUrbanSubcategory2;
	
	@Column(name = "ngb_rural_subcategory_2")
	private String ngbRuralSubcategory2;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCcnbTariff() {
		return ccnbTariff;
	}

	public void setCcnbTariff(String ccnbTariff) {
		this.ccnbTariff = ccnbTariff;
	}

	public String getCcnbCharTypeCd() {
		return ccnbCharTypeCd;
	}

	public void setCcnbCharTypeCd(String ccnbCharTypeCd) {
		this.ccnbCharTypeCd = ccnbCharTypeCd;
	}

	public String getCcnbCharVal() {
		return ccnbCharVal;
	}

	public void setCcnbCharVal(String ccnbCharVal) {
		this.ccnbCharVal = ccnbCharVal;
	}

	public String getCcnbVersion() {
		return ccnbVersion;
	}

	public void setCcnbVersion(String ccnbVersion) {
		this.ccnbVersion = ccnbVersion;
	}

	public String getCcnbOwnerFlag() {
		return ccnbOwnerFlag;
	}

	public void setCcnbOwnerFlag(String ccnbOwnerFlag) {
		this.ccnbOwnerFlag = ccnbOwnerFlag;
	}

	public String getCcnbPurpose() {
		return ccnbPurpose;
	}

	public void setCcnbPurpose(String ccnbPurpose) {
		this.ccnbPurpose = ccnbPurpose;
	}

	public String getNgbPurposeId() {
		return ngbPurposeId;
	}

	public void setNgbPurposeId(String ngbPurposeId) {
		this.ngbPurposeId = ngbPurposeId;
	}

	public String getNgbTariffCode() {
		return ngbTariffCode;
	}

	public void setNgbTariffCode(String ngbTariffCode) {
		this.ngbTariffCode = ngbTariffCode;
	}

	public String getNgbPurposeOfInstallation() {
		return ngbPurposeOfInstallation;
	}

	public void setNgbPurposeOfInstallation(String ngbPurposeOfInstallation) {
		this.ngbPurposeOfInstallation = ngbPurposeOfInstallation;
	}

	public String getNgbUrbanSubcategory1() {
		return ngbUrbanSubcategory1;
	}

	public void setNgbUrbanSubcategory1(String ngbUrbanSubcategory1) {
		this.ngbUrbanSubcategory1 = ngbUrbanSubcategory1;
	}

	public String getNgbRuralSubcategory1() {
		return ngbRuralSubcategory1;
	}

	public void setNgbRuralSubcategory1(String ngbRuralSubcategory1) {
		this.ngbRuralSubcategory1 = ngbRuralSubcategory1;
	}

	public String getNgbUrbanSubcategory2() {
		return ngbUrbanSubcategory2;
	}

	public void setNgbUrbanSubcategory2(String ngbUrbanSubcategory2) {
		this.ngbUrbanSubcategory2 = ngbUrbanSubcategory2;
	}

	public String getNgbRuralSubcategory2() {
		return ngbRuralSubcategory2;
	}

	public void setNgbRuralSubcategory2(String ngbRuralSubcategory2) {
		this.ngbRuralSubcategory2 = ngbRuralSubcategory2;
	}

	@Override
	public String toString() {
		return "CcnbNgbTariffMapping [id=" + id + ", ccnbTariff=" + ccnbTariff + ", ccnbCharTypeCd=" + ccnbCharTypeCd
				+ ", ccnbCharVal=" + ccnbCharVal + ", ccnbVersion=" + ccnbVersion + ", ccnbOwnerFlag=" + ccnbOwnerFlag
				+ ", ccnbPurpose=" + ccnbPurpose + ", ngbPurposeId=" + ngbPurposeId + ", ngbTariffCode=" + ngbTariffCode
				+ ", ngbPurposeOfInstallation=" + ngbPurposeOfInstallation + ", ngbUrbanSubcategory1="
				+ ngbUrbanSubcategory1 + ", ngbRuralSubcategory1=" + ngbRuralSubcategory1 + ", ngbUrbanSubcategory2="
				+ ngbUrbanSubcategory2 + ", ngbRuralSubcategory2=" + ngbRuralSubcategory2 + "]";
	}
}