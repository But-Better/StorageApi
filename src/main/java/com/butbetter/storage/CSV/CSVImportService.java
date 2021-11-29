package com.butbetter.storage.CSV;

import com.butbetter.storage.FileUpload.Exceptions.StorageException;
import com.butbetter.storage.ProductRepository;
import com.butbetter.storage.model.ProductInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.List;

@Service
public class CSVImportService {

	private final CSVConverter converter;
	private final ProductRepository repo;

	private Logger logger = LoggerFactory.getLogger(CSVImportService.class);

	@Autowired
	public CSVImportService(CSVConverter converter, ProductRepository repo) {
		this.converter = converter;
		this.repo = repo;
	}

	/**
	 * Converting and then Saving all objects from the given File at Path
	 *
	 * @param path Path to the File with CSV Information
	 *
	 * @throws StorageException thrown, if the File couldn't be properly stored/processed before
	 * @throws FaultyCSVException thrown, if no convertable CSV Elements were found in the File
	 */
	public void fromFile(Path path) throws StorageException, FaultyCSVException {
		List<ProductInformation> info = getInformationOutOfFile(path);
		if (info.isEmpty()) {
			String message = "no elements where found in csv file at: " + path.toString();
			logger.error(message);
			throw new FaultyCSVException(message);
		}
		repo.saveAll(info);
	}

	private List<ProductInformation> getInformationOutOfFile(Path path) throws StorageException {
		try {
			return converter.getFromCSV(path);
		} catch (FileNotFoundException e) {
			String message = "the file wasn't stored properly/is nowhere to be found, and therefore couldn't be further processed";
			logger.error(message);
			throw new StorageException(message, e);
		}
	}
}
