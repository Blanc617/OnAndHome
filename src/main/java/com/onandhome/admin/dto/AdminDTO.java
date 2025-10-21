package com.onandhome.admin.dto;

import lombok.*;
/**
 * 관리자(Admin) 기능별 API 정보를 담는 DTO 클래스
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AdminDTO {

    /** 기능 구분 (상품관리 / 주문관리 등) */
    private String category;

    /** 세부 기능명 (예: 상품 목록, 주문 상세 등) */
    private String name;

    /** 요청 URL (예: /admin/product/list) */
    private String url;

    /** HTTP 요청 메서드 (GET, POST, PUT, DELETE, PATCH 등) */
    private String httpMethod;

    /** 담당 컨트롤러 클래스명 (예: AdminProductController) */
    private String controller;

    /** 실행되는 메서드명 (예: list, detail, create 등) */
    private String methodName;
}
