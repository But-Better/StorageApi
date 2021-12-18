package com.butbetter.storage.customConverter;

import com.butbetter.storage.csvImport.customConverter.BeanAddressCsvConverter;
import com.butbetter.storage.restApi.model.Address;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BeanAddressCsvConverterTest {

	private final BeanAddressCsvConverter<String, String> converter = new BeanAddressCsvConverter<>();

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