package com.atong.leek.jedis.protocol;

import com.atong.leek.jedis.Command;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @program: leek
 * @description: Protocol
 * @author: atong
 * @create: 2021-03-10 15:00
 */
public class Protocol {
    /** 字符串 */
    public static final String DOLLAR_BYTE="$";
    /** 数组 */
    public static final String ASTERISK_BYTE="*";
    /** 换行 */
    public static final String BLANK_STRING="\r\n";


    /**
     * 封装redis需要的数据
     * @param os 
     * @param command
     * @param bs
     */
    public static void sendCommand(OutputStream os, Command command, byte[]...bs ) {
        StringBuffer sb = new StringBuffer();
        sb.append(ASTERISK_BYTE).append(bs.length+1).append(BLANK_STRING);
        sb.append(DOLLAR_BYTE).append(command.name().length()).append(BLANK_STRING);
        sb.append(command.name()).append(BLANK_STRING);
        System.out.println(command.name());
        for (byte[] bs2 : bs) {
            sb.append(DOLLAR_BYTE).append(bs2.length).append(BLANK_STRING);
            sb.append(new String(bs2)).append(BLANK_STRING);
        }

        try {
            //发送
            os.write(sb.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
