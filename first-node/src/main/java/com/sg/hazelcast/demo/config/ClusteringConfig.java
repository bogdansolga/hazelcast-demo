package com.sg.hazelcast.demo.config;

import com.hazelcast.config.Config;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClusteringConfig {

    @Value("${spring.application.name}")
    private String applicationName;

    @Bean
    public HazelcastInstance hazelcastInstance() {
        final Config config = new Config(applicationName);
        final NetworkConfig networkConfig = config.getNetworkConfig();

        return Hazelcast.newHazelcastInstance(config);
    }
}
