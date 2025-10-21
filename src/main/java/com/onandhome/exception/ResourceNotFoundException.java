package com.onandhome.exception; // ⬅️ 패키지 경로를 adminOrder 내부로 변경

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 리소스를 찾지 못했을 때 (HTTP 404 Not Found) 반환하기 위한 사용자 정의 예외
 * Spring MVC에서 @ResponseStatus에 의해 자동으로 404 응답을 반환하도록 설정합니다.
 */
@ResponseStatus(HttpStatus.NOT_FOUND) // 404 상태 코드 설정
public class ResourceNotFoundException extends RuntimeException {

    private final String resourceName;
    private final String fieldName;
    private final Object fieldValue;

    // 생성자: 리소스 이름, 필드 이름, 필드 값을 받아 메시지를 구성합니다.
    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    // 추가 정보 제공을 위한 Getter (필요 시 사용)
    public String getResourceName() {
        return resourceName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Object getFieldValue() {
        return fieldValue;
    }
}
