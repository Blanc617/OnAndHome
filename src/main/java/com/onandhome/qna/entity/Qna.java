package com.onandhome.qna.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.onandhome.admin.adminProduct.entity.Product;
import com.onandhome.qna.QnaReply;
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
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Qna {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;     // 질문 제목
    private String writer;    // 작성자
    private String question;  // 질문 내용
    private LocalDateTime createdAt = LocalDateTime.now();

    /** ✅ 상품 연결 (지연 로딩 방지용 설정 포함) */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Product product;

    /** ✅ 답변 리스트 */
    @OneToMany(mappedBy = "qna", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"qna", "hibernateLazyInitializer", "handler"})
    private List<QnaReply> replies = new ArrayList<>();

    /** ✅ 양방향 연관관계 편의 메서드 */
    public void addReply(QnaReply reply) {
        replies.add(reply);
        reply.setQna(this);
    }
}
