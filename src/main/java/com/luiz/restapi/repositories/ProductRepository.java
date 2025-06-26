package com.luiz.restapi.repositories;

import com.luiz.restapi.entities.Product;
import com.luiz.restapi.enums.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
