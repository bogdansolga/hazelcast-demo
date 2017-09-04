package com.sg.hazelcast.demo.service;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IExecutorService;
import com.hazelcast.core.IMap;
import com.hazelcast.core.Member;
import com.hazelcast.map.AbstractEntryProcessor;
import com.hazelcast.query.SqlPredicate;
import com.sg.hazelcast.demo.model.ProductDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.sg.hazelcast.demo.config.DistributedObjectsNames.FIRST_PRODUCT_IDENTIFIER;
import static com.sg.hazelcast.demo.config.DistributedObjectsNames.PRODUCTS_MAP;

@Service
public class ProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

    private static final String NO_PRODUCTS_WARN = "There are no products";

    private final HazelcastInstance hazelcastInstance;

    @Autowired
    public ProductService(final HazelcastInstance hazelcastInstance) {
        this.hazelcastInstance = hazelcastInstance;
    }

    public ProductDTO getFirstProduct() {
        final IMap<String, ProductDTO> products = getProducts();

        return Optional.ofNullable(products.get(FIRST_PRODUCT_IDENTIFIER))
                       .orElseThrow(() -> new IllegalArgumentException("There is no product"));
    }

    public Set<ProductDTO> getAllProducts() {
        final IMap<String, ProductDTO> products = getProducts();

        return products.entrySet()
                       .stream()
                       .map(Map.Entry::getValue)
                       .collect(Collectors.toSet());
    }

    public Collection<ProductDTO> distributedQuerying(final int referencePrice) {
        final IMap<String, ProductDTO> products = getProducts();

        final Collection<ProductDTO> affordableProducts = products.values(new SqlPredicate("price <= " + referencePrice));
        Assert.notEmpty(affordableProducts, "There are no products with a price LTE than " + referencePrice);

        LOGGER.info("There are {} affordable products", affordableProducts.size());
        affordableProducts.forEach(product -> LOGGER.debug("{}", affordableProducts));

        return affordableProducts;
    }

    public Set<String> distributedProcessing() {
        final IMap<String, ProductDTO> products = getProducts();

        products.executeOnEntries(new ProductProcessor());

        return products.values()
                       .stream()
                       .map(ProductDTO::getDiscountedPrice)
                       .collect(Collectors.toSet());
    }

    public Set<String> distributedThreadProcessing() {
        final IMap<String, ProductDTO> products = getProducts();

        final IExecutorService executorService = hazelcastInstance.getExecutorService("executor");

        // run the task on all the cluster members
        executorService.executeOnAllMembers(new ProductPrinter("Executed on all members"));

        final Member firstMember = hazelcastInstance.getCluster()
                                                    .getMembers()
                                                    .iterator()
                                                    .next();
        // run the task on a single member
        executorService.executeOnMember(new ProductPrinter("Run on a single member"), firstMember);

        return products.values()
                       .stream()
                       .map(ProductDTO::toString)
                       .collect(Collectors.toSet());
    }

    private IMap<String, ProductDTO> getProducts() {
        final IMap<String, ProductDTO> products = hazelcastInstance.getMap(PRODUCTS_MAP);
        Assert.notNull(products, NO_PRODUCTS_WARN);
        return products;
    }

    private static class ProductProcessor extends AbstractEntryProcessor<String, ProductDTO> {
        @Override
        public Object process(final Map.Entry<String, ProductDTO> entry) {
            final ProductDTO productDTO = entry.getValue();
            productDTO.setDiscountPercentage(25);

            entry.setValue(productDTO);
            return null;
        }
    }

    private static class ProductPrinter implements Runnable, Serializable {

        private final String displayedMessage;

        ProductPrinter(final String displayedMessage) {
            this.displayedMessage = displayedMessage;
        }

        @Override
        public void run() {
            System.out.println("[" + Thread.currentThread().getName() + "] " + displayedMessage);
        }
    }
}
