package com.butbetter.storage.csv;

import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.List;

@Service
public interface ICSVConverter<P> {

	/**
	 * conversion method from a file
	 *
	 * @param file path to file
	 * @return list of Objects in File
	 *
	 * @throws FileNotFoundException thrown, if file can't be found
	 */
	List<P> getFromCSV(Path file) throws FileNotFoundException;
}
