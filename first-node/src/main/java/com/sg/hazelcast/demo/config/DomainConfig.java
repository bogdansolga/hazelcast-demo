package com.sg.hazelcast.demo.config;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.sg.hazelcast.demo.model.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

import static com.sg.hazelcast.demo.config.DistributedObjectsNames.*;

@Configuration
@AutoConfigureAfter(ClusteringConfig.class)
public class DomainConfig {

    private final HazelcastInstance hazelcastInstance;

    @Autowired
    public DomainConfig(final HazelcastInstance hazelcastInstance) {
        this.hazelcastInstance = hazelcastInstance;
    }

    @Async
    @EventListener(ApplicationReadyEvent.class)
    public void initializeProducts() {
        final IMap<String, ProductDTO> productsMap = hazelcastInstance.getMap(PRODUCTS_MAP);
        productsMap.put(FIRST_PRODUCT_IDENTIFIER,   new ProductDTO(1, "Tablet",  200));
        productsMap.put(SECOND_PRODUCT_IDENTIFIER,  new ProductDTO(2, "Phone",   300));
        productsMap.put(THIRD_PRODUCT_IDENTIFIER,   new ProductDTO(3, "Laptop", 1000));
    }
}
