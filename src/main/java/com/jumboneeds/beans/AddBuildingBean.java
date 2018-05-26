package com.jumboneeds.beans;

import com.jumboneeds.entities.Block;
import com.jumboneeds.entities.Building;

import java.math.BigInteger;
import java.util.List;

public class AddBuildingBean {

    private String buildingId;
    private String buildingName;
    private String parentPartnerId;
    private Building buildingCopy;
    private String address;
    private String userName;
    private String password;
    private String partnerName;
    private String mobileNumber;
    private Double partnerCharges;
    private Integer status;
    private Double minimumAllowedAmount;
    private Double balance;
    private Double lat;
    private Double lng;
    private BigInteger productMasterCount;
    private BigInteger blockCount;
    private BigInteger userCount;
    private List<Block> blockList;

    public AddBuildingBean() {

    }

    public AddBuildingBean(String buildingId, String buildingName, String address, String partnerName, String mobileNumber, Double partnerCharges, Double minimumAllowedAmount, Double balance, String userName, String password, Integer status, BigInteger productMasterCount, BigInteger blockCount, BigInteger userCount) {
        this.buildingId = buildingId;
        this.buildingName = buildingName;
        this.address = address;
        this.partnerName = partnerName;
        this.mobileNumber = mobileNumber;
        this.partnerCharges = partnerCharges;
        this.minimumAllowedAmount = minimumAllowedAmount;
        this.balance = balance;
        this.userName = userName;
        this.password = password;
        this.status = status;
        this.productMasterCount = productMasterCount;
        this.blockCount = blockCount;
        this.userCount = userCount;
    }

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
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

    public Building getBuildingCopy() {
        return buildingCopy;
    }

    public void setBuildingCopy(Building buildingCopy) {
        this.buildingCopy = buildingCopy;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public Double getPartnerCharges() {
        return partnerCharges;
    }

    public void setPartnerCharges(Double partnerCharges) {
        this.partnerCharges = partnerCharges;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Double getMinimumAllowedAmount() {
        return minimumAllowedAmount;
    }

    public void setMinimumAllowedAmount(Double minimumAllowedAmount) {
        this.minimumAllowedAmount = minimumAllowedAmount;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
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

    public BigInteger getProductMasterCount() {
    	return productMasterCount;
    }

    public void setProductMasterCount(BigInteger productMasterCount) {
    	this.productMasterCount = productMasterCount;
    }

    public BigInteger getBlockCount() {
        return blockCount;
    }

    public void setBlockCount(BigInteger blockCount) {
        this.blockCount = blockCount;
    }

    public BigInteger getUserCount() {
        return userCount;
    }

    public void setUserCount(BigInteger userCount) {
        this.userCount = userCount;
    }

    public List<Block> getBlockList() {
        return blockList;
    }

    public void setBlockList(List<Block> blockList) {
        this.blockList = blockList;
    }

}