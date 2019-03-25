package com.epam.potato.service.bag;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epam.potato.api.domain.bag.PotatoBag;
import com.epam.potato.dao.entity.bag.PotatoBagEntity;
import com.epam.potato.dao.repository.bag.PotatoBagRepository;
import com.epam.potato.service.bag.exception.InvalidPotatoBagException;

@Service
public class PotatoBagService {

    @Autowired
    private PotatoBagRepository potatoBagRepository;
    @Autowired
    private PotatoBagTransformer potatoBagTransformer;

    @Transactional
    public PotatoBag create(PotatoBag potatoBag) {
        return Optional.ofNullable(potatoBag)
            .map(potatoBagTransformer::transform)
            .map(potatoBagRepository::save)
            .map(potatoBagTransformer::transform)
            .orElseThrow(() -> new InvalidPotatoBagException("Unable to create entity: " + potatoBag));
    }

    public List<PotatoBag> getPotatoBags(int count) {
        Iterable<PotatoBagEntity> potatoBagEntities;
        if (count < 0) {
            potatoBagEntities = potatoBagRepository.findAll();
        }
        else if (count == 0) {
            potatoBagEntities = Collections.emptyList();
        }
        else {
            potatoBagEntities = potatoBagRepository.findAll(PageRequest.of(0, count));
        }

        return StreamSupport.stream(potatoBagEntities.spliterator(), false)
            .map(potatoBagTransformer::transform)
            .collect(Collectors.toList());
    }

}
