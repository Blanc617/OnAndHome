package com.onandhome.admin;

import com.onandhome.product.ProductRepository;
import com.onandhome.product.entity.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AdminProductService { // 💡 클래스 이름은 AdminProductService 유지

    // 💡 상품 데이터 접근을 위한 ProductRepository 주입
    private final ProductRepository productRepository;

    // --- CRUD 기능 (상품 관리) ---

    /**
     * 1. 모든 상품 목록을 조회합니다. (Read All)
     */
    @Transactional(readOnly = true)
    public List<Product> findAllProducts() {
        // ProductRepository의 findAll() 메서드 사용
        return productRepository.findAll();
    }

    /**
     * 2. 새로운 상품을 등록합니다. (Create)
     * @param product 등록할 상품 엔티티
     * @return 등록된 상품 엔티티
     */
    public Product registerProduct(Product product) {
        // 상품 등록 시 유효성 검사 로직 (예: 상품명 중복 확인 등)이 추가될 수 있습니다.

        Product savedProduct = productRepository.save(product);

        log.info("새 상품 등록: {} (ID: {})", product.getName(), savedProduct.getId());
        return savedProduct;
    }

    /**
     * 3. 특정 ID로 상품을 조회합니다. (Read Detail)
     */
    @Transactional(readOnly = true)
    public Optional<Product> getProductById(Long productId) {
        return productRepository.findById(productId);
    }

    /**
     * 4. 기존 상품 정보를 업데이트합니다. (Update)
     */
    public Product updateProduct(Long productId, Product productDetails) {
        // 1. 기존 상품을 찾습니다.
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품 ID입니다: " + productId));

        // 2. 전달된 데이터로 필드를 업데이트합니다. (ID는 경로 변수로 이미 확인)
        existingProduct.setName(productDetails.getName());
        existingProduct.setDescription(productDetails.getDescription());
        existingProduct.setPrice(productDetails.getPrice());
        existingProduct.setStock(productDetails.getStock());

        // 3. 업데이트된 상품을 저장합니다.
        Product updatedProduct = productRepository.save(existingProduct);

        log.info("상품 정보 업데이트: {} (ID: {})", updatedProduct.getName(), updatedProduct.getId());
        return updatedProduct;
    }

    /**
     * 5. 특정 ID의 상품을 삭제합니다. (Delete)
     */
    public void deleteProduct(Long productId) {
        if (!productRepository.existsById(productId)) {
            throw new IllegalArgumentException("존재하지 않는 상품 ID입니다: " + productId);
        }

        // 삭제 로직 수행
        productRepository.deleteById(productId);
        log.info("상품 삭제: ID {}", productId);
    }
}