package com.atong.leek.imagetemp.controller;

import com.alibaba.fastjson.JSON;
import com.atong.leek.imagetemp.pojo.entity.ImageTemp;
import com.atong.leek.imagetemp.service.ImageTempService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: leek
 * @description: helloWorld
 * @author: atong
 * @create: 2020-12-16 17:35
 */
@RestController
public class ImageMigrationController {

    @Autowired
    ImageTempService imageTempService;

    @GetMapping("/imageTemp/{id}")
    public ImageTemp getImageTemp(@PathVariable Integer id) {
        ImageTemp imageTemp = imageTempService.getImageTempByPrimaryKey(id);
        return imageTemp;
    }

    @GetMapping("/imageTempa/{id}")
    public String getImageTempa(@PathVariable Integer id) {
        ImageTemp imageTemp = imageTempService.getImageTempByPrimaryKey(id);
        return JSON.toJSONString(imageTemp);
    }
}
