package com.atong.leek.imagetemp.service;

import com.atong.leek.imagetemp.pojo.entity.ImageTemp;

/**
 * @program: leek
 * @description: 图片迁移临时服务接口
 * @author: atong
 * @create: 2020-12-18 11:37
 */
public interface ImageTempService {
    ImageTemp getImageTempByPrimaryKey(Integer id);
}
