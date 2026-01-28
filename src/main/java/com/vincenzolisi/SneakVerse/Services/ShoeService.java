package com.vincenzolisi.SneakVerse.Services;

import com.vincenzolisi.SneakVerse.Models.Brand;
import com.vincenzolisi.SneakVerse.Models.OrderItem;
import com.vincenzolisi.SneakVerse.Models.Shoe;
import com.vincenzolisi.SneakVerse.ModelsDTO.ShoeDTO;
import com.vincenzolisi.SneakVerse.Repositories.BrandRepository;
import com.vincenzolisi.SneakVerse.Repositories.OrderItemRepository;
import com.vincenzolisi.SneakVerse.Repositories.ShoeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
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

    public List<ShoeDTO> getAllShoe() {
        List<Shoe> shoes = repository.findAll();

        List<ShoeDTO> shoesDTO = shoes.stream()
                .map(shoe -> new ShoeDTO(
                        shoe.getShoeId(),
                        shoe.getShoeName(),
                        shoe.getShoePrice(),
                        shoe.getShoeSize(),
                        shoe.getItems() != null
                            ? shoe.getItems()
                                .stream()
                                .map(item -> item.getOrderItemId())
                                .toList()
                                :List.of(),
                        shoe.getBrand().getBrandId()
                ))
                .toList();
        return shoesDTO;
    }

    public ShoeDTO getShoeById(int id) {
        Optional<Shoe> opt = repository.findById(id);
        ShoeDTO dto = null;
        if(opt.isPresent()){
            Shoe shoe = opt.get();
            dto = new ShoeDTO();
            dto.setShoeId(shoe.getShoeId());
            dto.setShoeName(shoe.getShoeName());
            dto.setShoePrice(shoe.getShoePrice());
            dto.setShoeSize(shoe.getShoeSize());
            List<Integer> itemsIds = new ArrayList<>();
            if(shoe.getItems() != null) {
                for(OrderItem item : shoe.getItems()) {
                    itemsIds.add(item.getOrderItemId());
                }
            }
            dto.setItems(itemsIds);
            dto.setBrandId(shoe.getBrand().getBrandId());
        }
        return dto;
    }

    public ShoeDTO addShoe(ShoeDTO dto) {
        Brand brand = brandRepository.findById(dto.getBrandId())
                .orElseThrow(() -> new RuntimeException("Brand not found"));
        Shoe shoe = new Shoe();
        shoe.setShoeName(dto.getShoeName());
        shoe.setShoePrice(dto.getShoePrice());
        shoe.setShoeSize(dto.getShoeSize());
        shoe.setItems(new ArrayList<>());
        shoe.setBrand(brand);
        shoe = repository.save(shoe);
        //dto
        dto.setShoeId(shoe.getShoeId());
        dto.setShoeName(shoe.getShoeName());
        dto.setShoePrice(shoe.getShoePrice());
        dto.setItems(new ArrayList<>());
        dto.setBrandId(shoe.getBrand().getBrandId());

        return dto;
    }

    public ShoeDTO updateShoe(int id, ShoeDTO dto) {
        Optional<Shoe> opt = repository.findById(id);
        if(opt.isEmpty()) {
            throw new RuntimeException("Shoe not found");
        }

        Shoe shoe = opt.get();

        if(dto.getShoeName() != null) {
            shoe.setShoeName(dto.getShoeName());
        }

        if(dto.getShoePrice() != null) {
            shoe.setShoePrice(dto.getShoePrice());
        }

        if(dto.getShoeSize() != null) {
            shoe.setShoeSize(dto.getShoeSize());
        }

        if(dto.getItems() != null && !dto.getItems().isEmpty()) {
            List<OrderItem> updatedOrderItems = new ArrayList<>();
            for(Integer orderItemId : dto.getItems()) {
                OrderItem orderItem = orderItemRepository.findById(orderItemId)
                        .orElseThrow(() -> new RuntimeException("Order item not found"));
                updatedOrderItems.add(orderItem);
            }
            shoe.setItems(updatedOrderItems);
        }

        if(dto.getBrandId() != null) {
            Brand brand = brandRepository.findById(dto.getBrandId())
                    .orElseThrow(() -> new RuntimeException("Brand not found"));
            shoe.setBrand(brand);
        }

        shoe = repository.save(shoe);
        //dto
        ShoeDTO updto = new ShoeDTO();
        updto.setShoeId(shoe.getShoeId());
        updto.setShoeName(shoe.getShoeName());
        updto.setShoePrice(shoe.getShoePrice());
        updto.setShoeSize(shoe.getShoeSize());
        updto.setItems(shoe.getItems() != null
            ? shoe.getItems()
                .stream()
                .map(OrderItem::getOrderItemId)
                .toList()
                :List.of());
        updto.setBrandId(shoe.getBrand().getBrandId());
        return updto;
    }

    public boolean deleteShoe(int id) {
        if(!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
