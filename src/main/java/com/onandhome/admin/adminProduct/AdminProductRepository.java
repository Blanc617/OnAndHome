package com.onandhome.admin.adminProduct;

import com.onandhome.admin.adminProduct.entity.AdminProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminProductRepository {/**
 * 관리자 API 명세(AdminApi) 엔티티에 대한 데이터 접근을 담당하는 Repository
 */
public interface AdminApiRepository extends JpaRepository<AdminProduct, Long> {

    /**
     * URL이 중복되는지 확인합니다. (등록 시 중복 검사)
     */
    boolean existsByUrl(String url);

    /**
     * 특정 URL을 가진 AdminApi 엔티티를 조회합니다. (수정 시 중복 검사)
     */
    Optional<AdminProduct> findByUrl(String url);
    }
}
