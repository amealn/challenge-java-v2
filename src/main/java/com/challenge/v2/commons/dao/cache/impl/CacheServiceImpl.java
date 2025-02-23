package com.challenge.v2.commons.dao.cache.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.challenge.v2.commons.dao.cache.CacheService;

@Service
public class CacheServiceImpl implements CacheService{
	
    private static final Logger logger = LoggerFactory.getLogger(CacheServiceImpl.class);
    private HashOperations<String, String, Object> hashOperations;
	
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public void setHashOperations(RedisTemplate<String, Object> redisTemplate) {
        this.hashOperations = redisTemplate.opsForHash();
    }
    
    @Override
    @EventListener(ContextRefreshedEvent.class)
    public void initCache() {
    	try {
            Map<Integer, String> salePointsMap = Map.of(
                    1, "CABA",
                    2, "GBA_1",
                    3, "GBA_2",
                    4, "Santa Fe",
                    5, "Córdoba",
                    6, "Misiones",
                    7, "Salta",
                    8, "Chubut",
                    9, "Santa Cruz",
                    10, "Catamarca"
            );
            salePointsMap.forEach((key, value) -> redisTemplate.opsForHash().put("salePointsCache", key.toString(), value));

            Integer[][] salePointsCostsEdges = {
                    {1, 2, 2},
                    {1, 3, 3},
                    {2, 3, 5},
                    {2, 4, 10},
                    {1, 4, 11},
                    {4, 5, 5},
                    {2, 5, 14},
                    {6, 7, 32},
                    {8, 9, 11},
                    {10, 7, 5},
                    {3, 8, 10},
                    {5, 8, 30},
                    {10, 5, 5},
                    {4, 6, 6}
            };

            for (Integer[] edge : salePointsCostsEdges) {
                String key = edge[0] + "_" + edge[1];
                Integer cost = edge[2];
                hashOperations.put("salePointsCostsGraph", key, cost);
            }
            
        } catch (Exception e) {
            logger.error("Error initializing cache", e);
        }
    }
    
    @Override
    public HashOperations<String, Object, Object> getCache() {
    	return redisTemplate.opsForHash();
    }

}
