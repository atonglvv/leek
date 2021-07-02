package com.atong.leek.plugin;

import org.mybatis.generator.api.PluginAdapter;

import java.util.List;

/**
 * @program: leek
 * @description:
 * @author: atong
 * @create: 2021-07-02 16:03
 */
public class ExtendsBasePlugin extends PluginAdapter {

    @Override
    public boolean validate(List<String> list) {
        return false;
    }
}
