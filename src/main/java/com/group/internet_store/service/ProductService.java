package com.group.internet_store.service;

import com.group.internet_store.dto.ProductRequest;
import com.group.internet_store.dto.ProductResponse;
import com.group.internet_store.entity.Product;
import com.group.internet_store.mapper.ProductMapper;
import com.group.internet_store.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = productMapper.productRequestToProduct(productRequest);
        Product savedProduct = productRepository.save(product);
        return productMapper.productToProductResponse(savedProduct);
    }

    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductResponse> productResponses = products.stream()
                .map(productMapper::productToProductResponse)
                .collect(Collectors.toList()); // .map(product -> productMapper.productToProductResponse(product))
        return productResponses;
    }

    public ProductResponse getProductById(long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            throw new RuntimeException("Product not found");
        }
        return productMapper.productToProductResponse(product.get());
    }

    public void deleteProductById(long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        productRepository.delete(product);
    }
}
