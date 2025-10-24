package com.onandhome.qna.dto;

import com.onandhome.qna.entity.Qna;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QnaDTO {

    private Long id;
    private String title;
    private String writer;
    private String question;
    private LocalDateTime createdAt;
    private List<QnaReplyDTO> replies;

    public static QnaDTO fromEntity(Qna qna) {
        return QnaDTO.builder()
                .id(qna.getId())
                .title(qna.getTitle())
                .writer(qna.getWriter())
                .question(qna.getQuestion())
                .createdAt(qna.getCreatedAt())
                .replies(qna.getReplies() != null
                        ? qna.getReplies().stream()
                        .map(QnaReplyDTO::fromEntity)
                        .collect(Collectors.toList())
                        : null)
                .build();
    }
}
