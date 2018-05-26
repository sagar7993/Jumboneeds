package com.jumboneeds.beans;

public class BuildingDateBean {

    private String buildingId;

    private Long date;

    public BuildingDateBean(){

    }

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

}