package com.challenge.v2.commons.service;

import java.util.List;

public interface GenericSalePointService<T, Id> {
	
	List<T> getAll();
	
    T save(T entity);
    
    T update(T entity);
    
    Boolean delete(Id id);

}
