package com.butbetter.storage.FileUpload;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequestMapping("storage/v1/")
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
	                               RedirectAttributes redirectAttributes) throws StorageException {
		logger.info("file upload started, saving: " + file.getOriginalFilename());
		storageService.store(file);
		redirectAttributes.addFlashAttribute("message",
				"You successfully uploaded " + file.getOriginalFilename() + "!");

		return "redirect:/";
	}

	/**
	 * Controller Exception Handler for StorageFileNotFoundException
	 * @param ex StorageFileNotFoundException
	 * @return Build Response-Entity
	 */
	@ExceptionHandler(StorageFileNotFoundException.class)
	public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException ex) {
		return ResponseEntity.notFound().build();
	}

	/**
	 * Controller Exception Handler for IOException
	 * @param ex IOException
	 * @return Build Response-Entity
	 */
	@ExceptionHandler(IOException.class)
	public ResponseEntity<?> handleIOException(IOException ex) {
		return ResponseEntity.badRequest().build();
	}
}