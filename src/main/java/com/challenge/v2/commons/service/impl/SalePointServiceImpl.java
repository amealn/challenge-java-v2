package com.challenge.v2.commons.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.challenge.v2.commons.dao.cache.CacheService;
import com.challenge.v2.commons.model.SalePoint;
import com.challenge.v2.commons.service.SalePointService;

@Service
public class SalePointServiceImpl implements SalePointService {
	
    private static final Logger logger = LoggerFactory.getLogger(SalePointServiceImpl.class);
	
	@Autowired
	private CacheService cacheService;

	@Override	
	public List<SalePoint> getAllSalePoints() {		
		try {
    		Map<Object, Object> entries = cacheService.getCache().entries("salePointsCache");
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
			return null;
		}
	}

	@Override
	public SalePoint saveSalePoint(SalePoint salePoint) {
		saveOrUpdateOperation(salePoint);
		return salePoint;
	}

	@Override
	public SalePoint updateSalePoint(SalePoint salePoint) {
		saveOrUpdateOperation(salePoint);
		return salePoint;
	}	

	@Override
	public Boolean deleteSalePoint(Integer id) {		
		try {
			cacheService.getCache().delete("salePointsCache", id.toString());
            logger.info("Evicted sale point with ID: {}", id);
            return Boolean.TRUE;
        } catch (Exception e) {
            logger.error("Error evicting sale point with ID: {}", id, e);
            return Boolean.FALSE;
        }
	}
	
	private void saveOrUpdateOperation(SalePoint salePoint) {
		try {
			cacheService.getCache().put("salePointsCache", salePoint.getId().toString(), salePoint.getName());
            logger.info("Added sale point with ID: {} and name: {}", salePoint.getId().toString(), salePoint.getName());
        } catch (Exception e) {
            logger.error("Error adding sale point with ID: {} and name: {}", salePoint.getId().toString(), salePoint.getName(), e);
        }
	}

	

}
