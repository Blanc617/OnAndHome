package com.onandhome.admin.adminProduct;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.onandhome.admin.adminProduct.dto.ProductDTO;
import com.onandhome.admin.adminProduct.entity.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

	private final ProductService productService;

	/**
	 * 모든 상품 조회
	 * GET /api/products/list
	 */
	@GetMapping("/list")
	public ResponseEntity<Map<String, Object>> list() {
		Map<String, Object> response = new HashMap<>();
		try {
			List<Product> products = productService.listAll();
			response.put("success", true);
			response.put("data", products);
			response.put("count", products.size());
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			log.error("상품 목록 조회 중 오류: {}", e.getMessage());
			response.put("success", false);
			response.put("message", "상품 목록 조회 중 오류가 발생했습니다.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	/**
	 * ID로 상품 조회
	 * GET /api/products/{id}
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Map<String, Object>> get(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		try {
			Optional<ProductDTO> product = productService.getById(id);
			if (product.isPresent()) {
				response.put("success", true);
				response.put("data", product.get());
				return ResponseEntity.ok(response);
			} else {
				response.put("success", false);
				response.put("message", "존재하지 않는 상품입니다.");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
			}
		} catch (Exception e) {
			log.error("상품 조회 중 오류: {}", e.getMessage());
			response.put("success", false);
			response.put("message", "상품 조회 중 오류가 발생했습니다.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	/**
	 * 상품 생성 (DTO 사용) ✅ Talent 테스트용
	 * POST /api/products/create
	 * 
	 * 요청 예시 (JSON):
	 * {
	 *     "name": "무선 이어폰",
	 *     "description": "고음질 무선 이어폰입니다.",
	 *     "price": 59900,
	 *     "stock": 100,
	 *     "thumbnailImage": "https://example.com/thumbnail.jpg",
	 *     "detailImage": "https://example.com/detail.jpg"
	 * }
	 */
	@PostMapping("/create")
	public ResponseEntity<Map<String, Object>> create(@RequestBody ProductDTO productDTO) {
		Map<String, Object> response = new HashMap<>();
		try {
			log.info("상품 생성 요청: {}", productDTO.getName());
			
			ProductDTO createdProduct = productService.create(productDTO);
			response.put("success", true);
			response.put("message", "상품이 성공적으로 생성되었습니다.");
			response.put("data", createdProduct);
			
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		} catch (IllegalArgumentException e) {
			log.warn("상품 생성 실패: {}", e.getMessage());
			response.put("success", false);
			response.put("message", e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception e) {
			log.error("상품 생성 중 오류: {}", e.getMessage());
			response.put("success", false);
			response.put("message", "상품 생성 중 오류가 발생했습니다.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	/**
	 * 상품 수정
	 * PUT /api/products/{id}
	 * 
	 * 요청 예시 (JSON):
	 * {
	 *     "name": "무선 이어폰 프로",
	 *     "description": "고음질 무선 이어폰 프로 버전입니다.",
	 *     "price": 79900,
	 *     "stock": 150,
	 *     "thumbnailImage": "https://example.com/thumbnail_pro.jpg",
	 *     "detailImage": "https://example.com/detail_pro.jpg"
	 * }
	 */
	@PutMapping("/{id}")
	public ResponseEntity<Map<String, Object>> update(
			@PathVariable Long id,
			@RequestBody ProductDTO productDTO) {
		Map<String, Object> response = new HashMap<>();
		try {
			log.info("상품 수정 요청: ID {}", id);
			
			ProductDTO updatedProduct = productService.update(id, productDTO);
			response.put("success", true);
			response.put("message", "상품이 성공적으로 수정되었습니다.");
			response.put("data", updatedProduct);
			
			return ResponseEntity.ok(response);
		} catch (IllegalArgumentException e) {
			log.warn("상품 수정 실패: {}", e.getMessage());
			response.put("success", false);
			response.put("message", e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception e) {
			log.error("상품 수정 중 오류: {}", e.getMessage());
			response.put("success", false);
			response.put("message", "상품 수정 중 오류가 발생했습니다.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	/**
	 * 상품 삭제
	 * DELETE /api/products/{id}
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Object>> delete(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		try {
			log.info("상품 삭제 요청: ID {}", id);
			
			productService.delete(id);
			response.put("success", true);
			response.put("message", "상품이 성공적으로 삭제되었습니다.");
			
			return ResponseEntity.ok(response);
		} catch (IllegalArgumentException e) {
			log.warn("상품 삭제 실패: {}", e.getMessage());
			response.put("success", false);
			response.put("message", e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception e) {
			log.error("상품 삭제 중 오류: {}", e.getMessage());
			response.put("success", false);
			response.put("message", "상품 삭제 중 오류가 발생했습니다.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	/**
	 * 상품 검색
	 * GET /api/products/search?keyword=검색어
	 */
	@GetMapping("/search")
	public ResponseEntity<Map<String, Object>> search(@RequestParam String keyword) {
		Map<String, Object> response = new HashMap<>();
		try {
			log.info("상품 검색 요청: {}", keyword);
			
			List<Product> products = productService.search(keyword);
			response.put("success", true);
			response.put("data", products);
			response.put("count", products.size());
			
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			log.error("상품 검색 중 오류: {}", e.getMessage());
			response.put("success", false);
			response.put("message", "상품 검색 중 오류가 발생했습니다.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}
}
