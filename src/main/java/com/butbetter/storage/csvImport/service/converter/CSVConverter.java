package com.butbetter.storage.csvImport.service.converter;

import com.butbetter.storage.csvImport.model.ProductInformationCsv;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;
import java.util.List;

/**
 * CSV-File-to-Objects-Converter
 */
@Service
public class CSVConverter implements ICSVProductInformationConverter {

	private final Logger logger = LoggerFactory.getLogger(CSVConverter.class);

	public List<ProductInformationCsv> getFromCSV(Path file) throws FileNotFoundException, CsvException {
		logger.info("converting csv data in file " + file + " to a List of Product Information");
		try {
			return new CsvToBeanBuilder<ProductInformationCsv>(new FileReader(String.valueOf(file))).withType(ProductInformationCsv.class).build().parse();
		} catch (IllegalStateException e) {
			String message = "failed to parse csv data into objects with error: " + e.getMessage();
			logger.error(message, e);
			throw new CsvException(message);
		}
	}

}