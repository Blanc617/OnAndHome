package com.onandhome.user;

import com.onandhome.user.dto.UserDTO;
import com.onandhome.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserService {

    private final UserRepository userRepository;

    /**
     * 사용자 회원가입
     */
    public UserDTO register(UserDTO userDTO) {
        // userId 중복 검사
        if (userRepository.existsByUserId(userDTO.getUserId())) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }

        // email 중복 검사
        if (userDTO.getEmail() != null && userRepository.existsByEmail(userDTO.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        // role 설정 (기본값: USER)
        if (userDTO.getRole() == null) {
            userDTO.setRole("USER");
        }

        // active 설정 (기본값: true)
        if (userDTO.getActive() == null) {
            userDTO.setActive(true);
        }

        User user = userDTO.toEntity();
        User savedUser = userRepository.save(user);

        log.info("새 사용자 등록: {}", userDTO.getUserId());
        return UserDTO.fromEntity(savedUser);
    }

    /**
     * 사용자 로그인 (userId와 password 검증)
     */
    public Optional<UserDTO> login(String userId, String password) {
        // userId로 사용자 조회
        Optional<User> userOptional = userRepository.findByUserId(userId);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            
            // 활성 사용자 확인
            if (!user.getActive()) {
                log.warn("비활성 사용자: {}", userId);
                return Optional.empty();
            }
            
            // 비밀번호 검증 (일반 문자열 비교)
            if (password != null && password.equals(user.getPassword())) {
                log.info("사용자 로그인 성공: {}", userId);
                return Optional.of(UserDTO.fromEntity(user));
            } else {
                log.warn("비밀번호 불일치: {}", userId);
            }
        } else {
            log.warn("존재하지 않는 사용자: {}", userId);
        }

        return Optional.empty();
    }

    /**
     * userId로 사용자 조회
     */
    @Transactional(readOnly = true)
    public Optional<UserDTO> getUserByUserId(String userId) {
        return userRepository.findByUserId(userId)
                .map(UserDTO::fromEntity);
    }

    /**
     * ID로 사용자 조회
     */
    @Transactional(readOnly = true)
    public Optional<UserDTO> getUserById(Long id) {
        return userRepository.findById(id)
                .map(UserDTO::fromEntity);
    }

    /**
     * 사용자 정보 업데이트
     */
    public UserDTO updateUser(UserDTO userDTO) {
        User user = userRepository.findById(userDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        if (userDTO.getEmail() != null) {
            user.setEmail(userDTO.getEmail());
        }
        if (userDTO.getUsername() != null) {
            user.setUsername(userDTO.getUsername());
        }
        if (userDTO.getPhone() != null) {
            user.setPhone(userDTO.getPhone());
        }
        if (userDTO.getGender() != null) {
            user.setGender(userDTO.getGender());
        }
        if (userDTO.getBirthDate() != null) {
            user.setBirthDate(userDTO.getBirthDate());
        }
        if (userDTO.getAddress() != null) {
            user.setAddress(userDTO.getAddress());
        }

        User updatedUser = userRepository.save(user);
        log.info("사용자 정보 업데이트: {}", userDTO.getUserId());

        return UserDTO.fromEntity(updatedUser);
    }

    /**
     * 비밀번호 변경
     */
    public void changePassword(Long userId, String oldPassword, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        // 기존 비밀번호 검증 (일반 문자열 비교)
        if (!oldPassword.equals(user.getPassword())) {
            throw new IllegalArgumentException("기존 비밀번호가 일치하지 않습니다.");
        }

        // 새 비밀번호로 업데이트
        user.setPassword(newPassword);
        userRepository.save(user);

        log.info("비밀번호 변경: {}", user.getUserId());
    }

    /**
     * 사용자 삭제
     */
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        userRepository.delete(user);
        log.info("사용자 삭제: {}", user.getUserId());
    }
}
