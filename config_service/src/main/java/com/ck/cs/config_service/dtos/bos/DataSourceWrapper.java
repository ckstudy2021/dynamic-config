package com.ck.cs.config_service.dtos.bos;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.kafka.core.KafkaTemplate;

@Slf4j
@Data
@SuperBuilder
@NoArgsConstructor
public class DataSourceWrapper {
    private MongoTemplate mongoTemplate;
    private RedissonClient redissonClient;
    private KafkaTemplate<String, String> kafkaTemplate;
}
