package com.onandhome.web;

import com.onandhome.user.UserService;
import com.onandhome.user.dto.UserDTO;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;
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
    //유연 작업(OK)
    //회원 정보 조회
    @GetMapping("/admin/edit-view")
    public ResponseEntity<Map<String, Object>> getMyInfo(HttpSession session) {
        Map<String, Object> response = new HashMap<>();

        try {
            UserDTO loginUser = (UserDTO) session.getAttribute(SESSION_USER_KEY);

            if (loginUser == null) {
                response.put("success", false);
                response.put("message", "로그인이 필요합니다.");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }

            Optional<UserDTO> userOptional = userService.getUserById(loginUser.getId());

            if (userOptional.isPresent()) {
                response.put("success", true);
                response.put("data", userOptional.get());
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "사용자를 찾을 수 없습니다.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "사용자 정보 조회 중 오류가 발생했습니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    //유연 작업(실패 창은 OK)
    //회원 정보 수정
    @PatchMapping("/admin/edit")
    public ResponseEntity<Map<String, Object>> editUser(
            @RequestBody UserDTO userDTO,
            HttpSession session) {

        Map<String, Object> response = new HashMap<>();

        try {
            UserDTO loginUser = (UserDTO) session.getAttribute(SESSION_USER_KEY);

            if (loginUser == null) {
                response.put("success", false);
                response.put("message", "로그인이 필요합니다.");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }

            userDTO.setId(loginUser.getId());

            //UserService는 userDTO 하나만 받음!!
            UserDTO updatedUser = userService.updateUser(userDTO);

            session.setAttribute(SESSION_USER_KEY, updatedUser);

            response.put("success", true);
            response.put("message", "회원 정보 수정 성공");
            response.put("data", updatedUser);
            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "회원 정보 수정 중 오류 발생");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    //유연 작업 (실패 창 OK)
    //회원 탈퇴
    @DeleteMapping("/admin/delete")
    public ResponseEntity<Map<String, Object>> deleteUser(HttpSession session) {
        Map<String, Object> response = new HashMap<>();

        try {
            UserDTO loginUser = (UserDTO) session.getAttribute(SESSION_USER_KEY);

            if (loginUser == null) {
                response.put("success", false);
                response.put("message", "로그인이 필요합니다.");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }

            userService.deleteUser(loginUser.getId());

            session.invalidate(); //세션 종료 (로그아웃)

            response.put("success", true);
            response.put("message", "회원 탈퇴가 완료되었습니다.");
            log.info("회원 탈퇴 완료: {}", loginUser.getUserId());
            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

        } catch (Exception e) {
            log.error("회원 탈퇴 중 오류: {}", e.getMessage(), e);
            response.put("success", false);
            response.put("message", "회원 탈퇴 중 오류가 발생했습니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}

