package com.atong.leek.redis.lock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @program: leek
 * @description:
 * 一步一步实现redis分布式锁
 * 第七步业务逻辑实现,分布式锁version1.7  考虑异常 与 机器宕机 [兜底方案 加过期时间, 保证原子操作]
 * 考虑某一次请求耗时过久, 且redis锁过期释放, 此时会有其他请求进入。
 * 当请求1 unlock 的时候, 是把请求2的 lock 给释放了。
 * 解决方案： 将redis锁的 value设置成唯一的请求id,  当解锁的时候, 先获取value判断是否是当前进程加的锁，再去删除。
 * 但是该方案还是存在问题：unlock的时候 先get后delete不是原子操作,
 * 如果 判断value是当前值, 还未删除锁时,此时锁过期,并且其他请求已获取锁成功, 这时候第一个请求会删除第二个请求获取到的锁
 * redis 命令 ： setnx
 * set if not exists
 * @author: atong
 * @create: 2021-09-02 16:25
 */
@RestController
public class LockSeven {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 扣减库存
     * @return end
     */
    @RequestMapping("/deduct_stock_seven")
    public String deductStock () {

        String lockKey = "lockKey";
        String value = UUID.randomUUID().toString();
        //jedis.setnx(key, value)
        Boolean result = stringRedisTemplate.opsForValue().setIfAbsent(lockKey, value, 10, TimeUnit.SECONDS);
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
            // unlock 当解锁的时候, 先获取value判断是否是当前进程加的锁，再去删除。
            if(value.equals(stringRedisTemplate.opsForValue().get(lockKey))) {
                stringRedisTemplate.delete(lockKey);
            }
        }

        return "end";
    }
}