package com.challenge.v2.commons.model;

import java.util.Objects;

public class DijkstraNode {
	
	private Integer node;
	private Integer distance;

    public DijkstraNode(Integer node, Integer distance) {
        this.node = node;
        this.distance = distance;
    }

	public Integer getNode() {
		return node;
	}

	public void setNode(Integer node) {
		this.node = node;
	}

	public Integer getDistance() {
		return distance;
	}

	public void setDistance(Integer distance) {
		this.distance = distance;
	}    
    
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DijkstraNode that = (DijkstraNode) o;
        return node == that.node;
    }

    @Override
    public int hashCode() {
        return Objects.hash(node);
    }

}
