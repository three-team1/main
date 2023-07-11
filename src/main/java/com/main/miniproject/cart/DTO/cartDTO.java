package com.main.miniproject.cart.DTO;

import com.main.miniproject.product.entity.Product;
import com.main.miniproject.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class cartDTO {

    private Long id;

    private Product product;

    private String productImage;

    private User user;

    private Integer cartQuantity;

}
