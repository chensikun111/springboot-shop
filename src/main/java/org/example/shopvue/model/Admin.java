package org.example.shopvue.model;

import io.swagger.annotations.ApiModelProperty;

public class Admin {
    @ApiModelProperty("管理员id")
    private String adminid;
    @ApiModelProperty("管理员姓名")
    private String name;
    @ApiModelProperty("密码")
    private String password;

    public String getAdminid() {
        return adminid;
    }

    public void setAdminid(String adminid) {
        this.adminid = adminid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
