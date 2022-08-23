package com.atong.leek.demo.location.service;

/**
 * @program: work-member
 * @description: 地图 Service
 * @author: atong
 * @create: 2022-05-18 18:12
 */
public interface LocationService {

    String reverseGeocoding(String lng, String lat);
}
