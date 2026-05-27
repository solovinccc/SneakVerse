package com.vincenzolisi.SneakVerse.Controllers;

import com.vincenzolisi.SneakVerse.ModelsDTO.OrderItemDTO;
import com.vincenzolisi.SneakVerse.Services.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/orderitems")
public class OrderItemController {

    @Autowired
    private OrderItemService service;

    @GetMapping
    public List<OrderItemDTO> getAllOrderItem() {
        return service.getAllOrderItem();
    }

    @GetMapping("/{id}")
    public OrderItemDTO getOrderItemById(@PathVariable int id) {
        OrderItemDTO dto = service.getOrderItemById(id);
        if(dto == null) {
            throw new RuntimeException("Order item with id " + id + ", not found");
        }
        return dto;
    }

    @PostMapping
    public OrderItemDTO addOrderItem(@RequestBody OrderItemDTO dto) {
        return service.addOrderItem(dto);
    }

    @PutMapping("/{id}")
    public OrderItemDTO updateOrderItem(@PathVariable int id, @RequestBody OrderItemDTO dto) {
        OrderItemDTO up = service.updateOrderItem(id, dto);
        if(up == null) {
            throw new RuntimeException("Order item not found");
        }
        return up;
    }

    @DeleteMapping("/{id}")
    public String deleteOrderItem(@PathVariable int id) {
        boolean deleted = service.deleteOrderItem(id);
        if(!deleted) {
            throw new RuntimeException("Order item not found");
        }
        return "Order item deleted successfully";
    }
}
