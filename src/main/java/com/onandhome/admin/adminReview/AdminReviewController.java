package com.onandhome.admin.adminReview;

import com.onandhome.review.ReviewService;
import com.onandhome.review.entity.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * 리뷰 관리 컨트롤러
 * (Thymeleaf 템플릿 기반)
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/board/review")
public class AdminReviewController {

    private final ReviewService reviewService;

    /** 리뷰 목록 페이지 */
    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("reviews", reviewService.getAllReviews());
        return "admin/board/review/list";
    }

    /** 리뷰 상세 페이지 */
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Long id, Model model) {
        Review review = reviewService.getReview(id)
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다."));
        model.addAttribute("review", review);
        return "admin/board/review/detail";
    }

    /** 리뷰 작성 폼 페이지 */
    @GetMapping("/write")
    public String writeForm(Model model) {
        model.addAttribute("review", new Review());
        return "admin/board/review/write";
    }

    /** 리뷰 등록 */
    @PostMapping("/write")
    public String write(@ModelAttribute Review review) {
        review.setCreatedAt(LocalDateTime.now());
        reviewService.save(review);
        return "redirect:/admin/board/review/list";
    }

    /** 리뷰 수정 폼 */
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Review review = reviewService.getReview(id)
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다."));
        model.addAttribute("review", review);
        return "admin/board/review/edit";
    }

    /** 리뷰 수정 저장 */
    @PostMapping("/edit/{id}")
    public String update(@PathVariable Long id, @ModelAttribute Review newReview) {
        Review review = reviewService.getReview(id)
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다."));
        review.setContent(newReview.getContent());
        review.setRating(newReview.getRating());
        reviewService.save(review);
        return "redirect:/admin/board/review/detail/" + id;
    }

    /** 리뷰 삭제 */
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return "redirect:/admin/board/review/list";
    }
}
