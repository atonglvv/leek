package com.atong.leek.redis.lock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @program: leek
 * @description:
 * 一步一步实现redis分布式锁
 * 第三步业务逻辑实现,分布式锁version1.1  考虑异常 与 机器宕机 [兜底方案 加过期时间]
 * redis 命令 ： setnx
 * set if not exists
 * @author: atong
 * @create: 2021-03-11 22:53
 */
@RestController
public class LockFive {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 扣减库存
     * @return end
     */
    @RequestMapping("/deduct_stock_five")
    public String deductStock () {

        String lockKey = "lockKey";
        //jedis.setnx(key, value)
        Boolean result = stringRedisTemplate.opsForValue().setIfAbsent(lockKey, "lockValue");
        //注意该行处, 若发生系统异常 或机器宕机。 则会产生问题。 set 与 expire 应是原子操作
        stringRedisTemplate.expire(lockKey, 10, TimeUnit.SECONDS);
        if (!result) {
            return "error_code";
        }

        //业务逻辑 try finally 释放锁
        try {
            //jedis.get("stock");
            int stock = Integer.parseInt(Objects.requireNonNull(stringRedisTemplate.opsForValue().get("stock")));
            if (stock > 0) {
                int realStock = stock - 1;
                // jedis.set(key, value)
                stringRedisTemplate.opsForValue().set("stock", realStock + "");
                System.out. println("扣减成功,剩余库存:" + realStock);
            }else {
                System.out.println("扣减失败,库存不足");
            }
        } finally {
            stringRedisTemplate.delete(lockKey);
        }



        return "end";
    }
}
