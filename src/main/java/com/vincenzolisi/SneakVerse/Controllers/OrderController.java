package com.vincenzolisi.SneakVerse.Controllers;

import com.vincenzolisi.SneakVerse.Models.Order;
import com.vincenzolisi.SneakVerse.ModelsDTO.CheckoutRequestDTO;
import com.vincenzolisi.SneakVerse.ModelsDTO.OrderDTO;
import com.vincenzolisi.SneakVerse.ModelsDTO.OrderViewDTO;
import com.vincenzolisi.SneakVerse.Services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/orders")
public class OrderController {

    @Autowired
    private OrderService service;

    @GetMapping
    public List<OrderDTO> getAllOrder() {
        return service.getAllOrder();
    }

    @GetMapping("/{id}")
    public OrderDTO getOrderById(@PathVariable int id) {
        OrderDTO dto = service.getOrderById(id);
        if(dto == null) {
            throw new RuntimeException("Order with " + id + " id, not found");
        }
        return dto;
    }

    @PostMapping
    public OrderDTO addOrder(@RequestBody OrderDTO dto){
        return service.addOrder(dto);
    }

    @PutMapping("/{id}")
    public OrderDTO updateOrder(@PathVariable int id, @RequestBody OrderDTO dto) {
        OrderDTO up = service.updateOrder(id, dto);
        if(up == null) {
            throw new RuntimeException("Order with id " + id + ", not found");
        }
        return up;
    }

    @DeleteMapping("/{id}")
    public String deleteOrder(@PathVariable int id) {
        boolean deleted = service.deleteOrder(id);
        if(!deleted) {
            throw new RuntimeException("Order with id " + id + ", not found");
        }
        return "Order deleted successfully";
    }


    @PostMapping("/checkout")
    public OrderViewDTO checkout(@RequestBody CheckoutRequestDTO req) {
        return service.checkout(req);
    }


    @GetMapping("/user/{userId}")
    public List<OrderViewDTO> getUserOrdersLast12h(@PathVariable int userId) {
        return service.getOrdersLast12hByUser(userId);
    }

}
