package com.vincenzolisi.SneakVerse.Services;

import com.vincenzolisi.SneakVerse.Models.Brand;
import com.vincenzolisi.SneakVerse.Models.OrderItem;
import com.vincenzolisi.SneakVerse.Models.Shoe;
import com.vincenzolisi.SneakVerse.ModelsDTO.ShoeDTO;
import com.vincenzolisi.SneakVerse.Repositories.BrandRepository;
import com.vincenzolisi.SneakVerse.Repositories.OrderItemRepository;
import com.vincenzolisi.SneakVerse.Repositories.ShoeRepository;
import com.vincenzolisi.SneakVerse.Repositories.ShoeStatsJdbc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ShoeService {

    @Autowired
    private ShoeRepository repository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ShoeStatsJdbc jdbc;

    public List<ShoeDTO> getAllShoe() {
        List<Shoe> shoes = repository.findAll();

        return shoes.stream()
                .map(shoe -> new ShoeDTO(
                        shoe.getShoeId(),
                        shoe.getShoeName(),
                        shoe.getShoePrice(),
                        shoe.getShoeSize(),
                        shoe.getItems() != null
                                ? shoe.getItems().stream()
                                .map(OrderItem::getOrderItemId)
                                .toList()
                                : List.of(),
                        shoe.getBrand().getBrandId(),
                        shoe.getImageUrl()
                ))
                .toList();
    }

    public ShoeDTO getShoeById(int id) {
        Optional<Shoe> opt = repository.findById(id);
        if (opt.isEmpty()) {
            return null;
        }

        Shoe shoe = opt.get();
        ShoeDTO dto = new ShoeDTO();
        dto.setShoeId(shoe.getShoeId());
        dto.setShoeName(shoe.getShoeName());
        dto.setShoePrice(shoe.getShoePrice());
        dto.setShoeSize(shoe.getShoeSize());
        dto.setItems(
                shoe.getItems() != null
                        ? shoe.getItems().stream()
                        .map(OrderItem::getOrderItemId)
                        .toList()
                        : new ArrayList<>()
        );
        dto.setBrandId(shoe.getBrand().getBrandId());
        dto.setImageUrl(shoe.getImageUrl());
        return dto;
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

        if (dto.getItems() != null && !dto.getItems().isEmpty()) {
            List<OrderItem> orderItems = new ArrayList<>();
            for (Integer itemId : dto.getItems()) {
                OrderItem orderItem = orderItemRepository.findById(itemId)
                        .orElseThrow(() -> new RuntimeException("Order item not found: " + itemId));
                orderItems.add(orderItem);
            }
            shoe.setItems(orderItems);
        } else {
            shoe.setItems(new ArrayList<>());
        }

        shoe = repository.save(shoe);

        dto.setShoeId(shoe.getShoeId());
        dto.setItems(
                shoe.getItems() != null
                        ? shoe.getItems().stream()
                        .map(OrderItem::getOrderItemId)
                        .toList()
                        : new ArrayList<>()
        );
        dto.setBrandId(shoe.getBrand().getBrandId());
        dto.setImageUrl(shoe.getImageUrl());
        return dto;
    }

    public ShoeDTO updateShoe(int id, ShoeDTO dto) {
        Shoe shoe = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shoe not found"));

        if (dto.getShoeName() != null) {
            shoe.setShoeName(dto.getShoeName());
        }

        if (dto.getShoePrice() != null) {
            shoe.setShoePrice(dto.getShoePrice());
        }


        if (dto.getShoeSize() != null) {
            String sizeStr = String.valueOf(dto.getShoeSize()).replace(",", ".");
            shoe.setShoeSize(Float.parseFloat(sizeStr));
        }

        if (dto.getItems() != null) {
            List<OrderItem> orderItems = new ArrayList<>();
            for (Integer itemId : dto.getItems()) {
                OrderItem orderItem = orderItemRepository.findById(itemId)
                        .orElseThrow(() -> new RuntimeException("Order item not found: " + itemId));
                orderItems.add(orderItem);
            }
            shoe.setItems(orderItems);
        }

        if (dto.getBrandId() != null) {
            Brand brand = brandRepository.findById(dto.getBrandId())
                    .orElseThrow(() -> new RuntimeException("Brand not found"));
            shoe.setBrand(brand);
        }

        if(dto.getImageUrl() != null) {
            shoe.setImageUrl(dto.getImageUrl());
        }

        shoe = repository.save(shoe);

        ShoeDTO updto = new ShoeDTO();
        updto.setShoeId(shoe.getShoeId());
        updto.setShoeName(shoe.getShoeName());
        updto.setShoePrice(shoe.getShoePrice());
        updto.setShoeSize(shoe.getShoeSize());
        updto.setItems(
                shoe.getItems() != null
                        ? shoe.getItems().stream()
                        .map(OrderItem::getOrderItemId)
                        .toList()
                        : new ArrayList<>()
        );
        updto.setBrandId(shoe.getBrand().getBrandId());
        updto.setImageUrl(shoe.getImageUrl());
        return updto;
    }

    public boolean deleteShoe(int id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }

    public List<ShoeDTO> findShoesByBrandName(String brandName) {

        if (brandName == null || brandName.trim().isEmpty()) {
            return repository.findAll()
                    .stream()
                    .map(shoe -> {
                        ShoeDTO dto = new ShoeDTO();
                        dto.setShoeId(shoe.getShoeId());
                        dto.setShoeName(shoe.getShoeName());
                        dto.setShoePrice(shoe.getShoePrice());
                        dto.setShoeSize(shoe.getShoeSize());
                        dto.setImageUrl(shoe.getImageUrl());
                        dto.setBrandId(
                                shoe.getBrand() != null ? shoe.getBrand().getBrandId() : null
                        );
                        return dto;
                    })
                    .toList();
        }

        Brand brand = brandRepository
                .findByNameIgnoreCase(brandName.trim())
                .orElse(null);

        if (brand == null) {
            return List.of();
        }

        return repository
                .findByBrandId(brand.getBrandId())
                .stream()
                .map(shoe -> {
                    ShoeDTO dto = new ShoeDTO();
                    dto.setShoeId(shoe.getShoeId());
                    dto.setShoeName(shoe.getShoeName());
                    dto.setShoePrice(shoe.getShoePrice());
                    dto.setShoeSize(shoe.getShoeSize());
                    dto.setImageUrl(shoe.getImageUrl());
                    dto.setBrandId(brand.getBrandId());
                    return dto;
                })
                .toList();
    }

    public BigDecimal getAverageShoePrice() {
        return jdbc.getAverageShoePrice();
    }


}