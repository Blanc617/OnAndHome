package com.onandhome.admin.adminProduct;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.onandhome.admin.adminProduct.entity.Product;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByNameContainingIgnoreCase(String keyword);
    // ⚠️ 아래 메서드 주석처리 또는 삭제
    // List<Product> findByCategoryId(Long categoryId);
}

