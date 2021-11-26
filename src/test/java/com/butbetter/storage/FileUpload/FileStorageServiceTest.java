package com.butbetter.storage.FileUpload;

import com.butbetter.storage.CSV.CSVImportService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.mockito.Mockito.mock;

class FileStorageServiceTest {

	private static final String BASE_PATH = "src/test/resources/";

	private StorageProperties property;

	private FileStorageService service;
	private CSVImportService importer;

	private MultipartFile file;

	@BeforeEach
	void setUp() throws IOException {
		importer = mock(CSVImportService.class);
		property = new StorageProperties(BASE_PATH + "/csvs/");

		String fileName = "test.csv";

		if (!new File(BASE_PATH + fileName).exists()) {
			throw new FileNotFoundException(fileName + " test file couldn't be found in " + BASE_PATH);
		}
		file = new MockMultipartFile(fileName, new FileInputStream(BASE_PATH + fileName));

		service = new FileStorageService(property, importer);
	}

	@AfterEach
	void tearDown() {
		file = null;
		service = null;
	}

	@Test
	void store() throws StorageException {
		service.store(file);
	}
}