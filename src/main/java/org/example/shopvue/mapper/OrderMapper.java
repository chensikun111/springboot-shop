package org.example.shopvue.mapper;

import org.apache.ibatis.annotations.*;
import org.example.shopvue.model.Order;
import org.example.shopvue.model.ShopOrder;

import java.util.List;

@Mapper
public interface OrderMapper {
    //查询用户所有的订单
    @Select("select * from `order` where uid=#{uid} order by create_date desc")
    List<Order> selectAllOrder(String uid);

    //根据订单号查找相关商品
    @Select("select s.shopid,num,price,image,name from shoporder s,shoplist l where s.shopid = l.shopid and orderid =#{orderid} ")
    List<ShopOrder> selectOrder(String orderid);

    //查找有几条订单
    @Select("select count(*) from `order`")
    int selectCount();

    //生成订单到order表
    @Insert("insert into `order` (orderid,uid,allprice,create_date,`status`,address,order_phone,harvest_name) values (#{orderid},#{uid},#{allPrice},#{create_date},#{status},#{address},#{order_phone},#{harvest_name})")
    int insertOrder(Order order);

    //生成订单到shoporder表
    @Insert("insert into shoporder (shopid,orderid,num)values (#{shopOrder.shopid},#{orderid},#{shopOrder.num})")
    int insertShopOrder(@Param("shopOrder") ShopOrder shopOrder, @Param("orderid") String orderid);

    //根据订单号查找订单
    @Select("select * from `order` where orderid=#{orderid}")
    Order selectOrderById(@Param("orderid") String orderid);

    //查找待支付的订单
    @Select("select * from `order` where uid=#{uid} and `status`='待支付' order by paydate desc")
    List<Order> selectOrderByUidNp(String uid);

    @Select("select * from `order` where uid=#{uid} and `status`='待发货' order by paydate desc")
    List<Order> selectOrderByUidNoSent(String uid);

    @Update("update `order` set `status`=#{status},paydate=#{paydate},alipayTradeNo=#{alipayTradeNo} where orderid=#{orderid}")
    int updateOrderStatus(@Param("orderid") String orderid,@Param("status") String status,@Param("paydate") String paydate,@Param("alipayTradeNo") String alipayTradeNo);


    //订单状态
    @Update("update `order` set `status`=#{status} where orderid=#{orderid}")
    int updateOrderStatusd(@Param("orderid") String orderid,@Param("status") String status);


    @Select("select * from `order` where uid=#{uid} and `status`='待收货' order by create_date desc")
    List<Order> selectOrderByUidHH(String uid);
}
