package org.acrighthere.order.controller;

import lombok.RequiredArgsConstructor;
import org.acrighthere.order.dto.OrderRequest;
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
    public String placeOrder(@RequestBody OrderRequest orderRequest)
    {
        orderService.placeOrder(orderRequest);
        return "Order Placed Successfully";
    }
}
