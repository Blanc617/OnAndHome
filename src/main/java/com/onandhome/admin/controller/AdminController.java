package com.onandhome.admin.controller;

import com.onandhome.order.OrderService;
import com.onandhome.order.dto.OrderDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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
@RequiredArgsConstructor
public class AdminController {

    private final OrderService orderService;

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
    // ⚠️ 주의: AdminProductController로 이동됨
    // 중복 매핑 방지를 위해 삭제됨
    // @GetMapping("/product/list")
    // @GetMapping("/product/create")
    // 위 매핑들은 AdminProductController에서 처리합니다.

    @GetMapping("/product/detail")
    public String productDetail() {
        return "admin/product/detail";
    }

    // ==================== 주문 관리 ====================
    @GetMapping("/order/list")
    public String orderList(Model model) {
        try {
            // 모든 주문 조회
            List<OrderDTO> orders = orderService.getAllOrders();
            model.addAttribute("orders", orders);
        } catch (Exception e) {
            // 오류 발생 시 빈 리스트로 처리
            model.addAttribute("orders", List.of());
        }
        return "admin/order/list";
    }

    @GetMapping("/order/detail")
    public String orderDetail(@RequestParam("id") Long orderId, Model model) {
        try {
            OrderDTO order = orderService.getOrder(orderId);
            model.addAttribute("order", order);
        } catch (Exception e) {
            model.addAttribute("error", "주문 정보를 불러올 수 없습니다.");
        }
        return "admin/order/detail";
    }

    // ==================== 게시판 대시보드 ====================
    @GetMapping("/board/dashboard")
    public String boardDashboard() {
        return "admin/board/dashboard";
    }
}
