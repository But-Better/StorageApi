package com.butbetter.storage.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AddressTest {

    private Address testAddress;

    @Test
    void testNullHashCodeGeneration() {
        testAddress = new Address("a", "a", "a", "a", "a", null);
        assertDoesNotThrow(() -> {
            testAddress.hashCode();
        });
    }
}
