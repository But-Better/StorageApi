package com.butbetter.storage.FileUpload;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CSVFileUploadController {

	private final Logger logger = LoggerFactory.getLogger(CSVFileUploadController.class);
	private final StorageService storageService;

	@Autowired
	public CSVFileUploadController(StorageService storageService) {
		this.storageService = storageService;
	}

	@GetMapping("/")
	public ResponseEntity<List<Path>> listUploadedFiles() throws IOException {
		logger.info("requested all file paths");
		return new ResponseEntity<>(storageService.loadAll().collect(Collectors.toList()), HttpStatus.OK);
	}

	@PostMapping("/")
	public String handleFileUpload(@RequestParam("file") MultipartFile file,
	                               RedirectAttributes redirectAttributes) throws StorageException {
		logger.info("file upload started, saving: " + file.getOriginalFilename());
		storageService.store(file);
		redirectAttributes.addFlashAttribute("message",
				"You successfully uploaded " + file.getOriginalFilename() + "!");

		return "redirect:/";
	}

	@ExceptionHandler(StorageFileNotFoundException.class)
	public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException ex) {
		return ResponseEntity.notFound().build();
	}

	@ExceptionHandler(IOException.class)
	public ResponseEntity<?> handleIOException(IOException ex) {
		return ResponseEntity.badRequest().build();
	}
}