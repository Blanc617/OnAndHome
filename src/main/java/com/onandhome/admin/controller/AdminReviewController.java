package com.onandhome.admin.controller;

import com.onandhome.review.ReviewService;
import com.onandhome.review.entity.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/board/review")
public class AdminReviewController {

    private final ReviewService reviewService;

    // 리뷰 목록
//    @GetMapping("/list")
//    public String list(Model model) {
//        model.addAttribute("reviews", reviewService.getAllReviews());
//        return "admin/review-list"; // templates/admin/review-list.html
//    }

    // 리뷰 상세 보기
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Long id, Model model) {
        Review review = reviewService.getReview(id)
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다."));
        model.addAttribute("review", review);
        return "admin/review/detail"; // templates/admin/review-detail.html
    }

    // 리뷰 삭제
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return "redirect:/admin/board/review/list";
    }
}
