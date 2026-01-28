package com.vincenzolisi.SneakVerse.Services;

import com.vincenzolisi.SneakVerse.Models.Order;
import com.vincenzolisi.SneakVerse.Models.OrderItem;
import com.vincenzolisi.SneakVerse.Models.Shipment;
import com.vincenzolisi.SneakVerse.Models.User;
import com.vincenzolisi.SneakVerse.ModelsDTO.OrderDTO;
import com.vincenzolisi.SneakVerse.Repositories.OrderItemRepository;
import com.vincenzolisi.SneakVerse.Repositories.OrderRepository;
import com.vincenzolisi.SneakVerse.Repositories.ShipmentRepository;
import com.vincenzolisi.SneakVerse.Repositories.UserRepository;
import org.hibernate.annotations.AnyDiscriminatorImplicitValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    //usato per il getOrderById(), per verificare che ci sia l'utente
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ShipmentRepository shipmentRepository;

    public List<OrderDTO> getAllOrder() {
        List<Order> orders = repository.findAll();

        List<OrderDTO> ordersDTO = orders.stream()
                .map(order -> new OrderDTO(
                        order.getOrderId(),
                        order.getOrderDate(),
                        order.getUser().getUserId(),
                        order.getItems() != null
                                ? order.getItems()
                                .stream()
                                .map(item -> item.getOrderItemId())
                                .toList()
                                : List.of(),
                        order.getShipment()  != null
                                ? order.getShipment().getShipmentId()
                                : null

                ))
                .toList();
        return ordersDTO;
    }

    public OrderDTO getOrderById(int id) {
        Optional<Order> opt = repository.findById(id);
        OrderDTO dto = null;
        if(opt.isPresent()){
            Order order = opt.get();
            dto = new OrderDTO();
            dto.setOrderId(order.getOrderId());
            dto.setOrderDate(order.getOrderDate());
            dto.setUserId(order.getUser().getUserId());
            //aggiungo gli orderitems
            List<Integer> itemIds = new ArrayList<>();
            if(order.getItems() != null) {
                for(OrderItem item : order.getItems()) {
                    itemIds.add(item.getOrderItemId());
                }
            }
            dto.setOrderItemId(itemIds);
            if(order.getShipment() != null) {
                dto.setShipmentId(order.getShipment().getShipmentId());
            } else {
                dto.setShipmentId(null);
            }
        }
        return dto;
    }

    public OrderDTO addOrder(OrderDTO dto){

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Order order = new Order();
        order.setUser(user);
        order.setItems(new ArrayList<>());
        order.setShipment(null);

        order = repository.save(order);
        //dto aggiornato con id generato
        dto.setOrderId(order.getOrderId());
        dto.setOrderDate(order.getOrderDate());
        dto.setOrderItemId(new ArrayList<>()); //nel caso non ci fossero spedizioni
        dto.setShipmentId(null);

        return dto;
    }

    public OrderDTO updateOrder(int id, OrderDTO dto) {

        Optional<Order> opt = repository.findById(id);
        if(opt.isEmpty()) {
            throw new RuntimeException("Order not found");
        }

        Order order = opt.get();

        //controllo che ogni campo sia fornito
        if(dto.getOrderDate() != null) {
            order.setOrderDate(dto.getOrderDate());
        }

        if(dto.getOrderItemId() != null && !dto.getOrderItemId().isEmpty()) {
            List<OrderItem> updatedOrderItems = new ArrayList<>();
            for(Integer orderItemId : dto.getOrderItemId()) {
                OrderItem orderItem = orderItemRepository.findById(orderItemId)
                        .orElseThrow(() -> new RuntimeException("Order Item not found"));
                updatedOrderItems.add(orderItem);
            }
            order.setItems(updatedOrderItems);
        }

        if(dto.getShipmentId() != null) {
            Shipment shipment = shipmentRepository.findById(dto.getShipmentId())
                    .orElseThrow(() -> new RuntimeException("Shipment not found: " + dto.getShipmentId()));
            order.setShipment(shipment);
        }

        //ordine aggiornato
        order = repository.save(order);

        //creo il DTO aggiornato
        //updto = updatedDto
        OrderDTO updto = new OrderDTO();
        updto.setOrderId(order.getOrderId());
        updto.setOrderDate(order.getOrderDate());
        updto.setUserId(order.getUser().getUserId());
        updto.setOrderItemId(order.getItems() != null
            ? order.getItems().stream().map(OrderItem::getOrderItemId). toList()
            : List.of());
        updto.setShipmentId(order.getShipment() != null
            ? order.getShipment().getShipmentId()
            : null);
        return updto;
    }

    public boolean deleteOrder(int id) {
        if(!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
