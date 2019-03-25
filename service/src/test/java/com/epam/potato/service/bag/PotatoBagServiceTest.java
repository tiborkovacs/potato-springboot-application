package com.epam.potato.service.bag;

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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.epam.potato.api.domain.bag.PotatoBag;
import com.epam.potato.dao.entity.bag.PotatoBagEntity;
import com.epam.potato.dao.repository.bag.PotatoBagRepository;
import com.epam.potato.service.bag.exception.InvalidPotatoBagException;

public class PotatoBagServiceTest {

    private static final int DEFAULT_COUNT = 3;

    @Mock
    private PotatoBagRepository potatoBagRepository;
    @Mock
    private PotatoBagTransformer potatoBagTransformer;

    @InjectMocks
    private PotatoBagService underTest;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreate() {
        // GIVEN
        PotatoBag potatoBag = createPotatoBag();
        PotatoBagEntity potatoBagEntity = createPotatoBagEntity();

        // WHEN
        when(potatoBagTransformer.transform(potatoBag)).thenReturn(potatoBagEntity);
        when(potatoBagRepository.save(potatoBagEntity)).thenReturn(potatoBagEntity);
        when(potatoBagTransformer.transform(potatoBagEntity)).thenReturn(potatoBag);

        PotatoBag actual = underTest.create(potatoBag);

        // THEN
        verify(potatoBagTransformer).transform(potatoBag);
        verify(potatoBagRepository).save(potatoBagEntity);
        verify(potatoBagTransformer).transform(potatoBagEntity);
        verifyNoMoreInteractions(potatoBagTransformer, potatoBagRepository);

        assertEquals(actual, potatoBag);
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
        verifyZeroInteractions(potatoBagTransformer, potatoBagRepository);

        assertTrue(actual instanceof InvalidPotatoBagException);
    }

    @Test
    public void testGetPotatoBags() {
        // GIVEN
        PotatoBagEntity potatoBagEntity = createPotatoBagEntity();
        Page<PotatoBagEntity> potatoBagEntities = new PageImpl(List.of(potatoBagEntity, potatoBagEntity, potatoBagEntity));
        PotatoBag potatoBag = createPotatoBag();

        // WHEN
        when(potatoBagRepository.findAll(PageRequest.of(0, DEFAULT_COUNT))).thenReturn(potatoBagEntities);
        when(potatoBagTransformer.transform(potatoBagEntity)).thenReturn(potatoBag);

        List<PotatoBag> actual = underTest.getPotatoBags(DEFAULT_COUNT);

        // THEN
        verify(potatoBagRepository).findAll(PageRequest.of(0, DEFAULT_COUNT));
        verify(potatoBagTransformer, times(DEFAULT_COUNT)).transform(potatoBagEntity);
        verifyNoMoreInteractions(potatoBagRepository, potatoBagTransformer);

        List<PotatoBag> expected = List.of(potatoBag, potatoBag, potatoBag);
        assertEquals(actual, expected);
    }

    @Test
    public void testGetPotatoBagsWhenCountIsNegative() {
        // GIVEN
        PotatoBagEntity potatoBagEntity = createPotatoBagEntity();
        Page<PotatoBagEntity> potatoBagEntities = new PageImpl(List.of(potatoBagEntity, potatoBagEntity, potatoBagEntity, potatoBagEntity));
        PotatoBag potatoBag = createPotatoBag();

        // WHEN
        when(potatoBagRepository.findAll()).thenReturn(potatoBagEntities);
        when(potatoBagTransformer.transform(potatoBagEntity)).thenReturn(potatoBag);

        List<PotatoBag> actual = underTest.getPotatoBags(-1);

        // THEN
        verify(potatoBagRepository).findAll();
        verify(potatoBagTransformer, times(4)).transform(potatoBagEntity);
        verifyNoMoreInteractions(potatoBagRepository, potatoBagTransformer);

        List<PotatoBag> expected = List.of(potatoBag, potatoBag, potatoBag, potatoBag);
        assertEquals(actual, expected);
    }

    @Test
    public void testGetPotatoBagsWhenCountIsZero() {
        // GIVEN
        // WHEN
        List<PotatoBag> actual = underTest.getPotatoBags(0);

        // THEN
        verifyZeroInteractions(potatoBagRepository, potatoBagTransformer);

        assertEquals(actual.size(), 0);
    }

    @Test
    public void testGetPotatoBagsWhenThereAreNoEntries() {
        // GIVEN
        // WHEN
        when(potatoBagRepository.findAll(PageRequest.of(0, DEFAULT_COUNT))).thenReturn(new PageImpl(Collections.emptyList()));

        List<PotatoBag> actual = underTest.getPotatoBags(DEFAULT_COUNT);

        // THEN
        verifyZeroInteractions(potatoBagTransformer);
        verify(potatoBagRepository).findAll(PageRequest.of(0, DEFAULT_COUNT));
        verifyNoMoreInteractions(potatoBagRepository);

        assertEquals(actual.size(), 0);
    }

    private PotatoBag createPotatoBag() {
        return new PotatoBag.Builder()
            .build();
    }

    private PotatoBagEntity createPotatoBagEntity() {
        return new PotatoBagEntity();
    }

}
