package org.acrighthere.order.service;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.acrighthere.order.client.InventoryClient;
import org.acrighthere.order.dto.OrderRequest;
import org.acrighthere.order.dto.OrderResponse;
import org.acrighthere.order.event.OrderPlacedEvent;
import org.acrighthere.order.model.Order;
import org.acrighthere.order.repository.OrderRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;
    private final KafkaTemplate<String,OrderPlacedEvent> kafkaTemplate;
    public OrderResponse placeOrder(OrderRequest orderRequest){
        boolean isProductInStock = inventoryClient.isInStock(orderRequest.skuCode(),orderRequest.quantity());

        if (isProductInStock){
            Order order = new Order();
            order.setOrderNumber(UUID.randomUUID().toString());
            order.setPrice(orderRequest.price());
            order.setQuantity(orderRequest.quantity());
            order.setSkuCode(orderRequest.skuCode());
            log.info("Order {} has been placed", order.getOrderNumber());
            orderRepository.save(order);
            OrderPlacedEvent orderPlacedEvent = new OrderPlacedEvent(order.getOrderNumber(),orderRequest.userDetails().email());
            log.info("Start sending OrderPlacedEvent {} to Kafka topic order-placed", orderPlacedEvent);
            kafkaTemplate.send("order-placed",orderPlacedEvent);
            log.info("End sending OrderPlacedEvent {} to Kafka topic order-placed", orderPlacedEvent);
            return new OrderResponse(order.getId(),order.getOrderNumber(),order.getSkuCode(),order.getPrice(),order.getQuantity());
        } else {
            throw new RuntimeException("Product with skuCode "+orderRequest.skuCode()+" is not in stock");
        }

    }
}
