package com.butbetter.storage.csv;

import com.butbetter.storage.csv.exceptions.FaultyCSVException;
import com.butbetter.storage.fileUpload.exceptions.StorageFileNotFoundException;
import com.butbetter.storage.model.ProductInformation;
import com.butbetter.storage.repository.FileAddressRepository;
import com.butbetter.storage.repository.FileProductRepository;
import com.butbetter.storage.validator.IProductInformationValidator;
import com.butbetter.storage.validator.ProductInformationValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.List;

/**
 * File to CSV-Objects Converter Service
 */
@Service
public class CSVImportService {

	private final CSVConverter converter;
	private final FileAddressRepository addressRepository;
	private final FileProductRepository productRepository;

	private final IProductInformationValidator validator;

	private final Logger logger = LoggerFactory.getLogger(CSVImportService.class);

	/**
	 * Autowired CSVImportService Constructor
	 *
	 * @param converter         Converter
	 * @param productRepository repo to save objects to
	 * @param validator         {@link IProductInformationValidator}
	 */
	@Autowired
	public CSVImportService(CSVConverter converter, FileProductRepository productRepository, FileAddressRepository addressRepository, IProductInformationValidator validator) {
		this.converter = converter;
		this.productRepository = productRepository;
		this.addressRepository = addressRepository;
		this.validator = validator;
	}

	/**
	 * Converting and then Saving all objects from the given File at Path
	 *
	 * @param path Path to the File with CSV Information
	 *
	 * @throws StorageFileNotFoundException thrown, if the File couldn't be properly stored/processed before
	 * @throws FaultyCSVException           thrown, if no convertable CSV Elements were found in the File
	 */
	public void fromFile(Path path) throws FaultyCSVException, StorageFileNotFoundException {
		List<ProductInformation> info = getInformationOutOfFile(path);
		if (info.isEmpty()) {
			String message = "no elements where found in csv file at: " + path.toString();
			logger.error(message);
			throw new FaultyCSVException(message);
		}

		try {
			validateProductInformationList(info);
		} catch (NullPointerException | IllegalArgumentException e) {
			logger.error("couldn't validate all newly added Product-Information", e);
		}

		info.forEach(i -> addressRepository.save(i.getAddress()));
		productRepository.saveAll(info);
		logger.info("saved add new Product-Information (newly added:" + info.size() + ")");
	}

	/**
	 * validates all given {@link ProductInformation} in a list using the {@link IProductInformationValidator}
	 *
	 * @param info list of {@link ProductInformation}
	 */
	private void validateProductInformationList(List<ProductInformation> info) {
		info.forEach(validator::validate);
	}

	/**
	 * pulling information out of the file at given path
	 *
	 * @param path path to csv file
	 *
	 * @return List of converted ProductInformation
	 *
	 * @throws StorageFileNotFoundException thrown, if file can't be processed or File was not properly stored beforehand
	 */
	private List<ProductInformation> getInformationOutOfFile(Path path) throws StorageFileNotFoundException {
		try {
			return converter.getFromCSV(path);
		} catch (FileNotFoundException e) {
			String message = "the file wasn't stored properly/is nowhere to be found, and therefore couldn't be further processed";
			logger.error(message);
			throw new StorageFileNotFoundException(message, e);
		}
	}
}
