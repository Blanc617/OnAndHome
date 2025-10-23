package com.onandhome.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 관리자 공통 페이지 컨트롤러
 * - 사용자, 주문, 대시보드 관리
 *
 * ⚠️ 상품 관리는 AdminProductController로 이동됨
 * - /admin/product/list → AdminProductController.list()
 * - /admin/product/create → AdminProductController.createForm()
 * - /admin/product/edit/{id} → AdminProductController.editForm()
 * - /admin/product/{id} → AdminProductController.detail()
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    // ==================== 사용자 관리 ====================
    /* 🚨 AdminUserController와 중복되므로 주석 처리 (2025-10-23)
    @GetMapping("/user/list")
    public String userList() {
        return "admin/user/list";
    }
    */
    @GetMapping("/user/detail")
    public String userDetail() {
        return "admin/user/detail";
    }

    // ==================== 상품 관리 ====================
    // ⚠️ 주의: AdminProductController로 이동됨
    // 중복 매핑 방지를 위해 삭제됨
    // @GetMapping("/product/list")
    // @GetMapping("/product/create")
    // 위 매핑들은 AdminProductController에서 처리됩니다.

    @GetMapping("/product/detail")
    public String productDetail() {
        return "admin/product/detail";
    }

    // ==================== 주문 관리 ====================

    // ✅ [수정] AdminOrderController와 충돌하므로 주석 처리합니다.
    // @GetMapping("/order/list")
    // public String orderList() {
    //     return "admin/order/list";
    // }

    @GetMapping("/order/detail")
    public String orderDetail() {
        return "admin/order/detail";
    }

    // ==================== 게시판 관리 ====================

    // ✅ [수정] AdminNoticeController, AdminQnaController 등과
    // URL 매핑이 중복되므로(Ambiguous mapping) 모두 주석 처리합니다.
    // @GetMapping("/board/notice/list")
    // public String noticeList() {
    //     return "admin/board/notice/list";
    // }
    //
    // @GetMapping("/board/notice/write")
    // public String noticeWrite() {
    //     return "admin/board/notice/write";
    // }
    //
    // @GetMapping("/board/qna/list")
    // public String qnaList() {
    //     return "admin/board/qna/list";
    // }
    //
    // @GetMapping("/board/review/list")
    // public String reviewList() {
    //     return "admin/board/review/list";
    // }
}
