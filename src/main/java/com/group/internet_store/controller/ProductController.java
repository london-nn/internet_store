package com.group.internet_store.controller;

import com.group.internet_store.dto.ProductRequest;
import com.group.internet_store.dto.ProductResponse;
import com.group.internet_store.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest) {
        ProductResponse productResponse = productService.createProduct(productRequest);
        return ResponseEntity.ok().body(productResponse);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getProducts() {
        List<ProductResponse> products = productService.getAllProducts();
        return ResponseEntity.ok().body(products);
    }

    @GetMapping
    public ResponseEntity<ProductResponse> getProductById(@RequestParam Long id) {
        ProductResponse productResponse = productService.getProductById(id);
        return ResponseEntity.ok().body(productResponse);
    }

    @DeleteMapping
    public ResponseEntity deleteProduct(@RequestParam Long id) {
        productService.deleteProductById(id);
        return ResponseEntity.ok().build();
    }

}
