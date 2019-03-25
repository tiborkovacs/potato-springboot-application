package com.epam.potato.rest.supplier;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.epam.potato.api.domain.supplier.Supplier;
import com.epam.potato.service.supplier.SupplierService;
import com.epam.potato.service.supplier.exception.UnableToCreateSupplierException;

public class SupplierRestControllerTest {

    @Mock
    private SupplierService supplierService;

    @InjectMocks
    private SupplierRestController underTest;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetSuppliers() {
        // GIVEN
        Supplier supplier = createSupplier();
        List<Supplier> suppliers = List.of(supplier);

        // WHEN
        when(supplierService.getSuppliers()).thenReturn(suppliers);

        List<Supplier> actual = underTest.getSuppliers();

        // THEN
        verify(supplierService).getSuppliers();
        verifyNoMoreInteractions(supplierService);

        assertEquals(actual, suppliers);
    }

    @Test
    public void testCreateSupplier() {
        // GIVEN
        Supplier supplier = createSupplier();

        // WHEN
        when(supplierService.create(supplier)).thenReturn(supplier);

        Supplier actual = underTest.createSupplier(supplier);

        // THEN
        verify(supplierService).create(supplier);
        verifyNoMoreInteractions(supplierService);

        assertEquals(actual, supplier);
    }

    @Test
    public void testCreateSupplierShouldFailWhenSupplierServiceThrowException() {
        // GIVEN
        Supplier supplier = createSupplier();

        // WHEN
        when(supplierService.create(supplier)).thenThrow(new RuntimeException());

        Exception actual = null;
        try {
            underTest.createSupplier(supplier);
        }
        catch (Exception exception) {
            actual = exception;
        }

        // THEN
        verify(supplierService).create(supplier);
        verifyNoMoreInteractions(supplierService);

        assertTrue(actual instanceof UnableToCreateSupplierException);
    }

    private Supplier createSupplier() {
        return new Supplier.Builder()
            .build();
    }
}