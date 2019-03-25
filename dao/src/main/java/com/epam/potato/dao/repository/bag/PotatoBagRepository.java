package com.epam.potato.dao.repository.bag;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.epam.potato.dao.entity.bag.PotatoBagEntity;

@Repository
public interface PotatoBagRepository extends PagingAndSortingRepository<PotatoBagEntity, Long> {

}
