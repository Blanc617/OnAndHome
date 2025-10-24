package com.onandhome.qna;

import com.onandhome.admin.adminProduct.ProductRepository;
import com.onandhome.admin.adminProduct.entity.Product;
import com.onandhome.qna.dto.QnaDTO;
import com.onandhome.qna.entity.Qna;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QnaService {

    private final QnaRepository qnaRepository;
    private final ProductRepository productRepository;

    /* ============================================================
       ✅ 관리자 기능
       ============================================================ */

    /** ✅ 전체 QnA 조회 (관리자용) */
    @Transactional(readOnly = true)
    public List<QnaDTO> findAllDTO() {
        return qnaRepository.findAll()
                .stream()
                .map(QnaDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /** ✅ 단일 QnA 상세 조회 (관리자용) */
    @Transactional(readOnly = true)
    public QnaDTO findDTOById(Long id) {
        Qna qna = qnaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 QnA가 존재하지 않습니다. id=" + id));
        return QnaDTO.fromEntity(qna);
    }

    /** ✅ QnA 저장 (관리자용 등록 기능) */
    @Transactional
    public Qna save(Qna qna) {
        qna.setCreatedAt(LocalDateTime.now());
        return qnaRepository.save(qna);
    }

    /** ✅ QnA 수정 (관리자용) */
    @Transactional
    public QnaDTO update(Long id, QnaDTO dto) {
        Qna qna = qnaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 QnA가 존재하지 않습니다. id=" + id));

        qna.setTitle(dto.getTitle());
        qna.setWriter(dto.getWriter());
        qna.setQuestion(dto.getQuestion());
        qna.setCreatedAt(LocalDateTime.now());

        qnaRepository.save(qna);
        return QnaDTO.fromEntity(qna);
    }

    /** ✅ QnA 삭제 (관리자용) */
    @Transactional
    public void delete(Long id) {
        if (!qnaRepository.existsById(id)) {
            throw new IllegalArgumentException("삭제할 QnA가 존재하지 않습니다. id=" + id);
        }
        qnaRepository.deleteById(id);
    }

    /* ============================================================
       ✅ 사용자 기능 (상품 상세 페이지)
       ============================================================ */

    /** ✅ 상품별 QnA 조회 (사용자용) */
    @Transactional(readOnly = true)
    public List<QnaDTO> findByProductId(Long productId) {
        return qnaRepository.findByProductId(productId)
                .stream()
                .map(QnaDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /** ✅ 상품 QnA 등록 (사용자용) */
    @Transactional
    public QnaDTO createForProduct(Long productId, QnaDTO dto) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다. id=" + productId));

        Qna qna = new Qna();
        qna.setTitle(dto.getTitle());
        qna.setWriter(dto.getWriter());
        qna.setQuestion(dto.getQuestion());
        qna.setCreatedAt(LocalDateTime.now());
        qna.setProduct(product);

        qnaRepository.save(qna);
        return QnaDTO.fromEntity(qna);
    }
}
