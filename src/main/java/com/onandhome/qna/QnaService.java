package com.onandhome.qna;

import com.onandhome.qna.entity.Qna;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QnaService {

    private final QnaRepository qnaRepository;

    /** 모든 질문 조회 */
    public List<Qna> findAll() {
        return qnaRepository.findAll();
    }

    /** ID로 질문 조회 */
    public Qna findById(Long id) {
        return qnaRepository.findById(id).orElse(null);
    }

    /** 새 질문 등록 */
    public Qna save(Qna qna) {
        qna.setCreatedAt(LocalDateTime.now());
        return qnaRepository.save(qna);
    }

    /** 질문 수정 (답변은 여기서 제외) */
    public Qna update(Long id, Qna updated) {
        Qna qna = qnaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 질문이 존재하지 않습니다. id=" + id));
        qna.setTitle(updated.getTitle());
        qna.setWriter(updated.getWriter());
        qna.setQuestion(updated.getQuestion());
        // qna.setAnswer() 제거됨 ✅
        return qnaRepository.save(qna);
    }

    /** 질문 삭제 */
    public void delete(Long id) {
        qnaRepository.deleteById(id);
    }
}
