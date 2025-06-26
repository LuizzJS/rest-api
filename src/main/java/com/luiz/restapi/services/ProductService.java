package com.luiz.restapi.services;

import com.luiz.restapi.entities.Product;
import com.luiz.restapi.enums.ProductStatus;
import com.luiz.restapi.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findByStatus(ProductStatus status) {
        return productRepository.findAll().stream().filter(x -> x.getStatus().equals(status)).collect(Collectors.toList());
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    public void addProduct(Product product) {
        productRepository.save(product);
    }

    public void removeProduct(Long id) {
        productRepository.findById(id).ifPresent(productRepository::delete);
    }

    public void updateProduct(Product product) {
        if (productRepository.findById(product.getId()).isEmpty()) {
            System.out.println("Product not found with id: " + product.getId());
            return;
        }

        productRepository.save(product);
    }
}
