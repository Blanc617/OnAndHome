package com.onandhome.product.dto;

import com.onandhome.product.entity.Product;
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
                .build();
    }

    /**
     * DTO를 Entity로 변환
     */
    public Product toEntity() {
        return new Product(
                this.id,
                this.name,
                this.description,
                this.price,
                this.stock,
                this.thumbnailImage,
                this.detailImage
        );
    }
}
