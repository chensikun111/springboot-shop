package org.example.shopvue.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.example.shopvue.mapper.ReviewMapper;
import org.example.shopvue.model.Power;
import org.example.shopvue.model.Review;
import org.example.shopvue.utils.AddToken;
import org.example.shopvue.utils.GetNowTime;
import org.example.shopvue.utils.Result;
import org.example.shopvue.utils.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@Api(tags="评价接口")
public class ReviewController {
    @Autowired
    private ReviewMapper reviewMapper;

    @ApiOperation("查找商品评论")
    @GetMapping(value = "/review/shopid/{shopid}")
    public List<Review> selectTextByShopId(@PathVariable("shopid") String shopid) {
        return reviewMapper.selectTextByShopId(shopid);
    }

    @ApiOperation("商品评论")
    @PostMapping(value = "/review/addreview")
    public Result<String> addReview(@RequestBody Review review,HttpServletRequest request) {
        String token = request.getHeader("token");
        Power power = AddToken.getInformation(token);
        if (power != null) {
            review.setUid(power.getId());
            review.setReview_data(GetNowTime.GetNowTime());
            int index = reviewMapper.insert(review);
            if(index > 0) {
                int index2 = reviewMapper.updateOrderStatus(1,review.getOrderid(),review.getShopid());
                if(index2 > 0) {
                    return Result.success();
                }else {
                    return Result.failure(ResultCodeEnum.FAIL);
                }
            }else {
                return Result.failure(ResultCodeEnum.FAIL);
            }
        }else{
            return Result.failure(ResultCodeEnum.UNAUTHORIZED);
        }

    }


    //查找未评价的订单
    @ApiOperation("未评价的订单")
    @GetMapping(value = "/review/noreview/user")
    public Result<List<Review>> noReview(HttpServletRequest request) {
        String token = request.getHeader("token");
        Power power = AddToken.getInformation(token);
        if(power != null) {
            return Result.success(reviewMapper.selectOrder(power.getId()));
        }else {
            return Result.failure(ResultCodeEnum.UNAUTHORIZED);
        }
    }

    @ApiOperation("已评价的订单")
    @GetMapping(value ="/review/hreview/user")
    public Result<List<Review>> Review(HttpServletRequest request) {
        String token = request.getHeader("token");
        Power power = AddToken.getInformation(token);
        if(power != null) {
            return Result.success(reviewMapper.selectReview(power.getId()));
        }else{
            return Result.failure(ResultCodeEnum.UNAUTHORIZED);
        }
    }

}
