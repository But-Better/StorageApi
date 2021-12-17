package com.butbetter.storage.file_upload;

import com.butbetter.storage.csv.exceptions.FaultyCSVException;
import com.butbetter.storage.file_upload.exceptions.StorageException;
import com.butbetter.storage.file_upload.exceptions.StorageFileNotFoundException;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {

	/**
	 * initialize this Service
	 * @throws StorageException thrown, if storage couldn't be initialized
	 */
	void init() throws StorageException;

	/**
	 * store file
	 * @param file file to store
	 * @throws StorageException thrown, if file couldn't be stored
	 * @throws FaultyCSVException thrown, if file couldn't be correctly processed
	 */
	void store(MultipartFile file) throws StorageException, FaultyCSVException, StorageFileNotFoundException;

	/**
	 * get all available file-paths
	 * @return stream of all available paths
	 * @throws StorageException thrown, if no files cold be read
	 */
	Stream<Path> loadAll() throws StorageException;

	/**
	 * get last uploaded file
	 * @return path to last uploaded file
	 * @throws StorageFileNotFoundException thrown, no files where loaded in yet
	 * @throws StorageException thrown, if the last file couldn't be read in general
	 */
	Path loadLast() throws StorageFileNotFoundException, StorageException;

	/**
	 * get specific path of file by name
	 * @param filename filename of requested file
	 * @return Path to file
	 *
	 * @throws StorageFileNotFoundException thrown, if file with given name couldn't be found
	 */
	Path load(String filename) throws StorageFileNotFoundException;

	/**
	 * get a specific file as resource
	 * @param filename filename of requested file
	 * @return resource of requested file
	 *
	 * @throws StorageFileNotFoundException thrown, if file with given name couldn't be found
	 */
	Resource loadAsResource(String filename) throws StorageFileNotFoundException;

	/**
	 * delete all files
	 * @throws StorageException thrown, if storage couldn't remove all files without complications
	 * (Files may be still present)
	 */
	void deleteAll() throws StorageException;
}