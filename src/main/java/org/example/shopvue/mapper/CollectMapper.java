package org.example.shopvue.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.shopvue.model.Collect;

import java.util.List;

@Mapper
public interface CollectMapper {
    //查找收藏
    @Select("select count(*) from collect where uid=#{uid} and shopid=#{shopid}")
    int countByUidAndShopid(Collect collect);

    //添加收藏
    @Insert("insert into collect (uid,shopid) values (#{uid},#{shopid})")
    int insert(Collect collect);

    //取消收藏
    @Delete("delete from collect where uid=#{uid} and shopid=#{shopid}")
    int deleteByUidAndShopid(Collect collect);

    //查找用户所有收藏
    @Select("select * from collect c,shoplist s where c.shopid=s.shopid and uid=#{uid}")
    List<Collect> selectByUidAndShopid(String uid);

}
