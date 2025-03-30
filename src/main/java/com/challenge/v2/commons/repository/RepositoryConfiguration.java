package com.challenge.v2.commons.repository;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@EnableMongoRepositories(basePackages = {
	    "com.challenge.v2.commons.repository.mongo",
	    "com.challenge.v2.security.repository"
})
@EnableRedisRepositories(
	    basePackages = "com.challenge.v2.commons.repository.cache",
	    excludeFilters = @org.springframework.context.annotation.ComponentScan.Filter(
	        type = FilterType.ASSIGNABLE_TYPE,
	        classes = com.challenge.v2.commons.repository.mongo.dao.SalePointCredentialDao.class
	    )
)
public class RepositoryConfiguration {

}
