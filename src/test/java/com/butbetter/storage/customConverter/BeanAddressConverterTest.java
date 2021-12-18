package com.butbetter.storage.customConverter;

import com.butbetter.storage.model.Address;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class BeanAddressConverterTest {

	private final BeanAddressConverter<String, String> converter = new BeanAddressConverter<>();

	@Test
	void generalConvert() throws CsvConstraintViolationException, CsvDataTypeMismatchException {
		String input = "Address{name='a', companyName='a', street='a', city='a', postCode='a', country='a'}";
		Address address = new Address("a", "a", "a", "a", "a", "a");
		Address converted = (Address) converter.convert(input);

		address.setId(converted.getId());

		assertEquals(address, converted);
	}

	@Test
	void throwDataTypeMismatchOnGivenUuid() {
		String input = "Address{uuid=null, name='a', companyName='a', street='a', city='a', postCode='a', country='a'}";
		assertThrows(CsvDataTypeMismatchException.class,  () -> converter.convert(input));
	}

	@Test
	void generalBrokenStringTest() {
		String input = "this is definitely not a valid input string";
		assertThrows(CsvDataTypeMismatchException.class, () -> converter.convert(input));
	}
}