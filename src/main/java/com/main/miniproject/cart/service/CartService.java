package com.main.miniproject.cart.service;

import com.main.miniproject.cart.entity.Cart;
import com.main.miniproject.cart.repository.CartRepository;
import com.main.miniproject.qna.repository.QnaRepository;
import com.main.miniproject.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;

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
}
