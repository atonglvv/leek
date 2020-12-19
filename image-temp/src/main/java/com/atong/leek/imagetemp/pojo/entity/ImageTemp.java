package com.atong.leek.imagetemp.pojo.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: leek
 * @description: 图片临时表实体类
 * @author: atong
 * @create: 2020-12-18 11:24
 */
@Data
public class ImageTemp implements Serializable {
    /** 主键id */
    private Integer id;

    /** 原图片urlHash值 */
    private String oldHash;

    /** 原图片url */
    private String oldUrl;

    /** 现图片urlHash值 */
    private String newHash;

    /** 现图片url */
    private String newUrl;

    private static final long serialVersionUID = 1L;
}
