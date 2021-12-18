package com.butbetter.storage.csv;

import com.butbetter.storage.csvImport.service.converter.CSVConverter;
import com.butbetter.storage.csvImport.service.importer.CSVImportService;
import com.butbetter.storage.csvImport.exception.FaultyCSVException;
import com.butbetter.storage.csvImport.exception.StorageFileNotFoundException;
import com.butbetter.storage.csvImport.customConverter.BeanOffsetDateTimeConverter;
import com.butbetter.storage.csvImport.model.AddressCsv;
import com.butbetter.storage.csvImport.model.ProductInformationCsv;
import com.butbetter.storage.csvImport.repository.FileAddressRepository;
import com.butbetter.storage.csvImport.repository.FileProductRepository;
import com.butbetter.storage.csvImport.validator.ProductInformationCsvValidator;
import com.github.javafaker.Faker;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class CSVImportServiceTest {

	private static final String BASE_PATH = "src/test/resources/";

	private Path testFile = Path.of(BASE_PATH + "test.csv");

	private CSVConverter converter;
	private FileProductRepository prodRepo;
	private FileAddressRepository addrRepo;

	private ProductInformationCsvValidator validator;

	private CSVImportService service;

	@BeforeEach
	void setUp() {
		converter = mock(CSVConverter.class);
		prodRepo = mock(FileProductRepository.class);
		addrRepo = mock(FileAddressRepository.class);
		validator = new ProductInformationCsvValidator();

		service = new CSVImportService(converter, prodRepo, addrRepo, validator);
	}

	@AfterEach
	void tearDown() {
		service = null;
		converter = null;
		prodRepo = null;
	}

	@Test
	void normalFromFileConversionTest() throws FaultyCSVException, FileNotFoundException, StorageFileNotFoundException {
		List<ProductInformationCsv> convertedList = Arrays.stream(new ProductInformationCsv[]{new ProductInformationCsv(OffsetDateTime.now(), Faker.instance().number().randomDigit(), new AddressCsv())}).collect(Collectors.toList());
		when(converter.getFromCSV(any())).thenReturn(convertedList);

		service.fromFile(testFile);

		verify(converter, times(1)).getFromCSV(testFile);
		verify(prodRepo, times(1)).saveAll(convertedList);
	}

	@Test
	void noElementsInFileTest() throws FileNotFoundException {
		when(converter.getFromCSV(any())).thenReturn(Arrays.stream(new ProductInformationCsv[0]).collect(Collectors.toList()));

		assertThrows(FaultyCSVException.class, () -> service.fromFile(testFile));

		verify(converter, times(1)).getFromCSV(testFile);
		verify(prodRepo, times(0)).saveAll(any());
	}

	@Test
	void usingActualConverterTest() throws CsvConstraintViolationException, CsvDataTypeMismatchException, FaultyCSVException, StorageFileNotFoundException {
		OffsetDateTime date = (OffsetDateTime) new BeanOffsetDateTimeConverter<String, OffsetDateTime>().convert("2021-11-20T14:20:53.128120+01:00");
		AddressCsv address = new AddressCsv("a", "a", "a", "a", "a", "a");

		List<ProductInformationCsv> convertedList = Arrays.stream(new ProductInformationCsv[]{new ProductInformationCsv(date, 5, address)}).collect(Collectors.toList());

		converter = new CSVConverter();
		service = new CSVImportService(converter, prodRepo, addrRepo, validator);

		service.fromFile(testFile);

		verify(prodRepo, times(1)).saveAll(convertedList);
	}
}