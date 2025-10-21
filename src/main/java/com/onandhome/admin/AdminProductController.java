package com.onandhome.admin;

import com.onandhome.product.ProductService;
import com.onandhome.product.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 관리자 상품 관리 기능을 위한 REST Controller (AdminProductController)
 * - ProductService를 사용하여 상품 CRUD를 수행합니다.
 */
@RestController
@RequestMapping("/api/admin/product") // 상품 관리를 위한 자원 경로
@RequiredArgsConstructor
public class AdminProductController {

    // 💡 ProductService를 사용하도록 변경
    private final ProductService productService;

    // 💡 ProductController의 listAll()과 동일하지만, 관리자용으로 분리된 엔드포인트입니다.
    /**
     * 1. 상품 목록 조회 (list)
     * GET /api/admin/product/list
     */
    @GetMapping("/list")
    public ResponseEntity<List<Product>> list() {
        List<Product> productList = productService.listAll();
        return ResponseEntity.ok(productList);
    }

    /**
     * 2. 상품 등록 (create)
     * POST /api/admin/product/create
     * @RequestBody Product p: 상품 정보 (name, description, price, stock 등)
     */
    @PostMapping("/create")
    public ResponseEntity<Product> create(@RequestBody Product p) {
        try {
            // productService.save(p) 호출
            Product createdProduct = productService.save(p);
            // HTTP 상태 코드 201 Created 반환
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
        } catch (Exception e) {
            // 예외 처리 (데이터 유효성 검사 등은 Service/Validation 레이어에서 수행하는 것이 좋음)
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 3. 상품 수정 페이지 데이터 조회 (editView)
     * GET /api/admin/product/edit/{id}
     * @return Product: 특정 상품의 상세 정보
     */
    @GetMapping("/edit/{id}")
    public ResponseEntity<Product> editView(@PathVariable("id") Long productId) {
        // productService.get(id) 호출
        Product product = productService.get(productId);
        if (product != null) {
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 4. 상품 수정 처리 (edit)
     * PUT /api/admin/product/edit/{id}
     * @RequestBody Product p: 수정할 상품 정보 (ID 포함)
     */
    @PutMapping("/edit/{id}")
    public ResponseEntity<Product> edit(@PathVariable("id") Long productId,
                                        @RequestBody Product p) {
        // 경로 변수의 ID와 요청 본문의 ID가 일치하는지 확인하는 로직이 필요할 수 있습니다.
        if (!productId.equals(p.getId())) {
            return ResponseEntity.badRequest().build();
        }

        try {
            // productService.save(p)는 ID가 있으면 update, 없으면 insert를 수행합니다.
            Product updatedProduct = productService.save(p);
            return ResponseEntity.ok(updatedProduct);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 5. 상품 삭제 (delete)
     * DELETE /api/admin/product/delete/{id}
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long productId) {
        // 1. 상품 존재 여부 확인
        Product existingProduct = productService.get(productId);

        if (existingProduct == null) {
            // 상품이 존재하지 않으면 404 Not Found 반환
            return ResponseEntity.notFound().build();
        }

        try {
            // 2. 상품 삭제
            productService.delete(productId);

            // HTTP 상태 코드 204 No Content 반환 (삭제 성공)
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            // 데이터베이스 오류 등 예상치 못한 예외 발생 시 500 Internal Server Error 반환
            // 실제 애플리케이션에서는 더 구체적인 예외 처리 로직이 필요합니다.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}