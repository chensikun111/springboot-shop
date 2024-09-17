package org.example.shopvue.model;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("用户登录实体类")
public class UserResponse {
    @ApiModelProperty("用户id")
    private String uid;
    @ApiModelProperty("姓名")
    private String name;
    @ApiModelProperty("token")
    private String token;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
