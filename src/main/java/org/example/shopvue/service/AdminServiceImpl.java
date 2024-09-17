package org.example.shopvue.service;

import org.example.shopvue.mapper.AdminMapper;
import org.example.shopvue.mapper.OrderMapper;
import org.example.shopvue.model.*;
import org.example.shopvue.utils.CreateShopid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public List<ShopList> selectShopList(SelectShop selectShop){
        if(selectShop.getMinPrice() ==0 && selectShop.getMaxPrice() !=0){
            selectShop.setMinPrice(selectShop.getMaxPrice());
        }
        if(selectShop.getMinPrice() !=0 && selectShop.getMaxPrice() ==0){
            selectShop.setMaxPrice(selectShop.getMinPrice());
        }
        List<ShopList> list = adminMapper.selectShopList(selectShop);
        return list;
    }

    @Override
    public List<User> selectUser(String uid) {
        return adminMapper.selectUser("%"+uid+"%");
    }

    @Override
    public int insertShop(ShopList shopList) {
        shopList.setShopId(CreateShopid.createShopid(adminMapper.selectShopIds()));
        return adminMapper.insertShop(shopList);
    }

    @Override
    public int updateShopNewPrice(ShopList shopList){
        return adminMapper.updateShopNewPrice(shopList);
    }

    @Override
    public  List<ShopList> findShoplistByIDName(String set){
        String set2 = set.replace("=","");
        return adminMapper.findShoplistByIDName("%" +set2 + "%");
    }

    @Override
    public List<Order> selectOrderListALL() {
        List<Order> order = adminMapper.findAllOrder();
        for (Order order1 : order) {
            order1.setShopOrder(orderMapper.selectOrder(order1.getOrderid()));
        }
        return order;
    }

    @Override
    public List<Order> findOrderBySearchDate(SearchOrder searchOrder){
        List<Order> order = adminMapper.findOrderBySearchDate(searchOrder);
        for (Order order1 : order) {
            order1.setShopOrder(orderMapper.selectOrder(order1.getOrderid()));
        }
        return order;
    }

    @Override
    public int updateOrderStatus(String status,String orderid){
        return orderMapper.updateOrderStatusd(orderid,status);
    }
}
