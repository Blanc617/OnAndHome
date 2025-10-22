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

    private int price; // 정상가

    private Integer salePrice; // 할인가

    private int stock; // 재고수량

    private String thumbnailImage; // 썸네일 이미지 URL

    private String detailImage; // 상품 상세 이미지 URL

    private String category; // 소 카테고리

    private String manufacturer; // 제조사

    private String country; // 제조국

    /**
     * Entity를 DTO로 변환
     */
    public static ProductDTO fromEntity(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .salePrice(product.getSalePrice())
                .stock(product.getStock())
                .thumbnailImage(product.getThumbnailImage())
                .detailImage(product.getDetailImage())
                .category(product.getCategory())
                .manufacturer(product.getManufacturer())
                .country(product.getCountry())
                .build();
    }

    /**
     * DTO를 Entity로 변환
     */
    public Product toEntity() {
        return Product.builder()
                .id(this.id)
                .name(this.name)
                .description(this.description)
                .price(this.price)
                .salePrice(this.salePrice)
                .stock(this.stock)
                .thumbnailImage(this.thumbnailImage)
                .detailImage(this.detailImage)
                .category(this.category)
                .manufacturer(this.manufacturer)
                .country(this.country)
                .build();
    }
}
