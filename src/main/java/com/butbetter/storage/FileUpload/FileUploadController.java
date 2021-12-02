package com.butbetter.storage.FileUpload;

import com.butbetter.storage.CSV.Exceptions.FaultyCSVException;
import com.butbetter.storage.FileUpload.Exceptions.StorageException;
import com.butbetter.storage.FileUpload.Exceptions.StorageFileNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("csv/v1")
public class FileUploadController {

	private final Logger logger = LoggerFactory.getLogger(FileUploadController.class);

	private final StorageService storageService;

	@Autowired
	public FileUploadController(StorageService storageService) {
		this.storageService = storageService;
	}

	/**
	 * handling of post request for file upload
	 * @param file file to upload
	 * @param redirectAttributes
	 * @return response
	 * @throws StorageException thrown, if file couldn't be stored
	 */
	@PostMapping("/")
	public String handleFileUpload(@RequestParam("file") MultipartFile file,
	                               RedirectAttributes redirectAttributes) throws StorageException, FaultyCSVException, StorageFileNotFoundException {
		logger.info("file upload started, saving: " + file.getOriginalFilename());
		storageService.store(file);
		redirectAttributes.addFlashAttribute("message",
				"You successfully uploaded " + file.getOriginalFilename() + "!");

		return "redirect:/";
	}

	@GetMapping("/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable String filename) throws StorageFileNotFoundException {
		Resource file = storageService.loadAsResource(filename);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}

	/**
	 * Controller Exception Handler for StorageFileNotFoundException
	 * @param e StorageFileNotFoundException
	 * @return Build Response-Entity
	 */
	@ExceptionHandler(StorageFileNotFoundException.class)
	public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException e) {
		logger.error(e.getMessage());
		return ResponseEntity.notFound().build();
	}

	/**
	 * Controller Exception Handler for IOException
	 * @param e IOException
	 * @return Build Response-Entity
	 */
	@ExceptionHandler(IOException.class)
	public ResponseEntity<?> handleIOException(IOException e) {
		logger.error(e.getMessage());
		return ResponseEntity.badRequest().body(e.getMessage());
	}

	/**
	 * Controller Exception Handler for FaultyCSVException
	 * @param e FaultyCSVException
	 * @return Build Response-Entity
	 */
	@ExceptionHandler(FaultyCSVException.class)
	public ResponseEntity<?> handleFaultyCSVException(FaultyCSVException e) {
		logger.error(e.getMessage());
		return ResponseEntity.badRequest().body(e.getMessage());
	}
}