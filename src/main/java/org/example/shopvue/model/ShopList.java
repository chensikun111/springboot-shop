package org.example.shopvue.model;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("商品实体类")
public class ShopList {
    @ApiModelProperty("商品id")
    private String shopId;
    @ApiModelProperty("商品名称")
    private String name;
    @ApiModelProperty("商品图")
    private String image;
    @ApiModelProperty("商品分类")
    private String category;
    @ApiModelProperty("商品价格")
    private int price;
    @ApiModelProperty("商品原价")
    private int original_price;

    private String category_bar;


    public String getCategory_bar() {
        return category_bar;
    }

    public void setCategory_bar(String category_bar) {
        this.category_bar = category_bar;
    }

    public String getImage() {

        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getOriginal_price() {
        return original_price;
    }

    public void setOriginal_price(int original_price) {
        this.original_price = original_price;
    }
}
