package com.onandhome.product;

import java.util.List;

import com.onandhome.product.entity.Product;
import org.springframework.stereotype.Service;


@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> listAll() {
        return productRepository.findAll();
    }

    public Product get(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product save(Product p) {
        return productRepository.save(p);
    }
}