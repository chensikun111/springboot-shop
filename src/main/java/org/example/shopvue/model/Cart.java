package org.example.shopvue.model;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("购物车实体类")
public class Cart {
    @ApiModelProperty("用户id")
    private String uid;
    @ApiModelProperty("商品id")
    private String shopid;
    @ApiModelProperty("商品名称")
    private String name;
    @ApiModelProperty("商品图片")
    private String image;
    @ApiModelProperty("商品价格")
    private int price;
    @ApiModelProperty("商品数量")
    private int num;
    @ApiModelProperty("购物车选中")
    private Boolean isClick;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public Boolean getClick() {
        return isClick;
    }

    public void setClick(Boolean click) {
        isClick = click;
    }
}
