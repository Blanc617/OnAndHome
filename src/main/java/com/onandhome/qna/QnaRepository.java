package com.onandhome.qna;

import com.onandhome.qna.entity.Qna;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface QnaRepository extends JpaRepository<Qna, Long> {

    /** ✅ 상품별 QnA 조회 */
    @Query("SELECT q FROM Qna q WHERE q.product.id = :productId")
    List<Qna> findByProductId(@Param("productId") Long productId);
}
