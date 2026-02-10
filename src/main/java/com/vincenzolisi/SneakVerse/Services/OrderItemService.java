package com.vincenzolisi.SneakVerse.Services;

import com.vincenzolisi.SneakVerse.Models.Order;
import com.vincenzolisi.SneakVerse.Models.OrderItem;
import com.vincenzolisi.SneakVerse.Models.Shoe;
import com.vincenzolisi.SneakVerse.ModelsDTO.OrderItemDTO;
import com.vincenzolisi.SneakVerse.Repositories.OrderItemRepository;
import com.vincenzolisi.SneakVerse.Repositories.OrderRepository;
import com.vincenzolisi.SneakVerse.Repositories.ShoeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderItemService {

    @Autowired
    private OrderItemRepository repository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ShoeRepository shoeRepository;


    public List<OrderItemDTO> getAllOrderItem() {
        List<OrderItem> items = repository.findAll();

        List<OrderItemDTO> itemsDTO = items.stream()
                .map(item -> new OrderItemDTO(
                        item.getOrderItemId(),
                        item.getQuantity(),
                        item.getOrder() != null
                                ? item.getOrder().getOrderId()
                                : null,
                        item.getShoe() != null
                                ? item.getShoe().getShoeId()
                                : null
                ))
                .toList();
        return itemsDTO;
    }

    public OrderItemDTO getOrderItemById(int id) {
        Optional<OrderItem> opt = repository.findById(id);
        OrderItemDTO dto = null;
        if(opt.isPresent()) {
            OrderItem item = opt.get();
            dto = new OrderItemDTO();
            dto.setOrderItemId(item.getOrderItemId());
            dto.setQuantity(item.getQuantity());
            if(item.getOrder() != null) {
                dto.setOrderId(item.getOrder().getOrderId());
            } else {
                dto.setOrderId(null);
            }
            if(item.getShoe() != null) {
                dto.setShoeId(item.getShoe().getShoeId());
            } else {
                dto.setShoeId(null);
            }
        }
        return dto;
    }

    public OrderItemDTO addOrderItem(OrderItemDTO dto) {
        Order order = orderRepository.findById(dto.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        Shoe shoe = shoeRepository.findById(dto.getShoeId())
                .orElseThrow(() -> new RuntimeException("Shoe not found"));

        OrderItem item = new OrderItem();
        item.setQuantity(dto.getQuantity());
        item.setOrder(order);
        item.setShoe(shoe);
        item = repository.save(item);
        //dto
        dto.setOrderItemId(item.getOrderItemId());
        dto.setQuantity(item.getQuantity());
        dto.setOrderId(item.getOrder().getOrderId());
        dto.setShoeId(item.getShoe().getShoeId());
        return dto;
    }

    public OrderItemDTO updateOrderItem(int id, OrderItemDTO dto) {
        Optional<OrderItem> opt = repository.findById(id);
        if(opt.isEmpty()) {
            throw new RuntimeException("Order item not found");
        }

        OrderItem item = opt.get();

        if(dto.getQuantity() != null) {
            item.setQuantity(dto.getQuantity());
        }

        if(dto.getOrderId() != null) {
            Order order = orderRepository.findById(dto.getOrderId())
                    .orElseThrow(() -> new RuntimeException("Order not found"));
            item.setOrder(order);
        }

        if(dto.getShoeId() != null) {
            Shoe shoe = shoeRepository.findById(dto.getShoeId())
                    .orElseThrow(() -> new RuntimeException("Shoe not found"));
            item.setShoe(shoe);
        }

        item = repository.save(item);

        OrderItemDTO updto = new OrderItemDTO();
        updto.setOrderItemId(item.getOrderItemId());
        updto.setQuantity(item.getQuantity());
        updto.setOrderId(item.getOrder() != null
            ? item.getOrder().getOrderId()
            : null);
        updto.setShoeId(item.getShoe() != null
            ? item.getShoe().getShoeId()
            : null);
        return updto;
    }

    public boolean deleteOrderItem(int id) {
        if(!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }

}
