package org.example.shopvue.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.sql.Timestamp;

@ApiModel("评价实体类")
public class Review {
    @ApiModelProperty("用户id")
    private String uid;
    @ApiModelProperty("评价内容")
    private String text;
    @ApiModelProperty("商品id")
    private String shopid;
    @ApiModelProperty("姓名")
    private String name;
    @ApiModelProperty("订单号")
    private String orderid;
    @ApiModelProperty("商品图片")
    private String image;
    @ApiModelProperty("评价时间")
    private Timestamp review_data;
    @ApiModelProperty("下单时间")
    private Timestamp create_date;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getText() {
        return text;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    public Timestamp getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Timestamp create_date) {
        this.create_date = create_date;
    }

    public void setText(String text) {
        this.text = text;
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

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    public Timestamp getReview_data() {
        return review_data;
    }

    public void setReview_data(Timestamp review_data) {
        this.review_data = review_data;
    }
}
