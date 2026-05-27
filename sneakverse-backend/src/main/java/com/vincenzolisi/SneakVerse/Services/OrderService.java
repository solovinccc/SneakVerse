package com.vincenzolisi.SneakVerse.Services;

import com.vincenzolisi.SneakVerse.Events.OrderPlacedEvent;
import com.vincenzolisi.SneakVerse.Events.OrderPlacedItemEvent;
import com.vincenzolisi.SneakVerse.Models.*;
import com.vincenzolisi.SneakVerse.Models.Enum.PaymentMethod;
import com.vincenzolisi.SneakVerse.ModelsDTO.*;
import com.vincenzolisi.SneakVerse.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ShipmentRepository shipmentRepository;

    @Autowired
    private ShoeRepository shoeRepository;

    @Autowired
    private CourierRepository courierRepository;

    @Autowired
    private org.springframework.kafka.core.KafkaTemplate<String, com.vincenzolisi.SneakVerse.Events.OrderPlacedEvent> kafkaTemplate;

    private final String ORDER_TOPIC = "new_orders";

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${orders.service.url:http://localhost:8082/api/internal/orders}")
    private String MICROSERVICE_URL;

    private PaymentMethod parsePaymentMethod(String raw) {
        if (raw == null) throw new RuntimeException("paymentMethod null");
        String v = raw.trim().toUpperCase();
        try {
            return PaymentMethod.valueOf(v);
        } catch (Exception e) {
            throw new RuntimeException("paymentMethod not valid: " + raw);
        }
    }

    private Courier pickRandomCourier() {
        List<Courier> list = courierRepository.findAll();
        if (list.isEmpty()) throw new RuntimeException("No courier available");
        return list.get(new Random().nextInt(list.size()));
    }

    public List<OrderViewDTO> getAllOrder() {
        List<OrderViewDTO> result = new ArrayList<>();

        try {
            OSOrderResponse[] rawOrders = restTemplate.getForObject(MICROSERVICE_URL, OSOrderResponse[].class);
            if(rawOrders == null) return result;
            for(OSOrderResponse raw : rawOrders) {
                 result.add(composeOrderView(raw));
             }
        } catch (Exception e) {
            System.err.println("Communication error whit admin microservice: " + e.getMessage());
        }
        return result;
    }

    public OrderViewDTO composeOrderView(OSOrderResponse raw) {
        OrderViewDTO view = new OrderViewDTO();
        view.setOrderId(raw.orderId);
        view.setOrderDate(raw.orderDate);
        view.setUserId(raw.userId);
        view.setPaymentMethod(raw.paymentMethod);
        view.setStatus(raw.status);

        if (raw.shipment != null) {
            view.setShipmentId(raw.shipment.shipmentId);
            if (raw.shipment.courier != null) {
                view.setCourierId(raw.shipment.courier.courierId);
                view.setCourierPhoneNumber(raw.shipment.courier.phoneNumber);
            }
        }


        BigDecimal total = BigDecimal.ZERO;
        List<OrderItemViewDTO> viewItems = new ArrayList<>();

        if (raw.items != null) {
            for (OSOrderResponse.MicroItem rawItem : raw.items) {
                OrderItemViewDTO itemView = new OrderItemViewDTO();
                itemView.setShoeId(rawItem.shoeId);
                itemView.setQuantity(rawItem.quantity);

                
                Optional<Shoe> shoeOpt = shoeRepository.findById(rawItem.shoeId);
                if (shoeOpt.isPresent()) {
                    Shoe shoe = shoeOpt.get();
                    itemView.setShoeName(shoe.getShoeName());
                    itemView.setShoeSize(shoe.getShoeSize());
                    itemView.setShoePrice(shoe.getShoePrice());

                    BigDecimal lineTotal = shoe.getShoePrice().multiply(BigDecimal.valueOf(rawItem.quantity));
                    total = total.add(lineTotal);
                }
                viewItems.add(itemView);
            }
        }
        view.setTotal(total);
        view.setItems(viewItems);
        return view;
    }



    public OrderDTO getOrderById(int id) {
        Optional<Order> opt = repository.findById(id);
        if (opt.isEmpty()) return null;

        Order order = opt.get();

        OrderDTO dto = new OrderDTO();
        dto.setOrderId(order.getOrderId());
        dto.setOrderDate(order.getOrderDate());
        dto.setUserId(order.getUser() != null ? order.getUser().getUserId() : 0);
        dto.setPaymentMethod(order.getPaymentMethod() != null ? order.getPaymentMethod().name() : null);

        List<Integer> itemIds = new ArrayList<>();
        if (order.getItems() != null) {
            for (OrderItem item : order.getItems()) {
                itemIds.add(item.getOrderItemId());
            }
        }
        dto.setOrderItemId(itemIds);

        dto.setShipmentId(order.getShipment() != null ? order.getShipment().getShipmentId() : null);

        return dto;
    }

    public OrderDTO addOrder(OrderDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Order order = new Order();
        order.setUser(user);
        order.setItems(new ArrayList<>());
        order.setShipment(null);
        order.setOrderDate(LocalDateTime.now());
        order.setPaymentMethod(dto.getPaymentMethod() != null ? parsePaymentMethod(dto.getPaymentMethod()) : PaymentMethod.CARD);

        order = repository.save(order);

        OrderDTO out = new OrderDTO();
        out.setOrderId(order.getOrderId());
        out.setOrderDate(order.getOrderDate());
        out.setUserId(order.getUser() != null ? order.getUser().getUserId() : 0);
        out.setPaymentMethod(order.getPaymentMethod() != null ? order.getPaymentMethod().name() : null);
        out.setOrderItemId(new ArrayList<>());
        out.setShipmentId(null);

        return out;
    }

    public OrderDTO updateOrder(int id, OrderDTO dto) {
        Optional<Order> opt = repository.findById(id);
        if (opt.isEmpty()) throw new RuntimeException("Order not found");

        Order order = opt.get();

        if (dto.getOrderDate() != null) {
            order.setOrderDate(dto.getOrderDate());
        }

        if (dto.getPaymentMethod() != null && !dto.getPaymentMethod().isBlank()) {
            order.setPaymentMethod(parsePaymentMethod(dto.getPaymentMethod()));
        }

        if (dto.getOrderItemId() != null && !dto.getOrderItemId().isEmpty()) {
            List<OrderItem> updatedOrderItems = new ArrayList<>();
            for (Integer orderItemId : dto.getOrderItemId()) {
                OrderItem orderItem = orderItemRepository.findById(orderItemId)
                        .orElseThrow(() -> new RuntimeException("Order Item not found"));
                updatedOrderItems.add(orderItem);
            }
            order.setItems(updatedOrderItems);
        }

        if (dto.getShipmentId() != null) {
            Shipment shipment = shipmentRepository.findById(dto.getShipmentId())
                    .orElseThrow(() -> new RuntimeException("Shipment not found: " + dto.getShipmentId()));
            order.setShipment(shipment);
        }

        order = repository.save(order);

        OrderDTO updto = new OrderDTO();
        updto.setOrderId(order.getOrderId());
        updto.setOrderDate(order.getOrderDate());
        updto.setUserId(order.getUser() != null ? order.getUser().getUserId() : 0);
        updto.setPaymentMethod(order.getPaymentMethod() != null ? order.getPaymentMethod().name() : null);
        updto.setOrderItemId(order.getItems() != null
                ? order.getItems().stream().map(OrderItem::getOrderItemId).toList()
                : List.of());
        updto.setShipmentId(order.getShipment() != null ? order.getShipment().getShipmentId() : null);

        return updto;
    }

    public boolean deleteOrder(int id) {
        if (!repository.existsById(id)) return false;
        repository.deleteById(id);
        return true;
    }
    @Transactional
    public String checkout(CheckoutRequestDTO req) {
        if(req == null) throw new RuntimeException("Request null");
        if(req.getUserId() == null) throw new RuntimeException("userId is null");
        if(req.getItems() == null || req.getItems().isEmpty()) throw new RuntimeException("Empty cart");

        User user = userRepository.findById(req.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<OrderPlacedItemEvent> eventItems = new ArrayList<>();
        for(CheckoutItemDTO it : req.getItems()) {
            if(it != null && it.getShoeId() != null && it.getQuantity() != null && it.getQuantity() > 0) {
                shoeRepository.findById(it.getShoeId())
                        .orElseThrow(() -> new RuntimeException("Shoe not found " + it.getShoeId()));

                eventItems.add(new OrderPlacedItemEvent(it.getShoeId(), it.getQuantity()));
            }
        }

        if(eventItems.isEmpty()) throw new RuntimeException("Cart not valid");

        OrderPlacedEvent event = new OrderPlacedEvent(
                req.getUserId(),
                user.getEmail(),
                req.getPaymentMethod().toUpperCase(),
                eventItems
        );

        try {
            kafkaTemplate.send(ORDER_TOPIC, event).get();
            System.out.println("-> Event sent to kafka, topic: " + ORDER_TOPIC + " | EventId: " + event.getEventId());
        } catch (Exception e) {
            System.err.println("\n  FATAL ERROR WITH KAFKA ");
            e.printStackTrace();
            throw new RuntimeException("Impossibile inviare l'ordine a Kafka: " + e.getMessage());
        }

        return "Order received successfully, now is in process. TrackID: " + event.getEventId() + ".";
    }

    public List<OrderViewDTO> getActiveOrderByUser(int userId) {
        List<OrderViewDTO> result = new ArrayList<>();
        String url = MICROSERVICE_URL + "/user/" + userId;
        try {
            OSOrderResponse[] rawOrders = restTemplate.getForObject(url, OSOrderResponse[].class);
            if(rawOrders == null) return result;
            for(OSOrderResponse raw : rawOrders) {
                result.add(composeOrderView(raw));
            }
        } catch (Exception e) {
            System.err.println("Communication error with microservice " + e.getMessage());
        }
        return result;
    }

    public String confirmDelivery(int orderId) {
        String url = MICROSERVICE_URL + "/" + orderId + "/deliver";
        try {
            restTemplate.put(url, null);
            return "Order marked as delivered";
        } catch(Exception e) {
            throw  new RuntimeException("Failed to confirm delivery: " + e.getMessage());
        }
    }

    private OrderViewDTO toOrderViewDTO(Order o) {
        OrderViewDTO dto = new OrderViewDTO();

        dto.setOrderId(o.getOrderId());
        dto.setOrderDate(o.getOrderDate());
        dto.setUserId(o.getUser() != null ? o.getUser().getUserId() : 0);
        dto.setPaymentMethod(o.getPaymentMethod() != null ? o.getPaymentMethod().name() : null);

        BigDecimal total = BigDecimal.ZERO;
        List<OrderItemViewDTO> items = new ArrayList<>();

        if (o.getItems() != null) {
            for (OrderItem oi : o.getItems()) {
                Shoe s = oi.getShoe();
                if (s == null) continue;

                BigDecimal line = s.getShoePrice().multiply(BigDecimal.valueOf(oi.getQuantity()));
                total = total.add(line);

                OrderItemViewDTO it = new OrderItemViewDTO();
                it.setShoeId(s.getShoeId());
                it.setShoeName(s.getShoeName());
                it.setShoeSize(s.getShoeSize());
                it.setShoePrice(s.getShoePrice());
                it.setQuantity(oi.getQuantity());

                items.add(it);
            }
        }

        dto.setTotal(total);
        dto.setItems(items);

        if (o.getShipment() != null) {
            dto.setShipmentId(o.getShipment().getShipmentId());
            Courier c = o.getShipment().getCourier();
            if (c != null) {
                dto.setCourierId(c.getCourierId());
                dto.setCourierPhoneNumber(c.getPhoneNumber());
            }
        }

        return dto;
    }
}