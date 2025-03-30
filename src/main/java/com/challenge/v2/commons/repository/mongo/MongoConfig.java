package com.challenge.v2.commons.repository.mongo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Configuration
public class MongoConfig {

	@Value("${MONGO_CONNECTION_STRING}")
    private String mongoUri;

    @Bean
    public MongoClient mongoClient() throws Exception {        
        ConnectionString connectionString = new ConnectionString(mongoUri);
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
            .applyConnectionString(connectionString)
            .build();
        return MongoClients.create(mongoClientSettings);
    }

    @Bean
    public MongoDatabaseFactory mongoDatabaseFactory() throws Exception {
    	return new SimpleMongoClientDatabaseFactory(mongoUri);
    }

    @Bean
    public MongoTemplate mongoTemplate(MongoDatabaseFactory mongoDatabaseFactory) throws Exception {
        return new MongoTemplate(mongoDatabaseFactory);
    }
}