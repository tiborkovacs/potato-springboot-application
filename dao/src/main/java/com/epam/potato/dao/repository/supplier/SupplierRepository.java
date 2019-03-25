package com.epam.potato.dao.repository.supplier;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.epam.potato.dao.entity.supplier.SupplierEntity;

@Repository
public interface SupplierRepository extends PagingAndSortingRepository<SupplierEntity, Long> {

    Optional<SupplierEntity> getByName(String name);

}
