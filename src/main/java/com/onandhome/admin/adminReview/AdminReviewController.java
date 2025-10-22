package com.onandhome.admin.adminReview;

import com.onandhome.review.ReviewReplyService;
import com.onandhome.review.ReviewService;
import com.onandhome.review.dto.ReviewDTO;
import com.onandhome.review.entity.ReviewReply;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/board/review")
public class AdminReviewController {

    private final ReviewService reviewService;
    private final ReviewReplyService replyService;

    /** ✅ 리뷰 목록 (DTO 기반) */
    @GetMapping("/list")
    public String list(Model model) {
        // DTO 변환된 리뷰 리스트 가져오기
        List<ReviewDTO> reviewList = reviewService.getAllReviews();
        model.addAttribute("reviewList", reviewList);

        // ✅ 절대경로로 명시 (templates/admin/board/review/list.html)
        return "/admin/board/review/list";
    }

    /** ✅ 리뷰 상세 (답글 포함) */
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Long id, Model model) {
        ReviewDTO review = reviewService.getReview(id);
        List<ReviewReply> replies = replyService.findByReviewId(id);

        model.addAttribute("review", review);
        model.addAttribute("replies", replies);

        // ✅ 절대경로로 명시 (templates/admin/board/review/detail.html)
        return "/admin/board/review/detail";
    }

    /** ✅ 답글 등록 */
    @PostMapping("/reply/create")
    public String createReply(@RequestParam Long reviewId, @RequestParam String content) {
        replyService.createReply(reviewId, content, "관리자");
        return "redirect:/admin/board/review/detail/" + reviewId;
    }

    /** ✅ 답글 수정 폼 */
    @GetMapping("/reply/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        var reply = replyService.findById(id);
        var review = reply.getReview();

        model.addAttribute("reply", reply);
        model.addAttribute("review", review);

        // ✅ 절대경로로 명시 (templates/admin/board/review/reply-edit.html)
        return "/admin/board/review/reply-edit";
    }

    /** ✅ 답글 수정 처리 */
    @PostMapping("/reply/edit")
    public String updateReply(@RequestParam Long replyId,
                              @RequestParam Long reviewId,
                              @RequestParam String content) {
        replyService.updateReply(replyId, content);
        return "redirect:/admin/board/review/detail/" + reviewId;
    }

    /** ✅ 답글 삭제 */
    @PostMapping("/reply/delete/{id}")
    public String deleteReply(@PathVariable Long id, @RequestParam Long reviewId) {
        replyService.deleteReply(id);
        return "redirect:/admin/board/review/detail/" + reviewId;
    }
}
