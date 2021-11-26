package com.butbetter.storage.FileUpload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CSVFileUploadController {

	private final StorageService storageService;

	@Autowired
	public CSVFileUploadController(StorageService storageService) {
		this.storageService = storageService;
	}

	@GetMapping("/")
	public ResponseEntity<List<Path>> listUploadedFiles() throws IOException {
		return new ResponseEntity<>(storageService.loadAll().collect(Collectors.toList()), HttpStatus.OK);
	}

	@GetMapping("/files/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable String filename) throws StorageFileNotFoundException {

		Resource file = storageService.loadAsResource(filename);
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=\"" + file.getFilename() + "\"").body(file);
	}

	@PostMapping("/")
	public String handleFileUpload(@RequestParam("file") MultipartFile file,
	                               RedirectAttributes redirectAttributes) throws StorageException {

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