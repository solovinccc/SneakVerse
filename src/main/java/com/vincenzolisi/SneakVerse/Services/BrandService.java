package com.vincenzolisi.SneakVerse.Services;

import com.vincenzolisi.SneakVerse.Models.Brand;
import com.vincenzolisi.SneakVerse.Models.Shoe;
import com.vincenzolisi.SneakVerse.ModelsDTO.BrandDTO;
import com.vincenzolisi.SneakVerse.Repositories.BrandRepository;
import com.vincenzolisi.SneakVerse.Repositories.ShoeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BrandService {

    @Autowired
    private BrandRepository repository;

    @Autowired
    private ShoeRepository shoeRepository;

    public List<BrandDTO> getAllBrand() {
        List<Brand> brands = repository.findAll();

        List<BrandDTO> brandsDTO = brands.stream()
                .map(brand -> new BrandDTO(
                        brand.getBrandId(),
                        brand.getBrandName(),
                        brand.getShoes() != null
                            ? brand.getShoes()
                                .stream()
                                .map(shoe -> shoe.getShoeId())
                                .toList()
                                :List.of()
                ))
                .toList();
        return brandsDTO;
    }

    public BrandDTO getBrandById(int id) {
        Optional<Brand> opt = repository.findById(id);
        BrandDTO dto = null;
        if(opt.isPresent()) {
            Brand brand = opt.get();
            dto = new BrandDTO();
            dto.setBrandId(brand.getBrandId());
            dto.setBrandName(brand.getBrandName());
            List<Integer> shoeIds = new ArrayList<>();
            if(brand.getShoes() != null) {
                for(Shoe shoe : brand.getShoes()) {
                    shoeIds.add(shoe.getShoeId());
                }
            }
            dto.setShoeIds(shoeIds);
        }
        return dto;
    }

    public BrandDTO addBrand(BrandDTO dto) {
        Brand brand = new Brand();
        brand.setBrandName(dto.getBrandName());
        brand.setShoes(new ArrayList<>());
        brand = repository.save(brand);
        //dto
        dto.setBrandId(brand.getBrandId());
        dto.setBrandName(brand.getBrandName());
        dto.setShoeIds(new ArrayList<>());

        return dto;
    }

    public BrandDTO updateBrand(int id, BrandDTO dto) {
        Optional<Brand> opt = repository.findById(id);
        if(opt.isEmpty()) {
            throw new RuntimeException("Brand not found");
        }

        Brand brand = opt.get();

        if(dto.getBrandName() != null) {
            brand.setBrandName(dto.getBrandName());
        }

        if(dto.getShoeIds() != null) {
            List<Shoe> updatedShoes = new ArrayList<>();
            for(Integer shoeId : dto.getShoeIds()) {
                Shoe shoe = shoeRepository.findById(shoeId)
                        .orElseThrow(() -> new RuntimeException("Shoe not found"));
                updatedShoes.add(shoe);
            }
            brand.setShoes(updatedShoes);
        }


        brand = repository.save(brand);

        BrandDTO updto = new BrandDTO();
        updto.setBrandId(brand.getBrandId());
        updto.setBrandName(brand.getBrandName());
        updto.setShoeIds(brand.getShoes() != null
            ? brand.getShoes()
                .stream()
                .map(Shoe::getShoeId)
                .toList()
                : List.of()
                );
        return updto;
    }

    public boolean deleteBrand(int id) {
        if(!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }

}
