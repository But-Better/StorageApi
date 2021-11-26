package com.butbetter.storage.FileUpload;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CSVStorageService implements StorageService {

	private final Logger logger = LoggerFactory.getLogger(CSVStorageService.class);
	private final Path rootLocation;

	@Autowired
	public CSVStorageService(StorageProperties properties) {
		logger.info("Location: \"" + properties.getLocation() + "\" is getting used for the save location");
		this.rootLocation = Paths.get(properties.getLocation());
	}

	@Override
	public void init() throws StorageException {
		logger.info("initializing Storage");
		try {
			Files.createDirectories(rootLocation);
		}
		catch (IOException e) {
			String message = "Could not initialize storage, directory at \"" + rootLocation + "\" could not be created";
			logger.error(message);
			throw new StorageException(message, e);
		}
		logger.info("initialisation complete");
	}

	@Override
	public void store(MultipartFile file) throws StorageException {
		logger.info("storing " + file.getOriginalFilename());
		try {
			if (file.isEmpty()) {
				String message = "Failed to store empty file";
				logger.error(message);
				throw new StorageException(message);
			}
			Path destinationFile = this.rootLocation.resolve(
							Paths.get(Objects.requireNonNull(file.getOriginalFilename())))
					.normalize().toAbsolutePath();

			if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
				// This is a security check
				String message = "Cannot store file outside current directory";
				logger.error(message);
				throw new StorageException(message);
			}


			try (InputStream inputStream = file.getInputStream()) {
				Files.copy(inputStream, destinationFile,
						StandardCopyOption.REPLACE_EXISTING);
			}
		}
		catch (IOException e) {
			String message = "Failed to store file";
			logger.error(message);
			throw new StorageException(message, e);
		}
		logger.info("stored " + file.getOriginalFilename());

		try {
			List<ProductInformation> information = converter.getFromCSV(load(file.getOriginalFilename()));
		} catch (FileNotFoundException e) {
			String message = "the file wasn't stored correctly, and therefore couldn't be further processed";
			logger.error(message);
			throw new StorageException(message, e);
		}
	}

	@Override
	public Stream<Path> loadAll() throws StorageException {
		logger.info("loading all");
		try {
			Stream<Path> paths = Files.walk(this.rootLocation, 1)
					.filter(path -> !path.equals(this.rootLocation))
					.map(this.rootLocation::relativize);
			logger.info("loaded all");
			return paths;
		}
		catch (IOException e) {
			String message = "Failed to read stored files";
			logger.error(message);
			throw new StorageException(message, e);
		}
	}

	@Override
	public Path loadLast() throws StorageFileNotFoundException, StorageException {
		logger.info("loading last");
		try {
			Stream<Path> all = Files.walk(this.rootLocation, 1)
					.filter(path -> !path.equals(this.rootLocation));
			if(all.findAny().isEmpty()) {
				String message = "last file can't be found, since no files were uploaded yet";
				logger.error(message);
				throw new StorageFileNotFoundException(message);
			}
			logger.info("last loaded");
			return all.skip(all.count() - 1)
					.findFirst()
					.orElse(null);
		} catch (IOException e) {
			String message = "Failed to read stored files";
			logger.error(message);
			throw new StorageException(message, e);
		}
	}

	@Override
	public Path load(String filename) {
		logger.info("loading " + filename);
		return rootLocation.resolve(filename);
	}

	@Override
	public Resource loadAsResource(String filename) throws StorageFileNotFoundException {
		logger.info("loading " + filename);
		try {
			Path file = load(filename);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			}
			else {
				String message = "Could not read file: " + filename;
				logger.error(message);
				throw new StorageFileNotFoundException(message);

			}
		}
		catch (MalformedURLException e) {
			String message = "Could not read file: " + filename;
			logger.error(message);
			throw new StorageFileNotFoundException(message, e);
		}
	}

	@Override
	public void deleteAll() {
		logger.info("removing all known files");
		FileSystemUtils.deleteRecursively(rootLocation.toFile());
		logger.info("removed all known files");
	}
}

