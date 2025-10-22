package com.onandhome.admin.adminQna;


import com.onandhome.admin.adminQna.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

    // ✅ 특정 Qna에 속한 모든 답변 조회
    List<Answer> findByQnaId(Long qnaId);
}
