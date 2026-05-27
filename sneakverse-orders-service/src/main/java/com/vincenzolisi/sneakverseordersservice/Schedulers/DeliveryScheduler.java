package com.vincenzolisi.sneakverseordersservice.Schedulers;

import com.vincenzolisi.sneakverseordersservice.Models.Enum.OrderStatus;
import com.vincenzolisi.sneakverseordersservice.Models.Order;
import com.vincenzolisi.sneakverseordersservice.Repositories.OrderRepository;
import com.vincenzolisi.sneakverseordersservice.Services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class DeliveryScheduler {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private EmailService emailService;

    
    @Scheduled(fixedRate = 10000)
    public void checkDeliveries() {
        LocalDateTime now = LocalDateTime.now();

        
        List<Order> ordersToDeliver = orderRepository.findByStatusAndDeliveryDateBefore(OrderStatus.SHIPPED, now);

        if(!ordersToDeliver.isEmpty()) {
            for(Order order : ordersToDeliver) {
                
                order.setStatus(OrderStatus.DELIVERED);
                orderRepository.save(order);

                
                if(order.getUserEmail() != null && !order.getUserEmail().isEmpty()) {
                    emailService.sendDeliveryConfirmation(order.getUserEmail(), order.getOrderId());
                }

                System.out.println("L'ordine #" + order.getOrderId() + " è stato consegnato.");
            }
        }
    }
}
