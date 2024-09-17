package org.example.shopvue.controller;

import cn.hutool.core.util.ObjectUtil;
import org.example.shopvue.dto.EmailDto;
import org.example.shopvue.dto.LoginEmailDto;
import org.example.shopvue.mapper.UserMapper;
import org.example.shopvue.model.User;
import org.example.shopvue.model.UserResponse;
import org.example.shopvue.utils.AddToken;
import org.example.shopvue.utils.Result;
import org.example.shopvue.utils.ResultCodeEnum;
import org.example.shopvue.utils.ValidatorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.RedisSystemException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeUtility;
import java.io.UnsupportedEncodingException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static org.example.shopvue.utils.ResultCodeEnum.*;

@RestController
@RequestMapping("emailCode")
public class LoginEmailController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private JavaMailSender javaMailSender;

    @PostMapping("/sendCode")
    public Result sendCode(@RequestParam("email") String email) throws UnsupportedEncodingException, AddressException {
        if(email.isEmpty()){
            return Result.failure(PARAMS_IS_BLANK,"邮箱不能为空");
        }
        if(!ValidatorUtils.isValidEmail(email)){
            return Result.failure(PARAMS_IS_INVALID,"邮箱格式错误");
        }
        String codeIsNull = stringRedisTemplate.opsForValue().get("Email" + email);
        if(codeIsNull != null){
            return Result.failure(FAIL,"验证码请勿频繁发送");
        }
        Random random = new Random();
        String code = String.format("%06d", random.nextInt(900000) + 100000);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("邮箱验证码");
        message.setText("您的登录验证码为: "+code+",有效期2分钟，请不要把验证码信息泄露给其他人,如非本人请勿操作");
        message.setTo(email);
        message.setFrom(new InternetAddress(MimeUtility.encodeText("电商")+"	<1541721846@qq.com>").toString());
        try {
            javaMailSender.send(message);
            stringRedisTemplate.opsForValue().set("Email" + email, code, 2L, TimeUnit.MINUTES);
            return Result.success();
        }catch (MailException e) {
            // 处理邮件发送异常
            return Result.failure(FAIL, "邮件发送失败,或者检查邮箱地址是否正确");
        } catch (RedisSystemException | RedisConnectionFailureException e) {
            return Result.failure(FAIL, "发生异常");
        } catch (Exception e) {
            return Result.failure(FAIL, "发送失败,请稍后重试");
        }
    }


    @PostMapping("/login")
    public Result login(@RequestBody LoginEmailDto loginEmailDto) {
        User user = userMapper.findUserByEmail(loginEmailDto.getEmail());
        if(ObjectUtil.isEmpty(user)){
            return Result.failure(FAIL,"未找到邮箱");
        }
        String code = stringRedisTemplate.opsForValue().get("Email" + loginEmailDto.getEmail());
        if(loginEmailDto.getCode()==null || !loginEmailDto.getCode().equals(code)){
            return Result.failure(FAIL,"验证码错误");
        }
        String token = AddToken.AddToken(user);
        UserResponse userResponse = new UserResponse();
        userResponse.setToken(token);
        userResponse.setName(user.getName());
        userResponse.setUid(user.getUid());
        stringRedisTemplate.delete("Email" + loginEmailDto.getEmail());
        return Result.success(userResponse);
    }

    @GetMapping("/getEamil/{uid}")
    public Result getEmail(@PathVariable String uid){
        String email = userMapper.findEmailById(uid);
        return Result.success(email);
    }

    @PostMapping("updateEmail")
    public Result updateEmail(@RequestBody EmailDto emailDto){
        User user = userMapper.findUserByEmail(emailDto.getEmail());
        if(!ObjectUtil.isEmpty(user)){
            return Result.failure(FAIL,"该邮箱已被绑定");
        }
        if(!ValidatorUtils.isValidEmail(emailDto.getEmail())){
            return Result.failure(PARAMS_IS_INVALID,"邮箱格式错误");
        }
        int index = userMapper.updateEmail(emailDto.getUid(),emailDto.getEmail());
        if(index == 1){
            return Result.success();
        }else{
            return Result.failure(FAIL);
        }
    }


}
