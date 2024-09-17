package org.example.shopvue.model;

import io.swagger.annotations.ApiModel;

@ApiModel("商品分类实体类")
public class Category {

    private String category_bar;
    private String category;

    public String getCategory_bar() {
        return category_bar;
    }

    public void setCategory_bar(String category_bar) {
        this.category_bar = category_bar;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
