package com.atong.leek.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.cache.support.CompositeCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.util.Map;

/**
 * @author wangtao1609
 *
 */
@Configuration
public class Leaf6CacheManager {

	@Autowired
	private Map<String, RedisCacheConfiguration> cacheConfigurations;

	/**
	 * 混合缓存管理
	 *
	 * @param redisTemplate redis template
	 * @return cacheManager
	 */
	@Bean
	@Primary
	public CacheManager leafCacheManager(@Autowired RedisConnectionFactory redisConnectionFactory,
			@Autowired EhCacheManagerFactoryBean ehCacheManagerFactoryBean) {
		RedisCacheManager redisCacheManager = redisCacheManager(redisConnectionFactory);
		EhCacheCacheManager ehCacheCacheManager = ehCacheCacheManager(ehCacheManagerFactoryBean);
		CompositeCacheManager cacheManager = new CompositeCacheManager(redisCacheManager, ehCacheCacheManager);
		/* cacheManager.setFallbackToNoOpCache(true); */
		cacheManager.afterPropertiesSet();
		return cacheManager;
	}

	/**
	 * 获取redisCacheManager
	 *
	 * @param redisTemplate redisTemplate
	 * @return redisCacheManager
	 */
	@Bean
	public RedisCacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory) {
		RedisCacheManager cacheManager = RedisCacheManager.builder(redisConnectionFactory).disableCreateOnMissingCache()/**根据名称找不到时不自动在redis创建*/
				.withInitialCacheConfigurations(cacheConfigurations)/** 对每个缓存空间应用不同的配置 */
				.build();
		return cacheManager;
	}
	

	/**
	 * EhCacheManager
	 *
	 * @return EhCacheManager
	 */
	@Bean
	public EhCacheCacheManager ehCacheCacheManager(EhCacheManagerFactoryBean factoryBean) {
		EhCacheCacheManager ehCacheCacheManager = new EhCacheCacheManager(factoryBean.getObject());
		// 由于自己实列化EhCacheManager 需要执行 手动初始化 方法。
		ehCacheCacheManager.initializeCaches();// 初始化
		return ehCacheCacheManager;
	}

}