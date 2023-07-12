package com.main.miniproject.payment.service;

import java.util.List;

import javax.transaction.Transactional;

import com.main.miniproject.cart.entity.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.miniproject.cart.repository.CartRepository;
import com.main.miniproject.order.entity.OrderItem;
import com.main.miniproject.order.entity.Orders;
import com.main.miniproject.order.orderItemRepository.OrderItemRepository;
import com.main.miniproject.order.ordersRepository.OrdersRepository;
import com.main.miniproject.payment.DTO.PaymentDTO;
import com.main.miniproject.payment.entity.Payment;
import com.main.miniproject.payment.repository.PaymentRepository;
import com.main.miniproject.product.entity.Product;
import com.main.miniproject.product.repository.ProductRepository;
import com.main.miniproject.user.entity.User;
import com.main.miniproject.user.service.UserDetail;

@Service
public class PaymentService {

	private OrdersRepository ordersRepository;
	
	private OrderItemRepository orderItemRepository;
	
	private ProductRepository productRepository;
	
	private PaymentRepository paymentRepository;
	
	private CartRepository cartRepository;
	
	@Autowired
	public PaymentService(OrdersRepository ordersRepository, OrderItemRepository orderItemRepository,ProductRepository productRepository
			,PaymentRepository paymentRepository,CartRepository cartRepository) {
		this.ordersRepository = ordersRepository;
		this.orderItemRepository = orderItemRepository;
		this.productRepository = productRepository;
		this.paymentRepository = paymentRepository;
		this.cartRepository = cartRepository;
	}
	
	
	 	@Transactional
	    public void processPayment(PaymentDTO paymentDTO, UserDetail userDetail) {

	 		User user = userDetail.getUser();
	 		
	 		Payment payment = new Payment();
	 		payment.setImpUid(paymentDTO.getImp_uid());
	 		payment.setMerchantUid(paymentDTO.getMerchant_uid());
	 		payment.setPaymentPlan(paymentDTO.getPay_method());
	 		
	 		paymentRepository.save(payment);	
	 		
	        Orders order = new Orders();
	        order.setOrderTotalPrice(paymentDTO.getAmount());
	        order.setOrderTel(paymentDTO.getBuyer_tel());
	        order.setOrderAddress(paymentDTO.getBuyer_addr());
	        order.setOrderPostCode(paymentDTO.getBuyer_postcode());
	        order.setOrderDetailAddress(paymentDTO.getBuyer_detailAddr());
	        order.setUser(user);
	        order.setPayment(payment);

			System.out.println(paymentDTO.getAmount());
	        
	        order = ordersRepository.save(order); // save order and get saved entity back
	        
	        List<Long> productIds = paymentDTO.getProductId();
	        List<Integer> quantities = paymentDTO.getQuantity();

	        System.out.println("productId : " + productIds);
	        
	        for (int i = 0; i < productIds.size(); i++) {
	            OrderItem orderItem = new OrderItem();
	            Product product = productRepository.findById(productIds.get(i)).get();	            
	            
	            orderItem.setOrder(order);
	            orderItem.setProduct(product);
	            orderItem.setOrderQuantity(quantities.get(i));
	            
	            product.setProductQuantity(product.getProductQuantity() - quantities.get(i));
	            productRepository.save(product);
	            
	            orderItemRepository.save(orderItem);

				cartRepository.deleteByProductIdAndUser(productIds.get(i), user);
	        }

	    }
	 	
	 	public void validateGetQuantity(Product product, int orderQuantity) throws Exception {
	 	    int productQuantity = product.getProductQuantity();
	 	    if(productQuantity < orderQuantity) {
	 	        throw new Exception(product.getProductTitle() + " 상품의 재고를 확인해주세요.");
	 	    }
	 	}
	 	
	
	 	
	 	
	 	

	
}
