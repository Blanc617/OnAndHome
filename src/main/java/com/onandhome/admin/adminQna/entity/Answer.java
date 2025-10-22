package com.onandhome.admin.adminQna.entity;

import com.onandhome.qna.entity.Qna;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "answer")
@Getter
@Setter
@NoArgsConstructor
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ✅ Qna와 다대일 관계 (여러 답변이 하나의 질문에 속함)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "qna_id")
    private Qna qna;

    private String content;  // 답변 내용
    private String responder; // 답변 작성자 (관리자 등)
    private LocalDateTime createdAt = LocalDateTime.now();
}
