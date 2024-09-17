package org.example.shopvue.model;

import io.swagger.annotations.ApiModelProperty;

public class Address {
    @ApiModelProperty("用户id")
    public String uid;
    @ApiModelProperty("详细地址")
    private String address;
    @ApiModelProperty("地区")
    private String region;
    @ApiModelProperty("手机号")
    private String delivery_phone;
    @ApiModelProperty("姓名")
    private String delivery_name;
    @ApiModelProperty("地址编号")
    private int address_id;

    public int getAddress_id() {
        return address_id;
    }

    public void setAddress_id(int address_id) {
        this.address_id = address_id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getDelivery_phone() {
        return delivery_phone;
    }

    public void setDelivery_phone(String delivery_phone) {
        this.delivery_phone = delivery_phone;
    }

    public String getDelivery_name() {
        return delivery_name;
    }

    public void setDelivery_name(String delivery_name) {
        this.delivery_name = delivery_name;
    }
}
