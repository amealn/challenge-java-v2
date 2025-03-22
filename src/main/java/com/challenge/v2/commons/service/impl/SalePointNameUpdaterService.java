package com.challenge.v2.commons.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.challenge.v2.commons.repository.cache.CacheService;

@Service
public class SalePointNameUpdaterService {

    private static final Logger logger = LoggerFactory.getLogger(SalePointNameUpdaterService.class);
    private static final String SALE_POINTS_CACHE = "salePointsCache";

    @Autowired
    private CacheService cacheService;

    public void updateSalePointName(Integer salePointId, String salePointName) {
        try {
            cacheService.getRedisTemplateObject().opsForHash().put(SALE_POINTS_CACHE, String.valueOf(salePointId), salePointName);
            logger.info("Updated sale point name in cache. ID: {}, Name: {}", salePointId, salePointName);
        } catch (Exception e) {
            logger.error("Error updating sale point name in cache. ID: {}, Name: {}", salePointId, salePointName, e);
            throw new RuntimeException("Error updating sale point name in cache", e);
        }
    }
}
