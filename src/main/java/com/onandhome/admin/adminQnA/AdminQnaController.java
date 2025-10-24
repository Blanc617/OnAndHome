package com.onandhome.admin.adminQnA;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/board/qna")
public class AdminQnaController {

    /** ✅ QnA 목록 페이지 */
    @GetMapping("/list")
    public String qnaList() {
        return "admin/board/qna/list";
    }

    /** ✅ QnA 상세보기 페이지 */
    @GetMapping("/{id}")
    public String qnaDetail() {
        return "admin/board/qna/detail";
    }

    /** ✅ QnA 수정 페이지 */
    @GetMapping("/edit/{id}")
    public String qnaEdit() {
        return "admin/board/qna/edit";
    }

    /** ✅ 리플라이 수정 페이지 */
    @GetMapping("/reply/edit/{replyId}")
    public String replyEdit() {
        return "admin/board/qna/reply-edit";
    }
}
