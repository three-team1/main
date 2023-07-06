package com.main.miniproject.cart.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.main.miniproject.cart.entity.Cart;
import com.main.miniproject.cart.repository.CartRepository;
import com.main.miniproject.product.repository.ProductRepository;
import com.main.miniproject.user.entity.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    
    public Cart getCart(Long id){
        Cart cart = new Cart();

        if(cartRepository.findById(id).isPresent()){
            cart = cartRepository.findById(id).get();
        }
        return cart;
    }

    public List<Cart> getList(User user) {

        return cartRepository.findAllByUser(user);
    }

    public void delCart(Long id) {
        cartRepository.deleteById(id);	

    }
    public void updateCart(Cart cart) {
        cartRepository.save(cart);
    }

    public void insertCart(Cart cart) {

        cartRepository.save(cart);

    }
    
    public int countCart(User user) {
    			
    List<Cart> cartUserList = cartRepository.findAllByUser(user);  
    	
    	return cartUserList.size();
    }
}
