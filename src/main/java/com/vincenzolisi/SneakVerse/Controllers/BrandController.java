package com.vincenzolisi.SneakVerse.Controllers;

import com.vincenzolisi.SneakVerse.ModelsDTO.BrandDTO;
import com.vincenzolisi.SneakVerse.Services.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/brands")
public class BrandController {

    @Autowired
    private BrandService service;

    @GetMapping
    public List<BrandDTO> getAllBrand() {
        return service.getAllBrand();
    }

    @GetMapping("/{id}")
    public BrandDTO getBrandById(@PathVariable int id) {
        BrandDTO dto = service.getBrandById(id);
        if(dto == null) {
            throw new RuntimeException("Brand with " + id + ", not found");
        }
        return dto;
    }

    @PostMapping
    public BrandDTO addBrand(@RequestBody BrandDTO dto) {
        return service.addBrand(dto);
    }

    @PutMapping("/{id}")
    public BrandDTO updateBrand(@PathVariable int id, @RequestBody BrandDTO dto) {
        BrandDTO up = service.updateBrand(id, dto);
        if(up == null) {
            throw new RuntimeException("Brand with id " + id + ", not found");
        }
        return up;
    }

    @DeleteMapping("/{id}")
    public String deleteBrand(@PathVariable int id) {
        boolean deleted = service.deleteBrand(id);
        if(!deleted) {
            throw new RuntimeException("Brand with id " + id + ", not found");
        }
        return "Brand deleted successfully";
    }
}
