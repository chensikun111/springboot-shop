package org.example.shopvue.service;


import org.example.shopvue.model.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AdminService {
    List<ShopList> selectShopList(SelectShop selectShop);

    List<User>  selectUser(String uid);

    int insertShop(ShopList shopList);

    int updateShopNewPrice(ShopList shopList);

    List<ShopList> findShoplistByIDName(String set);

    List<Order> selectOrderListALL();

    List<Order> findOrderBySearchDate(SearchOrder searchOrder);

    /*
    *
    * 发货
    *
    * */
    int updateOrderStatus(String status,String orderid);

}
