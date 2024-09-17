package org.example.shopvue.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.example.shopvue.mapper.CartMapper;
import org.example.shopvue.model.Cart;
import org.example.shopvue.model.Power;
import org.example.shopvue.utils.AddToken;
import org.example.shopvue.utils.Result;
import org.example.shopvue.utils.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@Api(tags="购物车接口")
public class CartController {
    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @ApiOperation("查找购物车")
    @GetMapping(value = "/cart/uid")
    public Result<List<Cart>> getCart(HttpServletRequest request) {
        String token = request.getHeader("token");
        Power power = AddToken.getInformation(token);
        if (power != null) {
            return Result.success(cartMapper.selectCartByUid(power.getId()));
        }else{
            return Result.failure(ResultCodeEnum.UNAUTHORIZED);
        }
    }

//    @ApiOperation("查找购物车")
//    @PostMapping(value = "/cart/getCart")
//    public List<Cart> getCart2(UserResponse userResponse) {
//        if(AddToken.checkToken(userResponse.getToken())){
//            return
//        }
//    }


    @ApiOperation("删除单条购物车")
    @DeleteMapping(value = "/cart/shopid/{shopid}/uid")
    public Result<String> deleteCart(@PathVariable("shopid") String shopid ,HttpServletRequest request) {
        String token = request.getHeader("token");
        Power power = AddToken.getInformation(token);
        if (power != null) {
            int index = cartMapper.deleteCartByShopid(shopid,power.getId());
            if(index > 0) {
                return Result.success();
            }else {
                return Result.failure(ResultCodeEnum.FAIL,"添加失败！");
            }
        }else {
            return Result.failure(ResultCodeEnum.UNAUTHORIZED);
        }

    }


    @ApiOperation("加入购物车")
    @PostMapping(value = "/cart/addCart")
    public Result<String> addCart(@RequestBody Cart cart,HttpServletRequest request) {
        String token = request.getHeader("token");
        Power power = AddToken.getInformation(token);
        if (power != null) {
            cart.setUid(power.getId());
            Cart cart1 = cartMapper.isFindCart(cart.getShopid(),cart.getUid());
            if(cart1 != null) {
                int num = cart1.getNum()+cart.getNum();
                cart.setNum(num);
                int index = cartMapper.updateCartNum(cart);
                if(index > 0) {
                    return Result.success();
                }else {
                    return Result.failure(ResultCodeEnum.FAIL);
                }
            }else{
                int index = cartMapper.insertCart(cart);
                if(index > 0) {
                    return Result.success();
                }else{
                    return Result.failure(ResultCodeEnum.FAIL);
                }
            }
        }else {
            return Result.failure(ResultCodeEnum.UNAUTHORIZED);
        }




    }


    @ApiOperation("添加购物车")
    @PostMapping(value = "/cart/updatecount")
    public Result<String> updatecount(@RequestBody Cart cart,HttpServletRequest request) {
        String token = request.getHeader("token");
        Power power = AddToken.getInformation(token);
        if (power == null) {
            return Result.failure(ResultCodeEnum.FAIL,"添加失败！");
        }else {
            cart.setUid(power.getId());
            int index = cartMapper.updateCartNum(cart);
            if (index > 0) {
                return Result.success();
            } else {
                return Result.failure(ResultCodeEnum.UNAUTHORIZED, "身份认证失败！");
            }
        }
    }


    @ApiOperation("购物车选择")
    @PostMapping(value = "/cart/isOpt")
    public ResponseEntity<String> isOpt(@RequestBody Cart cart,HttpServletRequest request) {
        String token = request.getHeader("token");
        Power power = AddToken.getInformation(token);
        int check;
        if(cart.getClick()){
            check = 1;
        }else {
            check = 0;
        }
        if(power != null) {
            int index = cartMapper.updateIsclick(cart.getShopid(), power.getId(), check);
            if(index > 0) {
                return new ResponseEntity<>(HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        }else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

    }

    //购物车全选
    @ApiOperation("购物车全选")
    @PostMapping(value = "/cart/allIsOpt")
    public ResponseEntity<String> allIsOpt(@RequestBody Cart cart,HttpServletRequest request) {
        String token = request.getHeader("token");
        Power power = AddToken.getInformation(token);
        int check;
        if(cart.getClick()){
            check = 1;
        }else {
            check = 0;
        }
        if(power != null) {
            int index = cartMapper.updateAllIsclick(power.getId(),check);
            if(index > 0) {
                return new ResponseEntity<>(HttpStatus.OK);
            }else{
                return new ResponseEntity<>("fali",HttpStatus.NO_CONTENT);
            }
        }else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @ApiOperation("购物车提交，删除购物车里选中内容")
    @DeleteMapping(value ="/cart/sumitorder/user")
    public Result<String> deleteCartByUidOrder1(HttpServletRequest request) {
        String token = request.getHeader("token");
        Power power = AddToken.getInformation(token);
        if(power != null) {
            int index = cartMapper.deleteCartByUidOrder1(power.getId());
            if(index > 0) {
                return Result.success();
            }else{
                return Result.failure(ResultCodeEnum.FAIL);
            }
        }else{
            return Result.failure(ResultCodeEnum.UNAUTHORIZED);
        }
    }
}
