package com.example.demo.service.impl;

import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    @Override
    public UserOrder submitOrder(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(()-> {
            throw new RuntimeException("User not found");
        });
        UserOrder order = UserOrder.createFromCart(user.getCart());
        orderRepository.save(order);
        return order;
    }

    @Override
    public List<UserOrder> getOrdersForUser(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(()-> {
            throw new RuntimeException("User not found");
        });
        return orderRepository.findByUser(user);
    }
}
