package com.challenge.v2.controllers.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Request<T> {
	
	private T data;
    private String transactionId;
    private LocalDateTime timestamp;

    public Request(T data) {
        this.data = data;
        this.timestamp = LocalDateTime.now();
        this.transactionId = UUID.randomUUID().toString();
    }

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}    

}
