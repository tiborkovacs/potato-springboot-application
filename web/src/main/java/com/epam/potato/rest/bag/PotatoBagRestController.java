package com.epam.potato.rest.bag;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.epam.potato.api.domain.bag.PotatoBag;
import com.epam.potato.service.bag.PotatoBagService;
import com.epam.potato.service.bag.exception.UnableToCreatePotatoBagException;

@RestController
@RequestMapping(value = "/api/bags")
public class PotatoBagRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PotatoBagRestController.class);

    @Autowired
    private PotatoBagService potatoBagService;

    @GetMapping
    public List<PotatoBag> getPotatoBags(@RequestParam(name = "count", required = false, defaultValue = "3") int count) {
        return potatoBagService.getPotatoBags(count);
    }

    @PostMapping
    public PotatoBag createPotatoBag(@Valid @RequestBody PotatoBag potatoBag) {
        try {
            return potatoBagService.create(potatoBag);
        }
        catch (Exception exception) {
            LOGGER.error("Failed to create entity", exception);

            throw new UnableToCreatePotatoBagException("Unable to create entity: " + potatoBag + ", error cause: " + exception.getMessage());
        }
    }

}
