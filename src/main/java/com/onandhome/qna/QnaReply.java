package com.onandhome.qna;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.onandhome.qna.entity.Qna;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "qna_reply")
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class QnaReply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;
    private String responder;
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "qna_id")
    @JsonIgnoreProperties({"replies", "product"}) // 순환 참조 방지
    private Qna qna;
}
