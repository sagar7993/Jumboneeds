package com.jumboneeds.entities;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "subscription_exception")
public class SubscriptionException {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name="id")
    private String id;

	@Column(name = "date", nullable = false)
	private Date date;

	@Column(name = "product_quantity", nullable = false)
	private Integer productQuantity;

    @Transient
    private Integer transientSubscriptionExceptionType;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "subscription", nullable = false)
	private Subscription subscription;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "subscription_exception_type", nullable = false)
	private SubscriptionExceptionType subscriptionExceptionType;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user", nullable = false)
    private User user;

	public SubscriptionException() {

	}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(Integer productQuantity) {
        this.productQuantity = productQuantity;
    }

    public Integer getTransientSubscriptionExceptionType() {
        return transientSubscriptionExceptionType;
    }

    public void setTransientSubscriptionExceptionType(Integer transientSubscriptionExceptionType) {
        this.transientSubscriptionExceptionType = transientSubscriptionExceptionType;
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

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    public SubscriptionExceptionType getSubscriptionExceptionType() {
        return subscriptionExceptionType;
    }

    public void setSubscriptionExceptionType(SubscriptionExceptionType subscriptionExceptionType) {
        this.subscriptionExceptionType = subscriptionExceptionType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}