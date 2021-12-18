package com.butbetter.storage.csvImport.service.importer;

import com.butbetter.storage.csvImport.exception.FaultyCSVException;
import com.butbetter.storage.csvImport.exception.StorageFileNotFoundException;
import org.springframework.stereotype.Service;

import java.nio.file.Path;

@Service
public interface ICSVImportService {
	/**
	 * Converting and then Saving all objects from the given File at Path
	 *
	 * @param path Path to the File with CSV Information
	 *
	 * @throws StorageFileNotFoundException thrown, if the File couldn't be properly stored/processed before
	 * @throws FaultyCSVException           thrown, if no convertable CSV Elements were found in the File
	 */
	void fromFile(Path path) throws FaultyCSVException, StorageFileNotFoundException;
}
