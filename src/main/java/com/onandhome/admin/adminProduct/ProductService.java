package com.onandhome.admin.adminProduct;

import java.util.List;
import java.util.Optional;

import com.onandhome.admin.adminProduct.dto.ProductDTO;
import com.onandhome.admin.adminProduct.entity.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ProductService {
    private final ProductRepository productRepository;

    /**
     * 모든 상품 조회
     */
    public List<Product> listAll() {
        return productRepository.findAll();
    }

    /**
     * ID로 상품 조회
     */
    @Transactional(readOnly = true)
    public Optional<ProductDTO> getById(Long id) {
        return productRepository.findById(id)
                .map(ProductDTO::fromEntity);
    }

    /**
     * 상품 생성 (DTO 사용)
     */
    public ProductDTO create(ProductDTO productDTO) {
        if (productDTO.getName() == null || productDTO.getName().isEmpty()) {
            throw new IllegalArgumentException("상품명은 필수입니다.");
        }

        if (productDTO.getPrice() < 0) {
            throw new IllegalArgumentException("가격은 0 이상이어야 합니다.");
        }

        if (productDTO.getStock() < 0) {
            throw new IllegalArgumentException("재고는 0 이상이어야 합니다.");
        }

        Product product = productDTO.toEntity();
        Product savedProduct = productRepository.save(product);

        log.info("상품 생성: {} (ID: {})", productDTO.getName(), savedProduct.getId());
        return ProductDTO.fromEntity(savedProduct);
    }

    /**
     * 상품 수정
     */
    public ProductDTO update(Long id, ProductDTO productDTO) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));

        if (productDTO.getName() != null && !productDTO.getName().isEmpty()) {
            product.setName(productDTO.getName());
        }
        if (productDTO.getDescription() != null) {
            product.setDescription(productDTO.getDescription());
        }
        if (productDTO.getPrice() >= 0) {
            product.setPrice(productDTO.getPrice());
        }
        if (productDTO.getStock() >= 0) {
            product.setStock(productDTO.getStock());
        }
        if (productDTO.getThumbnailImage() != null) {
            product.setThumbnailImage(productDTO.getThumbnailImage());
        }
        if (productDTO.getDetailImage() != null) {
            product.setDetailImage(productDTO.getDetailImage());
        }

        Product updatedProduct = productRepository.save(product);
        log.info("상품 수정: {} (ID: {})", id, updatedProduct.getName());

        return ProductDTO.fromEntity(updatedProduct);
    }

    /**
     * 상품 삭제
     */
    public void delete(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));

        productRepository.delete(product);
        log.info("상품 삭제: {} (ID: {})", product.getName(), id);
    }

    /**
     * 상품 검색
     */
    @Transactional(readOnly = true)
    public List<Product> search(String keyword) {
        return productRepository.findByNameContainingIgnoreCase(keyword);
    }

    /**
     * 기존 save 메서드 유지 (하위호환성)
     */
    public Product save(Product p) {
        return productRepository.save(p);
    }
}
