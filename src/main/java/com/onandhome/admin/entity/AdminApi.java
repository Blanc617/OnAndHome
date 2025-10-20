// src/main/java/com/onandhome/admin/entity/AdminApi.java

package com.onandhome.admin.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.AccessLevel;

/**
 * 관리자 API 명세를 데이터베이스에 저장하기 위한 엔티티
 */
@Entity
@Getter // 💡 Getters 추가
@Setter // 💡 Setters 추가
@AllArgsConstructor // 💡 모든 필드를 포함하는 생성자 추가
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 💡 기본 생성자 추가 (JPA 요구사항 및 관례)
@Table(name = "admin_apis")
public class AdminApi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "api_id")
    private Long apiId;

    /** 기능 구분 (상품관리 / 주문관리 등) */
    @Column(nullable = false, length = 50)
    private String category;

    /** 세부 기능명 (예: 상품 목록, 주문 상세 등) */
    @Column(nullable = false, length = 100)
    private String name;

    /** 요청 URL (예: /admin/product/list) */
    @Column(nullable = false, unique = true, length = 255)
    private String url;

    /** HTTP 요청 메서드 (GET, POST, PUT, DELETE, PATCH 등) */
    @Column(nullable = false, length = 10)
    private String httpMethod;

    /** 담당 컨트롤러 클래스명 (예: AdminProductController) */
    @Column(nullable = false, length = 100)
    private String controller;

    /** 실행되는 메서드명 (예: list, detail, create 등) */
    @Column(nullable = false, length = 50)
    private String methodName;
}