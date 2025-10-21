package com.onandhome.admin.adminProduct.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;

/**
 * 관리자 API 명세를 데이터베이스에 저장하기 위한 엔티티
 */
@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "admin_apis") // (DB 테이블 질문) 테이블 명을 admin_product_id 로 하는지?
public class AdminProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "api_id")    // product_id
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

    // 💡 JPA가 요구하는 모든 필드를 포함하는 생성자 (AllArgsConstructor 대체)
    public AdminProduct(Long apiId, String category, String name, String url, String httpMethod, String controller, String methodName) {
        this.apiId = apiId;
        this.category = category;
        this.name = name;
        this.url = url;
        this.httpMethod = httpMethod;
        this.controller = controller;
        this.methodName = methodName;
    }

    // 💡 (추가/개선) ID를 제외한 나머지 필드만 받는 생성자 - DTO 등록용으로 사용
    public AdminProduct(String category, String name, String url, String httpMethod, String controller, String methodName) {
        this.category = category;
        this.name = name;
        this.url = url;
        this.httpMethod = httpMethod;
        this.controller = controller;
        this.methodName = methodName;
    }
}