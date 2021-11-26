package com.butbetter.storage.CSV;

import com.butbetter.storage.model.ProductInformation;
import com.opencsv.bean.CsvToBeanBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

/**
 * File-Importer for a class
 */
@Service
public class CSVImporter {

	private final Logger logger = LoggerFactory.getLogger(CSVImporter.class);

	public List<ProductInformation> getFromCSV(File file) throws FileNotFoundException {
		return new CsvToBeanBuilder<ProductInformation>(new FileReader(file))
				.withType(ProductInformation.class).build().parse();
	}

}