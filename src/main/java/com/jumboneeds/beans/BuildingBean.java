package com.jumboneeds.beans;

public class BuildingBean {

    private String id;

    private String buildingName;

    private String address;

    public BuildingBean(){

    }

    public BuildingBean(String id, String buildingName, String address){
        this.id = id;
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

}