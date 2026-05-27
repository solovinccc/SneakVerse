package com.vincenzolisi.sneakverseordersservice.Events;

import com.vincenzolisi.sneakverseordersservice.Clients.ProductsClient;
import com.vincenzolisi.sneakverseordersservice.Models.Courier;
import com.vincenzolisi.sneakverseordersservice.Models.Enum.OrderStatus;
import com.vincenzolisi.sneakverseordersservice.Models.Order;
import com.vincenzolisi.sneakverseordersservice.Models.OrderItem;
import com.vincenzolisi.sneakverseordersservice.Models.Shipment;
import com.vincenzolisi.sneakverseordersservice.Repositories.CourierRepository;
import com.vincenzolisi.sneakverseordersservice.Repositories.OrderItemRepository;
import com.vincenzolisi.sneakverseordersservice.Repositories.OrderRepository;
import com.vincenzolisi.sneakverseordersservice.Repositories.ShipmentRepository;
import com.vincenzolisi.sneakverseordersservice.Services.EmailService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class OrderEventListener {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ShipmentRepository shipmentRepository;

    @Autowired
    private CourierRepository courierRepository;

    @Autowired
    private EmailService emailServices;

    @Autowired
    private ProductsClient productsClient;

    
    private Courier pickRandomCourier() {
        List<Courier> list = courierRepository.findAll();
        return list.get(new Random().nextInt(list.size()));
    }

    
    @KafkaListener(topics = "new_orders", groupId = "orders-group", containerFactory = "kafkaListenerContainerFactory")
    @Transactional
    public void handleOrderPlacedEvent(OrderPlacedEvent event) {
        System.out.println("\n[KAFKA] Received new order, id event: " + event.getEventId());
        System.out.println("User ID: " + event.getUserId() + " Payment method: " + event.getPaymentMethod());

        
        Order order = new Order();
        order.setUserId(event.getUserId());
        order.setUserEmail(event.getUserEmail());
        order.setOrderDate(java.time.LocalDateTime.parse(event.getTimestamp()));
        order.setPaymentMethod(event.getPaymentMethod());
        order.setStatus(OrderStatus.PROCESSING);

        
        List<OrderItem> items = new ArrayList<>();
        BigDecimal orderTotal = BigDecimal.ZERO;

        for(OrderPlacedItemEvent itemEvent : event.getItems()) {
            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setShoeId(itemEvent.getShoeId());
            item.setQuantity(itemEvent.getQuantity());

            try {
                
                ProductsClient.ShoeInfo shoeInfo = productsClient.getShoeById(itemEvent.getShoeId());
                item.setShoePrice(shoeInfo.shoePrice);
                item.setShoeName(shoeInfo.shoeName);

                BigDecimal lineTotal = shoeInfo.shoePrice.multiply(new BigDecimal(itemEvent.getQuantity()));
                orderTotal = orderTotal.add(lineTotal);
            } catch (Exception e) {
                System.err.println("Errore nel recupero della scarpa ID " + itemEvent.getShoeId() + ". Prezzo impostato a 0.");
                item.setShoePrice(BigDecimal.ZERO);
                item.setShoeName("Sconosciuto");
            }

            items.add(item);
        }

        
        order.setTotal(orderTotal);

        
        order = orderRepository.save(order);
        orderItemRepository.saveAll(items);
        System.out.println("[DATABASE] Order " + order.getOrderId() + " saved. Total: €" + orderTotal);

        
        Courier courier = pickRandomCourier();
        Shipment shipment = new Shipment();
        shipment.setOrder(order);
        shipment.setCourier(courier);
        shipmentRepository.save(shipment);

        
        try {
            if(order.getUserEmail() != null && !order.getUserEmail().isEmpty()) {
                emailServices.sendOrderConfirmation(order.getUserEmail(), order.getOrderId());
                emailServices.sendCourierChoiceEmail(order.getUserEmail(), order.getOrderId());
            }
        } catch (Exception e) {
            System.err.println("Email fail: " + e.getMessage());
        }

    }

}
