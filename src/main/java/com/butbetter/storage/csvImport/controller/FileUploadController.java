package com.butbetter.storage.csvImport.controller;

import com.butbetter.storage.csvImport.exception.FaultyCSVException;
import com.butbetter.storage.csvImport.exception.StorageException;
import com.butbetter.storage.csvImport.exception.StorageFileNotFoundException;
import com.butbetter.storage.csvImport.exception.StorageFileNotProcessableException;
import com.butbetter.storage.csvImport.service.fileStorage.IFileStorageService;
import com.opencsv.exceptions.CsvException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("csv/v1")
public class FileUploadController {

	private final Logger logger = LoggerFactory.getLogger(FileUploadController.class);

	private final IFileStorageService fileStorageService;

	@Autowired
	public FileUploadController(IFileStorageService fileStorageService) {
		this.fileStorageService = fileStorageService;
	}

	@PostMapping("/")
	public ResponseEntity<?> handleFileUpload(@RequestParam("file") MultipartFile file) throws StorageException, FaultyCSVException, StorageFileNotFoundException, CsvException, StorageFileNotProcessableException {
		logger.info("file upload started, saving: " + file.getOriginalFilename());
		fileStorageService.store(file);
		return ResponseEntity.ok().body("file " + file.getOriginalFilename() + " was successfully migrated");
	}

	@GetMapping("/")
	public ResponseEntity<List<Path>> listAllFiles() throws StorageException {
		logger.info("request for listing all available files");
		List<Path> availableList = fileStorageService.loadAll().collect(Collectors.toList());
		return ResponseEntity.ok().body(availableList);
	}

	@GetMapping("/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable String filename) throws StorageFileNotFoundException {
		Resource file = fileStorageService.loadAsResource(filename);
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
	}


	@ExceptionHandler(IOException.class)
	public ResponseEntity<?> handleIOException(IOException e) {
		logger.error(e.getMessage());
		return ResponseEntity.badRequest().body(e.getMessage());
	}

	@ExceptionHandler(FaultyCSVException.class)
	public ResponseEntity<?> handleFaultyCSVException(FaultyCSVException e) {
		logger.error(e.getMessage());
		return ResponseEntity.badRequest().body(e.getMessage());
	}

	@ExceptionHandler(CsvException.class)
	public ResponseEntity<?> handleGeneralCsvException(CsvException e) {
		logger.error(e.getMessage());
		return ResponseEntity.badRequest().body(e.getMessage());
	}

	@ExceptionHandler(StorageException.class)
	public ResponseEntity<?> handleStorageException(StorageException e) {
		logger.error(e.getMessage());
		return ResponseEntity.badRequest().body(e.getMessage());
	}

	@ExceptionHandler(StorageFileNotFoundException.class)
	public ResponseEntity<?> handleStorageFileNotFoundException(StorageFileNotFoundException e) {
		logger.error(e.getMessage());
		return ResponseEntity.badRequest().body(e.getMessage());
	}

	@ExceptionHandler(StorageFileNotProcessableException.class)
	public ResponseEntity<?> handleStorageFileNotProcessableException(StorageFileNotProcessableException e) {
		logger.error(e.getMessage());
		return ResponseEntity.internalServerError().body(e.getMessage());
	}
}