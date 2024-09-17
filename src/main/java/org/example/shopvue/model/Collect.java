package org.example.shopvue.model;

import io.swagger.annotations.ApiModelProperty;

public class Collect {
    @ApiModelProperty("用户id")
    private String uid;
    @ApiModelProperty("商品id")
    private String shopid;
    @ApiModelProperty("商品图片")
    private String image;
    @ApiModelProperty("商品名称")
    private String name;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getShopid() {
        return shopid;
    }

    public void setShopid(String shopid) {
        this.shopid = shopid;
    }
}
