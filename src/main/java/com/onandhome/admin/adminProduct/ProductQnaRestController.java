package com.onandhome.admin.adminProduct;

import com.onandhome.qna.QnaService;
import com.onandhome.qna.dto.QnaDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * ✅ 상품 상세 페이지에서 QnA를 불러오기 위한 REST 컨트롤러
 *    경로: /api/product/{productId}/qna
 */
@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductQnaRestController {

    private final QnaService qnaService;

    /** ✅ 상품별 QnA 목록 반환 */
    @GetMapping("/{productId}/qna")
    public List<QnaDTO> getProductQna(@PathVariable Long productId) {
        return qnaService.findByProductId(productId);
    }
}
