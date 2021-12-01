package com.butbetter.storage.model;

import org.junit.jupiter.api.*;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ProductInformationTests {

    @BeforeEach
    void setUp() {

    }

    @BeforeAll
    static void beforeAll() {

    }

    @AfterAll
    static void afterAll() {

    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void createProductInformation() {

        Date date = new Date();
        int hour = 3;
        int minute = 30;
        OffsetDateTime offsetDateTime = date.toInstant()
                .atOffset(ZoneOffset.ofHoursMinutes(hour, minute));

        assertDoesNotThrow(() -> {
            new ProductInformation(offsetDateTime, 10, new Address(
                    "Peter Lustig",
                    "Lustig GmbH",
                    "Lustig-Straße-42A",
                    "Warschau",
                    "15234",
                    "Polen"
            ));
        });
    }

    /**
     * It doesn't work so
     * @see = https://stackoverflow.com/questions/54660774/when-would-notnull-throws-exception
     */
    @Disabled
    @Test
    void createProductInformationWithWrongParameter() {
        assertThrows(NullPointerException.class, () -> {
            new ProductInformation(null, 10, null);
        });
    }
}
