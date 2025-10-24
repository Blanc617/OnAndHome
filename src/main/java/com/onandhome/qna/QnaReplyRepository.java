package com.onandhome.qna;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface QnaReplyRepository extends JpaRepository<QnaReply, Long> {
    List<QnaReply> findByQnaId(Long qnaId);
}
