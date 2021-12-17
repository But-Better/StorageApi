package com.butbetter.storage.csv;

import com.butbetter.storage.model.ProductInformation;
import com.opencsv.bean.CsvToBeanBuilder;
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

	public List<ProductInformation> getFromCSV(Path file) throws FileNotFoundException {
		logger.info("converting csv data in file " + file + " to a List of Product Information");
		return new CsvToBeanBuilder<ProductInformation>(new FileReader(String.valueOf(file))).withType(ProductInformation.class).build().parse();
	}

}