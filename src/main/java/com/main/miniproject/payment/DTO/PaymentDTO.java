package com.main.miniproject.payment.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO {

	private String pg;
	
	private String pay_method;
	
	private String imp_uid;
	
	private String merchant_uid;
	
	private String name;
	
	private int amount;
	
	private String buyer_tel;
	
	private String buyer_addr;
	
	private String buyer_postcode;
	
	private String buyer_detailAddr;
	
	private List<Long> productId;
	
	private List<Integer> quantity;
	
//	 buyer_email: user.email,
//     buyer_name: user.username,
//     buyer_tel: user.tel,
//     buyer_addr: user.my_address,
//     buyer_postcode: user.my_postcode,
	
}
