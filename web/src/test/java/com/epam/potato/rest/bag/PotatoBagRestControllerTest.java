package com.epam.potato.rest.bag;

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

import com.epam.potato.api.domain.bag.PotatoBag;
import com.epam.potato.service.bag.PotatoBagService;
import com.epam.potato.service.bag.exception.UnableToCreatePotatoBagException;

public class PotatoBagRestControllerTest {

    private static final int COUNT = 1;

    @Mock
    private PotatoBagService potatoBagService;

    @InjectMocks
    private PotatoBagRestController underTest;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetPotatoBags() {
        // GIVEN
        PotatoBag potatoBag = createPotatoBag();
        List<PotatoBag> potatoBags = List.of(potatoBag);

        // WHEN
        when(potatoBagService.getPotatoBags(COUNT)).thenReturn(potatoBags);

        List<PotatoBag> actual = underTest.getPotatoBags(COUNT);

        // THEN
        verify(potatoBagService).getPotatoBags(COUNT);
        verifyNoMoreInteractions(potatoBagService);

        assertEquals(actual, potatoBags);
    }

    @Test
    public void testCreatePotatoBag() {
        // GIVEN
        PotatoBag potatoBag = createPotatoBag();

        // WHEN
        when(potatoBagService.create(potatoBag)).thenReturn(potatoBag);

        PotatoBag actual = underTest.createPotatoBag(potatoBag);

        // THEN
        verify(potatoBagService).create(potatoBag);
        verifyNoMoreInteractions(potatoBagService);

        assertEquals(actual, potatoBag);
    }

    @Test
    public void testCreatePotatoBagShouldFailWhenPotatoBagServiceThrowException() {
        // GIVEN
        PotatoBag potatoBag = createPotatoBag();

        // WHEN
        when(potatoBagService.create(potatoBag)).thenThrow(new RuntimeException());

        Exception actual = null;
        try {
            underTest.createPotatoBag(potatoBag);
        }
        catch (Exception exception) {
            actual = exception;
        }

        // THEN
        verify(potatoBagService).create(potatoBag);
        verifyNoMoreInteractions(potatoBagService);

        assertTrue(actual instanceof UnableToCreatePotatoBagException);
    }

    private PotatoBag createPotatoBag() {
        return new PotatoBag.Builder()
            .build();
    }

}
