package com.sg.hazelcast.demo.presentation;

import com.sg.hazelcast.demo.model.ProductDTO;
import com.sg.hazelcast.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Set;

@RestController
@RequestMapping(path = "/product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping(
            path = "/first",
            method = RequestMethod.GET
    )
    public ProductDTO getFirstProduct() {
        return productService.getFirstProduct();
    }

    @RequestMapping(
            path = "/dist/query/{referencePrice}",
            method = RequestMethod.GET
    )
    public Collection<ProductDTO> distributedQuery(@PathVariable final int referencePrice) {
        return productService.distributedQuerying(referencePrice);
    }

    @RequestMapping(
            path = "/dist/processor",
            method = RequestMethod.GET
    )
    public Set<String> distributedProcessor() {
        return productService.distributedProcessing();
    }

    @RequestMapping(
            path = "/dist/threads",
            method = RequestMethod.GET
    )
    public Set<String> distributedThreads() {
        return productService.distributedThreadProcessing();
    }
}
