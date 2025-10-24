package com.onandhome.qna;

import com.onandhome.qna.entity.Qna;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QnaReplyService {

    private final QnaRepository qnaRepository;
    private final QnaReplyRepository qnaReplyRepository;

    /** ✅ 답변 등록 */
    @Transactional
    public QnaReply createReply(Long qnaId, String content, String responder) {
        Qna qna = qnaRepository.findById(qnaId)
                .orElseThrow(() -> new IllegalArgumentException("질문을 찾을 수 없습니다. ID=" + qnaId));

        QnaReply reply = new QnaReply();
        reply.setQna(qna);
        reply.setContent(content);
        reply.setResponder(responder);
        reply.setCreatedAt(LocalDateTime.now());
        return qnaReplyRepository.save(reply);
    }

    /** ✅ 특정 QnA의 답변 목록 조회 (Lazy 방지 포함) */
    @Transactional(readOnly = true)
    public List<QnaReply> findByQnaId(Long qnaId) {
        List<QnaReply> replies = qnaReplyRepository.findByQnaId(qnaId);
        replies.forEach(r -> {
            if (r.getQna() != null) {
                // ✅ Lazy 로딩 강제 초기화 (세션이 닫히기 전에)
                r.getQna().getTitle();
                r.getQna().getQuestion();
            }
        });
        return replies;
    }

    /** ✅ 단일 답변 조회 (Lazy 방지 포함) */
    @Transactional(readOnly = true)
    public QnaReply findById(Long replyId) {
        QnaReply reply = qnaReplyRepository.findById(replyId)
                .orElseThrow(() -> new IllegalArgumentException("답변을 찾을 수 없습니다. ID=" + replyId));

        if (reply.getQna() != null) {
            // ✅ Lazy 로딩 강제 초기화
            reply.getQna().getTitle();
            reply.getQna().getQuestion();
        }

        return reply;
    }

    /** ✅ 답변 수정 */
    @Transactional
    public QnaReply updateReply(Long replyId, String content) {
        QnaReply reply = qnaReplyRepository.findById(replyId)
                .orElseThrow(() -> new IllegalArgumentException("답변을 찾을 수 없습니다. ID=" + replyId));
        reply.setContent(content);
        return qnaReplyRepository.save(reply);
    }

    /** ✅ 답변 삭제 */
    @Transactional
    public void deleteReply(Long replyId) {
        qnaReplyRepository.deleteById(replyId);
    }
}
