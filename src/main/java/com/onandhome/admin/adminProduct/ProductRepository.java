package com.onandhome.admin.adminProduct;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.onandhome.admin.adminProduct.entity.Product;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    /**
     * 상품명으로 검색
     */
    List<Product> findByNameContainingIgnoreCase(String keyword);
    
    /**
     * 카테고리별 상품 조회
     */
    List<Product> findByCategory(String category);
    
    /**
     * 카테고리명 포함 상품 조회
     */
    List<Product> findByCategoryContaining(String category);
}
