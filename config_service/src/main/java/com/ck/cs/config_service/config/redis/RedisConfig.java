package com.ck.cs.config_service.config.redis;

import com.ck.cs.config_service.constants.Constants;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.util.function.Supplier;

@Slf4j
@Configuration
public class RedisConfig {
    private Boolean enabled;
    private String host;
    private String user;
    private String password;

    public RedisConfig(@Value("${spring.data.redis.enabled:false}") Boolean enabled
            , @Value("${spring.data.redis.host:#{null}}") String host
            , @Value("${spring.data.redis.user:#{null}}") String user
            , @Value("${spring.data.redis.password:#{''}}") String password) {
        this.enabled = enabled;
        this.host = host;
        this.user = user;
        this.password = password;
    }

    @Bean(Constants.Application.Beans.REDIS_CLIENT)
    public Supplier<RedissonClient> redissonClientSupplier() throws Exception {
        if (!this.enabled) {
            log.info("spring.data.redis.enabled either not provided or is false, hence not connecting to redissonClient!!");
            return () -> null;
        }
        Config config = getRedissonConfig();
        RedissonClient redissonClient = Redisson.create(config);
        return () -> redissonClient;
    }

    private Config getRedissonConfig() throws Exception {
        if (!StringUtils.hasText(host)) {
            throw new Exception("redis minimum connection requirement is not met!!");
        }
        Config config = new Config();
        SingleServerConfig singleServerConfig = config.useSingleServer();
        singleServerConfig.setAddress(host);
        if (StringUtils.hasText(user)) {
            singleServerConfig.setUsername(user);
        }
        if (StringUtils.hasText(password)) {
            singleServerConfig.setPassword(password);
        }
        return config;
    }
}
