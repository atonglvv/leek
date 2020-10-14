package com.atong.leek.cache.properties;


public class RedisCacheConfigurationProperties {

	private Long expireTime = 1800L;

	private boolean cacheNullValues = true;

	private String keyPrefix = "leaf6";

	private boolean usePrefix = true;

	public Long getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Long expireTime) {
		this.expireTime = expireTime;
	}

	public boolean isCacheNullValues() {
		return cacheNullValues;
	}

	public void setCacheNullValues(boolean cacheNullValues) {
		this.cacheNullValues = cacheNullValues;
	}

	public String getKeyPrefix() {
		return keyPrefix;
	}

	public void setKeyPrefix(String keyPrefix) {
		this.keyPrefix = keyPrefix;
	}

	public boolean isUsePrefix() {
		return usePrefix;
	}

	public void setUsePrefix(boolean usePrefix) {
		this.usePrefix = usePrefix;
	}

	@Override
	public String toString() {
		return "RedisCacheConfigurationProperties{" + "expireTime='" + expireTime + '\'' + ", cacheNullValues="
				+ cacheNullValues + ", keyPrefix=" + keyPrefix + ", usePrefix=" + usePrefix + '}';
	}
}