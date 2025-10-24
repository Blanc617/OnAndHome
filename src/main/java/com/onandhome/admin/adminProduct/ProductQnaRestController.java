package com.onandhome.admin.adminProduct;

import com.onandhome.qna.QnaService;
import com.onandhome.qna.dto.QnaDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductQnaRestController {

    private final QnaService qnaService;

    /** ✅ 상품별 QnA 조회 (지연로딩 방지 포함) */
    @GetMapping("/{productId}/qna")
    public List<QnaDTO> getProductQna(@PathVariable Long productId) {
        return qnaService.findByProductId(productId);
    }

    /** ✅ QnA 등록 */
    @PostMapping("/{productId}/qna")
    public QnaDTO createProductQna(@PathVariable Long productId, @RequestBody QnaDTO dto) {
        return qnaService.createForProduct(productId, dto);
    }
}
