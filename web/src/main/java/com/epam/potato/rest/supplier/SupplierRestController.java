package com.epam.potato.rest.supplier;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epam.potato.api.domain.supplier.Supplier;
import com.epam.potato.service.supplier.SupplierService;
import com.epam.potato.service.supplier.exception.UnableToCreateSupplierException;

@RestController
@RequestMapping(value = "/api/suppliers")
public class SupplierRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SupplierRestController.class);

    @Autowired
    private SupplierService supplierService;

    @GetMapping
    public List<Supplier> getSuppliers() {
        return supplierService.getSuppliers();
    }

    @PostMapping
    public Supplier createSupplier(@Valid @RequestBody Supplier supplier) {
        try {
            return supplierService.create(supplier);
        }
        catch (Exception exception) {
            LOGGER.error("Failed to create entity", exception);

            throw new UnableToCreateSupplierException("Unable to create entity: " + supplier + ", error cause: " + exception.getMessage());
        }
    }

}
