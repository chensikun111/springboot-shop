package org.example.shopvue.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.example.shopvue.model.Category;
import org.example.shopvue.model.Collect;
import org.example.shopvue.model.ShopList;
import java.util.List;

@Mapper
public interface ShopListMapper {
    //查询所有的shoplist
    @Select("select * from shoplist")
    List<ShopList> findAll();

    //根据shopid查询
    @Select("select * from shoplist where shopid=#{shopid}")
    ShopList findById(String shopid);

    //查询所有的分类
    @Select("select distinct category_bar,category from shoplist")
    List<Category> findCategory();

    //根据分类查找有关商品
    @Select("select * from shoplist where category=#{category}")
    List<ShopList> findCategoryShop(String category);

    //查询有折扣的数据
    @Select("select * from shoplist where original_price <> 0")
    List<ShopList> findDiscountShop();

    //搜索商品
    @Select("select * from shoplist where name like #{name} or category like #{category}")
    List<ShopList> findByNameOrCategory(@Param("name") String name,@Param("category") String category);

    //查询非折扣商品
    @Select("select * from shoplist where original_price = 0 or original_price is NULL")
    List<ShopList> findShopListNoPrice();


}
