package com.atong.leek.imagetemp.service.impl;

import com.atong.leek.imagetemp.mapper.primary.ImageTempMapper;
import com.atong.leek.imagetemp.pojo.entity.ImageTemp;
import com.atong.leek.imagetemp.service.ImageTempService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @program: leek
 * @description: 图片迁移临时服务
 * @author: atong
 * @create: 2020-12-18 11:30
 */
@Service("imageTempServiceImpl")
public class ImageTempServiceImpl implements ImageTempService {

    @Resource
    ImageTempMapper imageTempMapper;


    @Override
    public ImageTemp getImageTempByPrimaryKey(Integer id) {
        ImageTemp imageTemp = imageTempMapper.selectByPrimaryKey(id);
        return imageTemp;
    }
}
