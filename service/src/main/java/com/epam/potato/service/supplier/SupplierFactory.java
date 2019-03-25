package com.epam.potato.service.supplier;

import org.springframework.stereotype.Component;

import com.epam.potato.api.domain.supplier.Supplier;

@Component
public class SupplierFactory {

    public Supplier create(String supplierName) {
        return new Supplier.Builder()
            .withName(supplierName)
            .build();
    }

}
