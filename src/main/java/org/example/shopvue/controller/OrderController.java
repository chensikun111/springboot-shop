package org.example.shopvue.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.example.shopvue.mapper.OrderMapper;
import org.example.shopvue.model.Order;
import org.example.shopvue.model.Power;
import org.example.shopvue.model.ShopOrder;
import org.example.shopvue.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@Api(tags = "订单接口")
public class OrderController {
    @Autowired
    private OrderMapper orderMapperde;

    //根据uid查找订单
    @ApiOperation("根据用户订单")
    @GetMapping(value = "/order/userAll")
    public Result<List<Order>> getOrderByUid(HttpServletRequest request) {
        String token = request.getHeader("token");
        Power power = AddToken.getInformation(token);
        if (power != null) {
            List<Order> or = orderMapperde.selectAllOrder(power.getId());
            for (int i = 0; i < or.size(); i++) {
                or.get(i).setShopOrder(orderMapperde.selectOrder(or.get(i).getOrderid()));
            }
            return Result.success(or);
        } else {
            return Result.failure(ResultCodeEnum.UNAUTHORIZED);
        }
    }

    //生成订单
    @ApiOperation("生成订单")
    @PostMapping(value = "/order/addorder")
    public Result<String> addOrder(@RequestBody Order order, HttpServletRequest request) {
        String token = request.getHeader("token");
        Power power = AddToken.getInformation(token);
        if (power != null) {
            order.setUid(power.getId());
            String orderId = AddOrderId.AddOrderId(orderMapperde.selectCount());
            order.setOrderid(orderId);
            order.setCreate_date(GetNowTime.GetNowTime());
            order.setStatus("待支付");
            int index1 = orderMapperde.insertOrder(order);
            if (index1 > 0) {
                for (ShopOrder item : order.getShopOrder()) {
                    orderMapperde.insertShopOrder(item, orderId);
                }
                return Result.success(orderId);
            } else {
                return Result.failure(ResultCodeEnum.FAIL);
            }
        }else{
            return Result.failure(ResultCodeEnum.UNAUTHORIZED);
        }

    }



    @ApiOperation("根据订单号查找订单")
    @GetMapping("/order/orderid/{orderid}")
    public Order getOrderById(@PathVariable("orderid") String orderid) {
        Order order = orderMapperde.selectOrderById(orderid);
        List<ShopOrder> shopOrder = orderMapperde.selectOrder(orderid);
        order.setShopOrder(shopOrder);
        return order;
    }

    @ApiOperation("查找未支付的订单")
    @GetMapping("/order/nopay/user")
    public Result<List<Order>> selectOrderByUidNp(HttpServletRequest request) {
        String token = request.getHeader("token");
        Power power = AddToken.getInformation(token);
        if (power != null) {
            List<Order> or = orderMapperde.selectOrderByUidNp(power.getId());
            for (int i = 0; i < or.size(); i++) {
                or.get(i).setShopOrder(orderMapperde.selectOrder(or.get(i).getOrderid()));
            }
            return Result.success(or);
        } else {
            return Result.failure(ResultCodeEnum.UNAUTHORIZED);
        }
    }

    @ApiOperation("查找待收获的商品")
    @GetMapping("/order/harvest/user")
    public Result<List<Order>> selectHarvestOrderByUid(HttpServletRequest request) {
        String token = request.getHeader("token");
        Power power = AddToken.getInformation(token);
        if (power != null) {
            List<Order> or = orderMapperde.selectOrderByUidHH(power.getId());
            for (int i = 0; i < or.size(); i++) {
                or.get(i).setShopOrder(orderMapperde.selectOrder(or.get(i).getOrderid()));
            }
            return Result.success(or);
        } else {
            return Result.failure(ResultCodeEnum.UNAUTHORIZED);
        }
    }

    @ApiOperation("查找待发货的商品")
    @GetMapping("/order/nosent/user")
    public Result<List<Order>> selectOrderByUidNoSent(HttpServletRequest request) {
        String token = request.getHeader("token");
        Power power = AddToken.getInformation(token);
        if (power != null) {
            List<Order> or = orderMapperde.selectOrderByUidNoSent(power.getId());
            for (int i = 0; i < or.size(); i++) {
                or.get(i).setShopOrder(orderMapperde.selectOrder(or.get(i).getOrderid()));
            }
            return Result.success(or);
        } else {
            return Result.failure(ResultCodeEnum.UNAUTHORIZED);
        }

    }


    @ApiOperation("确认收获")
    @PostMapping("/order/isorderharvest")
    public Result<String> isOrderHarvest(@RequestBody Order order, HttpServletRequest request) {
        String token = request.getHeader("token");
        Power power = AddToken.getInformation(token);
        if (power != null) {
            order.setStatus("已完成");
            order.setUid(power.getId());
            int index = orderMapperde.updateOrderStatusd(order.getOrderid(), order.getStatus());
            if (index > 0) {
//                return new ResponseEntity<>(order.getOrderid(),HttpStatus.OK);
                return Result.success(order.getOrderid());
            } else {
                return Result.failure(ResultCodeEnum.FAIL);
            }
        } else {
            return Result.failure(ResultCodeEnum.UNAUTHORIZED);
        }
    }

    @ApiOperation("总查找订单数")
    @GetMapping("/order/allordernum")
    public int getAllOrderNum() {
        return orderMapperde.selectCount();
    }

    @ApiOperation("发货")
    @PostMapping("/order/sent")
    public Result<String> sendOrder(@RequestBody Order order,HttpServletRequest request) {
        String token = request.getHeader("token");
        Power power = AddToken.getInformation(token);
        if (IsManger.isManger(power)) {
            order.setStatus("待收货");
            int index = orderMapperde.updateOrderStatusd(order.getOrderid(), order.getStatus());
            if (index > 0) {
                return Result.success();
            } else {
                return Result.failure(ResultCodeEnum.FAIL);
            }
        }else {
            return Result.failure(ResultCodeEnum.UNAUTHORIZED);
        }
    }

    @ApiOperation("取消订单")
    @PostMapping("/order/cancelorder")
    public Result<String> cancelOrder(@RequestBody Order order, HttpServletRequest request) {
        String token = request.getHeader("token");
        Power power = AddToken.getInformation(token);
                if (power != null) {
                    int index = orderMapperde.updateOrderStatusd(order.getOrderid(), order.getStatus());
                    if (index > 0) {
                return Result.success();
            }else{
                return Result.failure(ResultCodeEnum.FAIL);
            }
        }else{
            return Result.failure(ResultCodeEnum.UNAUTHORIZED);
        }
    }
}
