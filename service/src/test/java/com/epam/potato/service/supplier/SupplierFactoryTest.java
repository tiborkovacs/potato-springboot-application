package com.epam.potato.service.supplier;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.epam.potato.api.domain.supplier.Supplier;

public class SupplierFactoryTest {

    private static final String SUPPLIER_NAME = "supplierName";

    private SupplierFactory underTest;

    @BeforeMethod
    public void setUp() {
        underTest = new SupplierFactory();
    }

    @Test
    public void testCreate() {
        // GIVEN
        // WHEN
        Supplier actual = underTest.create(SUPPLIER_NAME);

        // THEN
        assertEquals(actual, createSupplier());
    }

    private Supplier createSupplier() {
        return new Supplier.Builder()
            .withName(SUPPLIER_NAME)
            .build();
    }

}