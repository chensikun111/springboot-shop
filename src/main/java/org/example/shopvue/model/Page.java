package org.example.shopvue.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel("分页实体类")
@Data
public class Page {
    private int PageNum;
    private int PageSize;
    private String status;
}
