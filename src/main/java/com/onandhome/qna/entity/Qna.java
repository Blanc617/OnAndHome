package com.onandhome.qna.entity;

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
public class Qna {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;     // 질문 제목
    private String writer;    // 작성자
    private String question;  // 질문 내용
    private LocalDateTime createdAt = LocalDateTime.now();

    /** ✅ QnA → Product (단방향) */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    /** ✅ QnA → QnaReply (단방향) */
    @OneToMany(mappedBy = "qna", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<QnaReply> replies = new ArrayList<>();

    public void addReply(QnaReply reply) {
        replies.add(reply);
        reply.setQna(this);
    }
}
