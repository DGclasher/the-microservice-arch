package com.clasher.order_service.controller;

import com.clasher.order_service.dto.OrderRequest;
import com.clasher.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public void isWorking() {
        System.out.println("Yes working");
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(@RequestBody OrderRequest orderRequest) {
        try {
            orderService.placeOrder(orderRequest);
            return "Order placed successfully";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Product is not in stock";
        }
    }

}
