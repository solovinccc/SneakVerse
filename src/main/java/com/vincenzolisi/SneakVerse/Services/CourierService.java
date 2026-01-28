package com.vincenzolisi.SneakVerse.Services;

import com.vincenzolisi.SneakVerse.Models.Courier;
import com.vincenzolisi.SneakVerse.Models.Shipment;
import com.vincenzolisi.SneakVerse.ModelsDTO.CourierDTO;
import com.vincenzolisi.SneakVerse.ModelsDTO.ShipmentDTO;
import com.vincenzolisi.SneakVerse.Repositories.CourierRepository;
import com.vincenzolisi.SneakVerse.Repositories.ShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourierService {

    @Autowired
    private CourierRepository repository;

    @Autowired
    private ShipmentRepository shipmentRepository;

    public List<CourierDTO> getAllCourier() {
        List<Courier> couriers = repository.findAll();

        List<CourierDTO> couriersDTO = couriers.stream()
                .map(courier -> new CourierDTO(
                        courier.getCourierId(),
                        courier.getPhoneNumber(),
                        courier.getShipment().getShipmentId()
                ))
                .toList();
        return couriersDTO;
    }

    public CourierDTO getCourierById(int id) {
        Optional<Courier> opt = repository.findById(id);
        CourierDTO dto = null;
        if(opt.isPresent()) {
            Courier courier = opt.get();
            dto = new CourierDTO();
            dto.setCourierId(courier.getCourierId());
            dto.setPhoneNumber(courier.getPhoneNumber());
            dto.setShipmentId(courier.getShipment().getShipmentId());
        }
        return dto;
    }

    public CourierDTO addCourier(CourierDTO dto) {
        Shipment shipment = shipmentRepository.findById(dto.getShipmentId())
                .orElseThrow(() -> new RuntimeException("Shipment not found"));

        Courier courier = new Courier();
        courier.setPhoneNumber(dto.getPhoneNumber());
        courier.setShipment(shipment);

        courier = repository.save(courier);
        //dto aggiornato con id generato
        dto.setCourierId(courier.getCourierId());
        dto.setPhoneNumber(courier.getPhoneNumber());
        dto.setShipmentId(courier.getShipment().getShipmentId());

        return dto;
    }

    public CourierDTO updateCourier(int id, CourierDTO dto) {
        Optional<Courier> opt = repository.findById(id);
        if(opt.isEmpty()) {
            throw new RuntimeException("Courier not found");
        }

        Courier courier = opt.get();

        if(dto.getPhoneNumber() != 0) {
            courier.setPhoneNumber(dto.getPhoneNumber());
        }

        if(dto.getShipmentId() != 0) {
            Shipment shipment = shipmentRepository.findById(dto.getShipmentId())
                    .orElseThrow(() -> new RuntimeException("Shipment not found"));
            courier.setShipment(shipment);
        }

        courier = repository.save(courier);

        //creo il DTO aggiornato
        CourierDTO updto = new CourierDTO();
        updto.setCourierId(courier.getCourierId());
        updto.setPhoneNumber(courier.getPhoneNumber());
        updto.setShipmentId(courier.getShipment().getShipmentId());
        return updto;
    }

    public boolean deleteCourier(int id) {
        if(!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
