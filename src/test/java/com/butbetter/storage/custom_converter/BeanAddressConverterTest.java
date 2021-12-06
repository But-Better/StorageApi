package com.butbetter.storage.custom_converter;

import com.butbetter.storage.model.Address;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class BeanAddressConverterTest {

	private final BeanAddressConverter<String, String> converter = new BeanAddressConverter<>();

	@Test
	void generalConvert() {
		String input = "Address{uuid='68c9791a-280a-4da0-b403-48b8d15f1301', name='a', companyName='a', street='a', city='a', postCode='a', country='a'}";
		Address address = new Address(UUID.fromString("68c9791a-280a-4da0-b403-48b8d15f1301"), "a", "a", "a", "a", "a", "a");
		try {
			assertEquals(address, converter.convert(input));
		} catch (CsvDataTypeMismatchException | CsvConstraintViolationException e) {
			fail(e);
		}
	}

	@Test
	void noUUIDConversionTest() {
		String input = "Address{uuid=null, name='a', companyName='a', street='a', city='a', postCode='a', country='a'}";
		assertThrows(CsvConstraintViolationException.class,  () -> converter.convert(input));
	}

	@Test
	void generalBrokenStringTest() {
		String input = "this is definitely not a valid input string";
		assertThrows(CsvDataTypeMismatchException.class, () -> converter.convert(input));
	}
}