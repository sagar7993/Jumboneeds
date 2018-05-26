package com.jumboneeds.entities;

import com.jumboneeds.beans.ParentPartnersBean;
import com.jumboneeds.beans.PartnerAmountsBean;
import com.jumboneeds.beans.PartnerDetailBean;
import com.jumboneeds.beans.PartnerQuantityBean;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "partner")

@SqlResultSetMappings({
    @SqlResultSetMapping(name="PartnerQuantityBean", classes = { @ConstructorResult(targetClass = PartnerQuantityBean.class,
        columns = {@ColumnResult(name="parent_partner_id"), @ColumnResult(name="partner_name"), @ColumnResult(name="date"),
            @ColumnResult(name="milk_quantity"), @ColumnResult(name="non_milk_quantity")
        })
    }),
    @SqlResultSetMapping(name="PartnerAmountsBean", classes = { @ConstructorResult(targetClass = PartnerAmountsBean.class,
        columns = {@ColumnResult(name="parent_partner_id"), @ColumnResult(name="partner_name"), @ColumnResult(name="date"),
            @ColumnResult(name="cashAmount"), @ColumnResult(name="couponRefundAmount"), @ColumnResult(name="nonDeliveryAmount"),
            @ColumnResult(name="onlineAmount"), @ColumnResult(name="othersAmount")
        })
    }),
    @SqlResultSetMapping(name="ParentPartners", classes = { @ConstructorResult(targetClass = ParentPartnersBean.class,
        columns = {@ColumnResult(name="id"), @ColumnResult(name="partner_name"), @ColumnResult(name="mobile_number")})
    }),
    @SqlResultSetMapping(name="PartnerDetailBean", classes = { @ConstructorResult(targetClass = PartnerDetailBean.class,
        columns = {@ColumnResult(name="partner_name"), @ColumnResult(name="mobile_number"), @ColumnResult(name="partner_charges"),
            @ColumnResult(name="user_name"), @ColumnResult(name="password"), @ColumnResult(name="id"), @ColumnResult(name="building_count")})
    })
})

public class Partner {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name="id")
    private String id;

    @Column(name = "building_name", nullable = false)
    private String buildingName;

    @Column(name = "parent_partner_id")
    private String parentPartnerId;

    @Column(name = "partner_charges", nullable = false, columnDefinition = "double default '1.5'")
    private Double partnerCharges = 1.5;

	@Column(name = "user_name", nullable = false, unique = true)
	private String userName;

	@Column(name = "password", nullable = false)
	private String password;

    @Column(name = "admin", columnDefinition = "tinyint(1) default 0")
    private Boolean admin = false;

    @Column(name = "balance", nullable = false, columnDefinition = "double default '0'")
    private Double balance = 0.0;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "partner_detail", nullable = false)
    private PartnerDetail partnerDetail;

    public Partner() {

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

    public String getParentPartnerId() {
        return parentPartnerId;
    }

    public void setParentPartnerId(String parentPartnerId) {
        this.parentPartnerId = parentPartnerId;
    }

    public Double getPartnerCharges() {
        return partnerCharges;
    }

    public void setPartnerCharges(Double partnerCharges) {
        this.partnerCharges = partnerCharges;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
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

    public PartnerDetail getPartnerDetail() {
        return partnerDetail;
    }

    public void setPartnerDetail(PartnerDetail partnerDetail) {
        this.partnerDetail = partnerDetail;
    }

}