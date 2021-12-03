package com.butbetter.storage.custom_converter;

import com.butbetter.storage.model.Address;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BeanAddressConverterTest {

	@Test
	void generalConvert() {
		BeanAddressConverter<String, String> converter = new BeanAddressConverter<>();
		String input = "Address{uuid=null, name='a', companyName='a', street='a', city='a', postCode='a', country='a'}";
		Address address = new Address("a", "a", "a", "a", "a", "a");
		try {
			assertEquals(address, converter.convert(input));
		} catch (CsvDataTypeMismatchException | CsvConstraintViolationException e) {
			fail();
		}
	}
}