package com.jumboneeds.entities;

import com.jumboneeds.beans.AddBuildingBean;
import com.jumboneeds.beans.ProductBean;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "building")

@SqlResultSetMapping(name="BuildingProductMasterUserCountList", classes = { @ConstructorResult(targetClass = AddBuildingBean.class,
    columns = {@ColumnResult(name="id"), @ColumnResult(name="building_name"), @ColumnResult(name="address"),
        @ColumnResult(name="partner_name"), @ColumnResult(name="mobile_number"), @ColumnResult(name="partner_charges"),
        @ColumnResult(name="minimum_allowed_amount"), @ColumnResult(name="balance"), @ColumnResult(name="user_name"),
        @ColumnResult(name="password"), @ColumnResult(name="status"), @ColumnResult(name="product_count"),
        @ColumnResult(name="block_count"), @ColumnResult(name="user_count")
    })
})

public class Building {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name="id")
    private String id;

	@Column(name = "building_name", nullable = false)
	private String buildingName;

	@Column(name = "address", nullable = false)
	private String address;

    @Column(name = "lat")
    private Double lat;

    @Column(name = "lng")
    private Double lng;

    @Column(name = "minimum_allowed_amount", nullable = false, columnDefinition = "double default '-50'")
    private Double minimumAllowedAmount = -50.0;

	@Column(name = "status", nullable = false, columnDefinition = "int(11) default 0")
	private Integer status = 0;

    @Column(name = "partner_name", nullable = false)
    private String partnerName;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "partner", nullable = false)
	private Partner partner;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "parent_partner", nullable = false)
	private Partner parentPartner;

    @Transient
    private List<ProductBean> productBeanList;

    public Building() {

    }

	public Building(String buildingName, String address) {
        this.buildingName = buildingName;
        this.address = address;
	}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getMinimumAllowedAmount() {
        return minimumAllowedAmount;
    }

    public void setMinimumAllowedAmount(Double minimumAllowedAmount) {
        this.minimumAllowedAmount = minimumAllowedAmount;
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

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public Partner getPartner() {
        return partner;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }

    public Partner getParentPartner() {
        return parentPartner;
    }

    public void setParentPartner(Partner parentPartner) {
        this.parentPartner = parentPartner;
    }

    public List<ProductBean> getProductBeanList() {
        return productBeanList;
    }

    public void setProductBeanList(List<ProductBean> productBeanList) {
        this.productBeanList = productBeanList;
    }

}