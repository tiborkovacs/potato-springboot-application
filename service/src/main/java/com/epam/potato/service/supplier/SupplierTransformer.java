package com.epam.potato.service.supplier;

import org.springframework.stereotype.Component;

import com.epam.potato.api.domain.supplier.Supplier;
import com.epam.potato.dao.entity.supplier.SupplierEntity;

@Component
class SupplierTransformer {

    SupplierEntity transform(Supplier supplier) {
        SupplierEntity supplierEntity = new SupplierEntity();

        supplierEntity.setName(supplier.getName());

        return supplierEntity;
    }

    Supplier transform(SupplierEntity supplierEntity) {
        return new Supplier.Builder()
            .withId(supplierEntity.getId())
            .withName(supplierEntity.getName())
            .build();
    }

}
