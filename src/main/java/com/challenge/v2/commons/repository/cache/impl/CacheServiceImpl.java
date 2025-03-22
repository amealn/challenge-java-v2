package com.challenge.v2.commons.repository.cache.impl;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.challenge.v2.commons.model.DijkstraNode;
import com.challenge.v2.commons.repository.cache.CacheService;

@Service
public class CacheServiceImpl implements CacheService{
	
    private static final Logger logger = LoggerFactory.getLogger(CacheServiceImpl.class);
    private final HashOperations<String, String, Object> hashOperations;
    private final RedisTemplate<String, Long> redisTemplateLong;
    private final RedisTemplate<String, Object> redisTemplateObject;
    
    @Autowired
    public CacheServiceImpl(RedisTemplate<String, Object> redisTemplateObject, RedisTemplate<String, Long> redisTemplateLong) {
    	this.redisTemplateObject = redisTemplateObject;
        this.redisTemplateLong = redisTemplateLong;
        this.hashOperations = redisTemplateObject.opsForHash();
    }    
    
    @Override
    public RedisTemplate<String, Object> getRedisTemplateObject() {
    	return redisTemplateObject;
    }
    
    @Override
    public RedisTemplate<String, Long> getRedisTemplateLong() {
    	return redisTemplateLong;
    } 
    
    @Override
    public Map<DijkstraNode, DijkstraNode> getAllRoutesFromSalePoint(Integer originSalePointId) {
    	try {
			if (!redisTemplateObject.opsForHash().hasKey("salePointsCache", String.valueOf(originSalePointId))) {
			    throw new IllegalArgumentException("Source sale point does not exist.");
			}
			Map<Integer, Map<Integer, Integer>> adjacencyList = getAdjacencyList();
			Map<DijkstraNode, DijkstraNode> previousNodes = new HashMap<>();
			Map<Integer, Integer> distances = new HashMap<>();
			PriorityQueue<DijkstraNode> queue = new PriorityQueue<>(Comparator.comparingInt(node -> node.getDistance()));
			Set<Integer> visited = new HashSet<>();

			for (int node : adjacencyList.keySet()) {
			    distances.put(node, Integer.MAX_VALUE);
			}
			distances.put(originSalePointId, 0);
			queue.add(new DijkstraNode(originSalePointId, 0));

			while (!queue.isEmpty()) {
			    DijkstraNode current = queue.poll();
			    int currentNode = current.getNode();

			    if (visited.contains(currentNode)) {
			        continue;
			    }
			    visited.add(currentNode);

			    if (!adjacencyList.containsKey(currentNode)) {
			        continue;
			    }

			    for (Map.Entry<Integer, Integer> neighborEntry : adjacencyList.get(currentNode).entrySet()) {
			        int neighbor = neighborEntry.getKey();
			        int cost = neighborEntry.getValue();
			        int newDistance = distances.get(currentNode) + cost;

			        if (newDistance < distances.get(neighbor)) {
			            distances.put(neighbor, newDistance);
			            DijkstraNode newNode = new DijkstraNode(neighbor, newDistance);
			            queue.add(newNode);
			            previousNodes.put(newNode, current);
			        }
			    }
			}
			return previousNodes;
		} catch (Exception e) {
			logger.error("Error getAllRoutesFromSalePoint from cache", e);
			throw new RuntimeException("Error getAllRoutesFromSalePoint from cache", e);
		}
    }
    
    private Map<Integer, Map<Integer, Integer>> getAdjacencyList() {
        try {
			Map<Integer, Map<Integer, Integer>> adjacencyList = new HashMap<>();
			Map<String, Object> salePointsCostsEdges = hashOperations.entries("salePointsCostsGraph");

			for (Map.Entry<String, Object> entry : salePointsCostsEdges.entrySet()) {
			    String[] nodes = entry.getKey().split("_");
			    int originSalePoint = Integer.parseInt(nodes[0]);
			    int destinationSalePoint = Integer.parseInt(nodes[1]);
			    int cost = (Integer) entry.getValue();

			    adjacencyList.computeIfAbsent(originSalePoint, k -> new HashMap<>()).put(destinationSalePoint, cost);
			    adjacencyList.computeIfAbsent(destinationSalePoint, k -> new HashMap<>()).put(originSalePoint, cost);
			}
			return adjacencyList;
		} catch (NumberFormatException e) {
			logger.error("Error getAdjacencyList", e);
			throw new RuntimeException("Error getAdjacencyList", e);
		}
    }

}
