package com.onandhome.user;

import com.onandhome.user.dto.UserDTO;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private static final String SESSION_USER_KEY = "loginUser";

    /**
     * User 회원가입 API
     * POST /api/user/register
     * role은 항상 1(일반사용자)로 설정됨
     */
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody UserDTO userDTO) {
        Map<String, Object> response = new HashMap<>();
        try {
            log.info("회원가입 요청: {}", userDTO.getUserId());

            // role 항상 1(일반사용자)로 설정
            userDTO.setRole(1);

            UserDTO registeredUser = userService.register(userDTO);
            response.put("success", true);
            response.put("message", "회원가입 성공");
            response.put("data", registeredUser);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            log.warn("회원가입 실패: {}", e.getMessage());
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            log.error("회원가입 중 오류: {}", e.getMessage(), e);
            response.put("success", false);
            response.put("message", "회원가입 처리 중 오류가 발생했습니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 로그인 API
     * POST /api/user/login
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(
            @RequestBody LoginRequest loginRequest,
            HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        try {
            log.info("로그인 요청: {}", loginRequest.getUserId());
            Optional<UserDTO> userOptional = userService.login(loginRequest.getUserId(), loginRequest.getPassword());

            if (userOptional.isPresent()) {
                UserDTO user = userOptional.get();
                // 세션에 사용자 정보 저장
                session.setAttribute(SESSION_USER_KEY, user);

                response.put("success", true);
                response.put("message", "로그인 성공");
                response.put("data", user);
                log.info("로그인 성공: {}", loginRequest.getUserId());
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "아이디 또는 비밀번호가 일치하지 않습니다.");
                log.warn("로그인 실패: {}", loginRequest.getUserId());
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
        } catch (Exception e) {
            log.error("로그인 중 오류: {}", e.getMessage(), e);
            response.put("success", false);
            response.put("message", "로그인 처리 중 오류가 발생했습니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 사용자 조회 API (ID로)
     * GET /api/user/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getUserById(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<UserDTO> userOptional = userService.getUserById(id);
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
            log.error("사용자 조회 중 오류: {}", e.getMessage(), e);
            response.put("success", false);
            response.put("message", "사용자 조회 중 오류가 발생했습니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 사용자 조회 API (userId로)
     * GET /api/user/username/{userId}
     */
    @GetMapping("/username/{userId}")
    public ResponseEntity<Map<String, Object>> getUserByUserId(@PathVariable String userId) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<UserDTO> userOptional = userService.getUserByUserId(userId);
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
            log.error("사용자 조회 중 오류: {}", e.getMessage(), e);
            response.put("success", false);
            response.put("message", "사용자 조회 중 오류가 발생했습니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 세션 정보 조회 API
     * GET /api/user/session-info
     * 로그인 상태, 사용자 정보, Admin 여부 반환
     */
    @GetMapping("/session-info")
    public ResponseEntity<Map<String, Object>> getSessionInfo(HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        try {
            UserDTO loginUser = (UserDTO) session.getAttribute(SESSION_USER_KEY);

            if (loginUser != null) {
                response.put("loggedIn", true);
                response.put("user", loginUser);
                response.put("isAdmin", loginUser.getRole() == 0); // role 0이 관리자
                log.debug("세션 정보 조회 - 로그인 사용자: {}", loginUser.getUserId());
                return ResponseEntity.ok(response);
            } else {
                response.put("loggedIn", false);
                response.put("user", null);
                response.put("isAdmin", false);
                log.debug("세션 정보 조회 - 비로그인 사용자");
                return ResponseEntity.ok(response);
            }
        } catch (Exception e) {
            log.error("세션 정보 조회 중 오류: {}", e.getMessage(), e);
            response.put("loggedIn", false);
            response.put("message", "세션 정보 조회 중 오류가 발생했습니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 로그인 요청 내부 클래스
     */
    public static class LoginRequest {
        private String userId;
        private String password;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
