package com.ccnb.bean;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity(name = "AdditionalSecurityDepositInstallment")
@Table(name = "additional_security_deposit_installment")
public class AdditionalSecurityDepositInstallment {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "additional_security_deposit_id")
    private long additionalSecurityDepositId;

    @Column(name = "installment_amount")
    private BigDecimal installmentAmount;

    @Column(name = "bill_month")
    private String billMonth;

    @Column(name = "posted")
    private boolean posted;

    @Column(name = "deleted")
    private boolean deleted;

    
    public long getId() {
        return id;
    }

    
    public void setId(long id) {
        this.id = id;
    }

    
    public long getAdditionalSecurityDepositId() {
        return additionalSecurityDepositId;
    }

    
    public void setAdditionalSecurityDepositId(long additionalSecurityDepositId) {
        this.additionalSecurityDepositId = additionalSecurityDepositId;
    }

    
    public BigDecimal getInstallmentAmount() {
        return installmentAmount;
    }

    
    public void setInstallmentAmount(BigDecimal installmentAmount) {
        this.installmentAmount = installmentAmount;
    }

    
    public String getBillMonth() {
        return billMonth;
    }

    
    public void setBillMonth(String billMonth) {
        this.billMonth = billMonth;
    }

    
    public boolean getPosted() {
        return posted;
    }

    
    public void setPosted(boolean posted) {
        this.posted = posted;
    }

    
    public boolean getDeleted() {
        return deleted;
    }

    
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
