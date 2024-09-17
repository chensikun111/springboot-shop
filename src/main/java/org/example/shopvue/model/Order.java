package org.example.shopvue.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.sql.Timestamp;
import java.util.List;

@ApiModel("订单实体类")
public class Order {
    @ApiModelProperty("商品id")
    private String orderid;
    @ApiModelProperty("用户id")
    private String uid;
    @ApiModelProperty("商品实体")
    private List<ShopOrder> shopOrder;
    @ApiModelProperty("订单时间")
    private Timestamp create_date;
    @ApiModelProperty("实付款")
    private int allPrice;
    @ApiModelProperty("订单状态")
    private String status;
    @ApiModelProperty("地址")
    private String address;
    @ApiModelProperty("手机号")
    private String order_phone;
    @ApiModelProperty("收获人")
    private String harvest_name;
    @ApiModelProperty("支付时间")
    private Timestamp paydate;
    @ApiModelProperty("支付宝流水号")
    private String alipayTradeNo;


    public String getAlipayTradeNo() {
        return alipayTradeNo;
    }

    public void setAlipayTradeNo(String alipayTradeNo) {
        this.alipayTradeNo = alipayTradeNo;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    public Timestamp getPaydate() {
        return paydate;
    }

    public void setPaydate(Timestamp paydate) {
        this.paydate = paydate;
    }

    public String getHarvest_name() {
        return harvest_name;
    }

    public void setHarvest_name(String harvest_name) {
        this.harvest_name = harvest_name;
    }

    public String getOrder_phone() {
        return order_phone;
    }

    public void setOrder_phone(String order_phone) {
        this.order_phone = order_phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public List<ShopOrder> getShopOrder() {
        return shopOrder;
    }

    public void setShopOrder(List<ShopOrder> shopOrder) {
        this.shopOrder = shopOrder;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    public Timestamp getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Timestamp create_date) {
        this.create_date = create_date;
    }

    public int getAllPrice() {
        return allPrice;
    }

    public void setAllPrice(int allPrice) {
        this.allPrice = allPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
