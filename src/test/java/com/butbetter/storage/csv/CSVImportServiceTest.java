package com.butbetter.storage.csv;

import com.butbetter.storage.csv.Exceptions.FaultyCSVException;
import com.butbetter.storage.file_upload.Exceptions.StorageFileNotFoundException;
import com.butbetter.storage.custom_converter.BeanAddressConverter;
import com.butbetter.storage.custom_converter.BeanOffsetDateTimeConverter;
import com.butbetter.storage.model.Address;
import com.butbetter.storage.model.ProductInformation;
import com.butbetter.storage.repository.FileProductRepository;
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
	private FileProductRepository repo;

	private CSVImportService service;

	@BeforeEach
	void setUp() {
		converter = mock(CSVConverter.class);
		repo = mock(FileProductRepository.class);

		service = new CSVImportService(converter, repo);
	}

	@AfterEach
	void tearDown() {
		service = null;
		converter = null;
		repo = null;
	}

	@Test
	void normalFromFileConversionTest() throws FaultyCSVException, FileNotFoundException, StorageFileNotFoundException {
		List<ProductInformation> convertedList = Arrays.stream(new ProductInformation[]{new ProductInformation()}).collect(Collectors.toList());
		when(converter.getFromCSV(any())).thenReturn(convertedList);

		service.fromFile(testFile);

		verify(converter, times(1)).getFromCSV(testFile);
		verify(repo, times(1)).saveAll(convertedList);
	}

	@Test
	void noElementsInFileTest() throws FileNotFoundException {
		when(converter.getFromCSV(any())).thenReturn(Arrays.stream(new ProductInformation[0]).collect(Collectors.toList()));

		assertThrows(FaultyCSVException.class, () -> service.fromFile(testFile));

		verify(converter, times(1)).getFromCSV(testFile);
		verify(repo, times(0)).saveAll(any());
	}

	@Test
	void usingActualConverterTest() throws CsvConstraintViolationException, CsvDataTypeMismatchException, FaultyCSVException, StorageFileNotFoundException {
		OffsetDateTime date = (OffsetDateTime) new BeanOffsetDateTimeConverter<String, OffsetDateTime>().convert("2021-11-20T14:20:53.128120+01:00");
		Address address = (Address) new BeanAddressConverter<String, Address>().convert("Address{uuid=null, name='a', companyName='a', street='a', city='a', postCode='a', country='a'}");

		List<ProductInformation> convertedList = Arrays.stream(new ProductInformation[]{new ProductInformation(date, 5, address)}).collect(Collectors.toList());

		converter = new CSVConverter();
		service = new CSVImportService(converter, repo);

		service.fromFile(testFile);

		verify(repo, times(1)).saveAll(convertedList);
	}
}