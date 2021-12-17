package com.butbetter.storage.csv;

import com.butbetter.storage.csv.exceptions.FaultyCSVException;
import com.butbetter.storage.file_upload.Exceptions.StorageFileNotFoundException;
import com.butbetter.storage.custom_converter.BeanOffsetDateTimeConverter;
import com.butbetter.storage.model.Address;
import com.butbetter.storage.model.ProductInformation;
import com.butbetter.storage.repository.FileAddressRepository;
import com.butbetter.storage.repository.FileProductRepository;
import com.butbetter.storage.validator.ProductInformationValidator;
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
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class CSVImportServiceTest {

	private static final String BASE_PATH = "src/test/resources/";

	private Path testFile = Path.of(BASE_PATH + "test.csv");

	private CSVConverter converter;
	private FileProductRepository prodRepo;
	private FileAddressRepository addrRepo;

	private ProductInformationValidator validator;

	private CSVImportService service;

	@BeforeEach
	void setUp() {
		converter = mock(CSVConverter.class);
		prodRepo = mock(FileProductRepository.class);
		addrRepo = mock(FileAddressRepository.class);
		validator = new ProductInformationValidator();

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
		List<ProductInformation> convertedList = Arrays.stream(new ProductInformation[]{new ProductInformation(UUID.randomUUID(), OffsetDateTime.now(), Faker.instance().number().randomDigit(), new Address())}).collect(Collectors.toList());
		when(converter.getFromCSV(any())).thenReturn(convertedList);

		service.fromFile(testFile);

		verify(converter, times(1)).getFromCSV(testFile);
		verify(prodRepo, times(1)).saveAll(convertedList);
	}

	@Test
	void noElementsInFileTest() throws FileNotFoundException {
		when(converter.getFromCSV(any())).thenReturn(Arrays.stream(new ProductInformation[0]).collect(Collectors.toList()));

		assertThrows(FaultyCSVException.class, () -> service.fromFile(testFile));

		verify(converter, times(1)).getFromCSV(testFile);
		verify(prodRepo, times(0)).saveAll(any());
	}

	@Test
	void usingActualConverterTest() throws CsvConstraintViolationException, CsvDataTypeMismatchException, FaultyCSVException, StorageFileNotFoundException {
		OffsetDateTime date = (OffsetDateTime) new BeanOffsetDateTimeConverter<String, OffsetDateTime>().convert("2021-11-20T14:20:53.128120+01:00");
		Address address = new Address(UUID.fromString("68c9791a-280a-4da0-b403-48b8d15f1301"), "a", "a", "a", "a", "a", "a");

		List<ProductInformation> convertedList = Arrays.stream(new ProductInformation[]{new ProductInformation(UUID.fromString("68c9791a-280a-4da0-b403-48b8d15f1301"), date, 5, address)}).collect(Collectors.toList());

		converter = new CSVConverter();
		service = new CSVImportService(converter, prodRepo, addrRepo, validator);

		service.fromFile(testFile);

		verify(prodRepo, times(1)).saveAll(convertedList);
	}
}