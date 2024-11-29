package com.example.demo.service.impl;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.requests.ModifyCartRequest;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.ItemRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final ItemRepository itemRepository;

    @Override
    public Cart addToCart(ModifyCartRequest cartRequest) {
        User user = userRepository.findByUsername(cartRequest.getUsername()).orElseThrow(()-> {
            throw new RuntimeException("User not found");
        });

        Optional<Item> item = itemRepository.findById(cartRequest.getItemId());
        if (!item.isPresent()) {
            throw new RuntimeException("Item not found");
        }
        Cart cart = user.getCart();
        IntStream.range(0, cartRequest.getQuantity())
                .forEach(i -> cart.addItem(item.get()));
        cartRepository.save(cart);
        return cart;
    }

    @Override
    public Cart removeFromcart(ModifyCartRequest cartRequest) {
        User user = userRepository.findByUsername(cartRequest.getUsername()).orElseThrow(()-> {
            throw new RuntimeException("User not found");
        });
        Optional<Item> item = itemRepository.findById(cartRequest.getItemId());
        if (!item.isPresent()) {
            throw new RuntimeException("Item not found");
        }
        Cart cart = user.getCart();
        IntStream.range(0, cartRequest.getQuantity())
                .forEach(i -> cart.removeItem(item.get()));
        cartRepository.save(cart);
        return cart;
    }

    @Override
    public List<Cart> getCarts() {
        return cartRepository.findAll();
    }
}
