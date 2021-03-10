package com.atong.leek.jedis.connection;

import com.atong.leek.jedis.Address;
import com.atong.leek.jedis.Command;
import com.atong.leek.jedis.protocol.Protocol;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @program: leek
 * @description: Connection
 * @author: atong
 * @create: 2021-03-10 14:12
 */
public class Connection {

    /** socket */
    private Socket socket;

    /** host */
    private String host;

    /** port */
    private int port;

    /** 输出流 */
    private OutputStream outputStream;
    /** 输入流 */
    private InputStream inputStream;

    public Connection() {
        this(Address.DEFAULT_HOST, Address.DEFAULT_PORT);
    }

    public Connection(final String host) {
        this(host, Address.DEFAULT_PORT);
    }

    public Connection(final String host, final int port) {
        this.host = host;
        this.port = port;
    }

    public Connection connection() {
        try {
            //建立socket连接
            socket = new Socket(host, port);
            //获取输入输出流
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return this;
    }
    /**
     * 发送数据和命令
     * @return
     */
    public Connection sendcommit(Command command, byte[]...bs) {
        connection();
        Protocol.sendCommand(outputStream, command, bs);
        return this;
    }

    /**
     *	接收服务端返回的数据
     */
    public String getStatusCodereply() {
        byte[] bytes = new byte[1024];

        try {
            inputStream.read(bytes);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return new String(bytes);
    }


}
