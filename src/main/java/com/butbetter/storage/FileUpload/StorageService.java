package com.butbetter.storage.FileUpload;

import com.butbetter.storage.FileUpload.Exceptions.StorageException;
import com.butbetter.storage.FileUpload.Exceptions.StorageFileNotFoundException;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {

	/**
	 * initialize this Service
	 */
	void init() throws StorageException;

	/**
	 * store file
	 * @param file file to store
	 */
	void store(MultipartFile file) throws StorageException;

	/**
	 * get all available file-paths
	 * @return stream of all available paths
	 */
	Stream<Path> loadAll() throws StorageException;

	/**
	 * get last uploaded file
	 * @return path to last uploaded file
	 */
	Path loadLast() throws StorageFileNotFoundException, StorageException;

	/**
	 * get specific path of file by name
	 * @param filename filename of requested file
	 * @return Path to file
	 */
	Path load(String filename);

	/**
	 * get a specific file as resource
	 * @param filename filename of requested file
	 * @return resource of requested file
	 */
	Resource loadAsResource(String filename) throws StorageFileNotFoundException;

	/**
	 * delete all files
	 */
	void deleteAll();
}