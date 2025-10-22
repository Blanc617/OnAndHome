package com.onandhome.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 사용자 마이페이지 및 관련 페이지 Controller
 * - 마이페이지, 주문내역, 장바구니, 회원정보, 사용자 홈 등의 View를 반환
 */
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserPageController {

    /**
     * 사용자 홈 (메인 페이지)
     * GET /user/index
     */
    @GetMapping("/index")
    public String index() {
        log.debug("사용자 홈 페이지 요청");
        return "user/index";  // templates/user/index.html
    }

    /**
     * 마이페이지 조회
     * GET /user/my_page
     */
    @GetMapping("/my_page")
    public String myPage() {
        log.debug("마이페이지 요청");
        return "user/my_page";
    }

    /**
     * 내 정보 조회
     * GET /user/my_info
     */
    @GetMapping("/my_info")
    public String myInfo() {
        log.debug("내 정보 조회 페이지 요청");
        return "user/my_info";
    }

    /**
     * 내 정보 수정
     * GET /user/my_info_edit
     */
    @GetMapping("/my_info_edit")
    public String myInfoEdit() {
        log.debug("내 정보 수정 페이지 요청");
        return "user/my_info_edit";
    }

    /**
     * 장바구니
     * GET /user/cart
     */
    @GetMapping("/cart")
    public String cart() {
        log.debug("장바구니 페이지 요청");
        return "user/cart";
    }

    /**
     * 주문하기
     * GET /user/order
     */
    @GetMapping("/order")
    public String order() {
        log.debug("주문 페이지 요청");
        return "user/order";
    }

    /**
     * 주문 내역
     * GET /user/my_order
     */
    @GetMapping("/my_order")
    public String myOrder() {
        log.debug("주문 내역 페이지 요청");
        return "user/my_order";
    }

    /**
     * 주문 결제
     * GET /user/order_payment
     */
    @GetMapping("/order_payment")
    public String orderPayment() {
        log.debug("주문 결제 페이지 요청");
        return "user/order_payment";
    }

    /**
     * 회원 정보 (마이페이지와 동일)
     * GET /user/member_info
     */
    @GetMapping("/member_info")
    public String memberInfo() {
        log.debug("회원 정보 페이지 요청");
        return "user/my_info";
    }

    /**
     * 회원 정보 수정 (마이페이지 수정과 동일)
     * GET /user/member_info_edit
     */
    @GetMapping("/member_info_edit")
    public String memberInfoEdit() {
        log.debug("회원 정보 수정 페이지 요청");
        return "user/my_info_edit";
    }
}
