package com.onandhome.admin.adminOrder;

import com.onandhome.order.dto.OrderDTO;
// import com.onandhome.order.entity.Order; // ⬅️ Order 엔티티는 이제 필요 없으므로 주석 처리 (혹은 삭제)
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

// ✅ [추가] 날짜 형식(Date Format)을 스프링에게 알려주기 위한 import
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;
// import java.util.stream.Collectors; // ⬅️ Collectors도 이제 필요 없습니다.

@Controller
@RequestMapping("/admin/order")
@RequiredArgsConstructor
public class AdminOrderController {

    private final AdminOrderService adminOrderService;

    /**
     * 주문 목록 페이지 (날짜 필터링)
     * GET /admin/order/list
     * GET /admin/order/list?date=2025-10-22 (오늘 주문만)
     */
    @GetMapping("/list")
    public String getOrderList(
            // ✅ [최종 수정] @DateTimeFormat 어노테이션을 추가합니다.
            // "URL로 넘어온 'yyyy-MM-dd' 형식의 문자열을 LocalDate 객체로 변환해줘!"
            @RequestParam(value = "date", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            Model model
    ) {

        // ✅ 1. Service가 List<OrderDTO>를 직접 반환합니다.
        List<OrderDTO> orderDTOs = adminOrderService.getOrders(date);

        // ✅ 2. DTO 변환 로직은 Service에 있습니다.

        // ✅ 3. HTML(Thymeleaf)에서 'orderList'라는 이름으로 사용할 수 있게 모델에 담기
        model.addAttribute("orderList", orderDTOs);

        // ✅ 4. 오늘 날짜를 화면에 전달 (달력 표시용)
        model.addAttribute("activeDate", date != null ? date : LocalDate.now());

        // ✅ 5. 팀의 'list.html' 파일 경로로 변경
        return "admin/order/list";
    }

}

