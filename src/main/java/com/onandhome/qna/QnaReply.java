package com.onandhome.qna;

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
public class QnaReply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;
    private String responder;
    private LocalDateTime createdAt = LocalDateTime.now();

    /** ✅ QnaReply → Qna (단방향) */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "qna_id")
    private Qna qna;
}
