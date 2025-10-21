package com.onandhome.admin.adminProduct;

import com.onandhome.product.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * 관리자 상품 관리 기능을 위한 REST Controller (AdminProductController)
 * - AdminProductService를 사용하여 상품 CRUD를 수행합니다. (의존성 변경)
 * - RESTful API 표준에 따라 경로를 정리했습니다.
 */
@RestController
@RequestMapping("/api/admin/product") // 상품 자원(Collection) 경로
@RequiredArgsConstructor
public class AdminProductController {

    // 💡 ProductService 대신 새로 만든 AdminProductService를 주입합니다.
    private final AdminProductService adminProductService;

    // 1. 상품 목록 조회 (list)
    // GET /api/admin/product
    @GetMapping // 경로: /api/admin/product (콜렉션 전체 조회)
    public ResponseEntity<List<Product>> list() {
        // 💡 findAllProducts() 메서드 사용
        List<Product> productList = adminProductService.findAllProducts();
        return ResponseEntity.ok(productList);
    }

    // 2. 상품 등록 (create)
    // POST /api/admin/product
    @PostMapping // 경로: /api/admin/product (새로운 자원 생성)
    public ResponseEntity<Product> create(@RequestBody Product p) {
        try {
            // 💡 registerProduct() 메서드 사용
            Product createdProduct = adminProductService.registerProduct(p);
            // HTTP 상태 코드 201 Created 반환
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
        } catch (Exception e) {
            // Bad Request
            return ResponseEntity.badRequest().build();
        }
    }
// ----------------- 상품 생성 방법 도움 / 확인 못 함 -------------------

    // 3. 특정 상품 조회 (detail/editView)
    // GET /api/admin/product/{id}
    @GetMapping("/{id}") // 경로: /api/admin/product/{id} (특정 자원 조회)
    public ResponseEntity<Product> detailView(@PathVariable("id") Long productId) {
        // 💡 Optional을 반환하는 getProductById() 메서드 사용 및 처리
        Optional<Product> productOpt = adminProductService.getProductById(productId);

        if (productOpt.isPresent()) {
            return ResponseEntity.ok(productOpt.get());
        } else {
            // 404 Not Found 반환
            return ResponseEntity.notFound().build();
        }
    }

    // 4. 상품 수정 처리 (edit)
    // PUT /api/admin/product/{id}
    @PutMapping("/{id}") // 경로: /api/admin/product/{id} (특정 자원 수정)
    public ResponseEntity<Product> edit(@PathVariable("id") Long productId,
                                        @RequestBody Product p) {
        // 경로 ID와 요청 본문 ID가 일치해야 함
        if (p.getId() == null || !productId.equals(p.getId())) {
            return ResponseEntity.badRequest().build();
        }

        try {
            // 💡 updateProduct() 메서드 사용
            Product updatedProduct = adminProductService.updateProduct(productId, p);
            return ResponseEntity.ok(updatedProduct);
        } catch (IllegalArgumentException e) {
            // Service에서 던진 '존재하지 않는 ID' 예외를 404로 처리
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // 5. 상품 삭제 (delete)
    // DELETE /api/admin/product/{id}
    @DeleteMapping("/{id}") // 경로: /api/admin/product/{id} (특정 자원 삭제)
    public ResponseEntity<Void> delete(@PathVariable("id") Long productId) {
        try {
            // 💡 deleteProduct() 메서드 사용
            adminProductService.deleteProduct(productId);
            // HTTP 상태 코드 204 No Content 반환 (삭제 성공)
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            // Service에서 던진 '존재하지 않는 ID' 예외를 404로 처리
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
