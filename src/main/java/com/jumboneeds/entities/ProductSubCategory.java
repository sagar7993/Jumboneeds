package com.jumboneeds.entities;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "product_sub_category")
public class ProductSubCategory {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name="id")
    private String id;

    @Column(name = "product_sub_category_name", nullable = false)
    private String productSubCategoryName;

    @Column(name = "type", nullable = false)
    private Integer type;

    @Column(name = "folder_name", nullable = false)
    private String folderName;

    @Column(name = "product_sub_category_image_url", nullable = false)
    private String productSubCategoryImageUrl;

    @Column(name = "product_sub_category_image_url_small", nullable = false)
    private String productSubCategoryImageUrlSmall;

    @Column(name = "weight", nullable = false, columnDefinition = "int(11) default '99'")
    private Integer weight = 99;

    @Column(name = "status", nullable = false, columnDefinition = "int(11) default '1'")
    private Integer status = 1;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_category", nullable = false)
    private ProductCategory productCategory;

    public ProductSubCategory(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductSubCategoryName() {
        return productSubCategoryName;
    }

    public void setProductSubCategoryName(String productSubCategoryName) {
        this.productSubCategoryName = productSubCategoryName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public String getProductSubCategoryImageUrl() {
        return productSubCategoryImageUrl;
    }

    public void setProductSubCategoryImageUrl(String productSubCategoryImageUrl) {
        this.productSubCategoryImageUrl = productSubCategoryImageUrl;
    }

    public String getProductSubCategoryImageUrlSmall() {
        return productSubCategoryImageUrlSmall;
    }

    public void setProductSubCategoryImageUrlSmall(String productSubCategoryImageUrlSmall) {
        this.productSubCategoryImageUrlSmall = productSubCategoryImageUrlSmall;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

}