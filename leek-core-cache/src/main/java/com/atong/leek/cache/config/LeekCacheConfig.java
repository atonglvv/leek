package com.atong.leek.cache.config;

import com.atong.leek.cache.properties.CachesConfigurationProperties;
import com.atong.leek.cache.properties.RedisCacheConfigurationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.*;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@AutoConfigureAfter(RedisAutoConfiguration.class)
public class LeekCacheConfig {

    @Value("${spring.ehcache.config}")
    private String ehCacheConfigPath;

    private final String DEFAULT_REDIS_CACHENAME = "leekRedisCache";
    private final String EHCACHE_MF_NAME="LeekEhCacheManager";

    /**
     * 装载SpringBoot的RedisProperties配置信息
     * {@link RedisProperties}
     */
    @Autowired
    private RedisProperties redisProperties;

    /**
     * 装载每个RedisCache的配置信息
     */
    @Autowired
    private CachesConfigurationProperties cachesConfigurationProperties;

    /**
     * @return
     */
    @Bean
    public EhCacheManagerFactoryBean ehCacheManagerFactoryBean() {
        EhCacheManagerFactoryBean cacheManagerFactoryBean = new EhCacheManagerFactoryBean();
        cacheManagerFactoryBean.setConfigLocation(new ClassPathResource(ehCacheConfigPath));
        cacheManagerFactoryBean.setShared(true);
        cacheManagerFactoryBean.setCacheManagerName(EHCACHE_MF_NAME);
        cacheManagerFactoryBean.afterPropertiesSet();// 初始化 读取配置文件

        return cacheManagerFactoryBean;
    }

    @Bean
    public RedisClusterConfiguration redisClusterConfiguration() {
        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();
        redisClusterConfiguration.setMaxRedirects(redisProperties.getCluster().getMaxRedirects());
        List<RedisNode> nodeList = new ArrayList<RedisNode>();
        for (String node : redisProperties.getCluster().getNodes()) {
            String[] tmpArray = node.split(":");
            nodeList.add(new RedisNode(tmpArray[0], Integer.parseInt(tmpArray[1])));
        }
        redisClusterConfiguration.setClusterNodes(nodeList);
        if (!StringUtils.isEmpty(redisProperties.getPassword())) {
            redisClusterConfiguration.setPassword(RedisPassword.of(redisProperties.getPassword()));
        }
        return redisClusterConfiguration;
    }

    /**
     * @param redisClusterConfiguration
     * @return
     */
    @Bean
    public RedisConnectionFactory redisConnectionFactory(RedisClusterConfiguration redisClusterConfiguration) {
        if (!CollectionUtils.isEmpty(redisClusterConfiguration.getClusterNodes())
                && redisClusterConfiguration.getClusterNodes().size() > 1) {
            return new LettuceConnectionFactory(redisClusterConfiguration);
        }
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(
                redisProperties.getHost(), redisProperties.getPort());
        return new LettuceConnectionFactory(redisStandaloneConfiguration);
    }

    /**
     * 根据application.yml配置文件里的spring.redis.selfdefine配置信息初始化每个缓存的配置包括缓存名称、失效时间、是否允许空值、前缀、是否使用前缀
     * ，供初始化RedisCacheManager时使用
     * @return Map<String, RedisCacheConfiguration> k为缓存名称，v为缓存配置{@link RedisCacheConfiguration}
     */
    @Bean
    public Map<String, RedisCacheConfiguration> cacheConfigurations() {
        Map<String, RedisCacheConfiguration> configMap = new HashMap<>();
        Map<String, RedisCacheConfigurationProperties> tmpMap = cachesConfigurationProperties.getMap();
        if (tmpMap.isEmpty()) {
            configMap.put(DEFAULT_REDIS_CACHENAME, RedisCacheConfiguration.defaultCacheConfig());
            return configMap;
        }
        for (Map.Entry<String, RedisCacheConfigurationProperties> entry : tmpMap.entrySet()) {
            //RedisSerializer<String> stringRedisSerializer = new StringRedisSerializer();
            //RedisSerializer<Object> redisSerializer = new GenericJackson2JsonRedisSerializer();
            RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                    .entryTtl(Duration.ofSeconds(entry.getValue().getExpireTime()))
                    /*
                     * .serializeKeysWith(
                     * RedisSerializationContext.SerializationPair.fromSerializer(
                     * stringRedisSerializer))
                     * .serializeValuesWith(RedisSerializationContext.SerializationPair.
                     * fromSerializer(redisSerializer))
                     */
                    .computePrefixWith(
                            cacheName -> entry.getValue().getKeyPrefix().concat(":").concat(cacheName).concat(":"));

            if (!entry.getValue().isCacheNullValues()) {
                redisCacheConfiguration = redisCacheConfiguration.disableCachingNullValues();
            }
            if (!entry.getValue().isUsePrefix()) {
                redisCacheConfiguration = redisCacheConfiguration.disableKeyPrefix();
            }
            configMap.put(entry.getKey(), redisCacheConfiguration);
        }

        return configMap;
    }
}
