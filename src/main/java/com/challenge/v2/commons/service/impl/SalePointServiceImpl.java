package com.challenge.v2.commons.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.challenge.v2.commons.model.SalePoint;
import com.challenge.v2.commons.model.SalePointCredential;
import com.challenge.v2.commons.repository.cache.CacheService;
import com.challenge.v2.commons.service.GenericSalePointService;

@Service("salePointService")
public class SalePointServiceImpl implements GenericSalePointService<SalePoint, Integer> {
	
    private static final Logger logger = LoggerFactory.getLogger(SalePointServiceImpl.class);
    private static final String SALE_POINTS_COUNTER_KEY = "salePointsCounter";
    private static final String SALE_POINTS_CACHE = "salePointsCache";
	
	@Autowired
	private CacheService cacheService;

	@Autowired
	@Qualifier("salePointsCredentialService")
	private GenericSalePointService<SalePointCredential, Integer> salePointCredentialService;

	@Override
	public List<SalePoint> getAll() {
		try {
    		Map<Object, Object> entries = cacheService.getRedisTemplateObject().opsForHash().entries(SALE_POINTS_CACHE);
            Map<Integer, String> salePointsMap = new HashMap<>();
            for (Map.Entry<Object, Object> entry : entries.entrySet()) {
                if (entry.getKey() instanceof String && entry.getValue() instanceof String) {
                    try {
                        Integer key = Integer.parseInt((String) entry.getKey());
                        salePointsMap.put(key, (String) entry.getValue());
                    } catch (NumberFormatException e) {
                        logger.error("Error parsing key to Integer", e);
                    }
                }
            }
            return salePointsMap.entrySet().stream()
                    .map(entry -> new SalePoint(entry.getKey(), entry.getValue()))
                    .collect(Collectors.toList());
            
		} catch (Exception e) {
			logger.error("Error trying to get all sale points from cache", e);
			throw new RuntimeException("Error trying to get all sale points from cache", e);
		}
	}
	
	/**
     * Deletes a sale point from SALE_POINTS_CACHE, every connection in "salePointsCostsGraph" that references that sale point and the corresponding credential in the database.
     *
     * @param id         A sale point id.
     */
	@Override
	public Boolean delete(Integer id) {
		try {
			cacheService.getRedisTemplateObject().opsForHash().delete(SALE_POINTS_CACHE, id.toString());
            logger.info("Evicted sale point with ID: {}", id);
            
            Map<Object, Object> edges = cacheService.getRedisTemplateObject().opsForHash().entries("salePointsCostsGraph");

            edges.forEach((key, value) -> {
                if (key instanceof String && value instanceof Integer) {
                    String keyStr = (String) key;
                    String[] nodes = keyStr.split("_");
                    int pointA = Integer.parseInt(nodes[0]);
                    int pointB = Integer.parseInt(nodes[1]);
                    if (pointA == id || pointB == id) {
                        cacheService.getRedisTemplateObject().opsForHash().delete("salePointsCostsGraph", keyStr);
                    }
                } else {
                    logger.warn("Unexpected key or value type in salePointsCostsGraph");
                }
            });
            
            salePointCredentialService.delete(id);
            
            return Boolean.TRUE;
        } catch (Exception e) {
            logger.error("Error evicting sale point with ID: {}", id, e);
            throw new RuntimeException("Error evicting sale point from cache", e);
        }
	}

	@Override
	public SalePoint save(SalePoint salePoint) {
		saveOrUpdateOperation(salePoint);
		return salePoint;
	}
	
	@Override
	public SalePoint update(SalePoint salePoint) {
		saveOrUpdateOperation(salePoint);
		return salePoint;
	}
	
	/**
     * If there is an id, it updates SALE_POINTS_CACHE with a name. If there is no id, it generates an incremental id and saves a sale point
     *
     * @param salePoint         A sale point.
     */
	private void saveOrUpdateOperation(SalePoint salePoint) {
		try {
			Integer id = salePoint.getId();
			if (id == null) {
				ValueOperations<String, Long> valueOps = cacheService.getRedisTemplateLong().opsForValue();
				Long newId = valueOps.increment(SALE_POINTS_COUNTER_KEY);
                if (newId != null) {
                    id = newId.intValue();
                    salePoint.setId(id);
                } else {
                    throw new RuntimeException("Failed to generate new ID from Redis.");
                }
            }
			cacheService.getRedisTemplateObject().opsForHash().put(SALE_POINTS_CACHE, String.valueOf(id), salePoint.getName());
            logger.info("Added or updating sale point with ID: {} and name: {}", id, salePoint.getName());
		} catch (Exception e) {
			logger.error("Error adding or updating sale point with ID: {} and name: {}", salePoint.getId().toString(), salePoint.getName(), e);
			throw new RuntimeException("Error adding or updating sale point from cache", e);
		}
	}	

}
