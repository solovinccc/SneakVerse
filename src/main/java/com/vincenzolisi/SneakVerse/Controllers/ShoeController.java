package com.vincenzolisi.SneakVerse.Controllers;

import com.vincenzolisi.SneakVerse.ModelsDTO.ShoeDTO;
import com.vincenzolisi.SneakVerse.Services.ShoeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/shoes")
public class ShoeController {

    @Autowired
    private ShoeService service;

    @GetMapping
    public List<ShoeDTO> getAllShoe() {
        return service.getAllShoe();
    }

    @GetMapping("/{id}")
    public ShoeDTO getShoeById(@PathVariable int id) {
        ShoeDTO dto = service.getShoeById(id);
        if(dto == null) {
            throw new RuntimeException("Shoe with id " + id + ", not found");
        }
        return dto;
    }

    @PostMapping
    public ShoeDTO addShoe(@RequestBody ShoeDTO dto) {
        return service.addShoe(dto);
    }

    @PutMapping("/{id}")
    public ShoeDTO updateShoe(@PathVariable int id, @RequestBody ShoeDTO dto) {
        ShoeDTO up = service.updateShoe(id, dto);
        if(up == null) {
            throw new RuntimeException("Shoe with id " + id + ", not found");
        }
        return up;
    }

    @DeleteMapping("/{id}")
    public String deleteShoe(@PathVariable int id) {
        boolean deleted = service.deleteShoe(id);
        if(!deleted) {
            throw new RuntimeException("Shoe with id " + id + ", not found");
        }
        return "Shoe deleted successfully";
    }

}
