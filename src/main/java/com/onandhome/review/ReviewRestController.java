package com.onandhome.review;

import com.onandhome.review.dto.ReviewDTO;
import com.onandhome.user.UserService;
import com.onandhome.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 리뷰 REST API 컨트롤러
 */
@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
@Slf4j
public class ReviewRestController {

    private final ReviewService reviewService;
    private final UserService userService;

    /**
     * 상품별 리뷰 목록 조회
     * GET /api/reviews/product/{productId}
     */
    @GetMapping("/product/{productId}")
    public ResponseEntity<Map<String, Object>> getReviewsByProduct(@PathVariable Long productId) {
        Map<String, Object> response = new HashMap<>();
        try {
            log.info("리뷰 목록 조회 요청 - productId: {}", productId);
            List<ReviewDTO> reviews = reviewService.findByProductId(productId);
            response.put("success", true);
            response.put("data", reviews);
            log.info("리뷰 목록 조회 성공 - 개수: {}", reviews.size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("리뷰 목록 조회 오류", e);
            response.put("success", false);
            response.put("message", "리뷰 목록을 불러올 수 없습니다.");
            return ResponseEntity.status(500).body(response);
        }
    }

    /**
     * 리뷰 작성
     * POST /api/reviews
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> createReview(@RequestBody Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();
        try {
            log.info("리뷰 작성 요청: {}", request);
            
            // 로그인한 사용자 정보 가져오기
            User user = userService.getLoginUser();
            
            Long productId = Long.valueOf(request.get("productId").toString());
            String content = request.get("content").toString();
            int rating = request.containsKey("rating") ? 
                    Integer.parseInt(request.get("rating").toString()) : 5;
            
            log.info("리뷰 작성 - userId: {}, productId: {}, content: {}", user.getId(), productId, content);
            
            ReviewDTO review = reviewService.createReview(productId, user.getId(), content, rating);
            
            response.put("success", true);
            response.put("message", "리뷰가 등록되었습니다.");
            response.put("data", review);
            log.info("리뷰 작성 성공 - reviewId: {}", review.getId());
            return ResponseEntity.ok(response);
        } catch (IllegalStateException e) {
            log.warn("로그인 필요: {}", e.getMessage());
            response.put("success", false);
            response.put("message", "로그인이 필요합니다.");
            return ResponseEntity.status(401).body(response);
        } catch (Exception e) {
            log.error("리뷰 작성 오류", e);
            response.put("success", false);
            response.put("message", "리뷰 작성 중 오류가 발생했습니다: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
}
