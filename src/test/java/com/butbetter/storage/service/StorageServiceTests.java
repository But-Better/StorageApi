package com.butbetter.storage.service;

import com.butbetter.storage.model.Address;
import com.butbetter.storage.model.ProductInformation;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class StorageServiceTests {

    private StorageService storageService;

    @Autowired
    private MockMvc mvc;

    @AfterEach
    void tearDown() {
        storageService = null;
    }

    @Disabled("Address or DeliveryTime is null")
    @Test
    void productInformationThrowNullPointerException() {
        this.storageService = new StorageService(null, null);
        assertThrows(NullPointerException.class, () -> {
            this.storageService.newProductInformation(new ProductInformation());
        });
    }

    @Test
    void getAllProductInformation() throws Exception {

        Date date = new Date();
        int hour = 3;
        int minute = 30;
        OffsetDateTime offsetDateTime = date.toInstant()
                .atOffset(ZoneOffset.ofHoursMinutes(hour, minute));
        ProductInformation productInformation = new ProductInformation(offsetDateTime, 10, new Address(
                "Peter Lustig",
                "Lustig GmbH",
                "Lustig-Strasse-42A",
                "Warschau",
                "15234",
                "Polen"
        ));

        List<ProductInformation> allProductInformation = List.of(productInformation);

        this.storageService = mock(StorageService.class);
        when(this.storageService.all()).thenReturn(allProductInformation);

        mvc.perform(get("/storage/v1/productInformation")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}