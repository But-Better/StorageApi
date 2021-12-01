package com.butbetter.storage.controller;

import com.butbetter.storage.model.Address;
import com.butbetter.storage.model.ProductInformation;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
public class StorageControllerTests {

    @Autowired
    private StorageController controller;

    @BeforeEach
    void setUp() {

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

        assertDoesNotThrow(() -> {
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

}
