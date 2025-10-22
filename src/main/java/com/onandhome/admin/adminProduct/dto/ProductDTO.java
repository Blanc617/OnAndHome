package com.onandhome.admin.adminProduct.dto;

import com.onandhome.admin.adminProduct.entity.Product;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ProductDTO {

    private Long id;

    private String name;

    private String description;

    private int price;

    private int stock;

    private String thumbnailImage; // 썸네일 이미지 URL

    private String detailImage; // 상품 상세 이미지 URL

    private String category; // 소 카테고리

    /**
     * Entity를 DTO로 변환
     */
    public static ProductDTO fromEntity(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .thumbnailImage(product.getThumbnailImage())
                .detailImage(product.getDetailImage())
                .category(product.getCategory())
                .build();
    }

    /**
     * DTO를 Entity로 변환
     * @AllArgsConstructor 생성자를 사용하지 않고 빌더 패턴 사용
     */
    public Product toEntity() {
        return Product.builder()
                .id(this.id)
                .name(this.name)
                .description(this.description)
                .price(this.price)
                .stock(this.stock)
                .thumbnailImage(this.thumbnailImage)
                .detailImage(this.detailImage)
                .category(this.category)
                .build();
    }
}
