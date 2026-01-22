package org.acrighthere.order.service;


import lombok.AllArgsConstructor;
import org.acrighthere.order.dto.OrderRequest;
import org.acrighthere.order.model.Order;
import org.acrighthere.order.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    public void placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setPrice(orderRequest.price());
        order.setQuantity(orderRequest.quantity());
        order.setSkuCode(orderRequest.skuCode());

        orderRepository.save(order);
    }
}
