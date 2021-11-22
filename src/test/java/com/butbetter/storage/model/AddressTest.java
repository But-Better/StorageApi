package com.butbetter.storage.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Null;

import static org.junit.jupiter.api.Assertions.*;

class AddressTest {

    private Address testAddress;

    @Test
    void testNullHashCodeGeneration() {
        testAddress = new Address("a", "a", "a", "a", "a", null);
        assertThrows(Exception.class, () -> {
            testAddress.hashCode();
        });
    }
}