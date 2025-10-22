package com.onandhome.qna.entity;

import com.onandhome.admin.adminProduct.entity.Product;
import com.onandhome.admin.adminQna.entity.Answer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "qna")
@Getter
@Setter
@NoArgsConstructor
public class Qna {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String title;   // 질문 제목
    private String writer;  // 작성자
    private String question; // 질문 내용

    private LocalDateTime createdAt = LocalDateTime.now();

    // ✅ 제품 정보 연결 (기존 유지)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    // ✅ 답변 여러 개 연결 (1:N 관계)
    @OneToMany(mappedBy = "qna", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Answer> answers = new ArrayList<>();

    // ✅ 편의 메서드: Qna에 답변 추가 시 자동 연결
    public void addAnswer(Answer answer) {
        answers.add(answer);
        answer.setQna(this);
    }
}
