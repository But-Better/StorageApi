package com.butbetter.storage.FileUpload;

import com.butbetter.storage.CSV.CSVImportService;
import com.butbetter.storage.CSV.Exceptions.FaultyCSVException;
import com.butbetter.storage.FileUpload.Exceptions.StorageException;
import com.butbetter.storage.FileUpload.Exceptions.StorageFileNotFoundException;
import com.butbetter.storage.FileUpload.Properties.StorageProperties;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FileStorageServiceTest {

	private static final String BASE_PATH = "src/test/resources/";

	private StorageProperties property;
	private CSVImportService importer;
	private Path properties_path;

	private MultipartFile file;
	private File fileHandle;

	private FileStorageService service;

	private final String fileName = "test.csv";

	@BeforeEach
	void setUp() throws IOException {
		importer = mock(CSVImportService.class);
		property = new StorageProperties(BASE_PATH + "csvs");

		fileHandle = new File(BASE_PATH + fileName);

		if (!fileHandle.exists()) {
			throw new FileNotFoundException(fileName + " test file couldn't be found in " + BASE_PATH);
		}

		file = mock(MultipartFile.class);
		when(file.getOriginalFilename()).thenReturn(fileName);
		when(file.getInputStream()).thenReturn(new FileInputStream(fileHandle));

		service = new FileStorageService(property, importer);
		service.init();

		properties_path = Paths.get(property.getLocation());
	}

	@AfterEach
	void tearDown() throws IOException {
		// clean csvs test directory
		if (Files.list(properties_path).findAny().isPresent()) {
			Files.list(properties_path).forEach(f -> f.toFile().delete());
		}

		file = null;
		service = null;
	}

	@Test
	void newFileIsStored() throws IOException, FaultyCSVException {
		service.store(file);
		assert Files.list(properties_path).findAny().isPresent();
	}

	@Test
	void fileNameAreTheSame() throws StorageException,FaultyCSVException {
		when(file.getOriginalFilename()).thenReturn("this_is_a_file");
		service.store(file);
		File newFileHandle = new File(property.getLocation() + "/" + file.getOriginalFilename());
		assert newFileHandle.exists();
	}

	@Test
	void contentsOfFileAreSame() throws IOException, FaultyCSVException {
		service.store(file);
		File newFileHandle = new File(property.getLocation() + "/" + file.getOriginalFilename());
		assertEquals(readEntireFile(fileHandle), readEntireFile(newFileHandle));
	}

	@Test
	void loadAllGeneralTest() throws FaultyCSVException, StorageException {
		service.store(file);
		Stream<Path> loadedFiles = service.loadAll();
		assertEquals(file.getOriginalFilename(), loadedFiles.findFirst().get().toString());
	}

	@Test
	void simpleLoadLastTest() throws FaultyCSVException, StorageException, StorageFileNotFoundException {
		service.store(file);
		Path loadedFile = service.loadLast();
		assertEquals(file.getOriginalFilename(), loadedFile.toString());
	}

	@Test
	void simpleLoadTest() throws FaultyCSVException, StorageException {
		service.store(file);
		assertEquals(file.getOriginalFilename(), service.load(file.getOriginalFilename()).toString());
	}

	@Test
	void simpleLoadAsResourceTest() {
		// TODO
	}

	@Test
	void singleDeleteAllTest() {
		// TODO
	}

}