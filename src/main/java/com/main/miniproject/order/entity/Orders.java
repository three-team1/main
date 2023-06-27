package com.main.miniproject.order.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import javax.persistence.Table;

import com.main.miniproject.payment.entity.Payment;
import com.main.miniproject.user.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class Orders {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_id")
	private Long id;
	
	@Column(name = "order_date")
	private LocalDateTime orderDate = LocalDateTime.now();

	@Column(name = "order_total_price")			// 결제 최종 금액
	private int orderTotalPrice;
	
	@Column(name = "order_address1")
	private String orderPostCode;
	
	@Column(name = "order_address2")
	private String orderAddress;
	
	@Column(name = "order_address3")
	private String orderDetailAddress;
	
	@Column(name = "order_tel")
	private String orderTel;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "payment_id")
	private Payment payment;
	
	
	
	
}

