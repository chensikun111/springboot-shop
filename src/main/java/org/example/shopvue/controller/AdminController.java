package org.example.shopvue.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.example.shopvue.mapper.AdminMapper;
import org.example.shopvue.model.*;
import org.example.shopvue.service.AdminServiceImpl;
import org.example.shopvue.utils.AddToken;
import org.example.shopvue.mapper.ShopListMapper;
import org.example.shopvue.utils.Result;
import org.example.shopvue.utils.IsManger;
import org.example.shopvue.utils.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@Api(tags = "管理员接口")
public class AdminController {
    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private AdminServiceImpl adminServiceImpl;


    @Autowired
    private ShopListMapper shopListMapper;


    @ApiOperation("登录")
    @PostMapping("/admin/login")
    public ResponseEntity<String> login(@RequestBody Admin admin) {
        int index = adminMapper.countAdmin(admin);
        String token = AddToken.AddToken(admin);
        if (index == 1) {
            return new ResponseEntity<>(token, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @ApiOperation("筛选商品")
    @PostMapping("/manger/shoplist")
    public Result<List<ShopList>> getShopList(@RequestBody SelectShop selectShop, HttpServletRequest request) {
        String token = request.getHeader("token");
        Power power = AddToken.getInformation(token);
        if(IsManger.isManger(power)){
            return Result.success(adminServiceImpl.selectShopList(selectShop));
        }else{
            return Result.failure(ResultCodeEnum.UNAUTHORIZED);
        }
    }

    @ApiOperation("查找用户")
    @GetMapping("/admin/seleuid/{uid}")
    public List<User> seleuid(@PathVariable("uid") String uid,HttpServletRequest request) {
        String token = request.getHeader("token");
        Power power = AddToken.getInformation(token);
        if(IsManger.isManger(power)){
            return adminServiceImpl.selectUser(uid);
        }else{
            return null;
        }
    }

    @ApiOperation("插入商品")
    @PostMapping("/admin/insertshop")
    public Result<String> insertShop(@RequestBody ShopList shopList, HttpServletRequest request) {
        String token = request.getHeader("token");
        Power power = AddToken.getInformation(token);
        if(IsManger.isManger(power)){
            int index = adminServiceImpl.insertShop(shopList);
            if (index == 1) {
                return Result.success();
            } else {
                return Result.failure(ResultCodeEnum.FAIL);
            }
        }else{
            return Result.failure(ResultCodeEnum.UNAUTHORIZED);
        }
    }

    @ApiOperation("查找商品所有的分类")
    @GetMapping("/admin/catrgory_bar")
    public Result<List<String>> catrgoryBar(HttpServletRequest request) {
        String token = request.getHeader("token");
        Power power = AddToken.getInformation(token);
        if (IsManger.isManger(power)) {
            return Result.success(adminMapper.findCategory());
        } else {
            return Result.failure(ResultCodeEnum.UNAUTHORIZED);
        }
    }

    @ApiOperation("删除商品")
    @DeleteMapping("/admin/deleteshop/{shopid}")
    public Result<String> deleteShop(@PathVariable("shopid") String shopid, HttpServletRequest request) {
        String token = request.getHeader("token");
        Power power = AddToken.getInformation(token);
        if (IsManger.isManger(power)) {
            int index = adminMapper.deleteShop(shopid);
            if (index == 1) {
                return Result.success();
            } else {
                return Result.failure(ResultCodeEnum.FAIL);
            }
        }else{
            return Result.failure(ResultCodeEnum.UNAUTHORIZED);
        }
    }

    @ApiOperation("修改商品")
    @PostMapping("/admin/updateshop")
    public Result<String> updateShop(@RequestBody ShopList shopList,HttpServletRequest request) {
        String token = request.getHeader("token");
        Power power = AddToken.getInformation(token);
        if (IsManger.isManger(power)) {
            int index = adminMapper.updateShopInformation(shopList);
            if (index == 1) {
                return Result.success();
            } else {
                return Result.failure(ResultCodeEnum.FAIL);
            }
        }else{
            return Result.failure(ResultCodeEnum.UNAUTHORIZED);
        }
    }

    @ApiOperation("移除折扣")
    @PostMapping("/admin/deletecount")
    public Result<String> deleteCount(@RequestBody ShopList shopList, HttpServletRequest request) {
        String token = request.getHeader("token");
        Power power = AddToken.getInformation(token);
        if (IsManger.isManger(power)) {
            int index = adminMapper.updateShopPrice(shopList);
            if (index == 1) {
                return Result.success();
            } else {
                return Result.failure(ResultCodeEnum.FAIL);
            }
        }else{
            return Result.failure(ResultCodeEnum.UNAUTHORIZED);
        }
    }

    @ApiOperation("修改价格")
    @PostMapping("/admin/updatePrice")
    public Result<String> updatePrice(@RequestBody ShopList shopList, HttpServletRequest request) {
        String token = request.getHeader("token");
        Power power = AddToken.getInformation(token);
        if (IsManger.isManger(power)) {
            int index = adminServiceImpl.updateShopNewPrice(shopList);
            if (index == 1) {
                return Result.success();
            } else {
                return Result.failure(ResultCodeEnum.FAIL);
            }
        }else{
            return Result.failure(ResultCodeEnum.UNAUTHORIZED);
        }
    }

    @ApiOperation("查找商品")
    @PostMapping("/admin/searchshop")
    public Result<List<ShopList>> searchShop(@RequestBody ShopList shopList, HttpServletRequest request) {
        String token = request.getHeader("token");
        Power power = AddToken.getInformation(token);
        if (IsManger.isManger(power)) {
            List<ShopList> shoplist = adminServiceImpl.findShoplistByIDName(shopList.getName());
            return Result.success(shoplist);
        }else{
            return Result.failure(ResultCodeEnum.UNAUTHORIZED);
        }
    }

    @ApiOperation("查找所有的订单")
    @GetMapping("/admin/findAllOrder")
    public List<Order> findAllOrder(HttpServletRequest request) {
        String token = request.getHeader("token");
        Power power = AddToken.getInformation(token);
        if (IsManger.isManger(power)) {
            return adminServiceImpl.selectOrderListALL();
        }
        return null;
    }

    @ApiOperation("筛选订单")
    @PostMapping("/admin/searchorder")
    public Result<List<Order>> searchOrder(@RequestBody SearchOrder searchOrder, HttpServletRequest request) {
        String token = request.getHeader("token");
        Power power = AddToken.getInformation(token);
        if (IsManger.isManger(power)) {
            List<Order> order = adminServiceImpl.findOrderBySearchDate(searchOrder);
            return Result.success(order);
        }else{
            return Result.failure(ResultCodeEnum.UNAUTHORIZED);
        }
    }


}
