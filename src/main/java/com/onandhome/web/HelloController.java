package com.onandhome.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @GetMapping("/health")
    @ResponseBody
    public String health() {
        return "OK";
    }

    @GetMapping("/hello")
    @ResponseBody
    public String hello() {
        return "Hello, OnAndHome from jinu branch!";
    }

    /**
     * 대시보드 페이지 조회
     */
    @GetMapping("/admin/dashboard")
    public String dashboard() {
        return "admin/dashboard";
    }

    /**
     * 홈페이지 리다이렉트
     */
    @GetMapping("/")
    public String index() {
        return "redirect:/admin/login";
    }
}
