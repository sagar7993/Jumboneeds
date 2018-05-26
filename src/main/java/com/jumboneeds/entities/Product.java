package com.jumboneeds.entities;

import com.jumboneeds.beans.AddProductBean;
import com.jumboneeds.beans.ProductBean;
import com.jumboneeds.beans.ProductBuildingBean;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "product")

@SqlResultSetMappings({
    @SqlResultSetMapping(name="BuildingProductsList", classes = { @ConstructorResult(targetClass = ProductBuildingBean.class,
        columns = {@ColumnResult(name="id"), @ColumnResult(name="product_name"),
            @ColumnResult(name="product_unit_size"), @ColumnResult(name="product_unit_price")
        })
    }),
    @SqlResultSetMapping(name="ProductMasterBuildingCountList", classes = { @ConstructorResult(targetClass = AddProductBean.class,
        columns = {@ColumnResult(name="id"), @ColumnResult(name="product_name"), @ColumnResult(name="product_alias"),
            @ColumnResult(name="product_image_url"), @ColumnResult(name="product_unit_size"),
            @ColumnResult(name="status"), @ColumnResult(name="weight"), @ColumnResult(name="product_sub_category"),
            @ColumnResult(name="product_category"), @ColumnResult(name="building_count")
        })
    }),
    @SqlResultSetMapping(name="FlashSaleBuildingList", classes = { @ConstructorResult(targetClass = ProductBean.class,
        columns = {@ColumnResult(name="building_id"), @ColumnResult(name="building_name"),
            @ColumnResult(name="address"), @ColumnResult(name="count")
        })
    }),
    @SqlResultSetMapping(name="FlashSaleProductList", classes = { @ConstructorResult(targetClass = ProductBean.class,
        columns = {@ColumnResult(name="product_id"), @ColumnResult(name="product_name"), @ColumnResult(name="product_unit_size"),
            @ColumnResult(name="product_unit_price"), @ColumnResult(name="strike_price"), @ColumnResult(name="special_text"),
            @ColumnResult(name="product_sub_category_name"), @ColumnResult(name="product_category_name"),
            @ColumnResult(name="product_image_url"), @ColumnResult(name="count")
        })
    })
})

public class Product {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name="id")
    private String id;

    @Column(name = "product_unit_price", nullable = false)
    private Double productUnitPrice;

    @Column(name = "fulfilled_by_partner", nullable = false, columnDefinition = "tinyint(1) default 0")
    private Boolean fulfilledByPartner = false;

    @Column(name = "product_alias")
    private String productAlias;

    @Column(name = "special_text")
    private String specialText;

    @Column(name = "flash_short_text")
    private String flashShortText;

    @Column(name = "flash_status", nullable = false, columnDefinition = "int(11) default '0'")
    private Integer flashStatus = 0;

    @Column(name = "strike_price")
    private Double strikePrice;

    @Column(name = "status", nullable = false, columnDefinition = "int(11) default '1'")
    private Integer status = 1;

    @Transient
    private Integer transientProductType;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_master", nullable = false)
    private ProductMaster productMaster;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "building", nullable = false)
    private Building building;

    public Product() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getProductUnitPrice() {
        return productUnitPrice;
    }

    public void setProductUnitPrice(Double productUnitPrice) {
        this.productUnitPrice = productUnitPrice;
    }

    public Boolean getFulfilledByPartner() {
        return fulfilledByPartner;
    }

    public void setFulfilledByPartner(Boolean fulfilledByPartner) {
        this.fulfilledByPartner = fulfilledByPartner;
    }

    public String getProductAlias() {
        return productAlias;
    }

    public void setProductAlias(String productAlias) {
        this.productAlias = productAlias;
    }

    public String getSpecialText() {
        return specialText;
    }

    public void setSpecialText(String specialText) {
        this.specialText = specialText;
    }

    public String getFlashShortText() {
        return flashShortText;
    }

    public void setFlashShortText(String flashShortText) {
        this.flashShortText = flashShortText;
    }

    public Integer getFlashStatus() {
        return flashStatus;
    }

    public void setFlashStatus(Integer flashStatus) {
        this.flashStatus = flashStatus;
    }

    public Double getStrikePrice() {
        return strikePrice;
    }

    public void setStrikePrice(Double strikePrice) {
        this.strikePrice = strikePrice;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getTransientProductType() {
        return transientProductType;
    }

    public void setTransientProductType(Integer transientProductType) {
        this.transientProductType = transientProductType;
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

    public ProductMaster getProductMaster() {
        return productMaster;
    }

    public void setProductMaster(ProductMaster productMaster) {
        this.productMaster = productMaster;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

}