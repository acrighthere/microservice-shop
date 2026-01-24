package org.acrighthere.order.controller;

import lombok.RequiredArgsConstructor;
import org.acrighthere.order.dto.OrderRequest;
import org.acrighthere.order.dto.OrderResponse;
import org.acrighthere.order.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse placeOrder(@RequestBody OrderRequest orderRequest)
    {
        return orderService.placeOrder(orderRequest);
    }
}
