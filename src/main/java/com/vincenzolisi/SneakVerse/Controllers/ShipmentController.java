package com.vincenzolisi.SneakVerse.Controllers;


import com.vincenzolisi.SneakVerse.ModelsDTO.ShipmentDTO;
import com.vincenzolisi.SneakVerse.Services.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/shipments")
public class ShipmentController {

    @Autowired
    private ShipmentService service;

    @GetMapping
    public List<ShipmentDTO> getAllShipment() {
        return service.getAllShipment();
    }

    @GetMapping("/{id}")
    public ShipmentDTO getShipmentById(@PathVariable int id) {
        ShipmentDTO dto = service.getShipmentById(id);
        if(dto == null) {
            throw new RuntimeException("Shipment with id " + id + ", not found");
        }
        return dto;
    }

    @PostMapping
    public ShipmentDTO addShipment(@RequestBody ShipmentDTO dto) {
        return service.addShipment(dto);
    }

    @PutMapping("/{id}")
    public ShipmentDTO updateShipment(@PathVariable int id, @RequestBody ShipmentDTO dto) {
        ShipmentDTO up = service.updateShipment(id, dto);
        if(up == null) {
            throw new RuntimeException("Shipment with id " + id + ", not found");
        }
        return up;
    }

    @DeleteMapping("/{id}")
    public String deleteShipment(@PathVariable int id) {
        boolean deleted = service.deleteShipment(id);
        if(!deleted) {
            throw new RuntimeException("Shipment with id " + id + ", not found");
        }
        return "Shipment deleted successfully";
    }
}

