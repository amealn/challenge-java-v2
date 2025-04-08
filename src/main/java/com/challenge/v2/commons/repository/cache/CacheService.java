package com.challenge.v2.commons.repository.cache;

import java.util.Map;

import org.springframework.data.redis.core.RedisTemplate;

import com.challenge.v2.commons.model.DijkstraNode;

public interface CacheService {
		
	public RedisTemplate<String, Object> getRedisTemplateObject();
	public RedisTemplate<String, Long> getRedisTemplateLong();
	public Map<DijkstraNode, DijkstraNode> getAllRoutesFromSalePoint(Integer originSalePointId);

}
