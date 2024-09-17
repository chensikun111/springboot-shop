package org.example.shopvue.mapper;

import org.apache.ibatis.annotations.*;
import org.example.shopvue.model.Cart;

import java.util.List;

@Mapper
public interface CartMapper {
    //查询购物车
    @Select("select uid,t.shopid,name,image,price,num,isclick from shoplist s,cart t where s.shopid=t.shopid and uid=#{uid}")
    List<Cart> selectCartByUid(String uid);

    //购物车右边的x删除购物车
    @Delete("delete from cart where shopid=#{shopid} and uid=#{uid}")
    int deleteCartByShopid(@Param("shopid") String shopid, @Param("uid") String uid);

    //加入购物车且购物车没有数据
    @Insert("insert into cart (uid,shopid,num) value (#{uid},#{shopid},#{num})")
    int insertCart(Cart cart);

    //加入购物车且购物车有数据或购物车数量更新
    @Update("update cart set num=#{num} where shopid=#{shopid} and uid=#{uid}")
    int updateCartNum(Cart cart);

    //查找要加入的购物车有没有数据
    @Select("select * from cart where shopid=#{shopid} and uid=#{uid}")
    Cart isFindCart(@Param("shopid") String shopid,@Param("uid") String uid);

    //购物车选择
    @Update("update cart set isclick=#{isclick} where shopid=#{shopid} and uid=#{uid}")
    int updateIsclick(@Param("shopid") String shopid, @Param("uid") String uid,@Param("isclick") int isClick);

    @Update("update cart set isclick = #{isclick} where uid=#{uid};")
    int updateAllIsclick(@Param("uid") String uid, @Param("isclick") int isClick);

    //购物车提交，删除购物车里选中内容
    @Delete("delete from cart where uid=#{uid} and isclick = 1")
    int deleteCartByUidOrder1(@Param("uid") String uid);
}
