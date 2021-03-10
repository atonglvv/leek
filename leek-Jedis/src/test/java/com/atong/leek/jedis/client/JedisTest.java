package com.atong.leek.jedis.client;

/**
 * @program: leek
 * @description: JedisTest
 * Jedis 测试类
 * @author: atong
 * @create: 2021-03-10 14:40
 */
public class JedisTest {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("", 6379);
        jedis.set("foo", "bar");
        String value = jedis.get("foo");
    }
}
