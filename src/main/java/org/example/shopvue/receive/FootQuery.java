package org.example.shopvue.receive;

import cn.hutool.json.JSONUtil;
import org.example.shopvue.bo.ShopBo;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class FootQuery {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "topic.foot.queue1"),
            exchange = @Exchange(name = "foot.topic", type = ExchangeTypes.TOPIC),
            key = "foot"
    ))
    public void listen(ShopBo shopBo) {
        String key = "foot:"+shopBo.getUid();
        long time = shopBo.getTime();
        String string = Long.toString(time);
        String dateStr = string.substring(0, 8);
        shopBo.setTime(Long.parseLong(dateStr));
        //保存到redis
        ZSetOperations<String, String> zSetOps = stringRedisTemplate.opsForZSet();
        stringRedisTemplate.opsForZSet().add(key,JSONUtil.toJsonStr(shopBo) ,time);
        stringRedisTemplate.expire(key, 50L, TimeUnit.DAYS);
        Long size = zSetOps.size(key);
        if (size != null && size > 50) {
            // 移除分数最低的元素（这里只是为了示例，实际可以根据需要调整）
            Set<ZSetOperations.TypedTuple<String>> lowestScoreElements = zSetOps.rangeByScoreWithScores(key, Double.MIN_VALUE, Double.MAX_VALUE, 0, 1);
            // 检查是否找到了元素
            if (lowestScoreElements != null && !lowestScoreElements.isEmpty()) {
                String elementToRemove = lowestScoreElements.iterator().next().getValue();
                zSetOps.remove(key, elementToRemove);
            }
        }

    }

}
