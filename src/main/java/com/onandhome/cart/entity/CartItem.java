package com.onandhome.cart.entity;

import com.onandhome.product.entity.Product;
import com.onandhome.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CartItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	private User user;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	private Product product;

	private int quantity;

}
