package com.jumboneeds.beans;

/**
 * Created by akash.mercer on 24-Nov-16.
 */
public class CouponUserBean {

    private String block;

    private String flat;

    public CouponUserBean() {

    }

    public CouponUserBean(String block, String flat) {
        this.block = block;
        this.flat = flat;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getFlat() {
        return flat;
    }

    public void setFlat(String flat) {
        this.flat = flat;
    }

    public String getMergedBlockAndFlat(){
        return (getBlock() != null ? getBlock() + getFlat() : getFlat());
    }
}
