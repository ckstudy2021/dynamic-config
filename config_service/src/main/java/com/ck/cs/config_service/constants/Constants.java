package com.ck.cs.config_service.constants;

public interface Constants {
    interface Application {
        interface Beans {
            String MONGO_TEMPLATE = "mongo_template";
            String REDIS_CLIENT = "redis_client";
            String KAFKA_TEMPLATE = "kafka_template";
            String DATA_SOURCE_WRAPPER = "application_data_source_wrapper";
        }
    }
}
