package com.vincenzolisi.SneakVerse.ModelsDTO;

import java.time.LocalDateTime;
import java.util.List;

public class OSOrderResponse {

    public Integer orderId;
    public LocalDateTime orderDate;
    public Integer userId;
    public String paymentMethod;
    public List<MicroItem> items;
    public MicroShipment shipment;
    public String status;

    public static class MicroItem {
        public Integer shoeId;
        public Integer quantity;
    }

    public static class MicroShipment {
        public Integer shipmentId;
        public MicroCourier courier;
    }

    public static class MicroCourier {
        public Integer courierId;
        public String phoneNumber;
    }

}
