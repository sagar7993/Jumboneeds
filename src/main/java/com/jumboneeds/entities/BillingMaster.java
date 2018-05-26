package com.jumboneeds.entities;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "billing_master")
public class BillingMaster {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name="id")
    private String id;

	@Column(name = "amount", nullable = false, columnDefinition = "double default '0'")
	private Double amount = 0.0;

    @Transient
    private Double transientAmount;

	@Column(name = "cashback", nullable = false, columnDefinition = "double default '0'")
	private Double cashback = 0.0;

    @Column(name = "milk_amount", nullable = false, columnDefinition = "double default '0'")
    private Double milkAmount = 0.0;

    @Column(name = "non_milk_amount", nullable = false, columnDefinition = "double default '0'")
    private Double nonMilkAmount = 0.0;

	@Column(name = "end_date")
	private Date endDate;

	@Column(name = "start_date", nullable = false)
	private Date startDate;

	@Column(name = "payment_date", nullable = false)
	private Date paymentDate;

	@Column(name = "current", nullable = false, columnDefinition = "tinyint(1) default 0")
	private Boolean current = false;

    @Column(name = "description")
    private String description;

	@Column(name = "bank_txn_id")
	private String bankTxnId;

	@Column(name = "txn_id")
	private String txnId;

    @Column(name = "payment_amount", nullable = false, columnDefinition = "double default '0'")
    private Double paymentAmount = 0.0;

    @Transient
    private Integer transientPaymentType;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user", nullable = false)
	private User user;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "payment_type")
	private PaymentType paymentType;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "partner")
    private Partner partner;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "executive")
	private Executive executive;

    public BillingMaster() {

	}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getTransientAmount() {
        return transientAmount;
    }

    public void setTransientAmount(Double transientAmount) {
        this.transientAmount = transientAmount;
    }

    public Double getCashback() {
        return cashback;
    }

    public void setCashback(Double cashback) {
        this.cashback = cashback;
    }

    public Double getMilkAmount() {
        return milkAmount;
    }

    public void setMilkAmount(Double milkAmount) {
        this.milkAmount = milkAmount;
    }

    public Double getNonMilkAmount() {
        return nonMilkAmount;
    }

    public void setNonMilkAmount(Double nonMilkAmount) {
        this.nonMilkAmount = nonMilkAmount;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Boolean getCurrent() {
        return current;
    }

    public void setCurrent(Boolean current) {
        this.current = current;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBankTxnId() {
        return bankTxnId;
    }

    public void setBankTxnId(String bankTxnId) {
        this.bankTxnId = bankTxnId;
    }

    public String getTxnId() {
        return txnId;
    }

    public void setTxnId(String txnId) {
        this.txnId = txnId;
    }

    public Double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(Double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public Integer getTransientPaymentType() {
        return transientPaymentType;
    }

    public void setTransientPaymentType(Integer transientPaymentType) {
        this.transientPaymentType = transientPaymentType;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public Partner getPartner() {
        return partner;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }

    public Executive getExecutive() {
        return executive;
    }

    public void setExecutive(Executive executive) {
        this.executive = executive;
    }

}