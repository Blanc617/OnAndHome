package com.onandhome.admin.adminProduct;

import java.util.List;

import com.onandhome.admin.adminProduct.entity.Product;
import jakarta.persistence.Column;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Column(name = "category_id")
    private Long categoryId;
	private final ProductService productService;

	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@GetMapping("/products")
	public List<Product> list() {
		return productService.listAll();
	}

	@GetMapping("/products/{id}")
	public Product get(@PathVariable Long id) {
		return productService.get(id);
	}

	@PostMapping("/product/create")
	public Product create(@RequestBody Product p) {
		return productService.save(p);
	}
}
