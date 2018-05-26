package com.jumboneeds.beans;

import java.util.ArrayList;
import java.util.List;

public class BuildingsBean extends StatusBean{

    private String buildingId;

    private String flat;

    private String blockId;

    private List<BuildingBean> buildingBeans = new ArrayList<>();

    public BuildingsBean(){

    }

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    public String getFlat() {
        return flat;
    }

    public void setFlat(String flat) {
        this.flat = flat;
    }

    public String getBlockId() {
        return blockId;
    }

    public void setBlockId(String blockId) {
        this.blockId = blockId;
    }

    public List<BuildingBean> getBuildingBeans() {
        return buildingBeans;
    }

    public void setBuildingBeans(List<BuildingBean> buildingBeans) {
        this.buildingBeans = buildingBeans;
    }

}