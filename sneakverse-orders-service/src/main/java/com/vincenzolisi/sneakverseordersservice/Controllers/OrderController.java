package com.vincenzolisi.sneakverseordersservice.Controllers;

import com.vincenzolisi.sneakverseordersservice.Models.Enum.OrderStatus;
import com.vincenzolisi.sneakverseordersservice.Models.Order;
import com.vincenzolisi.sneakverseordersservice.Repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/internal/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping
    public List<Order> getAllOrdersForAdmin() {
        return orderRepository.findAll();
    }

    @GetMapping("/user/{userId}")
    public List<Order> getActiveOrderByUser(@PathVariable Integer userId) {
        return orderRepository.findByUserIdAndStatusNotOrderByOrderDateDesc(userId,
                OrderStatus.DELIVERED);
    }

    @PutMapping("/{orderId}/deliver")
    public void markOrderAsDelivered(@PathVariable Integer orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(OrderStatus.DELIVERED);
        orderRepository.save(order);
    }

    @GetMapping("/{orderId}/schedule")
    public String scheduleDelivery(@PathVariable Integer orderId, @RequestParam int days) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        LocalDateTime deliveryTime = LocalDateTime.now().plusSeconds(days);

        order.setDeliveryDate(deliveryTime);
        order.setStatus(OrderStatus.SHIPPED);
        orderRepository.save(order);

        String expectedDate = deliveryTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

        return "<html><body style='font-family: sans-serif; text-align: center; margin-top: 50px;'>" +
                "<h2>Perfetto!</h2><p>La consegna dell'ordine #" + orderId +
                " è stata programmata per il <strong>" + expectedDate + "</strong>.</p></body></html>";
    }
}
