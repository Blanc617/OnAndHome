package com.onandhome.user;

import com.onandhome.user.dto.UserDTO;
import com.onandhome.user.entity.User;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 사용자 REST API 컨트롤러
 */
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Slf4j
public class UserRestController {

    private final UserService userService;
    private static final String SESSION_USER_KEY = "loginUser";

    /**
     * 현재 로그인한 사용자 정보 조회
     * GET /api/user/my-info
     */
    @GetMapping("/my-info")
    public ResponseEntity<Map<String, Object>> getMyInfo(HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        try {
            log.info("사용자 정보 조회 요청");
            
            // 세션에서 사용자 정보 가져오기
            UserDTO loginUser = (UserDTO) session.getAttribute(SESSION_USER_KEY);
            
            if (loginUser == null) {
                log.warn("세션에 사용자 정보가 없음");
                response.put("success", false);
                response.put("message", "로그인이 필요합니다.");
                return ResponseEntity.status(401).body(response);
            }
            
            // DB에서 최신 사용자 정보 다시 조회
            User user = userService.findByUserId(loginUser.getUserId());
            UserDTO userDTO = UserDTO.fromEntity(user);
            
            response.put("success", true);
            response.put("data", userDTO);
            log.info("사용자 정보 조회 성공 - userId: {}", loginUser.getUserId());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("사용자 정보 조회 오류", e);
            response.put("success", false);
            response.put("message", "사용자 정보를 불러올 수 없습니다: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    /**
     * 사용자 정보 업데이트
     * PUT /api/user/update
     */
    @PutMapping("/update")
    public ResponseEntity<Map<String, Object>> updateUserInfo(@RequestBody UserDTO userDTO, HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        try {
            log.info("사용자 정보 수정 요청: {}", userDTO.getUserId());
            
            // 세션에서 사용자 정보 가져오기
            UserDTO loginUser = (UserDTO) session.getAttribute(SESSION_USER_KEY);
            
            if (loginUser == null) {
                log.warn("세션에 사용자 정보가 없음");
                response.put("success", false);
                response.put("message", "로그인이 필요합니다.");
                return ResponseEntity.status(401).body(response);
            }
            
            // 본인 정보만 수정 가능
            if (!loginUser.getId().equals(userDTO.getId())) {
                response.put("success", false);
                response.put("message", "본인의 정보만 수정할 수 있습니다.");
                return ResponseEntity.status(403).body(response);
            }
            
            UserDTO updatedUser = userService.updateUser(userDTO);
            
            // 세션 정보도 업데이트
            session.setAttribute(SESSION_USER_KEY, updatedUser);
            
            response.put("success", true);
            response.put("message", "정보가 수정되었습니다.");
            response.put("data", updatedUser);
            log.info("사용자 정보 수정 성공 - userId: {}", updatedUser.getUserId());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("사용자 정보 수정 오류", e);
            response.put("success", false);
            response.put("message", "정보 수정 중 오류가 발생했습니다: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
}
