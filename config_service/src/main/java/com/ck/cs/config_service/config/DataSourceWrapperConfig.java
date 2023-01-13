package com.ck.cs.config_service.config;

import com.ck.cs.config_service.constants.Constants;
import com.ck.cs.config_service.dtos.bos.DataSourceWrapper;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.function.Supplier;

@Slf4j
@Configuration
public class DataSourceWrapperConfig {
    private MongoTemplate mongoTemplate;
    private RedissonClient redissonClient;
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public DataSourceWrapperConfig(@Qualifier(Constants.Application.Beans.MONGO_TEMPLATE) Supplier<MongoTemplate> mongoTemplateSupplier
            , @Qualifier(Constants.Application.Beans.REDIS_CLIENT) Supplier<RedissonClient> redissonClientSupplier
            , @Qualifier(Constants.Application.Beans.KAFKA_TEMPLATE) Supplier<KafkaTemplate<String, String>> kafkaTemplateSupplier) {
        this.mongoTemplate = mongoTemplateSupplier.get();
        this.redissonClient = redissonClientSupplier.get();
        this.kafkaTemplate = kafkaTemplateSupplier.get();
    }

    @Bean(Constants.Application.Beans.DATA_SOURCE_WRAPPER)
    public DataSourceWrapper getDataSourceWrapper() {
        return DataSourceWrapper.builder()
                .mongoTemplate(this.mongoTemplate)
                .redissonClient(this.redissonClient)
                .kafkaTemplate(this.kafkaTemplate)
                .build();
    }
}
