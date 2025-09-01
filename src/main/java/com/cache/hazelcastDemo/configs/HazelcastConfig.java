package com.cache.hazelcastDemo.configs;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.config.EvictionConfig;
import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.InMemoryFormat;
import com.hazelcast.config.NearCacheConfig;
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

        NearCacheConfig booksNearCache = new NearCacheConfig("books")
                .setInMemoryFormat(InMemoryFormat.BINARY)
                .setInvalidateOnChange(true)
                .setTimeToLiveSeconds(3600)
                .setMaxIdleSeconds(600)
                .setEvictionConfig(new EvictionConfig()
                        .setSize(1000)
                        .setMaxSizePolicy(EvictionConfig.DEFAULT_MAX_SIZE_POLICY.ENTRY_COUNT)
                        .setEvictionPolicy(EvictionPolicy.LRU));

        NearCacheConfig authorsNearCache = new NearCacheConfig("authors")
                .setInMemoryFormat(InMemoryFormat.BINARY)
                .setInvalidateOnChange(true)
                .setTimeToLiveSeconds(3600)
                .setMaxIdleSeconds(600)
                .setEvictionConfig(new EvictionConfig()
                        .setSize(500)
                        .setMaxSizePolicy(EvictionConfig.DEFAULT_MAX_SIZE_POLICY.ENTRY_COUNT)
                        .setEvictionPolicy(EvictionPolicy.LRU));

        clientConfig.addNearCacheConfig(booksNearCache);
        clientConfig.addNearCacheConfig(authorsNearCache);

        return HazelcastClient.newHazelcastClient(clientConfig);
    }
}

