package com.example.subproject.service;

import com.example.subproject.entity.Shipment;
import org.springframework.stereotype.Service;

@Service
public class ShipmentService {

    public Shipment createShipment(String method) {
        // Simulate drone shipping
        Shipment shipment = new Shipment();
        shipment.setMethod(method);
        shipment.setStatus("Shipped");
        return shipment;
    }
}
