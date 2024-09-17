package org.example.shopvue.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import io.swagger.annotations.Api;
import org.example.shopvue.model.CaptchaResponse;
import org.example.shopvue.model.Kaptcha;
import org.example.shopvue.service.CaptchaServiceImpl;
import org.example.shopvue.utils.CreateUUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.metadata.DataSourcePoolMetadataProvidersConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger.readers.operation.SwaggerOperationModelsProvider;


import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Api(tags = "图形验证码")
@RestController
@RequestMapping("/captcha")
public class CaptchaController {

    @Autowired
    private DefaultKaptcha defaultKaptcha;

    @Qualifier("redisTemplate")
    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/image/{uuid}")
    public void captchaImage(@PathVariable("uuid") String uuid, HttpServletResponse response) throws IOException {
        String captchaText = defaultKaptcha.createText();
        BufferedImage captchaImage = defaultKaptcha.createImage(captchaText);
        redisTemplate.opsForValue().set(uuid, captchaText,300, TimeUnit.SECONDS);

        // 设置响应头信息，防止缓存
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");

        // 将图片写入到响应输出流中
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(captchaImage, "jpeg", out); // 使用 ImageIO 将 BufferedImage 写入到输出流
        out.flush();
        out.close();
    }

    @GetMapping("/generate")
    public ResponseEntity<CaptchaResponse> generateCaptcha() {
        String uuid = CreateUUID.getUUID();
        String imageUrl = "http://127.0.0.1:8080/captcha/image/" + uuid; // 假设您会根据UUID动态生成图片
        CaptchaResponse response = new CaptchaResponse();
        response.setUuid(uuid);
        response.setImgUrl(imageUrl);

        return ResponseEntity.ok(response);
    }



    @PostMapping("/validate")
    public ResponseEntity<String> validateCaptcha(@RequestBody Kaptcha kaptcha) {
        String expectedCaptcha = (String) redisTemplate.opsForValue().get(kaptcha.getUuid());
        boolean isValid = kaptcha.getCaptchaInput().equalsIgnoreCase(expectedCaptcha);
        redisTemplate.delete(kaptcha.getUuid());
        return ResponseEntity.ok(isValid ? "验证成功" : "验证失败");

    }
}
