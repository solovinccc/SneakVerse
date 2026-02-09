package com.vincenzolisi.SneakVerse.Services;

import com.vincenzolisi.SneakVerse.Models.*;
import com.vincenzolisi.SneakVerse.Models.Enum.PaymentMethod;
import com.vincenzolisi.SneakVerse.ModelsDTO.*;
import com.vincenzolisi.SneakVerse.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private LocalDateTime cutoff12h() {
        return LocalDateTime.now().minusHours(12);
    }

    private PaymentMethod parsePaymentMethod(String raw) {
        if (raw == null) throw new RuntimeException("paymentMethod mancante");
        String v = raw.trim().toUpperCase();
        try {
            return PaymentMethod.valueOf(v);
        } catch (Exception e) {
            throw new RuntimeException("paymentMethod non valido: " + raw);
        }
    }

    private Courier pickRandomCourier() {
        List<Courier> list = courierRepository.findAll();
        if (list.isEmpty()) throw new RuntimeException("Nessun corriere disponibile");
        return list.get(new Random().nextInt(list.size()));
    }

    public List<OrderDTO> getAllOrder() {
        List<Order> orders = repository.findAll();
        return orders.stream()
                .map(order -> new OrderDTO(
                        order.getOrderId(),
                        order.getOrderDate(),
                        order.getUser().getUserId(),
                        order.getPaymentMethod() != null ? order.getPaymentMethod().name() : null,
                        order.getItems() != null
                                ? order.getItems().stream().map(OrderItem::getOrderItemId).toList()
                                : List.of(),
                        order.getShipment() != null ? order.getShipment().getShipmentId() : null
                ))
                .toList();
    }

    public OrderDTO getOrderById(int id) {
        Optional<Order> opt = repository.findById(id);
        if (opt.isEmpty()) return null;

        Order order = opt.get();

        OrderDTO dto = new OrderDTO();
        dto.setOrderId(order.getOrderId());
        dto.setOrderDate(order.getOrderDate());
        dto.setUserId(order.getUser().getUserId());
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
        out.setUserId(order.getUser().getUserId());
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
        updto.setUserId(order.getUser().getUserId());
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
    public OrderViewDTO checkout(CheckoutRequestDTO req) {
        if (req == null) throw new RuntimeException("Request null");
        if (req.getUserId() == null) throw new RuntimeException("userId mancante");
        if (req.getItems() == null || req.getItems().isEmpty()) throw new RuntimeException("Carrello vuoto");

        User user = userRepository.findById(req.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        PaymentMethod method = parsePaymentMethod(req.getPaymentMethod());

        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setPaymentMethod(method);
        order.setItems(new ArrayList<>());
        order.setShipment(null);

        order = repository.save(order);

        List<OrderItem> items = new ArrayList<>();
        for (CheckoutItemDTO it : req.getItems()) {
            if (it == null || it.getShoeId() == null) continue;
            int q = it.getQuantity() == null ? 0 : it.getQuantity();
            if (q <= 0) continue;

            Shoe shoe = shoeRepository.findById(it.getShoeId())
                    .orElseThrow(() -> new RuntimeException("Shoe not found: " + it.getShoeId()));

            OrderItem oi = new OrderItem();
            oi.setOrder(order);
            oi.setShoe(shoe);
            oi.setQuantity(q);
            items.add(oi);
        }

        if (items.isEmpty()) throw new RuntimeException("Carrello vuoto");

        items = orderItemRepository.saveAll(items);
        order.setItems(items);

        Courier courier = pickRandomCourier();

        Shipment shipment = new Shipment();
        shipment.setOrder(order);
        shipment.setCourier(courier);
        shipment = shipmentRepository.save(shipment);

        order.setShipment(shipment);

        return toOrderViewDTO(order);
    }

    public List<OrderViewDTO> getOrdersLast12hByUser(int userId) {
        LocalDateTime after = cutoff12h();
        List<Order> orders = repository.findByUser_UserIdAndOrderDateAfterOrderByOrderDateDesc(userId, after);
        return orders.stream().map(this::toOrderViewDTO).toList();
    }

    private OrderViewDTO toOrderViewDTO(Order o) {
        OrderViewDTO dto = new OrderViewDTO();

        dto.setOrderId(o.getOrderId());
        dto.setOrderDate(o.getOrderDate());
        dto.setUserId(o.getUser().getUserId());
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
