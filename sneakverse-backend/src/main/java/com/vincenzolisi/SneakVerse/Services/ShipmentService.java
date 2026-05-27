package com.vincenzolisi.SneakVerse.Services;

import com.vincenzolisi.SneakVerse.Models.Courier;
import com.vincenzolisi.SneakVerse.Models.Order;
import com.vincenzolisi.SneakVerse.Models.Shipment;
import com.vincenzolisi.SneakVerse.ModelsDTO.ShipmentDTO;
import com.vincenzolisi.SneakVerse.Repositories.CourierRepository;
import com.vincenzolisi.SneakVerse.Repositories.OrderRepository;
import com.vincenzolisi.SneakVerse.Repositories.ShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShipmentService {

    @Autowired
    private ShipmentRepository repository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CourierRepository courierRepository;

    public List<ShipmentDTO> getAllShipment() {
        List<Shipment> shipments = repository.findAll();

        List<ShipmentDTO> shipmentsDTO = shipments.stream()
                .map(shipment -> new ShipmentDTO(
                        shipment.getShipmentId(),
                        shipment.getOrder().getOrderId(),
                        shipment.getCourier().getCourierId()
                ))
                .toList();
        return shipmentsDTO;
    }

    public ShipmentDTO getShipmentById(int id) {
        Optional<Shipment> opt = repository.findById(id);
        ShipmentDTO dto = null;
        if(opt.isPresent()) {
            Shipment shipment = opt.get();
            dto = new ShipmentDTO();
            dto.setShipmentId(shipment.getShipmentId());
            dto.setOrderId(shipment.getOrder().getOrderId());
            dto.setCourierId(shipment.getCourier().getCourierId());
        }
        return dto;
    }

    public ShipmentDTO addShipment(ShipmentDTO dto) {
        Order order = orderRepository.findById(dto.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));
        Courier courier = courierRepository.findById(dto.getCourierId())
                .orElseThrow(() -> new RuntimeException("Courier not found"));

        Shipment shipment = new Shipment();
        shipment.setOrder(order);
        shipment.setCourier(courier);
        shipment = repository.save(shipment);
        dto.setShipmentId(shipment.getShipmentId());
        dto.setOrderId(shipment.getOrder().getOrderId());
        dto.setCourierId(shipment.getCourier().getCourierId());
        return dto;
    }

    public ShipmentDTO updateShipment(int id, ShipmentDTO dto) {
        Shipment shipment = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shipment not found"));
        if(dto.getOrderId() != null) {
            Order order = orderRepository.findById(dto.getOrderId())
                    .orElseThrow(() -> new RuntimeException("Order not found"));
            shipment.setOrder(order);
        }
        if(dto.getCourierId() != null) {
            Courier courier = courierRepository.findById(dto.getCourierId())
                    .orElseThrow(() -> new RuntimeException("Courier not found"));
            shipment.setCourier(courier);
        }

        shipment = repository.save(shipment);

        ShipmentDTO updto = new ShipmentDTO();
        updto.setShipmentId(shipment.getShipmentId());
        updto.setOrderId(shipment.getOrder().getOrderId());
        updto.setCourierId(shipment.getCourier().getCourierId());
        return updto;
    }

    public boolean deleteShipment(int id) {
        if(!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
