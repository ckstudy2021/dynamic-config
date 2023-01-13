package com.ck.cs.config_service.config.mongo;

import com.ck.cs.config_service.constants.Constants;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.util.StringUtils;

import java.util.function.Supplier;

@Slf4j
@Configuration
public class MongoConfig {
    private Boolean enabled;
    private String host;
    private String database;
    private String user;
    private String password;

    public MongoConfig(@Value("${spring.data.mongodb.enabled:false}") Boolean enabled
            , @Value("${spring.data.mongodb.host:#{null}}") String host
            , @Value("${spring.data.mongodb.database:#{null}}") String database
            , @Value("${spring.data.mongodb.user:#{null}}") String user
            , @Value("${spring.data.mongodb.password:#{''}}") String password) {
        this.enabled = enabled;
        this.host = host;
        this.database = database;
        this.user = user;
        this.password = password;
    }

    @Bean(Constants.Application.Beans.MONGO_TEMPLATE)
    public Supplier<MongoTemplate> mongoTemplateSupplier() throws Exception {
        if (!this.enabled) {
            log.info("spring.data.mongo.enabled either not provided or is false, hence not connecting to mongo!!");
            return () -> null;
        }
        MongoClient mongoClient = mongoClient();
        return () -> new MongoTemplate(mongoClient, this.database);
    }

    private MongoClient mongoClient() throws Exception {
        ConnectionString connectionString = new ConnectionString(getConnectionString());
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();

        return MongoClients.create(mongoClientSettings);
    }

    private String getConnectionString() throws Exception {
        if (StringUtils.hasText(host)
                && StringUtils.hasText(database)) {
            if (StringUtils.hasText(password)) {
                return String.format("mongodb://%s:%s@%s/%s", user, password, host, database);
            }
            return String.format("mongodb://%s@%s/%s", user, host, database);
        }
        throw new Exception("mongo minimum connection requirement is not met!!");
    }
}
