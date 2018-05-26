package com.jumboneeds.entities;

import com.jumboneeds.beans.ProductAnalysisBean;
import com.jumboneeds.beans.RevenueAnalysisBean;
import com.jumboneeds.beans.UserAnalysisBean;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "billing_log")

@SqlResultSetMappings({
    @SqlResultSetMapping(name="RevenueAnalysisQueryList", classes = { @ConstructorResult(targetClass = RevenueAnalysisBean.class,
        columns = {@ColumnResult(name="jumbo_order_count"), @ColumnResult(name="jumbo_user_count"), @ColumnResult(name="jumbo_revenue"),
            @ColumnResult(name="vendor_order_count"), @ColumnResult(name="vendor_user_count"), @ColumnResult(name="vendor_revenue"),
            @ColumnResult(name="total_order_count"), @ColumnResult(name="total_user_count"), @ColumnResult(name="total_revenue")
        })
    }),
    @SqlResultSetMapping(name="ProductAnalysisQueryList", classes = { @ConstructorResult(targetClass = ProductAnalysisBean.class,
        columns = {@ColumnResult(name="category"), @ColumnResult(name="id"), @ColumnResult(name="quantity"), @ColumnResult(name="user_count"), @ColumnResult(name="order_count")})
    }),
    @SqlResultSetMapping(name="UserAnalysisQueryList", classes = { @ConstructorResult(targetClass = UserAnalysisBean.class,
        columns = {@ColumnResult(name="user_id"), @ColumnResult(name="user_name"), @ColumnResult(name="building_name"),
            @ColumnResult(name="block_name"), @ColumnResult(name="flat"), @ColumnResult(name="mobile_number"),
            @ColumnResult(name="quantity"), @ColumnResult(name="order_count"), @ColumnResult(name="revenue")
        })
    })
})

public class BillingLog {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name="id")
    private String id;
    
	@Column(name = "amount", nullable = false, columnDefinition = "double default '0'")
	private Double amount = 0.0;

	@Column(name = "date", nullable = false)
	private Date date;

    @Column(name = "cashback", nullable = false, columnDefinition = "double default '0'")
    private Double cashback = 0.0;

    @Column(name = "milk_amount", nullable = false, columnDefinition = "double default '0'")
    private Double milkAmount = 0.0;

    @Column(name = "non_milk_amount", nullable = false, columnDefinition = "double default '0'")
    private Double nonMilkAmount = 0.0;

	@Column(name = "description", nullable = false)
	private String description;

    @Column(name = "flat", nullable = false)
    private String flat;

    @Column(name = "previous_balance", nullable = false, columnDefinition = "double default '0'")
    private Double previousBalance = 0.0;

    @Transient
    private Integer transientBillingLogType;

    @Transient
    private Integer transientPaymentType;

    @Transient
    private Double jumboNeedsAmount;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_type")
    private PaymentType paymentType;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user", nullable = false)
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "executive")
    private Executive executive;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "subscription")
    private Subscription subscription;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "subscription_exception")
    private SubscriptionException subscriptionException;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "billing_log_type")
    private BillingLogType billingLogType;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "billing_master")
    private BillingMaster billingMaster;
	
	public BillingLog() {

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFlat() {
        return flat;
    }

    public void setFlat(String flat) {
        this.flat = flat;
    }

    public Double getPreviousBalance() {
        return previousBalance;
    }

    public void setPreviousBalance(Double previousBalance) {
        this.previousBalance = previousBalance;
    }

    public Integer getTransientBillingLogType() {
        return transientBillingLogType;
    }

    public void setTransientBillingLogType(Integer transientBillingLogType) {
        this.transientBillingLogType = transientBillingLogType;
    }

    public Integer getTransientPaymentType() {
        return transientPaymentType;
    }

    public void setTransientPaymentType(Integer transientPaymentType) {
        this.transientPaymentType = transientPaymentType;
    }

    public Double getJumboNeedsAmount() {
        return jumboNeedsAmount;
    }

    public void setJumboNeedsAmount(Double jumboNeedsAmount) {
        this.jumboNeedsAmount = jumboNeedsAmount;
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

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Executive getExecutive() {
        return executive;
    }

    public void setExecutive(Executive executive) {
        this.executive = executive;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    public SubscriptionException getSubscriptionException() {
        return subscriptionException;
    }

    public void setSubscriptionException(SubscriptionException subscriptionException) {
        this.subscriptionException = subscriptionException;
    }

    public BillingLogType getBillingLogType() {
        return billingLogType;
    }

    public void setBillingLogType(BillingLogType billingLogType) {
        this.billingLogType = billingLogType;
    }

    public BillingMaster getBillingMaster() {
    	return billingMaster;
    }

    public void setBillingMaster(BillingMaster billingMaster) {
    	this.billingMaster = billingMaster;
    }

}