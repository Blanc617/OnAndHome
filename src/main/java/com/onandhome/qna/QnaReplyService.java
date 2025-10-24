package com.onandhome.qna;

import com.onandhome.qna.entity.Qna;
import com.onandhome.qna.QnaReply;
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

    /** ✅ Lazy 로딩 방지 (트랜잭션 내 초기화) */
    @Transactional(readOnly = true)
    public List<QnaReply> findByQnaId(Long qnaId) {
        List<QnaReply> replies = qnaReplyRepository.findByQnaId(qnaId);
        replies.forEach(r -> {
            if (r.getQna() != null) r.getQna().getTitle();
        });
        return replies;
    }

    @Transactional(readOnly = true)
    public QnaReply findById(Long replyId) {
        QnaReply reply = qnaReplyRepository.findById(replyId)
                .orElseThrow(() -> new IllegalArgumentException("답변을 찾을 수 없습니다. ID=" + replyId));
        if (reply.getQna() != null) reply.getQna().getTitle();
        return reply;
    }

    @Transactional
    public QnaReply updateReply(Long replyId, String content) {
        QnaReply reply = qnaReplyRepository.findById(replyId)
                .orElseThrow(() -> new IllegalArgumentException("답변을 찾을 수 없습니다. ID=" + replyId));
        reply.setContent(content);
        return qnaReplyRepository.save(reply);
    }

    @Transactional
    public void deleteReply(Long replyId) {
        qnaReplyRepository.deleteById(replyId);
    }
}
