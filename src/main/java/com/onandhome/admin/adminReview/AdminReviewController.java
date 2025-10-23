package com.onandhome.admin.adminReview;

import com.onandhome.review.ReviewService;
import com.onandhome.review.ReviewReplyService;
import com.onandhome.review.dto.ReviewDTO;
import com.onandhome.review.dto.ReviewReplyDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 관리자용 리뷰 컨트롤러
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/board/review")
public class AdminReviewController {

    private final ReviewService reviewService;
    private final ReviewReplyService reviewReplyService;

    /** ✅ 리뷰 목록 */
    @GetMapping("/list")
    public String list(Model model) {
        List<ReviewDTO> reviews = reviewService.findAll();
        model.addAttribute("reviews", reviews);
        return "admin/board/review/list";
    }

    /** ✅ 리뷰 상세 페이지 */
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Long reviewId, Model model) {
        ReviewDTO review = reviewService.findById(reviewId);
        List<ReviewReplyDTO> replies = reviewReplyService.findByReviewId(reviewId);
        model.addAttribute("review", review);
        model.addAttribute("replies", replies);
        return "admin/board/review/detail";
    }

    /** ✅ 답글 등록 */
    @PostMapping("/reply/{id}")
    public String createReply(@PathVariable("id") Long reviewId,
                              @RequestParam("content") String content) {

        reviewReplyService.createReply(reviewId, content);
        return "redirect:/admin/board/review/detail/" + reviewId;
    }

    /** ✅ 답글 수정 폼 */
    @GetMapping("/reply/edit/{id}")
    public String editReplyForm(@PathVariable("id") Long replyId, Model model) {
        ReviewReplyDTO reply = reviewReplyService.findById(replyId);
        model.addAttribute("reply", reply);
        return "admin/board/review/reply-edit";
    }

    /** ✅ 답글 수정 처리 */
    @PostMapping("/reply/edit/{id}")
    public String updateReply(@PathVariable("id") Long replyId,
                              @RequestParam("content") String content) {
        reviewReplyService.updateReply(replyId, content);
        Long reviewId = reviewReplyService.getReviewIdByReplyId(replyId);
        return "redirect:/admin/board/review/detail/" + reviewId;
    }

    /** ✅ 답글 삭제 */
    @PostMapping("/reply/delete/{id}")
    public String deleteReply(@PathVariable("id") Long replyId) {
        Long reviewId = reviewReplyService.getReviewIdByReplyId(replyId);
        reviewReplyService.deleteReply(replyId);
        return "redirect:/admin/board/review/detail/" + reviewId;
    }
}
