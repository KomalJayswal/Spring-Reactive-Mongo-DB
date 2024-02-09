package net.learning.springreactivemongocurdpoc.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * Config file for all the configuration code
 */
@Configuration
@RequiredArgsConstructor
public class Config {

    private final ApplicationContext applicationContext;

    @Value("${spring.data.mongodb.uri}")
    private String connectionString;

    @Value("${spring.data.mongodb.read-timeout:5000}")
    private int connectionTimeout;

    @Value("${spring.data.mongodb.connection-timeout:5000}")
    private int readTimeout;

    @Value("${spring.data.mongodb.server-selection-timeout:3000}")
    private int serverSelectionTimeout;

    /**
     * Modify default mongodb timeout from 60 secs to 10 secs
     */
    @Bean
    public MongoClient mongoClient() {
        return MongoClients.create(
                MongoClientSettings.builder()
                        .applyToSocketSettings(
                                builder ->
                                        builder
                                                .connectTimeout(connectionTimeout, TimeUnit.MILLISECONDS)
                                                .readTimeout(readTimeout, TimeUnit.MILLISECONDS))
                        .applyToClusterSettings(
                                builder ->
                                        builder.serverSelectionTimeout(serverSelectionTimeout, TimeUnit.MILLISECONDS))
                        .applyConnectionString(new ConnectionString(connectionString))
                        .build());
    }
}