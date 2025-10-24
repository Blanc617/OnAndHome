package com.onandhome.admin.adminQnA;

import com.onandhome.qna.QnaReplyService;
import com.onandhome.qna.QnaService;
import com.onandhome.qna.dto.QnaDTO;
import com.onandhome.qna.dto.QnaReplyDTO;
import com.onandhome.qna.QnaReply;
import com.onandhome.qna.entity.Qna;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin/qna")
@RequiredArgsConstructor
public class AdminQnaRestController {

    private final QnaService qnaService;
    private final QnaReplyService qnaReplyService;

    /* ============================================================
       ✅ QnA (관리자용)
       ============================================================ */

    /** ✅ 모든 QnA 조회 */
    @GetMapping
    public List<QnaDTO> getAllQna() {
        return qnaService.findAllDTO();
    }

    /** ✅ 단건 조회 */
    @GetMapping("/{id}")
    public QnaDTO getQna(@PathVariable Long id) {
        return qnaService.findDTOById(id);
    }

    /** ✅ 등록 */
    @PostMapping
    public QnaDTO createQna(@RequestBody Qna qna) {
        return QnaDTO.fromEntity(qnaService.save(qna));
    }

    /** ✅ 수정 */
    @PutMapping("/{id}")
    public QnaDTO updateQna(@PathVariable Long id, @RequestBody QnaDTO dto) {
        return qnaService.update(id, dto);
    }

    /** ✅ 삭제 */
    @DeleteMapping("/{id}")
    public void deleteQna(@PathVariable Long id) {
        qnaService.delete(id);
    }

    /* ============================================================
       ✅ QnA Reply (관리자용)
       ============================================================ */

    /** ✅ 특정 QnA의 모든 리플라이 조회 */
    @GetMapping("/{id}/replies")
    public List<QnaReplyDTO> getReplies(@PathVariable Long id) {
        List<QnaReply> replies = qnaReplyService.findByQnaId(id);
        return replies.stream()
                .map(QnaReplyDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /** ✅ 리플라이 단건 조회 */
    @GetMapping("/replies/{replyId}")
    public QnaReplyDTO getReply(@PathVariable Long replyId) {
        QnaReply reply = qnaReplyService.findById(replyId);
        return QnaReplyDTO.fromEntity(reply);
    }

    /** ✅ 리플라이 등록 */
    @PostMapping("/{id}/replies")
    public QnaReplyDTO createReply(@PathVariable Long id, @RequestBody QnaReplyDTO dto) {
        QnaReply reply = qnaReplyService.createReply(id, dto.getContent(), dto.getResponder());
        return QnaReplyDTO.fromEntity(reply);
    }

    /** ✅ 리플라이 수정 */
    @PutMapping("/replies/{replyId}")
    public QnaReplyDTO updateReply(@PathVariable Long replyId, @RequestBody QnaReplyDTO dto) {
        QnaReply reply = qnaReplyService.updateReply(replyId, dto.getContent());
        return QnaReplyDTO.fromEntity(reply);
    }

    /** ✅ 리플라이 삭제 */
    @DeleteMapping("/replies/{replyId}")
    public void deleteReply(@PathVariable Long replyId) {
        qnaReplyService.deleteReply(replyId);
    }
}
