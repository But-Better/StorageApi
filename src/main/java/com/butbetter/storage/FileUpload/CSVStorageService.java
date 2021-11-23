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
import java.util.stream.Stream;

@Service
public class CSVStorageService implements StorageService {

	private final Logger logger = LoggerFactory.getLogger(CSVStorageService.class);
	private final Path rootLocation;

	@Autowired
	public CSVStorageService(StorageProperties properties) {
		this.rootLocation = Paths.get(properties.getLocation());
	}

	@Override
	public void init() throws StorageException {
		try {
			Files.createDirectories(rootLocation);
		}
		catch (IOException e) {
			String message = "Could not initialize storage";
			logger.error(message);
			throw new StorageException(message, e);
		}
	}

	@Override
	public void store(MultipartFile file) throws StorageException {
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
	}

	@Override
	public Stream<Path> loadAll() throws StorageException {
		try {
			return Files.walk(this.rootLocation, 1)
					.filter(path -> !path.equals(this.rootLocation))
					.map(this.rootLocation::relativize);
		}
		catch (IOException e) {
			String message = "Failed to read stored files";
			logger.error(message);
			throw new StorageException(message, e);
		}

	}

	@Override
	public Path load(String filename) {
		return rootLocation.resolve(filename);
	}

	@Override
	public Resource loadAsResource(String filename) throws StorageFileNotFoundException {
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
		FileSystemUtils.deleteRecursively(rootLocation.toFile());
	}
}

