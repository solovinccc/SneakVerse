package com.vincenzolisi.sneakverseproducts.Services;

import com.vincenzolisi.sneakverseproducts.Models.Brand;
import com.vincenzolisi.sneakverseproducts.Models.Shoe;
import com.vincenzolisi.sneakverseproducts.ModelsDTO.ShoeDTO;
import com.vincenzolisi.sneakverseproducts.Repositories.BrandRepository;
import com.vincenzolisi.sneakverseproducts.Repositories.ShoeRepository;
import com.vincenzolisi.sneakverseproducts.Repositories.ShoeStatsJdbc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShoeService {

    @Autowired
    private ShoeRepository repository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private ShoeStatsJdbc jdbc;

    public List<ShoeDTO> getAllShoe() {
        return repository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public ShoeDTO getShoeById(int id) {
        Optional<Shoe> opt = repository.findById(id);
        return opt.map(this::mapToDTO).orElse(null);
    }

    public ShoeDTO addShoe(ShoeDTO dto) {
        Brand brand = brandRepository.findById(dto.getBrandId())
                .orElseThrow(() -> new RuntimeException("Brand not found"));

        Shoe shoe = new Shoe();
        shoe.setShoeName(dto.getShoeName());
        shoe.setShoePrice(dto.getShoePrice());

        if (dto.getShoeSize() != null) {
            String sizeStr = String.valueOf(dto.getShoeSize()).replace(",", ".");
            shoe.setShoeSize(Float.parseFloat(sizeStr));
        }

        shoe.setBrand(brand);
        shoe.setImageUrl(dto.getImageUrl());

        shoe = repository.save(shoe);
        return mapToDTO(shoe);
    }

    public ShoeDTO updateShoe(int id, ShoeDTO dto) {
        Shoe shoe = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shoe not found"));

        if (dto.getShoeName() != null) shoe.setShoeName(dto.getShoeName());
        if (dto.getShoePrice() != null) shoe.setShoePrice(dto.getShoePrice());
        if (dto.getShoeSize() != null) {
            String sizeStr = String.valueOf(dto.getShoeSize()).replace(",", ".");
            shoe.setShoeSize(Float.parseFloat(sizeStr));
        }
        if (dto.getBrandId() != null) {
            Brand brand = brandRepository.findById(dto.getBrandId())
                    .orElseThrow(() -> new RuntimeException("Brand not found"));
            shoe.setBrand(brand);
        }
        if(dto.getImageUrl() != null) shoe.setImageUrl(dto.getImageUrl());

        shoe = repository.save(shoe);
        return mapToDTO(shoe);
    }

    public boolean deleteShoe(int id) {
        if (!repository.existsById(id)) return false;
        repository.deleteById(id);
        return true;
    }

    public List<ShoeDTO> findShoesByBrandName(String brandName) {
        if (brandName == null || brandName.trim().isEmpty()) return getAllShoe();
        Brand brand = brandRepository.findByBrandNameIgnoreCase(brandName.trim()).orElse(null);
        if (brand == null) return List.of();
        return repository.findByBrandId(brand.getBrandId()).stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public BigDecimal getAverageShoePrice() {
        return jdbc.getAverageShoePrice();
    }

    private ShoeDTO mapToDTO(Shoe shoe) {
        ShoeDTO dto = new ShoeDTO();
        dto.setShoeId(shoe.getShoeId());
        dto.setShoeName(shoe.getShoeName());
        dto.setShoePrice(shoe.getShoePrice());
        dto.setShoeSize(shoe.getShoeSize());
        dto.setBrandId(shoe.getBrand() != null ? shoe.getBrand().getBrandId() : null);
        dto.setImageUrl(shoe.getImageUrl());
        return dto;
    }
}