package com.sg.hazelcast.demo.presentation;

import com.sg.hazelcast.demo.model.ProductDTO;
import com.sg.hazelcast.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
}
