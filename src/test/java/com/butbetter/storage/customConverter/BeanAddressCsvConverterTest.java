package com.butbetter.storage.customConverter;

import com.butbetter.storage.csvImport.customConverter.BeanAddressCsvConverter;
import com.butbetter.storage.csvImport.model.AddressCsv;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BeanAddressCsvConverterTest {

	private final BeanAddressCsvConverter<String, String> converter = new BeanAddressCsvConverter<>();

	@Test
	void generalConvert() throws CsvConstraintViolationException, CsvDataTypeMismatchException {
		String input = "Address{uuid=null, name='a', companyName='a', street='a', city='a', postCode='a', country='a'}";
		AddressCsv address = new AddressCsv("a", "a", "a", "a", "a", "a");
		AddressCsv converted = (AddressCsv) converter.convert(input);

		address.setId(converted.getId());

		assertEquals(address, converted);
	}

	@Test
	void generalBrokenStringTest() {
		String input = "this is definitely not a valid input string";
		assertThrows(CsvDataTypeMismatchException.class, () -> converter.convert(input));
	}
}