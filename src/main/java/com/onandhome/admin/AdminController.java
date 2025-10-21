package com.onandhome.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 관리자 페이지 컨트롤러
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
    
    // ==================== 사용자 관리 ====================
    @GetMapping("/user/list")
    public String userList() {
        return "admin/user/list";
    }
    
    @GetMapping("/user/detail")
    public String userDetail() {
        return "admin/user/detail";
    }
    
    // ==================== 상품 관리 ====================
    @GetMapping("/product/list")
    public String productList() {
        return "admin/product/list";
    }
    
    @GetMapping("/product/create")
    public String productCreate() {
        return "admin/product/create";
    }
    
    @GetMapping("/product/detail")
    public String productDetail() {
        return "admin/product/detail";
    }
    
    // ==================== 주문 관리 ====================
    @GetMapping("/order/list")
    public String orderList() {
        return "admin/order/list";
    }
    
    @GetMapping("/order/detail")
    public String orderDetail() {
        return "admin/order/detail";
    }
    
    // ==================== 게시판 관리 ====================
    @GetMapping("/board/dashboard")
    public String boardDashboard() {
        return "admin/board/dashboard";
    }
    
    // ========== 공지사항 ==========
//    @GetMapping("/board/notice/list")
//    public String noticeList() {
//        return "admin/board/notice/list";
//    }
    
    @GetMapping("/board/notice/detail")
    public String noticeDetail() {
        return "admin/board/notice/detail";
    }
    
//    @GetMapping("/board/notice/write")
//    public String noticeWrite() {
//        return "admin/board/notice/write";
//    }
    
    @GetMapping("/board/notice/edit")
    public String noticeEdit() {
        return "admin/board/notice/edit";
    }
    
    // ========== 리뷰 ==========
    @GetMapping("/board/review/list")
    public String reviewList() {
        return "admin/board/review/list";
    }
    
    @GetMapping("/board/review/detail")
    public String reviewDetail() {
        return "admin/board/review/detail";
    }
    
    // ========== Q&A ==========
//    @GetMapping("/board/qna/list")
//    public String qnaList() {
//        return "admin/board/qna/list";
//    }
    
    @GetMapping("/board/qna/detail")
    public String qnaDetail() {
        return "admin/board/qna/detail";
    }
}
