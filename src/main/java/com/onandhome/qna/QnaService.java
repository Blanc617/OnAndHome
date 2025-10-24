package com.onandhome.qna;

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

    /** ✅ 전체 QnA 목록 DTO 변환 */
    @Transactional(readOnly = true)
    public List<QnaDTO> findAllDTO() {
        return qnaRepository.findAll()
                .stream()
                .map(QnaDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /** ✅ 단일 QnA 조회 */
    @Transactional(readOnly = true)
    public QnaDTO findDTOById(Long id) {
        Qna qna = qnaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("QnA 없음"));
        return QnaDTO.fromEntity(qna);
    }

    /** ✅ 상품별 QnA 조회 */
    @Transactional(readOnly = true)
    public List<QnaDTO> findByProductId(Long productId) {
        return qnaRepository.findByProductId(productId)
                .stream()
                .map(QnaDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /** ✅ QnA 생성 */
    @Transactional
    public Qna save(Qna qna) {
        qna.setCreatedAt(LocalDateTime.now());
        return qnaRepository.save(qna);
    }

    /** ✅ QnA 수정 */
    @Transactional
    public Qna update(Long id, Qna updated) {
        Qna qna = qnaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 질문이 존재하지 않습니다. id=" + id));
        qna.setTitle(updated.getTitle());
        qna.setWriter(updated.getWriter());
        qna.setQuestion(updated.getQuestion());
        return qnaRepository.save(qna);
    }

    /** ✅ QnA 삭제 */
    @Transactional
    public void delete(Long id) {
        qnaRepository.deleteById(id);
    }
}
