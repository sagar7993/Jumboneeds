package com.jumboneeds.beans;

import java.util.List;

public class BlockBuildingListBean extends StatusBean {

    private String buildingName;

    private List<BlockBuildingBean> blockBuildingBeanList;

    public BlockBuildingListBean(){

    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public List<BlockBuildingBean> getBlockBuildingBeanList() {
        return blockBuildingBeanList;
    }

    public void setBlockBuildingBeanList(List<BlockBuildingBean> blockBuildingBeanList) {
        this.blockBuildingBeanList = blockBuildingBeanList;
    }

}