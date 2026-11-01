package com.group.internet_store.service;

import com.group.internet_store.dto.ProductRequest;
import com.group.internet_store.dto.ProductResponse;
import com.group.internet_store.entity.Product;
import com.group.internet_store.entity.User;
import com.group.internet_store.exceptions.ProductNotFound;
import com.group.internet_store.exceptions.ProductsListIsEmpty;
import com.group.internet_store.exceptions.UserNotFound;
import com.group.internet_store.mapper.ProductMapper;
import com.group.internet_store.repository.ProductRepository;
import com.group.internet_store.repository.UserRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ProductMapper productMapper;
    private final EntityManager entityManager;

    public ProductResponse createProduct(String username, ProductRequest productRequest) {
        Optional<User> optUser = userRepository.getOptionalUserByUsername(username);
        User user = optUser.orElseThrow(() -> new UserNotFound("User not found with username " + username));
        Product product = productMapper.productRequestToProduct(productRequest);
        product.setUser(user);
        Product savedProduct = productRepository.save(product);
        return productMapper.productToProductResponse(savedProduct);
    }

    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();

        if (products.isEmpty()) {
            throw new ProductsListIsEmpty("No products found");
        }

        List<ProductResponse> productResponses = products.stream()
                .map(productMapper::productToProductResponse)
                .collect(Collectors.toList()); // .map(product -> productMapper.productToProductResponse(product))
        return productResponses;
    }

    public ProductResponse getProductById(long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            throw new ProductNotFound("Product not found with id " + id);
        }
        return productMapper.productToProductResponse(product.get());
    }

    public ProductResponse getProductByName(String name) {
        Optional<Product> product = productRepository.findByName(name);
        if (product.isEmpty()) {
            throw new ProductNotFound("Product not found with name " + name);
        }
        return productMapper.productToProductResponse(product.get());
    }

    public void deleteProductById(long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFound("Product not found with id: " + id));
        productRepository.delete(product);
    }

    @Transactional
    public ProductResponse updateProduct(long id, ProductRequest productRequest) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFound("Product not found with id: " + id));
        productMapper.updateProductFromRequest(productRequest, product);
        entityManager.flush();
        return productMapper.productToProductResponse(product);
    }
}
