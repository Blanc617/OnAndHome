package com.onandhome.user.dto;

import com.onandhome.user.entity.User;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class UserDTO {

    private Long id;

    private String userId;

    private String password;

    private String email;

    private String username;

    private String phone;

    private String gender;

    private String birthDate;

    private String address;
<<<<<<< HEAD

    private String role;

=======
    
    private Integer role;  // 0: ADMIN, 1: USER
    
>>>>>>> 907911771ca0e39edc38d892143979f2daf3eca9
    private Boolean active;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    /**
     * Entity를 DTO로 변환
     */
    public static UserDTO fromEntity(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .userId(user.getUserId())
                .password(user.getPassword())
                .email(user.getEmail())
                .username(user.getUsername())
                .phone(user.getPhone())
                .gender(user.getGender())
                .birthDate(user.getBirthDate())
                .address(user.getAddress())
                .role(user.getRole())
                .active(user.getActive())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    /**
     * DTO를 Entity로 변환
     */
    public User toEntity() {
        return User.builder()
                .id(this.id)
                .userId(this.userId)
                .password(this.password)
                .email(this.email)
                .username(this.username)
                .phone(this.phone)
                .gender(this.gender)
                .birthDate(this.birthDate)
                .address(this.address)
                .role(this.role)
                .active(this.active)
                .build();
    }
}
