package com.dripstock.service;

import com.dripstock.model.Product;
import com.dripstock.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com id: " + id));
    }

    public List<Product> findByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    public List<Product> searchByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public Product update(Long id, Product productData) {
        Product existing = findById(id);
        existing.setName(productData.getName());
        existing.setDescription(productData.getDescription());
        existing.setPrice(productData.getPrice());
        existing.setStock(productData.getStock());
        existing.setCategory(productData.getCategory());
        existing.setImageUrl(productData.getImageUrl());
        return productRepository.save(existing);
    }

    public void delete(Long id) {
        Product existing = findById(id);
        productRepository.delete(existing);
    }
}