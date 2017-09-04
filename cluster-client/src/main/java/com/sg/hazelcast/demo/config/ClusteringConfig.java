package com.sg.hazelcast.demo.config;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.config.GroupConfig;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClusteringConfig {

    @Bean
    public HazelcastInstance hazelcastInstance() {
        final ClientConfig config = new ClientConfig();

        final GroupConfig groupConfig = config.getGroupConfig();
        groupConfig.setName("dev");
        groupConfig.setPassword("dev-pass");

        return HazelcastClient.newHazelcastClient(config);
    }
}