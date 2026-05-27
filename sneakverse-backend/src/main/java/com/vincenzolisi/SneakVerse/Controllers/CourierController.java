package com.vincenzolisi.SneakVerse.Controllers;

import com.vincenzolisi.SneakVerse.ModelsDTO.CourierDTO;
import com.vincenzolisi.SneakVerse.Services.CourierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/couriers")
public class CourierController {

    @Autowired
    private CourierService service;

    @GetMapping
    public List<CourierDTO> getAllCourier() {
        return service.getAllCourier();
    }

    @GetMapping("/{id}")
    public CourierDTO getCourierById(@PathVariable int id){
        CourierDTO dto = service.getCourierById(id);
        if(dto == null) {
            throw new RuntimeException("Courier with id " + id + ", not found");
        }
        return dto;
    }

    @PostMapping
    public CourierDTO addCourier(@RequestBody CourierDTO dto){
        return service.addCourier(dto);
    }

    @PutMapping("/{id}")
    public CourierDTO updateCourier(@PathVariable int id, @RequestBody CourierDTO dto) {
        CourierDTO up = service.updateCourier(id, dto);
        if(up == null) {
            throw new RuntimeException("Courier with id " + id + ", not found");
        }
        return up;
    }

    @DeleteMapping("/{id}")
    public String deleteCourier(@PathVariable int id) {
        boolean deleted = service.deleteCourier(id);
        if(!deleted){
            throw new RuntimeException("Courier with id " + id + ", not found");
        }
        return "Courier eliminated successfully";
    }

}
