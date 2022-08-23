package com.hackaton.kurly.domain.order;

import com.hackaton.kurly.domain.order.dto.PatchOrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public Page<Order> findOrders(Pageable pageable){
        return orderRepository.findAll(pageable);
    }

    public Optional<Order> findOneOrderById(Long orderId){
        return orderRepository.findById(orderId);
    }

    public void update(Order order, PatchOrderRequest request) {
        order.updateStatus(request);
        orderRepository.save(order);
    }

    public Order save(Order order){
        order= orderRepository.save(order);
        return order;
    }
}
