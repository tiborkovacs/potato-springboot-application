package com.epam.potato;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.epam.potato.api.domain.bag.PotatoBag;
import com.epam.potato.api.domain.supplier.Supplier;
import com.epam.potato.service.bag.PotatoBagService;
import com.epam.potato.service.supplier.SupplierFactory;
import com.epam.potato.service.supplier.SupplierService;

@Component
public class DummyDataPopulatorCommandLineRunner implements CommandLineRunner {

    private static final List<String> SUPPLIER_NAMES = List.of("De Coster", "Owel", "Patatas Ruben", "Yunnan Spices");
    private static final ThreadLocalRandom RND = ThreadLocalRandom.current();
    private static final int MAX_NUMBER_OF_POTATO_BAGS_PER_SUPPLIER = 5;

    @Autowired
    private PotatoBagService potatoBagService;
    @Autowired
    private SupplierService supplierService;
    @Autowired
    private SupplierFactory supplierFactory;

    @Override
    public void run(String... args) {
        List<Supplier> suppliers = persistDummySuppliers();

        suppliers.forEach(this::persistDummyPotatoBags);
    }

    private List<Supplier> persistDummySuppliers() {
        return SUPPLIER_NAMES.stream()
            .map(supplierFactory::create)
            .map(supplierService::create)
            .collect(Collectors.toList());
    }

    private void persistDummyPotatoBags(Supplier supplier) {
        IntStream.range(0, RND.nextInt(MAX_NUMBER_OF_POTATO_BAGS_PER_SUPPLIER) + 1)
            .mapToObj(index -> createPotatoBag(supplier))
            .forEach(potatoBagService::create);
    }

    private PotatoBag createPotatoBag(Supplier supplier) {
        return new PotatoBag.Builder()
            .withNumberOfPotatoes(RND.nextInt(101))
            .withSupplierName(supplier.getName())
            .withPackedDateTime(getRandomLocalDateTime())
            .withPrice(getRandomPryce())
            .build();
    }

    private LocalDateTime getRandomLocalDateTime() {
        long minDay = LocalDate.of(2019, 1, 1).toEpochDay();
        long maxDay = LocalDate.now().toEpochDay();
        long randomDay = RND.nextLong(minDay, maxDay);

        int randomHour = RND.nextInt(8, 19);
        int randomMinute = RND.nextInt(0, 60);

        return LocalDate.ofEpochDay(randomDay).atTime(randomHour, randomMinute);
    }

    private double getRandomPryce() {
        double price = (RND.nextDouble() * 49) + 1;

        return new BigDecimal(price).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

}
