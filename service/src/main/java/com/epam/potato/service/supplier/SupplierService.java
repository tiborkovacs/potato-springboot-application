package com.epam.potato.service.supplier;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epam.potato.api.domain.supplier.Supplier;
import com.epam.potato.dao.entity.supplier.SupplierEntity;
import com.epam.potato.dao.repository.supplier.SupplierRepository;
import com.epam.potato.service.supplier.exception.InvalidSupplierException;

@Service
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private SupplierTransformer supplierTransformer;

    @Transactional
    public Supplier create(Supplier supplier) {
        return Optional.ofNullable(supplier)
            .map(supplierTransformer::transform)
            .map(supplierRepository::save)
            .map(supplierTransformer::transform)
            .orElseThrow(() ->  new InvalidSupplierException("Invalid entity: " + supplier));
    }

    public List<Supplier> getSuppliers() {
        Iterable<SupplierEntity> suppliers = supplierRepository.findAll();

        return StreamSupport.stream(suppliers.spliterator(), false)
            .map(supplierTransformer::transform)
            .collect(Collectors.toList());

    }

}
