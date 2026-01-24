package org.acrighthere.order.service;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.acrighthere.order.dto.OrderRequest;
import org.acrighthere.order.dto.OrderResponse;
import org.acrighthere.order.model.Order;
import org.acrighthere.order.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    public OrderResponse placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setPrice(orderRequest.price());
        order.setQuantity(orderRequest.quantity());
        order.setSkuCode(orderRequest.skuCode());
        log.info("Order {} has been placed", order.getOrderNumber());
        orderRepository.save(order);
        return new OrderResponse(order.getId(),order.getOrderNumber(),order.getSkuCode(),order.getPrice(),order.getQuantity());
    }
}
