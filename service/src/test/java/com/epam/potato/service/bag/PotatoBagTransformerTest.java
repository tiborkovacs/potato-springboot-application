package com.epam.potato.service.bag;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.epam.potato.api.domain.bag.PotatoBag;
import com.epam.potato.dao.entity.bag.PotatoBagEntity;
import com.epam.potato.dao.entity.supplier.SupplierEntity;
import com.epam.potato.dao.repository.supplier.SupplierRepository;
import com.epam.potato.service.supplier.exception.SupplierNotFoundException;

public class PotatoBagTransformerTest {

    private static final long ID = 1L;
    private static final int NUMBER_OF_POTATOES = 20;
    private static final String SUPPLIER_NAME = "supplierName";
    private static final Date PACKED_DATE = new Date();
    private static final LocalDateTime PACKED_DATE_TIME = LocalDateTime.ofInstant(PACKED_DATE.toInstant(), ZoneId.systemDefault());
    private static final double PRICE = 25D;

    @Mock
    private SupplierRepository supplierRepository;

    @InjectMocks
    private PotatoBagTransformer underTest;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testTransformPotatoBag() {
        // GIVEN
        PotatoBag potatoBag = createPotatoBag();
        SupplierEntity supplierEntity = createSupplierEntity();

        // WHEN
        when(supplierRepository.getByName(SUPPLIER_NAME)).thenReturn(Optional.ofNullable(supplierEntity));

        PotatoBagEntity actual = underTest.transform(potatoBag);

        // THEN
        verify(supplierRepository).getByName(SUPPLIER_NAME);
        verifyNoMoreInteractions(supplierRepository);

        assertEquals(actual, createPotatoBagEntity(null));
    }

    @Test
    public void testTransformPotatoBagShouldFAilWhenSupplierDoesNotExist() {
        // GIVEN
        PotatoBag potatoBag = createPotatoBag();

        // WHEN
        when(supplierRepository.getByName(SUPPLIER_NAME)).thenReturn(Optional.empty());

        Exception actual = null;
        try {
            underTest.transform(potatoBag);
        }
        catch (Exception exception) {
            actual = exception;
        }

        // THEN
        verify(supplierRepository).getByName(SUPPLIER_NAME);
        verifyNoMoreInteractions(supplierRepository);

        assertTrue(actual instanceof SupplierNotFoundException);
    }

    @Test
    public void testTransformPotatoBagEntity() {
        // GIVEN
        PotatoBagEntity potatoBagEntity = createPotatoBagEntity(ID);

        // WHEN
        PotatoBag actual = underTest.transform(potatoBagEntity);

        // THEN
        verifyZeroInteractions(supplierRepository);

        assertEquals(actual, createPotatoBag());
    }

    private PotatoBag createPotatoBag() {
        return new PotatoBag.Builder()
            .withId(ID)
            .withNumberOfPotatoes(NUMBER_OF_POTATOES)
            .withSupplierName(SUPPLIER_NAME)
            .withPackedDateTime(PACKED_DATE_TIME)
            .withPrice(PRICE)
            .build();
    }

    private PotatoBagEntity createPotatoBagEntity(Long id) {
        PotatoBagEntity potatoBagEntity = new PotatoBagEntity();

        Optional.ofNullable(id).ifPresent(potatoBagEntity::setId);
        potatoBagEntity.setNumberOfPotatoes(NUMBER_OF_POTATOES);
        potatoBagEntity.setSupplierEntity(createSupplierEntity());
        potatoBagEntity.setPackedDate(PACKED_DATE);
        potatoBagEntity.setPrice(PRICE);

        return potatoBagEntity;
    }

    private SupplierEntity createSupplierEntity() {
        SupplierEntity supplierEntity = new SupplierEntity();

        supplierEntity.setName(SUPPLIER_NAME);

        return supplierEntity;
    }

}
