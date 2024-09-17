package org.example.shopvue.model;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("订单中商品实体类")
public class ShopOrder {
    @ApiModelProperty("商品id")
    private String shopid;
    @ApiModelProperty("商品数量")
    private int num;
    @ApiModelProperty("商品价格")
    private int price;
    @ApiModelProperty("商品图片")
    private String image;
    @ApiModelProperty("商品名称")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShopid() {
        return shopid;
    }

    public void setShopid(String shopid) {
        this.shopid = shopid;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
