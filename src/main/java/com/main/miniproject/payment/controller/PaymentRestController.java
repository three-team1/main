package com.main.miniproject.payment.controller;


import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.main.miniproject.payment.DTO.PaymentDTO;
import com.main.miniproject.payment.service.PaymentService;
import com.main.miniproject.product.entity.Product;
import com.main.miniproject.product.repository.ProductRepository;
import com.main.miniproject.user.service.UserDetail;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;

@lombok.extern.slf4j.Slf4j
@RestController
public class PaymentRestController {
	
	
	private IamportClient iamportClient;
	
	private PaymentService paymentService;
	
	private ProductRepository productRepository;
	
	@Autowired
	public PaymentRestController(PaymentService paymentService,ProductRepository productRepository) {
        this.iamportClient = new IamportClient("6726834646432386", "j3ibQpza6I2mvrN9WFcOnEeFTarl5A3U9vZDS2jnUNImUu4xyHBRjogsegfEfVEGd38rK5qxGgEflHI5");
		this.paymentService = paymentService;
		this.productRepository = productRepository;
		
	}
	
	
	// 여기서 사용된 Payment 객체는 Entity가아닌 IamPort에서 지원하는 Payment 객체임@@@@
    @PostMapping("/verifyIamport/{imp_uid}")
    public IamportResponse<Payment> paymentByImpUid(@PathVariable String imp_uid) throws IamportResponseException, IOException{
        log.info("paymentByImpUid 진입" + imp_uid);
        return iamportClient.paymentByImpUid(imp_uid);
    }
    
    @PostMapping("/api/payment")
    public ResponseEntity<Void> processPayment(@RequestBody PaymentDTO paymentDTO, @AuthenticationPrincipal UserDetail userDetail) {	
        paymentService.processPayment(paymentDTO,userDetail);
        return ResponseEntity.ok().build();  // response can be adjusted based on your requirements
    }
    
    @GetMapping("/api/product/{productId}/validateQuantity")
    public ResponseEntity<?> validateQuantity(@PathVariable Long productId, @RequestParam int orderQuantity) {
        Product product = productRepository.findById(productId).get();
        try {
            paymentService.validateGetQuantity(product, orderQuantity);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }
    
	
}
	

