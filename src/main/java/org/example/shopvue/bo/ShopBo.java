package org.example.shopvue.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ShopBo implements Serializable {
    private String uid;
    private String shopId;
    private String name;
    private String image;
    private int price;
    private long time;
}
