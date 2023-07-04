package com.main.miniproject.payment.controller;


import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.main.miniproject.payment.DTO.PaymentDTO;
import com.main.miniproject.payment.service.PaymentService;
import com.main.miniproject.user.service.UserDetail;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;

import lombok.RequiredArgsConstructor;

@lombok.extern.slf4j.Slf4j
@RestController
@RequiredArgsConstructor
public class PaymentRestController {
	
	
	private final IamportClient iamportClient;
	
	private final PaymentService paymentService;
	
	@Autowired
	public PaymentRestController(PaymentService paymentService) {
		this.iamportClient = new IamportClient("6726834646432386", "R5GWEp4mtCCyGtQDDw07QCCg0YZo0qcABi9YedKuvMTRSriI2HQmhYA5a8sHWE96VicmlAb5UPOohGWE");
		this.paymentService = paymentService;
		
	}
	
	
	// 여기서 사용된 Payment 객체는 Entity가아닌 IamPort에서 지원하는 Payment 객체임@@@@
    @PostMapping("/verifyIamport/{imp_uid}")
    public IamportResponse<Payment> paymentByImpUid(@PathVariable String imp_uid) throws IamportResponseException, IOException{
        log.info("paymentByImpUid 진입");
        return iamportClient.paymentByImpUid(imp_uid);
    }
    
    @PostMapping("/api/payment")
    public ResponseEntity<Void> processPayment(@RequestBody PaymentDTO paymentDTO, @AuthenticationPrincipal UserDetail userDetail) {	
        paymentService.processPayment(paymentDTO,userDetail);
        return ResponseEntity.ok().build();  // response can be adjusted based on your requirements
    }
    
	
}
	

