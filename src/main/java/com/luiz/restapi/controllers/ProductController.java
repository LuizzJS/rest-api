package com.luiz.restapi.controllers;

import com.luiz.restapi.entities.Product;
import com.luiz.restapi.entities.Response;
import com.luiz.restapi.enums.ProductStatus;
import com.luiz.restapi.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // C - CREATE
    @PostMapping("/add")
    public ResponseEntity<Response> addProduct(@RequestBody Product product) {
        try {
            productService.addProduct(product);
            return ResponseEntity.ok(new Response("Product added successfully", true));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Response("Failed to add product: " + e.getMessage(), false));
        }
    }


    // R - READ
    @GetMapping
    public List<Product> findAll(@RequestParam(required = false) String status) {
        if (status == null) {
            return productService.findAll();
        }

        try {
            ProductStatus productStatus = ProductStatus.valueOf(status.trim().toUpperCase());

            return productService.findByStatus(productStatus);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid status value: " + status);
        }
    }

    // U - UPDATE
    @PutMapping("/update/{id}")
    public ResponseEntity<Response> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        Product existingProduct = productService.findById(id);
        if (existingProduct == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }

        existingProduct.setName(product.getName());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setStatus(product.getStatus());
        existingProduct.setDescription(product.getDescription());

        productService.updateProduct(existingProduct);

        return ResponseEntity.ok(new Response("Product updated successfully", true));
    }


    // D - DELETE
    @DeleteMapping("/remove/{id}")
    public ResponseEntity<Response> removeProduct(@PathVariable Long id) {
        Product product = productService.findById(id);

        if (product == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }

        productService.removeProduct(id);
        return ResponseEntity.ok(new Response("Product deleted successfully", true));
    }

}
