package com.onandhome.order.entity;

import com.onandhome.product.entity.Product;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long productId;
	private String productName;
	private int unitPrice;
	private int quantity;

	@ManyToOne(fetch = FetchType.LAZY)
	private Order order;

    public void setProduct(Product product) {
    }

    public void setPrice(int price) {

    }
}