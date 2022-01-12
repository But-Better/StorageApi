package com.butbetter.storage.csv;

import com.butbetter.storage.csvImport.service.converter.CSVConverter;
import com.butbetter.storage.restApi.model.Address;
import com.butbetter.storage.restApi.model.ProductInformation;

import com.butbetter.storage.csvImport.model.ProductInformationCsv;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// TODO: FIX STUFF
class CSVConverterTest {

	private static final String BASE_PATH = "src/test/resources";
	private File file = new File(BASE_PATH + "/test.csv");
	private List<ProductInformation> productInformation;
	private final CSVConverter importer = new CSVConverter();

	@BeforeEach
	void setup() {
		productInformation = new ArrayList<>();

		productInformation.add(new ProductInformation(OffsetDateTime.parse("2021-12-19T19:28:36.615032+01:00"), 5, new Address( "a", "a", "a", "a", "a", "a")));
	}

	@Test
	void generalAddressConversionTest() throws IOException, CsvException {
		List<ProductInformationCsv> beans = importer.getFromCSV(file.toPath());
		assertEquals(productInformation.get(0).getAddress(), beans.get(0).getAddress());
	}

	@Test
	void generalFullConversionTest() throws IOException, CsvException {
		List<ProductInformationCsv> beans = importer.getFromCSV(file.toPath());
		assertEquals(productInformation.get(0), beans.get(0));
	}

	@Test
	void noFileFoundTest() {
		file = new File(RandomStringUtils.randomAlphabetic(10));
		assertThrows(FileNotFoundException.class, () -> importer.getFromCSV(file.toPath()));
	}
}