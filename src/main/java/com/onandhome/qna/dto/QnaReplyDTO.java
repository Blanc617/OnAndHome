package com.onandhome.qna.dto;

import com.onandhome.qna.QnaReply;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QnaReplyDTO {

    private Long id;
    private Long qnaId;
    private String qnaTitle;
    private String qnaQuestion;
    private String content;
    private String responder;
    private LocalDateTime createdAt;

    /** ✅ 엔티티 → DTO 변환 */
    public static QnaReplyDTO fromEntity(QnaReply reply) {
        return QnaReplyDTO.builder()
                .id(reply.getId())
                .qnaId(reply.getQna() != null ? reply.getQna().getId() : null)
                .qnaTitle(reply.getQna() != null ? reply.getQna().getTitle() : null)
                .qnaQuestion(reply.getQna() != null ? reply.getQna().getQuestion() : null)
                .content(reply.getContent())
                .responder(reply.getResponder())
                .createdAt(reply.getCreatedAt())
                .build();
    }
}
