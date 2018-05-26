package com.jumboneeds.entities;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "subscription")
public class Subscription {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name="id")
    private String id;

	@Column(name = "start_date", nullable = false)
	private Date startDate;

	@Column(name = "end_date", nullable = false)
	private Date endDate;

	@Column(name = "product_quantity", nullable = false)
	private Integer productQuantity;

    @Column(name = "product_unit_price")
    private double productUnitPrice;

    @Transient
    private Integer transientProductQuantity;

    @Transient
    private Integer transientSubscriptionType;

	@Column(name = "flash_sale_product", nullable = false, columnDefinition = "boolean default '0'")
	private Boolean flashSaleProduct;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "subscription_type", nullable = false)
    private SubscriptionType subscriptionType;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "product", nullable = false)
	private Product product;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user", nullable = false)
	private User user;

    public Subscription() {

	}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(Integer productQuantity) {
        this.productQuantity = productQuantity;
    }

    public double getProductUnitPrice() {
        return productUnitPrice;
    }

    public void setProductUnitPrice(double productUnitPrice) {
        this.productUnitPrice = productUnitPrice;
    }

    public Integer getTransientProductQuantity() {
        return transientProductQuantity;
    }

    public void setTransientProductQuantity(Integer transientProductQuantity) {
        this.transientProductQuantity = transientProductQuantity;
    }

    public Integer getTransientSubscriptionType() {
        return transientSubscriptionType;
    }

    public void setTransientSubscriptionType(Integer transientSubscriptionType) {
        this.transientSubscriptionType = transientSubscriptionType;
    }

    public Boolean getFlashSaleProduct() {
        return flashSaleProduct;
    }

    public void setFlashSaleProduct(Boolean flashSaleProduct) {
        this.flashSaleProduct = flashSaleProduct;
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

    public SubscriptionType getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(SubscriptionType subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}