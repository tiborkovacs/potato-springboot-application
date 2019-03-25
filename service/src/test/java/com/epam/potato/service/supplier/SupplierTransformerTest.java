package com.epam.potato.service.supplier;

import static org.testng.Assert.assertEquals;

import java.util.Optional;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.epam.potato.api.domain.supplier.Supplier;
import com.epam.potato.dao.entity.supplier.SupplierEntity;

public class SupplierTransformerTest {

    private static final long ID = 1L;
    private static final String SUPPLIER_NAME = "supplierName";

    private SupplierTransformer underTest;

    @BeforeMethod
    public void setUp() {
        underTest = new SupplierTransformer();
    }

    @Test
    public void testTransformSupplier() {
        // GIVEN
        Supplier supplier = createSupplier();

        // WHEN
        SupplierEntity actual = underTest.transform(supplier);

        // THEN
        assertEquals(actual, createSupplierEntity(null));
    }

    @Test
    public void testTransformSupplierEntity() {
        // GIVEN
        SupplierEntity supplierEntity = createSupplierEntity(ID);

        // WHEN
        Supplier actual = underTest.transform(supplierEntity);

        // THEN
        assertEquals(actual, createSupplier());
    }

    private Supplier createSupplier() {
        return new Supplier.Builder()
            .withId(ID)
            .withName(SUPPLIER_NAME)
            .build();
    }

    private SupplierEntity createSupplierEntity(Long id) {
        SupplierEntity supplierEntity = new SupplierEntity();

        Optional.ofNullable(id).ifPresent(supplierEntity::setId);
        supplierEntity.setName(SUPPLIER_NAME);

        return supplierEntity;
    }

}
