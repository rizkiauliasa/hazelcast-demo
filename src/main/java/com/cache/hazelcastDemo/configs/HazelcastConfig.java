package com.cache.hazelcastDemo.configs;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HazelcastConfig {
    @Bean
    public HazelcastInstance hazelcastInstance() throws Exception {
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.setClusterName("dev");
        clientConfig.getNetworkConfig()
                .setSmartRouting(true)
                .setRedoOperation(true)
                .addAddress("hazelcast-member1:5701", "hazelcast-member2:5701");

        return HazelcastClient.newHazelcastClient(clientConfig);
    }
}

