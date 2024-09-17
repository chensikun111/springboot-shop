package org.example.shopvue.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.example.shopvue.mapper.CollectMapper;
import org.example.shopvue.model.Collect;
import org.example.shopvue.model.Power;
import org.example.shopvue.utils.AddToken;
import org.example.shopvue.utils.Result;
import org.example.shopvue.utils.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@Api(tags="收藏")
public class CollectController {
    @Autowired
    private CollectMapper collectMapper;

    @ApiOperation("查找商品详情页是否收藏")
    @PostMapping("/collect/iscollect")
    public Result<String> isCollect(@RequestBody Collect collect,HttpServletRequest request) {
        String token = request.getHeader("token");
        Power power = AddToken.getInformation(token);
        if (power != null) {
            collect.setUid(power.getId());

            int index = collectMapper.countByUidAndShopid(collect);

            if (index == 1) {
                return Result.success();
            }else {
                return Result.failure(ResultCodeEnum.USER_IS_EXITES);
            }
        }else{
            return Result.failure(ResultCodeEnum.UNAUTHORIZED);
        }
    }

    @ApiOperation("添加收藏")
    @PostMapping("/collect/addcollect")
    public Result<String> addCollect(@RequestBody Collect collect, HttpServletRequest request) {
        String token = request.getHeader("token");
        Power power = AddToken.getInformation(token);
        if (power != null) {
            collect.setUid(power.getId());
            int index = collectMapper.insert(collect);
            if (index > 0 ) {
                return Result.success();
            }else {
                return Result.failure(ResultCodeEnum.FAIL);
            }
        }else{
            return Result.failure(ResultCodeEnum.UNAUTHORIZED);
        }
    }

    @ApiOperation("取消收藏")
    @PostMapping("/collect/deletecollect")
    public Result<String> deleteCollect(@RequestBody Collect collect,HttpServletRequest request) {
        String token = request.getHeader("token");
        Power power = AddToken.getInformation(token);
        if (power != null) {
            collect.setUid(power.getId());
            int index = collectMapper.deleteByUidAndShopid(collect);
            if (index > 0 ) {
                return Result.success();
            }else {
                return Result.failure(ResultCodeEnum.FAIL);
            }
        }else{
            return Result.failure(ResultCodeEnum.UNAUTHORIZED);
        }
    }

    @ApiOperation("查找个人中心收藏")
    @GetMapping("/collect/user")
    public Result<List<Collect>> getCollect(HttpServletRequest request) {
        String token = request.getHeader("token");
        Power power = AddToken.getInformation(token);
        if (power != null) {
            return Result.success(collectMapper.selectByUidAndShopid(power.getId()));
        }else{
            return Result.failure(ResultCodeEnum.UNAUTHORIZED);
        }

    }

}
