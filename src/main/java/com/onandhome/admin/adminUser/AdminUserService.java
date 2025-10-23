package com.onandhome.admin.adminUser;

// 1. 필요한 부품들을 import 합니다.
import com.onandhome.user.UserRepository;
import com.onandhome.user.dto.UserDTO;
import com.onandhome.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor // final이 붙은 필드를 자동으로 주입해주는 Lombok 기능
public class AdminUserService {

    // 2. DB에서 데이터를 가져올 UserRepository를 주입받음
    private final UserRepository userRepository;

    /**
     * '전체 회원' 목록을 가져오는 메서드
     * (AdminUserController에서 type이 null일 때 호출)
     */
    public List<UserDTO> getAllUsers() {

        // 1. DB에서 모든 User 엔티티(Entity)를 찾습니다.
        List<User> userList = userRepository.findAll();

        // 2. List<User>를 List<UserDTO>로 변환해서 컨트롤러에게 돌려줍니다.
        //    (stream().map()... 부분이 변환해주는 코드)
        return userList.stream()
                .map(this::entityToDTO) // 아래 4번에 있는 헬퍼 메서드 호출
                .collect(Collectors.toList());
    }

    /**
     * '신규 회원' 목록을 가져오는 메서드
     * (AdminUserController에서 type이 "new"일 때 호출)
     */
    public List<UserDTO> getNewUsers() {

        // 🚨 TODO: "신규 회원"을 어떻게 찾을지 팀원과 정해야 합니다!
        // (예: 가입일 순서로 10명만: userRepository.findTop10ByOrderByJoinDateDesc())
        // 지금은 일단 '전체 회원' 목록을 반환하도록 해뒀습니다.
        List<User> userList = userRepository.findAll(); // 👈 나중에 이 부분을 고쳐야 함

        // 3. 변환해서 돌려줌
        return userList.stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
    }
    /**
     * '탈퇴 회원' 목록을 가져오는 메서드
     * (AdminUserController에서 type이 "quit"일 때 호출)
     */
    public List<UserDTO> getQuitUsers() {
        // 🚨 중요: "탈퇴한 회원"을 DB에서 찾는 기능이 필요합니다!
        // "findByActiveFalse"는 "활성(active) 상태가 아닌" 유저를 찾는 기능입니다.
        List<User> userList = userRepository.findByActiveFalse();
        // 3. 변환해서 돌려줌
        return userList.stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
    }

    /**
     * 4. [중요] User 엔티티를 UserDTO로 변환하는 도우미 메서드
     * * 🚨 주의: User.java 와 UserDTO.java 에 있는
     * 실제 필드(변수명)에 맞게 이 메서드 안쪽을 고쳐야 할 수도 있습니다!
     */
    private UserDTO entityToDTO(User user) {
        UserDTO dto = new UserDTO();

        // (UserRepository.java와 UserController.java에서 확인된 필드들)
        dto.setUserId(user.getUserId());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());

        // (list.html 화면에 필요하지만, 실제 필드명은 다를 수 있는 필드들)
        // dto.setId(user.getId()); // "No" (DB 기본키)
        // dto.setName(user.getName()); // "이름"
        // dto.setGender(user.getGender()); // "성별"
        // dto.setPhoneNumber(user.getPhoneNumber()); // "연락처"
        // dto.setBirthDate(user.getBirthDate()); // "생년월일"
        // dto.setCreatedAt(user.getCreatedAt()); // "가입일자"

        return dto;
    }
}