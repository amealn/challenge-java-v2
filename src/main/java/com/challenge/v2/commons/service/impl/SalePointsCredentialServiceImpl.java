package com.challenge.v2.commons.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.challenge.v2.commons.model.SalePointCredential;
import com.challenge.v2.commons.repository.cache.CacheService;
import com.challenge.v2.commons.repository.mongo.dao.SalePointCredentialDao;
import com.challenge.v2.commons.service.GenericSalePointService;

@Service("salePointsCredentialService")
public class SalePointsCredentialServiceImpl implements GenericSalePointService<SalePointCredential, Integer> {
	
	private static final Logger logger = LoggerFactory.getLogger(SalePointsCredentialServiceImpl.class);
	private static final String SALE_POINTS_CACHE = "salePointsCache";
	
	@Autowired
	private SalePointCredentialDao salePointCredentialDao;
	
	@Autowired
	private CacheService cacheService;
	
	@Autowired
    private SalePointNameUpdaterService salePointNameUpdaterService;

	@Override
	public List<SalePointCredential> getAll() {
		try {
			Sort sortAsc = Sort.by("salePointId").ascending();
			return salePointCredentialDao.findAll(sortAsc);
		} catch (Exception e) {
			logger.error("Error trying to get sale point credentials in db", e);
			throw new RuntimeException("Error trying to get sale point credentials in db", e);
		}
	}
	
	/**
     * Ensures an id is received and then checks if it exists on cache, then it saves on DDBB a credential referencing that existing sale point and with a new reception date
     *
     * @param salePointCredential         A sale point credential.
     */
	@Override
	public SalePointCredential save(SalePointCredential salePointCredential) {
		try {
			if(salePointCredential.getSalePointId() != null) {
				if(cacheService.getRedisTemplateObject().opsForHash().hasKey(SALE_POINTS_CACHE, salePointCredential.getSalePointId())) {
					salePointCredential.setReceptionDate(new Date());
					return salePointCredentialDao.insert(salePointCredential);				
				}
				throw new RuntimeException("No id matching in existing sale point cache");
			}
			throw new RuntimeException("Sale point ID must not be null in order to create credentials for it in DDBB");
		} catch (Exception e) {
			logger.error("Error trying to save new sale point credential in db", e);
			throw new RuntimeException("Error trying to save new sale point credential in db", e);
		}
	}

	/**
     * Ensures an id is received and then checks if credentials exists on DDBB, then it updates on DDBB the credential's amount, reception date and name referencing that existing sale point. It also updates the name of existing sale point in cache
     *
     * @param salePointCredential         A sale point credential.
     */
	@Override
	public SalePointCredential update(SalePointCredential salePointCredential) {
		try {
			if(salePointCredential.getSalePointId() != null) {
				SalePointCredential savedRow = salePointCredentialDao.findById(salePointCredential.getSalePointId().toString())
				        .orElseThrow(() -> new RuntimeException("SalePointCredential not found"));
				savedRow.setAmount(salePointCredential.getAmount());
				savedRow.setSalePointName(salePointCredential.getSalePointName());
				savedRow.setReceptionDate(new Date());
				SalePointCredential savedSalePointCredential = salePointCredentialDao.save(savedRow);				
				
				salePointNameUpdaterService.updateSalePointName(Integer.parseInt(salePointCredential.getSalePointId()), salePointCredential.getSalePointName());
				return savedSalePointCredential;				
			}
			throw new RuntimeException("Sale point ID must not be null in order to update its credentials in DDBB");
		} catch (Exception e) {
			logger.error("Error trying to update sale point credential in db", e);
			throw new RuntimeException("Error trying to update sale point credential in db", e);
		}
	}

	
	/**
     * Deletes sale point credential in DDBB
     *
     * @param salePointId         A sale point credential id.
     */
	@Override
	public Boolean delete(Integer salePointId) {
		try {
			salePointCredentialDao.deleteById(salePointId.toString());
			return Boolean.TRUE;
		} catch (Exception e) {
			logger.error("Error trying to delete sale point credential in db", e);
			throw new RuntimeException("Error trying to delete sale point credential in db", e);
		}
	}

}
