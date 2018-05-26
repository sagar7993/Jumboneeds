package com.jumboneeds.beans;

import java.math.BigInteger;

public class BlockBuildingBean {

    private String id;

    private String blockName;

    private Boolean active;

    private String buildingName;

    private String buildingId;

    private BigInteger userCount;

    public BlockBuildingBean(){

    }

    public BlockBuildingBean(String id, String blockName, Boolean active, String buildingName, String buildingId, BigInteger userCount) {
        this.id = id;
        this.blockName = blockName;
        this.active = active;
        this.buildingName = buildingName;
        this.buildingId = buildingId;
        this.userCount = userCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBlockName() {
        return blockName;
    }

    public void setBlockName(String blockName) {
        this.blockName = blockName;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    public BigInteger getUserCount() {
        return userCount;
    }

    public void setUserCount(BigInteger userCount) {
        this.userCount = userCount;
    }

}