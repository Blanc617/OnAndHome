package com.onandhome.order.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.onandhome.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order")
public class Order {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

	private String productName;
	private int quantity;
	private int totalPrice;

	// ✅ 회원 정보 추가 (ManyToOne 관계)
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	public void setCreatedAt(LocalDateTime now) {
		// TODO Auto-generated method stub

	}

	public void setStatus(String string) {
		// TODO Auto-generated method stub

	}

	public void setItems(List<OrderItem> items) {
		// TODO Auto-generated method stub

	}

    public void setUser(User user) {
    }

    public void setOrderNumber(String substring) {
    }

    public void setTotalAmount(double total) {
    }

    public void setPaidAt(LocalDateTime now) {
    }

    public String getStatus() {
        return null;
    }

}
