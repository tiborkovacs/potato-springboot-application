package com.epam.potato.service.supplier;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.Collections;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.epam.potato.api.domain.supplier.Supplier;
import com.epam.potato.dao.entity.supplier.SupplierEntity;
import com.epam.potato.dao.repository.supplier.SupplierRepository;
import com.epam.potato.service.supplier.exception.InvalidSupplierException;

public class SupplierServiceTest {

    @Mock
    private SupplierRepository supplierRepository;
    @Mock
    private SupplierTransformer supplierTransformer;

    @InjectMocks
    private SupplierService underTest;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreate() {
        // GIVEN
        Supplier supplier = createSupplier();
        SupplierEntity supplierEntity = createSupplierEntity();

        // WHEN
        when(supplierTransformer.transform(supplier)).thenReturn(supplierEntity);
        when(supplierRepository.save(supplierEntity)).thenReturn(supplierEntity);
        when(supplierTransformer.transform(supplierEntity)).thenReturn(supplier);

        Supplier actual = underTest.create(supplier);

        // THEN
        verify(supplierTransformer).transform(supplier);
        verify(supplierRepository).save(supplierEntity);
        verify(supplierTransformer).transform(supplierEntity);
        verifyNoMoreInteractions(supplierTransformer, supplierRepository);

        assertEquals(actual, supplier);
    }

    @Test
    public void testCreateShouldFailWhenInputIsNull() {
        // GIVEN

        // WHEN
        Exception actual = null;
        try {
            underTest.create(null);
        }
        catch (Exception exception) {
            actual = exception;
        }

        // THEN
        verifyZeroInteractions(supplierTransformer, supplierRepository);

        assertTrue(actual instanceof InvalidSupplierException);
    }

    @Test
    public void testGetSuppliers() {
        // GIVEN
        SupplierEntity supplierEntity = createSupplierEntity();
        Iterable<SupplierEntity> supplierEntities = List.of(supplierEntity, supplierEntity);
        Supplier supplier = createSupplier();

        // WHEN
        when(supplierRepository.findAll()).thenReturn(supplierEntities);
        when(supplierTransformer.transform(supplierEntity)).thenReturn(supplier);

        List<Supplier> actual = underTest.getSuppliers();

        // THEN
        verify(supplierRepository).findAll();
        verify(supplierTransformer, times(2)).transform(supplierEntity);
        verifyNoMoreInteractions(supplierRepository, supplierTransformer);

        List<Supplier> expected = List.of(supplier, supplier);
        assertEquals(actual, expected);
    }

    @Test
    public void testGetSupplierShouldReturnEmptyListWhenThereAreNoEntries() {
        // GIVEN
        // WHEN
        when(supplierRepository.findAll()).thenReturn(Collections.emptyList());

        List<Supplier> actual = underTest.getSuppliers();

        // THEN
        verifyZeroInteractions(supplierTransformer);
        verify(supplierRepository).findAll();
        verifyNoMoreInteractions(supplierRepository);

        assertEquals(actual.size(), 0);
    }

    private Supplier createSupplier() {
        return new Supplier.Builder()
            .build();
    }

    private SupplierEntity createSupplierEntity() {
        return new SupplierEntity();
    }

}
