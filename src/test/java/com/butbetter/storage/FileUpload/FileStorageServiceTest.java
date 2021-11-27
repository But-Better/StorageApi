package com.butbetter.storage.FileUpload;

import com.butbetter.storage.CSV.CSVImportService;
import com.butbetter.storage.FileUpload.Exceptions.StorageException;
import com.butbetter.storage.FileUpload.Properties.StorageProperties;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import static com.butbetter.storage.FileUtilities.readEntireFile;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FileStorageServiceTest {

	private static final String BASE_PATH = "src/test/resources/";

	private StorageProperties property;
	private CSVImportService importer;

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
	}

	@AfterEach
	void tearDown() {
		file = null;
		service = null;
	}

	@Test
	void newFileIsStored() throws IOException {
		service.store(file);
		Path path = Paths.get(property.getLocation());
		assert Files.list(path).findAny().isPresent();
	}

	@Test
	void fileNameAreTheSame() throws StorageException {
		when(file.getOriginalFilename()).thenReturn("this_is_a_file");
		service.store(file);
		File newFileHandle = new File(property.getLocation() + "/" + file.getOriginalFilename());
		assert newFileHandle.exists();
	}

	@Test
	void contentsOfFileAreSame() throws IOException {
		service.store(file);
		File newFileHandle = new File(property.getLocation() + "/" + file.getOriginalFilename());
		assertEquals(readEntireFile(fileHandle), readEntireFile(newFileHandle));
	}
}