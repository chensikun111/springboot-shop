package org.example.shopvue.mapper;

import org.apache.ibatis.annotations.*;
import org.example.shopvue.model.Review;

import java.util.List;

@Mapper
public interface ReviewMapper {
    //查找该商品的评论
    @Select("select text,`name`,review_data from review r,user u where r.uid=u.uid and shopid=#{shopid} order by review_data desc")
    List<Review> selectTextByShopId(String shopid);

    //评价商品
    @Insert("insert into review (uid,shopid,text,review_data,orderid)value (#{uid},#{shopid},#{text},#{review_data},#{orderid})")
    int insert(Review review);

    //评价状态
    @Update("update shoporder set review_status=#{review_status} where orderid=#{orderid} and shopid=#{shopid}")
    int updateOrderStatus(@Param("review_status") int review_status, @Param("orderid") String orderid, @Param("shopid") String shopid);

    //查找用户未评论的商品
    @Select("select * from shoporder s,`order` o,shoplist l where s.orderid=o.orderid and s.shopid=l.shopid and review_status =0 and uid=#{uid} and status ='已完成' ")
    List<Review> selectOrder(String uid);

    //查找用户已评价的商品
    @Select("select * from review r,shoplist s where r.shopid = s.shopid and uid=#{uid} order by review_data desc")
    List<Review> selectReview(String uid);
}
