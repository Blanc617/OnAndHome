package com.onandhome.qna;

import com.onandhome.qna.entity.Qna;
import com.onandhome.qna.entity.QnaReply;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * QnaReply 서비스
 */
@Service
@RequiredArgsConstructor
public class QnaReplyService {

    private final QnaRepository qnaRepository;
    private final QnaReplyRepository qnaReplyRepository;

    /** ✅ 리플라이 등록 */
    public void createReply(Long qnaId, String content, String responder) {
        Qna qna = qnaRepository.findById(qnaId)
                .orElseThrow(() -> new IllegalArgumentException("질문을 찾을 수 없습니다. ID=" + qnaId));

        QnaReply reply = new QnaReply();
        reply.setQna(qna);
        reply.setContent(content);
        reply.setResponder(responder);
        reply.setCreatedAt(LocalDateTime.now());

        qnaReplyRepository.save(reply);
    }

    /** ✅ 특정 Qna에 속한 모든 리플라이 조회 */
    public List<QnaReply> findByQnaId(Long qnaId) {
        return qnaReplyRepository.findByQnaId(qnaId);
    }

    /** ✅ 리플라이 단건 조회 */
    @Transactional(readOnly = true)
    public QnaReply findById(Long replyId) {
        QnaReply reply = qnaReplyRepository.findById(replyId)
                .orElseThrow(() -> new IllegalArgumentException("답변을 찾을 수 없습니다. ID=" + replyId));

        // Lazy 로딩 초기화
        if (reply.getQna() != null) {
            reply.getQna().getTitle();
        }
        return reply;
    }

    /** ✅ 리플라이 수정 */
    public void updateReply(Long replyId, String content) {
        QnaReply reply = qnaReplyRepository.findById(replyId)
                .orElseThrow(() -> new IllegalArgumentException("답변을 찾을 수 없습니다. ID=" + replyId));

        reply.setContent(content);
        qnaReplyRepository.save(reply);
    }

    /** ✅ 리플라이 삭제 */
    public void deleteReply(Long replyId) {
        qnaReplyRepository.deleteById(replyId);
    }
}
