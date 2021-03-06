package com.butbetter.storage.fileUpload;

import com.butbetter.storage.csvImport.exception.StorageFileNotProcessableException;
import com.butbetter.storage.csvImport.service.importer.CSVImportService;
import com.butbetter.storage.csvImport.exception.FaultyCSVException;
import com.butbetter.storage.csvImport.service.fileStorage.FileStorageService;
import com.butbetter.storage.csvImport.exception.StorageException;
import com.butbetter.storage.csvImport.exception.StorageFileNotFoundException;
import com.butbetter.storage.csvImport.properties.StorageProperties;
import com.opencsv.exceptions.CsvException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static com.butbetter.storage.FileUtilities.readEntireFile;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FileStorageServiceTest {

	private static final String BASE_PATH = "src/test/resources/";

	private StorageProperties property;
	private Path properties_path;

	private MultipartFile file;
	private File fileHandle;

	private FileStorageService service;

	private final String fileName = "write_test.csv";

	@BeforeEach
	void setUp() throws IOException {
		CSVImportService importer = mock(CSVImportService.class);
		property = new StorageProperties(BASE_PATH + "csvs");

		fileHandle = new File(BASE_PATH + fileName);

		if (!fileHandle.exists()) {
			throw new FileNotFoundException(fileName + " test file couldn't be found in " + BASE_PATH);
		}

		file = mock(MultipartFile.class);
		when(file.getOriginalFilename()).thenReturn(fileName);
		when(file.getInputStream()).thenReturn(new FileInputStream(fileHandle));
		when(file.getResource()).thenReturn(new UrlResource(fileHandle.toURI()));

		service = new FileStorageService(property, importer);
		service.init();

		properties_path = Paths.get(property.getLocation());
	}

	@AfterEach
	void tearDown() throws IOException {
		// clean csvs test directory
		if (Files.list(properties_path).findAny().isPresent()) {
			FileSystemUtils.deleteRecursively(properties_path);
		}

		file = null;
		service = null;
	}

	@Test
	void newFileIsStored() throws IOException, FaultyCSVException, CsvException, StorageFileNotProcessableException {
		service.store(file);
		assert Files.list(properties_path).findAny().isPresent();
	}

	@Test
	void fileNameAreTheSame() throws StorageException, FaultyCSVException, CsvException, StorageFileNotProcessableException {
		when(file.getOriginalFilename()).thenReturn("this_is_a_file");
		service.store(file);
		File newFileHandle = new File(property.getLocation() + "/" + file.getOriginalFilename());
		assert newFileHandle.exists();
	}

	@Test
	void contentsOfFileAreSame() throws IOException, FaultyCSVException, CsvException, StorageFileNotProcessableException {
		service.store(file);
		File newFileHandle = new File(property.getLocation() + "/" + file.getOriginalFilename());
		assertEquals(readEntireFile(fileHandle), readEntireFile(newFileHandle));
	}

	@Test
	void loadAllFileExistsTest() throws FaultyCSVException, StorageException, CsvException, StorageFileNotProcessableException {
		service.store(file);

		assertTrue(service.loadAll().findFirst().isPresent());
	}

	@Test
	void loadAllGeneralTest() throws FaultyCSVException, StorageException, CsvException, StorageFileNotProcessableException {
		service.store(file);

		Stream<Path> loadedFiles = service.loadAll();
		assertEquals(file.getOriginalFilename(), loadedFiles.findFirst().get().toString());
	}

	@Test
	void simpleLoadLastTest() throws FaultyCSVException, StorageException, StorageFileNotFoundException, CsvException, StorageFileNotProcessableException {
		service.store(file);
		Path loadedFile = service.loadLast();
		assertEquals(file.getOriginalFilename(), loadedFile.toString());
	}

	@Test
	void simpleLoadTest() throws FaultyCSVException, StorageException, StorageFileNotFoundException, CsvException, StorageFileNotProcessableException {
		service.store(file);
		assertEquals(properties_path.toString() + "/" + fileName, service.load(file.getOriginalFilename()).toString());
	}

	@Test
	void simpleLoadAsResourceTest() throws FaultyCSVException, IOException, StorageFileNotFoundException, CsvException, StorageFileNotProcessableException {
		service.store(file);
		File expectedFileHandle = new File(properties_path.toAbsolutePath() + "/" + fileName);
		Resource expected = new UrlResource(expectedFileHandle.toURI());
		assertEquals(expected, service.loadAsResource(fileName));
	}

	@Test
	void singleDeleteAllTest() throws FaultyCSVException, StorageException, CsvException, StorageFileNotProcessableException {
		service.store(file);
		service.deleteAll();
		assert service.loadAll().findAny().isEmpty();
	}

}