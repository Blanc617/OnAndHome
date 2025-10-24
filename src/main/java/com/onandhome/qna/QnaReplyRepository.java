package com.onandhome.qna;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface QnaReplyRepository extends JpaRepository<QnaReply, Long> {
    List<QnaReply> findByQnaId(Long qnaId);
}
