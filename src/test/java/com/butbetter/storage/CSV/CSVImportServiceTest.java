package com.butbetter.storage.CSV;

import com.butbetter.storage.CSV.Exceptions.FaultyCSVException;
import com.butbetter.storage.FileUpload.Exceptions.StorageException;
import com.butbetter.storage.ProductRepository;
import com.butbetter.storage.customConverter.BeanAddressConverter;
import com.butbetter.storage.customConverter.BeanOffsetDateTimeConverter;
import com.butbetter.storage.model.Address;
import com.butbetter.storage.model.ProductInformation;
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
	private ProductRepository repo;

	private CSVImportService service;

	@BeforeEach
	void setUp() {
		converter = mock(CSVConverter.class);
		repo = mock(ProductRepository.class);

		service = new CSVImportService(converter, repo);
	}

	@AfterEach
	void tearDown() {
		service = null;
		converter = null;
		repo = null;
	}

	@Test
	void normalFromFileConversionTest() throws StorageException, FaultyCSVException, FileNotFoundException {
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
	void usingActualConverterTest() throws CsvConstraintViolationException, CsvDataTypeMismatchException, FaultyCSVException, StorageException {
		OffsetDateTime date = (OffsetDateTime) new BeanOffsetDateTimeConverter<String, OffsetDateTime>().convert("2021-11-20T14:20:53.128120+01:00");
		Address address = (Address) new BeanAddressConverter<String, Address>().convert("Address{uuid=null, name='a', companyName='a', street='a', city='a', postCode='a', country='a'}");

		List<ProductInformation> convertedList = Arrays.stream(new ProductInformation[]{new ProductInformation(date, 5, address)}).collect(Collectors.toList());

		converter = new CSVConverter();
		service = new CSVImportService(converter, repo);

		service.fromFile(testFile);

		verify(repo, times(1)).saveAll(convertedList);
	}
}