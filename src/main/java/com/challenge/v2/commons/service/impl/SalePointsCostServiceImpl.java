package com.challenge.v2.commons.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.challenge.v2.commons.model.DijkstraNode;
import com.challenge.v2.commons.model.SalePointsCost;
import com.challenge.v2.commons.repository.cache.CacheService;
import com.challenge.v2.commons.service.SalePointsCostService;

@Service
public class SalePointsCostServiceImpl implements SalePointsCostService {
	
	private static final Logger logger = LoggerFactory.getLogger(SalePointsCostServiceImpl.class);
	
	@Autowired
	private CacheService cacheService;
	
	
	/**
     * Ensures both sale point IDs exists on "salePointsCache" and inserts them into "salePointsCostsGraph" with an attached cost or updates an existing entry with a new cost
     *
     * @param SalePointsCost         An object depicting the cost between two sale points.
     */
	@Override
	public Boolean saveCostBetweenSalePoints(SalePointsCost request) {
		try {
			if (!cacheService.getRedisTemplateObject().opsForHash().hasKey("salePointsCache", String.valueOf(request.getOriginSalePointId())) ||
		        !cacheService.getRedisTemplateObject().opsForHash().hasKey("salePointsCache", String.valueOf(request.getDestinationSalePointId()))) {
				throw new IllegalArgumentException("Sale point IDs do not exist in 'salePointsCache'.");
		    }
			cacheService.getRedisTemplateObject().opsForHash().put("salePointsCostsGraph", getKey(request, false), request.getCost());
	        cacheService.getRedisTemplateObject().opsForHash().put("salePointsCostsGraph", getKey(request, true), request.getCost());
			return Boolean.TRUE;			
		} catch (Exception e) {
			logger.error("Error saving Cost Between Sale Points in cache", e);
			throw new RuntimeException("Error saving Cost Between Sale Points in cache", e);
		}		
	}

	
	/**
     * Deletes a connection between two sale points and its cost
     *
     * @param SalePointsCost         An object depicting the cost between two sale points.
     */
	@Override
	public Boolean deleteCostBetweenSalePoints(SalePointsCost request) {
		try {
			cacheService.getRedisTemplateObject().opsForHash().delete("salePointsCostsGraph", getKey(request, false));
			cacheService.getRedisTemplateObject().opsForHash().delete("salePointsCostsGraph", getKey(request, true));
			return Boolean.TRUE;
		} catch (Exception e) {
			logger.error("Error deleting Cost Between Sale Points in cache", e);
			throw new RuntimeException("Error deleting Cost Between Sale Points in cache", e);
		}
	}

	
	/**
     * Returns a list of all possible connections from a sale point
     *
     * @param originSalePointId         A sale point id from which to calculate.
     */
	@Override
	public List<SalePointsCost> getAllCostsToSalePoint(Integer originSalePointId) {
		try {
			if(cacheService.getRedisTemplateObject().opsForHash().hasKey("salePointsCache", String.valueOf(originSalePointId))) {
				Map<DijkstraNode, DijkstraNode> previousNodes = cacheService.getAllRoutesFromSalePoint(originSalePointId);
				List<SalePointsCost> result = new ArrayList<>();

				for (DijkstraNode destinationNode : previousNodes.keySet()) {
				    List<Integer> path = new ArrayList<>();
				    DijkstraNode current = destinationNode;

				    while (previousNodes.containsKey(current)) {
				        path.add(current.getNode());
				        current = previousNodes.get(current);
				    }
				    path.add(originSalePointId);
				    Collections.reverse(path);
				    if(path.size() > 1){
				        int cost = 0;
				        for(int i = 0; i < path.size() - 1; i++){
				            cost += getCostBetween(path.get(i), path.get(i+1));
				        }
				        result.add(new SalePointsCost(originSalePointId, destinationNode.getNode(), (long) cost));
				    }
				}
				result.add(new SalePointsCost(originSalePointId, originSalePointId, 0L));
				return result;								
			}
			throw new RuntimeException("No id matching in existing sale point cache");
		} catch (Exception e) {
			logger.error("Error getting all Cost Between Sale Points from cache", e);
			throw new RuntimeException("Error getting all Cost Between Sale Points from cache", e);
		}
	}

	
	/**
     * Returns the shortest path from sale point A to sale point B and its total cost
     *
     * @param SalePointsCost         An object depicting the cost between two sale points.
     */
	@Override
	public String getMinimumCostToSalePoint(SalePointsCost request) {
		try {
			if(cacheService.getRedisTemplateObject().opsForHash().hasKey("salePointsCache", String.valueOf(request.getOriginSalePointId()))
			&& cacheService.getRedisTemplateObject().opsForHash().hasKey("salePointsCache", String.valueOf(request.getDestinationSalePointId()))) {
				Map<DijkstraNode, DijkstraNode> previousNodes = cacheService.getAllRoutesFromSalePoint(request.getOriginSalePointId());
				DijkstraNode destinationNode = new DijkstraNode(request.getDestinationSalePointId(), 0);

				if (!previousNodes.containsKey(destinationNode) && request.getOriginSalePointId() != request.getDestinationSalePointId()) {
				    return "Destination not reachable.";
				}

				List<Integer> path = new ArrayList<>();
				DijkstraNode current = destinationNode;

				while (previousNodes.containsKey(current)) {
				    path.add(current.getNode());
				    current = previousNodes.get(current);
				}
				path.add(request.getOriginSalePointId());
				Collections.reverse(path);

				StringBuilder result = new StringBuilder();
				int totalCost = 0;
				for (int i = 0; i < path.size(); i++) {
				    int node = path.get(i);
				    String nodeName = (String) cacheService.getRedisTemplateObject().opsForHash().get("salePointsCache", String.valueOf(node));
				    result.append(node).append(": ").append(nodeName);
				    if (i < path.size() - 1) {
				        result.append(" -> ");
				        totalCost += getCostBetween(path.get(i), path.get(i + 1));
				    }
				}
				result.append(". Total cost: ").append(totalCost);
				return result.toString();				
			}
			throw new RuntimeException("No IDs matching in existing sale point cache");			
		} catch (Exception e) {
			logger.error("Error getting minimum Cost Between Sale Points from cache", e);
			throw new RuntimeException("Error getting minimum Cost Between Sale Points from cache", e);
		}
	}

	private String getKey(SalePointsCost request, Boolean reversePath) {
		try {
			if(Boolean.TRUE.equals(reversePath)) {
				return request.getDestinationSalePointId() + "_" + request.getOriginSalePointId();
			}else {
				return request.getOriginSalePointId() + "_" + request.getDestinationSalePointId();			
			}
		} catch (Exception e) {
			logger.error("Error creating key for cache", e);
			throw new RuntimeException("Error creating key for cache", e);
		}
	}
	
	private int getCostBetween(int nodeA, int nodeB) {
        try {
			String key1 = nodeA + "_" + nodeB;
			String key2 = nodeB + "_" + nodeA;
			Object costObj = cacheService.getRedisTemplateObject().opsForHash().get("salePointsCostsGraph", key1);
			if (costObj == null) {
			    costObj = cacheService.getRedisTemplateObject().opsForHash().get("salePointsCostsGraph", key2);
			}
			if (costObj instanceof Integer) {
			    return (Integer) costObj;
			}
			return 0;
		} catch (Exception e) {
			logger.error("Error getting Cost Between from cache", e);
			throw new RuntimeException("Error getting Cost Between from cache", e);
		}
    }
}
