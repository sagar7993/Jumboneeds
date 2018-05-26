package com.jumboneeds.beans;

import java.util.ArrayList;
import java.util.List;

public class BlocksBean extends StatusBean {

    private List<BlockBean> blockBeans = new ArrayList<>();

    public BlocksBean(){

    }

    public List<BlockBean> getBlockBeans() {
        return blockBeans;
    }

    public void setBlockBeans(List<BlockBean> blockBeans) {
        this.blockBeans = blockBeans;
    }

}