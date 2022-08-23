package com.atong.leek.demo.location.service.impl;

import com.atong.leek.demo.location.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: work-member
 * @description: 百度地图服务
 * @see
 * @author: atong
 * @create: 2022-05-19 10:51
 */
@Service("baiduMap")
public class BaiduMapServiceImpl implements LocationService {

    /** todo @Value; 申请企业开发者 */
    private static final String BAIDU_MAP_KEY = "fO8IOnN4ACAQK6NsvMAZKEuiR8pVedOn";
    /** todo @Value */
    private static final String URL = "https://api.map.baidu.com/reverse_geocoding/v3/?ak={key}&output=json&coordtype=wgs84ll&location={location}";


    @Autowired
    private RestTemplate restTemplate;

    @Override
    public String reverseGeocoding(String lng, String lat) {
        String location = lng + "," + lat;
        Map<String,String> map = new HashMap<>();
        map.put("location", location);
        map.put("key", BAIDU_MAP_KEY);
        String forObject = restTemplate.getForObject(URL, String.class, map);
        return forObject;
    }
}
