package com.atong.leek.redis.lock.controller.redisson;

import org.redisson.Redisson;
import org.redisson.RedissonRedLock;
import org.redisson.api.RLock;
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
 * Redisson实现分布式锁
 * 底层原理:
 * 乐观锁
 * 每隔key过期时间的1/3[比如key过期时间是30s,则每10s],检查是否还有锁,如果持有则延长锁的时间
 * lua脚本 保证原子
 *
 * 存在的问题：
 * redis 主从架构。如果主节点宕机了,并且主节点数据还未同步至从节点。redis重新选举主节点。
 * 若此时扣库存线程访问了新的主节点去获取锁,这时候是可以获取到的。存在并发问题。
 * 对于 CAP , Redis架构更倾向于 AP
 * RedLock 可以解决以上问题, 或者使用zk来实现分布式锁。zk的ZAB分布式协议保证数据一致。
 * RedLock 跟 zookeeper的zab思想很像。
 *
 * redis 命令 ： setnx
 * set if not exists
 * @author: atong
 * @create: 2021-03-11 22:53
 */
@RestController
public class LockRedLock {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private Redisson redisson;

    /**
     * 扣减库存
     * @return end
     */
    @RequestMapping("/deduct_stock_five")
    public String deductStock () {

        String lockKey = "lockKey";
        //这里需要自己实例化不同redis实例的redisson客户端连接,这里只是伪代码用一个redisson客户端简化了
        RLock lock1 = redisson.getLock(lockKey);
        RLock lock2 = redisson.getLock(lockKey);
        RLock lock3 = redisson.getLock(lockKey);

        //根据多个RLock对象构建RedissonRedLock(最核心的差别就在这里)
        RedissonRedLock redLock = new RedissonRedLock(lock1, lock2, lock3);

        //业务逻辑 try finally 释放锁
        try {
            /**
             * waitTimeout尝试获取锁的最大等待时间，超过这个值，则认为获取锁失败
             * leaselime锁的持有时间,超过这个时间锁会自动失效（值应设置为大于业务处理的时间,确保在锁有效期内业务能执行完毕)
             */
            boolean res = redLock.tryLock(0, 30, TimeUnit.SECONDS);
            if (res) {
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
            }
        } catch (Exception e) {
            throw new RuntimeException("lock fail");
        } finally{
            //释放锁
            redLock.unlock();
        }



        return "end";
    }
}
