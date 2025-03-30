package com.challenge.v2.security.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.challenge.v2.security.model.User;

@Repository
public interface LoginDao extends MongoRepository<User, String> {
	
	public Optional<User> findByUsername(String username);
	
	public Optional<User> findByEmail(String email);

}
