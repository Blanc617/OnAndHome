package com.onandhome.admin.adminUser;

import com.onandhome.user.UserService;
import com.onandhome.user.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin/user")
@RequiredArgsConstructor
public class AdminUserController {

     private final AdminUserService adminUserService;

    // (생성자 주입 - 나중에 추가)
    // public AdminUserController(UserService userService) {
    //     this.userService = userService;
    // }

    /**
     * 관리자 - 회원 목록 페이지 조회
     * GET /admin/user/list
     */
    @GetMapping("/list")
    public String getUserList(@RequestParam(name = "type", required = false)
                                  String type, Model model) {
        // 5. List<UserDTO>를 담을 빈 리스트를 미리 만듭니다.
        List<UserDTO> userList;
        if ("new".equals(type)) {
            // "new" 꼬리표가 붙었을 때 (신규 회원)
            // 6. Service에서 '신규 회원' 목록을 가져옵니다.
            userList = adminUserService.getNewUsers();
        } else if ("quit".equals(type)) {
            // "quit" 꼬리표 (탈퇴 회원)
            // 7. Service에서 '탈퇴 회원' 목록을 가져옵니다.
            userList = adminUserService.getQuitUsers();
        } else {
            // 꼬리표가 없거나(null) 모르는 값일 때 (전체 회원)
            // 8. Service에서 '전체 회원' 목록을 가져옵니다.
            userList = adminUserService.getAllUsers();
        }
        // 9. DB에서 가져온 userList를 "userList"라는 이름으로 HTML에 전달
        model.addAttribute("userList", userList);

        return "admin/user/list";
    }
}
