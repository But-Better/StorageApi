package com.butbetter.storage.csvImport.service.importer;

import com.butbetter.storage.csvImport.exception.FaultyCSVException;
import com.butbetter.storage.csvImport.exception.StorageFileNotFoundException;
import com.butbetter.storage.csvImport.model.ProductInformationCsv;
import com.butbetter.storage.csvImport.repository.FileAddressRepository;
import com.butbetter.storage.csvImport.repository.FileProductRepository;
import com.butbetter.storage.csvImport.service.converter.ICSVProductInformationConverter;
import com.butbetter.storage.csvImport.validator.IProductInformationCsvValidator;
import com.butbetter.storage.restApi.validator.IProductInformationValidator;
import com.opencsv.exceptions.CsvException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.List;

/**
 * File to CSV-Objects Converter Service
 */
@Service
public class CSVImportService implements ICSVImportService {

	private final ICSVProductInformationConverter converter;
	private final FileAddressRepository addressRepository;
	private final FileProductRepository productRepository;

	private final IProductInformationCsvValidator validator;

	private final Logger logger = LoggerFactory.getLogger(CSVImportService.class);

	/**
	 * Autowired CSVImportService Constructor
	 *
	 * @param converter         Converter
	 * @param productRepository repo to save objects to
	 * @param validator         {@link IProductInformationCsvValidator}
	 */
	@Autowired
	public CSVImportService(@Qualifier("CSVConverter") ICSVProductInformationConverter converter, FileProductRepository productRepository, FileAddressRepository addressRepository, IProductInformationCsvValidator validator) {
		this.converter = converter;
		this.productRepository = productRepository;
		this.addressRepository = addressRepository;
		this.validator = validator;
	}

	public void fromFile(Path path) throws FaultyCSVException, StorageFileNotFoundException, CsvException {
		List<ProductInformationCsv> productInformationList = getInformationOutOfFile(path);
		if (productInformationList.isEmpty()) {
			String message = "no elements where found in csv file at: " + path.toString();
			logger.error(message);
			throw new FaultyCSVException(message);
		}

		try {
			validateProductInformationList(productInformationList);
		} catch (NullPointerException | IllegalArgumentException e) {
			logger.error("couldn't validate all newly added Product-Information", e);
		}

		productInformationList.forEach(productInformation -> addressRepository.save(productInformation.getAddress()));

		productRepository.saveAll(productInformationList);
		logger.info("saved add new Product-Information (newly added:" + productInformationList.size() + ")");
	}

	/**
	 * validates all given {@link ProductInformationCsv} in a list using the {@link IProductInformationValidator}
	 *
	 * @param info list of {@link ProductInformationCsv}
	 */
	private void validateProductInformationList(List<ProductInformationCsv> info) {
		info.forEach(validator::validate);
	}

	/**
	 * pulling information out of the file at given path
	 *
	 * @param path path to csv file
	 *
	 * @return List of converted ProductInformation
	 *
	 * @throws StorageFileNotFoundException thrown, if file can't be processed or File was not properly stored
	 *                                      beforehand
	 */
	private List<ProductInformationCsv> getInformationOutOfFile(Path path) throws StorageFileNotFoundException, CsvException {
		try {
			return converter.getFromCSV(path);
		} catch (FileNotFoundException e) {
			String message = "the file wasn't stored properly/is nowhere to be found, and therefore couldn't be further processed";
			logger.error(message);
			throw new StorageFileNotFoundException(message, e);
		}
	}
}
