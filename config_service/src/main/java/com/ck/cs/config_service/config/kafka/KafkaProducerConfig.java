package com.ck.cs.config_service.config.kafka;

import com.ck.cs.config_service.constants.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@Slf4j
@Configuration
public class KafkaProducerConfig {
    private Boolean enabled;
    private String boostStrapAddress;

    public KafkaProducerConfig(@Value("${spring.pub_sub.kafka.enabled:false}") Boolean enabled
            , @Value("${spring.pub_sub.kafka.bootstrap_address:false}") String boostStrapAddress) {
        this.enabled = enabled;
        this.boostStrapAddress = boostStrapAddress;
    }

    @Bean(Constants.Application.Beans.KAFKA_TEMPLATE)
    public Supplier<KafkaTemplate<String, String>> getKafkaTemplateSupplier() throws Exception {
        if (!this.enabled) {
            log.info("spring.pub_sub.kafka.enabled either not provided or is false, hence not connecting to mongo!!");
            return () -> null;
        }
        KafkaTemplate<String, String> kafkaTemplate = new KafkaTemplate<>(producerFactory());
        return () -> kafkaTemplate;
    }

    private ProducerFactory<String, String> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, boostStrapAddress);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        return new DefaultKafkaProducerFactory<>(configProps);
    }
}
