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

    public static QnaReplyDTO fromEntity(QnaReply reply) {
        return QnaReplyDTO.builder()
                .id(reply.getId())
                .qnaId(reply.getQna().getId())
                .qnaTitle(reply.getQna().getTitle())
                .qnaQuestion(reply.getQna().getQuestion())
                .content(reply.getContent())
                .responder(reply.getResponder())
                .createdAt(reply.getCreatedAt())
                .build();
    }
}
