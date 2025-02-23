package com.challenge.v2.commons.dao.cache;

import org.springframework.data.redis.core.HashOperations;

public interface CacheService {
	
	public void initCache();	
	public HashOperations<String, Object, Object> getCache();

}
