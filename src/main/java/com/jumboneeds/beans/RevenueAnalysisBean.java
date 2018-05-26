package com.jumboneeds.beans;

import java.math.BigInteger;

public class RevenueAnalysisBean {

    private BigInteger jumboOrderCount;
    private BigInteger jumboUserCount;
    private Double jumboRevenue;
    private BigInteger vendorOrderCount;
    private BigInteger vendorUserCount;
    private Double vendorRevenue;
    private BigInteger totalOrderCount;
    private BigInteger totalUserCount;
    private Double totalRevenue;

    public RevenueAnalysisBean(){

    }

    public RevenueAnalysisBean(BigInteger jumboOrderCount, BigInteger jumboUserCount, Double jumboRevenue, BigInteger vendorOrderCount, BigInteger vendorUserCount, Double vendorRevenue, BigInteger totalOrderCount, BigInteger totalUserCount, Double totalRevenue) {
        this.jumboOrderCount = jumboOrderCount;
        this.jumboUserCount = jumboUserCount;
        this.jumboRevenue = jumboRevenue;
        this.vendorOrderCount = vendorOrderCount;
        this.vendorUserCount = vendorUserCount;
        this.vendorRevenue = vendorRevenue;
        this.totalOrderCount = totalOrderCount;
        this.totalUserCount = totalUserCount;
        this.totalRevenue = totalRevenue;
    }

    public BigInteger getJumboOrderCount() {
        return jumboOrderCount;
    }

    public void setJumboOrderCount(BigInteger jumboOrderCount) {
        this.jumboOrderCount = jumboOrderCount;
    }

    public BigInteger getJumboUserCount() {
        return jumboUserCount;
    }

    public void setJumboUserCount(BigInteger jumboUserCount) {
        this.jumboUserCount = jumboUserCount;
    }

    public Double getJumboRevenue() {
        return jumboRevenue;
    }

    public void setJumboRevenue(Double jumboRevenue) {
        this.jumboRevenue = jumboRevenue;
    }

    public BigInteger getVendorOrderCount() {
        return vendorOrderCount;
    }

    public void setVendorOrderCount(BigInteger vendorOrderCount) {
        this.vendorOrderCount = vendorOrderCount;
    }

    public BigInteger getVendorUserCount() {
        return vendorUserCount;
    }

    public void setVendorUserCount(BigInteger vendorUserCount) {
        this.vendorUserCount = vendorUserCount;
    }

    public Double getVendorRevenue() {
        return vendorRevenue;
    }

    public void setVendorRevenue(Double vendorRevenue) {
        this.vendorRevenue = vendorRevenue;
    }

    public BigInteger getTotalOrderCount() {
        return totalOrderCount;
    }

    public void setTotalOrderCount(BigInteger totalOrderCount) {
        this.totalOrderCount = totalOrderCount;
    }

    public BigInteger getTotalUserCount() {
        return totalUserCount;
    }

    public void setTotalUserCount(BigInteger totalUserCount) {
        this.totalUserCount = totalUserCount;
    }

    public Double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(Double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

}