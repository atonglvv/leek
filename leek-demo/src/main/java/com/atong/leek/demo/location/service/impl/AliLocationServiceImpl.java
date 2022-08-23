package com.atong.leek.demo.location.service.impl;

import com.atong.leek.demo.location.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: work-member
 * @description: 高德地图服务
 * @author: atong
 * @create: 2022-05-18 23:35
 */
@Service("amap")
public class AliLocationServiceImpl implements LocationService {

    private static final String URL = "https://restapi.amap.com/v3/geocode/regeo?location={location}&key=5bcce6a5c5bd409d4a5b2fa2edb597fa&radius=1000&extensions=all";

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public String reverseGeocoding(String lng, String lat) {
        String location = lng + "," + lat;
        Map<String,String> map = new HashMap<>();
        map.put("location", location);
        String forObject = restTemplate.getForObject(URL, String.class, map);
        return forObject;
    }

    public static void main(String[] args) {
        AliLocationServiceImpl aliLocationService = new AliLocationServiceImpl();
        String s = aliLocationService.reverseGeocoding("116.397499", "39.908722");
        System.out.println(s);
    }
}
