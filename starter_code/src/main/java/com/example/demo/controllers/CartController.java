package com.example.demo.controllers;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.requests.ModifyCartRequest;
import com.example.demo.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping
    public ResponseEntity<List<Cart>> getCarts() {
        return ResponseEntity.ok(cartService.getCarts());
    }

    @PostMapping("/addToCart")
    public ResponseEntity<Cart> addToCart(@RequestBody ModifyCartRequest request) {
        Cart cart = cartService.addToCart(request);
        return ResponseEntity.ok(cart);

    }

    @PostMapping("/removeFromCart")
    public ResponseEntity<Cart> removeFromCart(@RequestBody ModifyCartRequest request) {
        Cart cart = cartService.removeFromcart(request);
        return ResponseEntity.ok(cart);

    }

}
