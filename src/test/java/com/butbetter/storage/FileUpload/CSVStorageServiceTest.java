package com.butbetter.storage.FileUpload;

import com.butbetter.storage.CSV.CSVImporter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

class CSVStorageServiceTest {

	private static final String BASE_PATH = "src/test/resources/";

	private CSVImporter converter;
	private StorageProperties property;

	private CSVStorageService service;

	private MultipartFile file;
	private final String fileName = "test.csv";
	@BeforeEach
	void setUp() throws IOException {
		converter = new CSVImporter();
		property = new StorageProperties(BASE_PATH + "/csvs/");

		if (!new File(BASE_PATH + fileName).exists()) {
			throw new FileNotFoundException(fileName + " test file couldn't be found in " + BASE_PATH);
		}
		file = new MockMultipartFile("testFile", new FileInputStream(BASE_PATH + fileName));

		service = new CSVStorageService(property, converter);
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