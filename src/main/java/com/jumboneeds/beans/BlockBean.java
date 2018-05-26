package com.jumboneeds.beans;

public class BlockBean {

    private String id;

    private String blockName;

    public BlockBean(){

    }

    public BlockBean(String id, String blockName){
        this.id = id;
        this.blockName = blockName;
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

}