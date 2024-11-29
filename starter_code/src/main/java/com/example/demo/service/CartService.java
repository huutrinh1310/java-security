package com.example.demo.service;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.requests.ModifyCartRequest;

import java.util.List;

public interface CartService {
    Cart addToCart(ModifyCartRequest cart);

    Cart removeFromcart(ModifyCartRequest cart);

    List<Cart> getCarts();
}
