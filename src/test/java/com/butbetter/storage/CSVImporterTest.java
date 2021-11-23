package com.butbetter.storage;

import com.butbetter.storage.model.Address;
import com.butbetter.storage.model.ProductInformation;

import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CSVImporterTest {

	private static final String BASE_PATH = "src/test/resources";
	private final File file = new File(BASE_PATH + "/test.csv");
	private List<ProductInformation> productInformation;
	private final CSVImporter importer = new CSVImporter();

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
	void generalTheoreticalSerializeTest() throws IOException {
		List<ProductInformation> beans = importer.getFromCSV(file);
		assertEquals(productInformation.get(0), beans.get(0));
	}
}