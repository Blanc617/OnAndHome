package com.onandhome.admin.adminQna;

import com.onandhome.admin.adminQna.entity.Answer;
import com.onandhome.qna.QnaRepository;
import com.onandhome.qna.entity.Qna;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // ✅ 추가
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final QnaRepository qnaRepository;
    private final AnswerRepository answerRepository;

    /** ✅ 답변 등록 */
    public void createAnswer(Long qnaId, String content, String responder) {
        Qna qna = qnaRepository.findById(qnaId)
                .orElseThrow(() -> new IllegalArgumentException("질문을 찾을 수 없습니다. ID=" + qnaId));

        Answer answer = new Answer();
        answer.setQna(qna);
        answer.setContent(content);
        answer.setResponder(responder);
        answer.setCreatedAt(LocalDateTime.now());

        answerRepository.save(answer);
    }

    /** ✅ 답변 목록 조회 */
    public List<Answer> findByQnaId(Long qnaId) {
        return answerRepository.findByQnaId(qnaId);
    }

    /** ✅ 단일 답변 조회 (Lazy 로딩 오류 방지) */
    @Transactional(readOnly = true)
    public Answer findById(Long answerId) {
        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new IllegalArgumentException("답변을 찾을 수 없습니다. ID=" + answerId));

        // ✅ 트랜잭션 세션 안에서 Qna 초기화
        if (answer.getQna() != null) {
            answer.getQna().getTitle();
        }

        return answer;
    }

    /** ✅ 답변 수정 */
    public void updateAnswer(Long answerId, String content) {
        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new IllegalArgumentException("답변을 찾을 수 없습니다. ID=" + answerId));

        answer.setContent(content);
        answerRepository.save(answer);
    }

    /** ✅ 답변 삭제 */
    public void deleteAnswer(Long answerId) {
        answerRepository.deleteById(answerId);
    }
}
