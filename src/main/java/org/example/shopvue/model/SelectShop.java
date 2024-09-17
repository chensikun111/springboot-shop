package org.example.shopvue.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("商品筛选")
public class SelectShop {
    private String shopid;
    private String name;
    private int minPrice;
    private int maxPrice;
    private int price;
    private String category;
}
