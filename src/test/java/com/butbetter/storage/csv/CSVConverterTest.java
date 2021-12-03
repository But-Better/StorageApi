package com.butbetter.storage.csv;

import com.butbetter.storage.file_upload.Exceptions.StorageFileNotFoundException;
import com.butbetter.storage.model.Address;
import com.butbetter.storage.model.ProductInformation;

import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CSVConverterTest {

	private static final String BASE_PATH = "src/test/resources";
	private File file = new File(BASE_PATH + "/test.csv");
	private List<ProductInformation> productInformation;
	private final CSVConverter importer = new CSVConverter();

	@BeforeEach
	void setup() throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
		if (!file.exists()){
			if (!file.createNewFile()) {
				throw new IOException("not able to create temp file, probably no permissions");
			}
		}

		productInformation = new ArrayList<>();

		productInformation.add(new ProductInformation(OffsetDateTime.now(), 5, new Address("a", "a", "a", "a", "a", "a")));
		Writer writer = new FileWriter(file);
		StatefulBeanToCsv<ProductInformation> beanToCsv = new StatefulBeanToCsvBuilder<ProductInformation>(writer).build();
		beanToCsv.write(productInformation);
		writer.close();
	}

	@Test
	void generalTheoreticalSerializeTest() throws IOException, StorageFileNotFoundException {
		List<ProductInformation> beans = importer.getFromCSV(file.toPath());
		assertEquals(productInformation.get(0), beans.get(0));
	}

	@Test
	void noFileFoundTest() {
		file = new File(RandomStringUtils.randomAlphabetic(10));
		assertThrows(StorageFileNotFoundException.class, () -> importer.getFromCSV(file.toPath()));
	}
}