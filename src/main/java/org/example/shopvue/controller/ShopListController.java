package org.example.shopvue.controller;

import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.example.shopvue.bo.ShopBo;
import org.example.shopvue.context.UserContext;
import org.example.shopvue.mapper.ShopListMapper;
import org.example.shopvue.model.Category;
import org.example.shopvue.model.ShopList;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@Api(tags="处理商品接口")
public class ShopListController {

    @Autowired
    private ShopListMapper shopListMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    //查询所有的shoplist
    @ApiOperation("查询所有的shoplist")
    @GetMapping("/shoplist")
    public List<ShopList> findAll() {
        return shopListMapper.findAll();
    }

    //根据shopid查找shoplist
    @ApiOperation("根据shopid查找shoplist")
    @GetMapping(value = "/shop/{shopid}")
    public ShopList findById(@PathVariable("shopid") String shopid) {
        ShopList shop = shopListMapper.findById(shopid);
        try {
            String uid = UserContext.getUserId();
            if(uid != null){
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
                String formattedTime = now.format(formatter);
                long timeAsLong = Long.parseLong(formattedTime);
                ShopBo shopBo = BeanUtil.copyProperties(shop, ShopBo.class);
                shopBo.setTime(timeAsLong);
                shopBo.setUid(uid);
                rabbitTemplate.convertAndSend("foot.topic","foot", shopBo);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return shop;
    }

    //查询所有的分类
    @ApiOperation("查询所有的分类")
    @GetMapping("/category")
    public List<Category> findCategory() {

        return shopListMapper.findCategory();
    }

    //根据分类查找有关商品
    @ApiOperation("根据分类查找有关商品")
    @GetMapping(value = "/category/{category}")
    public List<ShopList> findCategoryShop(@PathVariable("category") String category) {
        return shopListMapper.findCategoryShop(category);
    }

    //查询有折扣的数据
    @ApiOperation("查询有折扣的数据")
    @GetMapping(value = "/discount")
    public List<ShopList> findDiscountShop() {
        return shopListMapper.findDiscountShop();
    }

    @ApiOperation("搜索商品")
    @GetMapping(value = "/search/{shop}")
    public List<ShopList> findSearch(@PathVariable("shop") String shop) {
        String name = "%"+shop+"%";
        String category = "%"+shop+"%";
        return shopListMapper.findByNameOrCategory(name,category);
    }

    @ApiOperation("非折扣商品")
    @GetMapping("/shoplist/nodeisount")
    public List<ShopList> findNodeisount() {
        return shopListMapper.findShopListNoPrice();
    }
}
