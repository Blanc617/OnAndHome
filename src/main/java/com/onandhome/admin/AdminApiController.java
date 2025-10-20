package com.onandhome.admin;

import com.onandhome.admin.dto.AdminApiDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 관리자 API 정보를 JSON 형태로 반환하는 컨트롤러
 */
@RestController
@RequestMapping("/api/admin")
public class AdminApiController {

    /**
     * 관리자 API 목록을 반환
     */
    @GetMapping("/apis")
    public List<AdminApiDTO> list() {

        // 빨간색 행 제외한 API 목록
        return List.of(
                new AdminApiDTO("상품관리", "상품 목록", "/admin/product/list", "GET", "AdminProductController", "list"),
                new AdminApiDTO("상품관리", "상품 상세", "/admin/product/detail", "GET", "AdminProductController", "detail"),
                new AdminApiDTO("상품관리", "상품 등록", "/admin/product/create", "POST", "AdminProductController", "create"),
                new AdminApiDTO("상품관리", "상품 수정 페이지", "/admin/product/edit/{id}", "GET", "AdminProductController", "editView"),
                new AdminApiDTO("상품관리", "상품 수정", "/admin/product/edit", "PUT", "AdminProductController", "edit"),
                new AdminApiDTO("상품관리", "상품 삭제", "/admin/product/delete/{id}", "DELETE", "AdminProductController", "delete"),
                new AdminApiDTO("주문관리", "주문 목록", "/admin/order/list", "GET", "AdminOrderController", "list"),
                new AdminApiDTO("주문관리", "주문 상세", "/admin/order/detail/{id}", "GET", "AdminOrderController", "detail"),
                new AdminApiDTO("주문관리", "주문 상태 변경", "/admin/order/update-status", "PATCH", "AdminOrderController", "updateStatus"),
                new AdminApiDTO("주문관리", "주문 검색(주문자명)", "/admin/order/search", "GET", "AdminOrderController", "search")
        );
    }
}