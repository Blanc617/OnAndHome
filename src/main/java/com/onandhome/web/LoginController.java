package com.onandhome.web;

import com.onandhome.user.UserService;
import com.onandhome.user.dto.UserDTO;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {
    
    private final UserService userService;
    
    private static final String SESSION_USER_KEY = "loginUser";
    
    /**
     * 로그인 페이지 GET 요청
     */
    @GetMapping("/admin/login")
    public String loginPage() {
        log.debug("로그인 페이지 요청");
        return "admin/login";
    }
    
    /**
     * 회원가입 페이지 GET 요청
     */
    @GetMapping("/admin/signup")
    public String signupPage() {
        log.debug("회원가입 페이지 요청");
        return "admin/signup";
    }
    
    /**
     * 로그인 처리 POST 요청
     */
    @PostMapping("/admin/login")
    public String login(
            @RequestParam String userId,
            @RequestParam String password,
            HttpSession session,
            Model model,
            RedirectAttributes redirectAttributes) {
        
        log.info("로그인 시도: {}", userId);
        
        try {
            // 사용자 인증
            Optional<UserDTO> userOptional = userService.login(userId, password);
            
            if (userOptional.isPresent()) {
                UserDTO user = userOptional.get();
                
                // 세션에 사용자 정보 저장
                session.setAttribute(SESSION_USER_KEY, user);
                
                log.info("로그인 성공: {}", userId);
                
                // 대시보드로 리다이렉트
                return "redirect:/admin/dashboard";
                
            } else {
                log.warn("로그인 실패 - 아이디 또는 비밀번호 오류: {}", userId);
                
                // 로그인 실패 메시지와 함께 로그인 페이지로 리다이렉트
                redirectAttributes.addAttribute("error", true);
                return "redirect:/admin/login";
            }
            
        } catch (Exception e) {
            log.error("로그인 처리 중 오류 발생: {}", e.getMessage());
            model.addAttribute("errorMessage", "로그인 처리 중 오류가 발생했습니다.");
            return "admin/login";
        }
    }
    
    /**
     * 로그아웃 처리
     */
    @GetMapping("/admin/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        
        // 세션에서 사용자 정보 제거
        session.removeAttribute(SESSION_USER_KEY);
        session.invalidate();
        
        log.info("로그아웃 처리 완료");
        
        redirectAttributes.addAttribute("logout", true);
        return "redirect:/admin/login";
    }
    
    /**
     * 로그인 사용자 확인 (디버깅용)
     */
    @GetMapping("/admin/check-login")
    public String checkLogin(HttpSession session, Model model) {
        UserDTO loginUser = (UserDTO) session.getAttribute(SESSION_USER_KEY);
        
        if (loginUser != null) {
            model.addAttribute("message", "현재 로그인 사용자: " + loginUser.getUserId());
        } else {
            model.addAttribute("message", "로그인하지 않은 상태입니다.");
        }
        
        return "admin/login";
    }
}
