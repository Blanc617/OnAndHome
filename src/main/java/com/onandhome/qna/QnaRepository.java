// 📂 src/main/java/com/onandhome/qna/QnaRepository.java
package com.onandhome.qna;

import com.onandhome.qna.entity.Qna;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface QnaRepository extends JpaRepository<Qna, Long> {
    List<Qna> findByProductId(Long productId);
}
