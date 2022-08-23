package com.atong.leek.demo.location.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @program: work-member
 * @description:
 * @author: atong
 * @create: 2022-05-18 23:57
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class LocationServiceTest {

    @Resource
    @Qualifier("amap")
    private LocationService locationService;

    @Resource
    @Qualifier("baiduMap")
    private LocationService baiduLocationService;

    @Test
    public void reverseGeocoding() {
        String s = locationService.reverseGeocoding("116.397499", "29.908722");
        log.info("高德地图逆地理编码返回：{}", s);
    }

    @Test
    public void reverseGeocodingBaidu() {
        String s = baiduLocationService.reverseGeocoding("116.397499", "29.908722");
        log.info("baiduMap逆地理编码返回：{}", s);
    }
}
