package com.onandhome.order;

import com.onandhome.order.entity.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    private Long currentUserId() { return 1L; }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("orders", orderService.list(currentUserId()));
        return "order/list";
    }

    @GetMapping("/checkout")
    public String checkout(Model model) {
        model.addAttribute("cart", orderService.checkout(currentUserId()));
        return "order/checkout";
    }

    @PostMapping("/create")
    public String create() {
        Order o = orderService.create(currentUserId());
        return "redirect:/order/detail/" + o.getId();
    }

    @PostMapping("/pay/{orderId}")
    public String pay(@PathVariable Long orderId) {
        orderService.pay(orderId);
        return "redirect:/order/detail/" + orderId;
    }

    @GetMapping("/detail/{orderId}")
    public String detail(@PathVariable Long orderId, Model model) {
        model.addAttribute("order", orderService.detail(orderId));
        return "order/detail";
    }

    @GetMapping("/track/{orderId}")
    @ResponseBody
    public String track(@PathVariable Long orderId) {
        return orderService.track(orderId);
    }
}
