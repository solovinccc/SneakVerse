package com.vincenzolisi.SneakVerse.Services;

import com.vincenzolisi.SneakVerse.Models.Courier;
import com.vincenzolisi.SneakVerse.Models.Shipment;
import com.vincenzolisi.SneakVerse.ModelsDTO.CourierDTO;
import com.vincenzolisi.SneakVerse.Repositories.CourierRepository;
import com.vincenzolisi.SneakVerse.Repositories.ShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourierService {

    @Autowired
    private CourierRepository repository;

    @Autowired
    private ShipmentRepository shipmentRepository;

    public List<CourierDTO> getAllCourier() {
        return repository.findAll().stream()
                .map(c -> {
                    CourierDTO dto = new CourierDTO();
                    dto.setCourierId(c.getCourierId());
                    dto.setPhoneNumber(c.getPhoneNumber());

                    List<Integer> shipmentIds = c.getShipments() != null
                            ? c.getShipments().stream().map(Shipment::getShipmentId).toList()
                            : List.of();

                    dto.setShipmentIds(shipmentIds);
                    return dto;
                })
                .toList();
    }

    public CourierDTO getCourierById(int id) {
        return repository.findById(id)
                .map(c -> {
                    CourierDTO dto = new CourierDTO();
                    dto.setCourierId(c.getCourierId());
                    dto.setPhoneNumber(c.getPhoneNumber());

                    List<Integer> shipmentIds = c.getShipments() != null
                            ? c.getShipments().stream().map(Shipment::getShipmentId).toList()
                            : List.of();

                    dto.setShipmentIds(shipmentIds);
                    return dto;
                })
                .orElse(null);
    }

    public CourierDTO addCourier(CourierDTO dto) {
        Courier courier = new Courier();
        courier.setPhoneNumber(dto.getPhoneNumber());

        courier = repository.save(courier);

        CourierDTO out = new CourierDTO();
        out.setCourierId(courier.getCourierId());
        out.setPhoneNumber(courier.getPhoneNumber());
        out.setShipmentIds(List.of());
        return out;
    }

    public CourierDTO updateCourier(int id, CourierDTO dto) {
        Courier courier = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Courier not found"));

        if (dto.getPhoneNumber() != null) {
            courier.setPhoneNumber(dto.getPhoneNumber());
        }

        courier = repository.save(courier);

        CourierDTO out = new CourierDTO();
        out.setCourierId(courier.getCourierId());
        out.setPhoneNumber(courier.getPhoneNumber());

        List<Integer> shipmentIds = courier.getShipments() != null
                ? courier.getShipments().stream().map(Shipment::getShipmentId).toList()
                : List.of();

        out.setShipmentIds(shipmentIds);
        return out;
    }

    public boolean deleteCourier(int id) {
        if (!repository.existsById(id)) return false;
        repository.deleteById(id);
        return true;
    }
}
