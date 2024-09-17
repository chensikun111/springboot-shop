package org.example.shopvue.mapper;

import com.alipay.api.domain.Shop;
import org.apache.ibatis.annotations.*;
import org.example.shopvue.model.*;

import java.util.List;

@Mapper
public interface AdminMapper {
    //验证账号密码
    @Select("select count(*) from admin where adminid=#{adminid} and password=#{password}")
    int countAdmin(Admin admin);

    List<ShopList> selectShopList(SelectShop selectShop);

    @Select("select * from `user` where uid like #{uid}")
    List<User>  selectUser(String uid);

    @Insert("insert into shoplist (shopid,name,image,category,price,category_bar) value (#{shopId},#{name},#{image},#{category},#{price},#{category_bar})")
    int insertShop(ShopList shopList);

    @Select("select shopid from shoplist")
    List<String> selectShopIds();

    @Select("select distinct category_bar from shoplist")
    List<String> findCategory();


    @Delete("delete from shoplist where shopid =#{shopid}")
    int deleteShop(String shopid);

    @Update("update shoplist set name=#{name},image=#{image},category=#{category},category_bar=#{category_bar} where shopid=#{shopId}")
    int updateShopInformation(ShopList shopList);

    @Update("update shoplist set price=#{price},original_price= 0 where shopid=#{shopId}")
    int updateShopPrice(ShopList shopList);

    int updateShopNewPrice(ShopList shopList);

    @Select("select * from shoplist where shopid like #{set} or `name` like #{set} or category like #{set} or category_bar like #{set}")
    List<ShopList> findShoplistByIDName(String set);


    List<Order> findAllOrder();


    List<Order> findOrderBySearchDate(SearchOrder searchOrder);

}
