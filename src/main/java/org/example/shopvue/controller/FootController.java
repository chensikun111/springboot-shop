package org.example.shopvue.controller;

import cn.hutool.json.JSONUtil;
import org.example.shopvue.context.UserContext;
import org.example.shopvue.utils.Result;
import org.example.shopvue.vo.FootVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("foot")
public class FootController {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("get")
    public Result get() {
        String uid = UserContext.getUserId();
        List<FootVo> footVos = new ArrayList<>();
        Set<String> set = stringRedisTemplate.opsForZSet().range("foot:" + uid, 0, -1);
        for(String s : set){
            FootVo footVo = JSONUtil.toBean(s, FootVo.class);
            String string = Long.toString(footVo.getTime());
            String dateStr = string.substring(0, 8);
            footVo.setTime(Long.parseLong(dateStr));
            footVos.add(footVo);
        }
        return Result.success(footVos);
    }

}
