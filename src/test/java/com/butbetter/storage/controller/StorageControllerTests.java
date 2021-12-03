package com.butbetter.storage.controller;

import com.butbetter.storage.model.Address;
import com.butbetter.storage.model.ProductInformation;
import com.butbetter.storage.repository.ProductRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class StorageControllerTests {

    @Autowired
    private StorageController controller;

    @Autowired
    private ProductRepository repository;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    @BeforeAll
    static void beforeAll() {

    }

    @AfterEach
    void tearDown() {

    }

    @AfterAll
    static void afterAll() {

    }

    @Test
    public void contextLoads() {
        assertThat(controller).isNotNull();
    }

    @Test
    void addProduct() {
        Date date = new Date();
        int hour = 3;
        int minute = 30;
        OffsetDateTime offsetDateTime = date.toInstant()
                .atOffset(ZoneOffset.ofHoursMinutes(hour, minute));

        assertDoesNotThrow(() -> {
            controller.newProductInformation(
                    new ProductInformation(offsetDateTime, 10, new Address(
                            "Peter Lustig",
                            "Lustig GmbH",
                            "Lustig-Straße-42A",
                            "Warschau",
                            "15234",
                            "Polen"
                    ))
            );
        });
    }

    @Test
    void addProductWithAmountNegativeOne() {
        Date date = new Date();
        int hour = 3;
        int minute = 30;
        OffsetDateTime offsetDateTime = date.toInstant()
                .atOffset(ZoneOffset.ofHoursMinutes(hour, minute));

        assertThrows(IllegalArgumentException.class, () -> {
            controller.newProductInformation(
                    new ProductInformation(offsetDateTime, -1, new Address(
                            "Peter Lustig",
                            "Lustig GmbH",
                            "Lustig-Straße-42A",
                            "Warschau",
                            "15234",
                            "Polen"
                    ))
            );
        });
    }

    /**
     * Required Runnable Psql
     */
    @Disabled
    @Test
    void createAProduct() {
        Date date = new Date();
        int hour = 3;
        int minute = 30;
        OffsetDateTime offsetDateTime = date.toInstant()
                .atOffset(ZoneOffset.ofHoursMinutes(hour, minute));

        controller.newProductInformation(
                new ProductInformation(offsetDateTime, 10, new Address(
                        "Peter Lustig",
                        "Lustig GmbH",
                        "Lustig-Ja-42A",
                        "Warschau",
                        "4332",
                        "DE"
                ))
        );

        assertEquals(repository.findAll().size(), 1);
    }
}
