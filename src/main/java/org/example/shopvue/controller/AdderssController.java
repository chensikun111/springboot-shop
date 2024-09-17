package org.example.shopvue.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.example.shopvue.mapper.AddressMapper;
import org.example.shopvue.model.Address;
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
@Api(tags="地址接口")
public class AdderssController {

    @Autowired
    private AddressMapper addressMapper;

    @ApiOperation("查找用户地址信息")
    @GetMapping(value = "/address/uid")
    public Result<List<Address>> getAddressByUid(HttpServletRequest request) {
        String token = request.getHeader("token");
        Power power = AddToken.getInformation(token);
        if (power != null) {
            return Result.success(addressMapper.selectByUid(power.getId()));
        }else {
            return Result.failure(ResultCodeEnum.UNAUTHORIZED);
        }
    }



    @ApiOperation("添加地址")
    @PostMapping(value = "/address/addregion")
    public Result<String> addAddress(@RequestBody Address address ,HttpServletRequest request) {
        String token = request.getHeader("token");
        Power power = AddToken.getInformation(token);
        if (power != null) {
            address.setUid(power.getId());
            int index = addressMapper.insert(address);
            if (index > 0) {
                return Result.success();
            }else {
                return Result.failure(ResultCodeEnum.FAIL);
            }
        }else {
            return Result.failure(ResultCodeEnum.UNAUTHORIZED);
        }
    }

    @ApiOperation("修改地址")
    @PostMapping(value = "/address/updateaddress")
    public Result<String> updateAddress(@RequestBody Address address,HttpServletRequest request) {
        String token = request.getHeader("token");
        Power power = AddToken.getInformation(token);
        if (power != null) {
            address.setUid(power.getId());
            int index = addressMapper.update(address);  //修改地址
            if (index > 0) {
                return Result.success();
            }else {
                return Result.failure(ResultCodeEnum.FAIL);
            }
        }else {
            return Result.failure(ResultCodeEnum.UNAUTHORIZED);
        }
    }

    @ApiOperation("删除地址")
    @DeleteMapping(value = "/address/delete/aid/{address_id}")
    public Result<String> deleteAddress(@PathVariable("address_id") int address_id,HttpServletRequest request) {
        String token = request.getHeader("token");
        Power power = AddToken.getInformation(token);
        if (power != null) {
            int index = addressMapper.delete(power.getId(),address_id);
            if (index > 0) {
                return Result.success();
            }else {
                return Result.failure(ResultCodeEnum.FAIL);
            }
        }else{
            return Result.failure(ResultCodeEnum.UNAUTHORIZED);
        }
    }
}
