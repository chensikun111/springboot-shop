package org.example.shopvue.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.example.shopvue.mapper.UserMapper;
import org.example.shopvue.model.Power;
import org.example.shopvue.model.User;
import org.example.shopvue.model.UserResponse;
import org.example.shopvue.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@Api(tags="用户接口")

public class UserController {
    @Autowired
    private UserMapper userMapper;

    //根据uid查找用户
    @ApiOperation("根据uid查找用户")
    @GetMapping(value = "/user/uid")
    public User findUidUser(HttpServletRequest request) {
        String token = request.getHeader("token");
        Power power = AddToken.getInformation(token);
        if (power != null) {
            return userMapper.findUidUser(power.getId());
        }else {
            return null;
        }
    }

    //根据电话号码查找用户
    @ApiOperation("根据电话号码查找用户")
    @GetMapping(value = "/user/phone/{phone}")
    public User findPhoneUser(@PathVariable("phone") String phone) {
        return userMapper.findPhoneUser(phone);
    }

    //根据uid查找手机号
    @ApiOperation("根据uid查找手机号")
    @GetMapping(value = "/user/byuidgetphone")
    public Result<String> findByUidGetPhone(HttpServletRequest request){
        String token = request.getHeader("token");
        Power power = AddToken.getInformation(token);
        if(power != null){
            return Result.success(userMapper.findPhoneByUid(power.getId()));
        }else{
            return Result.failure(ResultCodeEnum.UNAUTHORIZED);
        }
    }


    //注册用户
    @ApiOperation("注册用户")
    @PostMapping(value = "/user/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        int num = userMapper.findUserNum();
        ADDUid adduid = new ADDUid();
        String uid = adduid.addUid(num);
        user.setUid(uid);
        int count = userMapper.findPhone(user.getPhone());
        if(count > 0) {
            return new ResponseEntity<>("手机号存在",HttpStatus.ACCEPTED);
        }
        try {
            // 调用 UserMapper 的 insertUser 方法来保存用户
            int result = userMapper.insertUser(user);
            if (result == 1) {
                // 如果插入成功，返回成功消息
                return new ResponseEntity<>("success", HttpStatus.CREATED);
            } else {
                // 如果插入失败（通常 result 应该是 0 或负数，但具体取决于你的实现）
                return new ResponseEntity<>("faild", HttpStatus.RESET_CONTENT);
            }
        } catch (Exception e) {
            // 如果发生异常，返回错误消息
            return new ResponseEntity<>("Error occurred while registering user: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //登录功能
    @ApiOperation("登录功能")
    @PostMapping(value = "/user/login")
    public ResponseEntity<UserResponse> loginUser(@RequestBody User user) {
        User u = userMapper.findUser(user.getUid(),user.getPhone(),user.getPassword());
        if(u != null) {
            String token = AddToken.AddToken(u);
            UserResponse userResponse = new UserResponse();
            userResponse.setToken(token);
            userResponse.setName(u.getName());
            userResponse.setUid(u.getUid());
            return new ResponseEntity<>(userResponse, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @ApiOperation("修改用户信息")
    @PostMapping(value = "/user/updatemation")
    public Result<String> updateUser(@RequestBody User user,HttpServletRequest request) {
        String token = request.getHeader("token");
        Power power = AddToken.getInformation(token);
        if(power != null) {
            user.setUid(power.getId());
            int index = userMapper.updateUser(user);
            if(index == 1) {
                return Result.success();
            }else{
                return Result.failure(ResultCodeEnum.FAIL);
            }
        }else{
            return Result.failure(ResultCodeEnum.UNAUTHORIZED);
        }
    }

    @ApiOperation("修改用户密码")
    @PostMapping(value = "/user/updatepassword")
    public Result<String> updatePassword(@RequestBody User user,HttpServletRequest request) {
        String token = request.getHeader("token");
        Power power = AddToken.getInformation(token);
        if(power != null) {
            int index = userMapper.updatePassword(power.getId(),user.getPassword());
            if(index == 1) {
                return Result.success();
            }else{
                return Result.failure(ResultCodeEnum.FAIL);
            }
        }else {
            return Result.failure(ResultCodeEnum.UNAUTHORIZED);
        }
    }

    @ApiOperation("修改用户手机号")
    @PostMapping(value ="/user/updatephone")
    public Result<String> updatePhoneUser(@RequestBody User user,HttpServletRequest request) {
        String token = request.getHeader("token");
        Power power = AddToken.getInformation(token);
        if(power != null) {
            int num = userMapper.findPhone(user.getPhone());
            if(num == 1) {
                return Result.failure(ResultCodeEnum.USER_IS_EXITES);
            }else{
                userMapper.updatePhone(power.getId(), user.getPhone());
                return Result.success();
            }
        }else{
            return Result.failure(ResultCodeEnum.UNAUTHORIZED);
        }
    }

    @ApiOperation("查询所有的用户")
    @GetMapping("/user/all")
    public List<User> findAllUser(HttpServletRequest request) {
        String token = request.getHeader("token");
        Power power = AddToken.getInformation(token);
        if (IsManger.isManger(power)) {
            return userMapper.findAll();
        }else{
            return null;
        }
    }

    @ApiOperation("验证普通用户权限")
    @GetMapping(value = "/user/checktoken")
    public Boolean pettingUser(HttpServletRequest request) {
        String token = request.getHeader("token");
        return AddToken.checkToken(token);
    }

    @ApiOperation("验证管理员权限")
    @GetMapping(value = "/user/checktokenadmin")
    public Boolean pettingUserAdmin(HttpServletRequest request) {
        String token = request.getHeader("token");
        Power power = AddToken.getInformation(token);
        if(power.getRole().equals("manager")){
            return true;
        }else{
            return false;
        }
    }
}









