package com.atong.leek.jedis.client;

import com.atong.leek.jedis.Command;
import com.atong.leek.jedis.connection.Connection;

/**
 * @program: leek
 * @description: Jedis
 * 简单实现Jedis GET and SET
 *
 * RESP(Redis Serialization Protocol)
 * @author: atong
 * @create: 2021-03-10 13:47
 */
public class Jedis extends Connection {

    public Jedis() {
        super();
    }

    public Jedis(final String host) {
        super(host);
    }

    public Jedis(final String host, int port) {
        super(host, port);
    }

    /**
     * SET
     * @param key key
     * @param value value
     * @return string
     */
    public String set(String key, String value) {
        this.sendcommit(Command.SET,key.getBytes(),value.getBytes());
        return this.getStatusCodereply();
    }

    /**
     * GET redis 相关key 的 value
     * @param key key
     * @return value
     */
    public String get(String key) {
        this.sendcommit(Command.GET,key.getBytes());
        return this.getStatusCodereply();
    }

}
