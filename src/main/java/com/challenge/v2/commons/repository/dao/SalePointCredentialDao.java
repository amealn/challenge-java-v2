package com.challenge.v2.commons.repository.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.challenge.v2.commons.model.SalePointCredential;

@Repository
public interface SalePointCredentialDao extends MongoRepository<SalePointCredential, String> {

}
