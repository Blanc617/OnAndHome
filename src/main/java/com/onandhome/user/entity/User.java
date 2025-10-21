package com.onandhome.user.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String userId;
    
    @Column(nullable = false)
    private String password;
    
    @Column(unique = true)
    private String email;
    
    @Column
    private String username;
    
    @Column
    private String phone;
    
    @Column
    private String gender;
    
    @Column
    private String birthDate;
    
    @Column
    private String address;
    
    @Column(nullable = false, columnDefinition = "INT DEFAULT 1")
    private Integer role;  // 0: ADMIN, 1: USER
    
    @Column(nullable = false, columnDefinition = "TINYINT(1) DEFAULT 1")
    private Boolean active;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (role == null) {
            role = 1;  // 기본값: 일반사용자
        }
        if (active == null) {
            active = true;
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
