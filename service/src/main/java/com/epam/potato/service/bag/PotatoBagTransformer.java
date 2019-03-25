package com.epam.potato.service.bag;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.epam.potato.api.domain.bag.PotatoBag;
import com.epam.potato.dao.entity.bag.PotatoBagEntity;
import com.epam.potato.dao.entity.supplier.SupplierEntity;
import com.epam.potato.dao.repository.supplier.SupplierRepository;
import com.epam.potato.service.supplier.exception.SupplierNotFoundException;

@Component
class PotatoBagTransformer {

    @Autowired
    private SupplierRepository supplierRepository;

    PotatoBagEntity transform(PotatoBag potatoBag) {
        PotatoBagEntity potatoBagEntity = new PotatoBagEntity();

        potatoBagEntity.setNumberOfPotatoes(potatoBag.getNumberOfPotatoes());
        potatoBagEntity.setSupplierEntity(getSupplierEntity(potatoBag.getSupplierName()));
        potatoBagEntity.setPackedDate(getPackedDate(potatoBag.getPackedDateTime()));
        potatoBagEntity.setPrice(potatoBag.getPrice());

        return potatoBagEntity;
    }

    PotatoBag transform(PotatoBagEntity potatoBagEntity) {
        return new PotatoBag.Builder()
            .withId(potatoBagEntity.getId())
            .withNumberOfPotatoes(potatoBagEntity.getNumberOfPotatoes())
            .withSupplierName(potatoBagEntity.getSupplierEntity().getName())
            .withPackedDateTime(getPackedDateTime(potatoBagEntity.getPackedDate()))
            .withPrice(potatoBagEntity.getPrice())
            .build();
    }

    private SupplierEntity getSupplierEntity(String supplierName) {
        return supplierRepository.getByName(supplierName)
            .orElseThrow(() -> new SupplierNotFoundException("Supplier doesn't exist with name: " + supplierName));
    }

    private Date getPackedDate(LocalDateTime packedDateTime) {
        return Date.from(packedDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    private LocalDateTime getPackedDateTime(Date packedDate) {
        return LocalDateTime.ofInstant(packedDate.toInstant(), ZoneId.systemDefault());
    }

}
