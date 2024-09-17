package org.example.shopvue.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class FootVo implements Serializable {
    private String shopId;
    private String name;
    private String image;
    private int price;
    private long time;
}
