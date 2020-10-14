package com.atong.leek.cache.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
@ConfigurationProperties(prefix = "spring.redis.selfdefine")
public class CachesConfigurationProperties {
	
	private Map<String, RedisCacheConfigurationProperties> map;

	public Map<String, RedisCacheConfigurationProperties> getMap() {
		return map;
	}

	public void setMap(Map<String, RedisCacheConfigurationProperties> map) {
		this.map = map;
	}
	
}
