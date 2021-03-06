package com.butbetter.storage.restApi.controller;

import com.butbetter.storage.restApi.model.ProductInformation;
import com.butbetter.storage.restApi.service.IStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("storage/v1")
public class StorageController {

	private static final Logger logger = LoggerFactory.getLogger(StorageController.class);

	private final IStorageService storageService;

	@Autowired
	public StorageController(IStorageService storageService) {
		this.storageService = storageService;
	}

	/**
	 * Get all {@link ProductInformation}
	 *
	 * @return = all {@link ProductInformation} as JSON format
	 */
	@GetMapping("/productInformation")
	public ResponseEntity<List<ProductInformation>> all() {
		logger.info("calling all");
		List<ProductInformation> list = this.storageService.all();
		logger.info(Arrays.toString(list.toArray()));
		return ResponseEntity.ok().body(list);
	}

	/**
	 * Get one {@link ProductInformation}
	 *
	 * @param id = type UUID
	 *
	 * @return = one {@link ProductInformation} as JSON format
	 *
	 * @throws ProductInformationNotFoundException = not found a {@link ProductInformation}
	 */
	@GetMapping("/productInformation/{id}")
	public ResponseEntity<ProductInformation> one(@PathVariable UUID id) throws ProductInformationNotFoundException {
		logger.info("calling one");
		ProductInformation productInformation = this.storageService.one(id);
		logger.info(String.valueOf(productInformation));
		return ResponseEntity.ok().body(productInformation);
	}

	/**
	 * Create a {@link ProductInformation}
	 *
	 * @param productInformation = {@link ProductInformation}
	 *
	 * @return Ok status if Post request correct
	 */
	@PostMapping(value = "/productInformation", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> newProductInformation(@Valid @RequestBody ProductInformation productInformation) {
		logger.info(String.valueOf(productInformation));
		this.storageService.newProductInformation(productInformation);
		return ResponseEntity.ok().build();
	}

	@ExceptionHandler(ProductInformationNotFoundException.class)
	private ResponseEntity<?> productInformationNotFoundException(ProductInformationNotFoundException e) {
		logger.error(e.getMessage());
		return ResponseEntity.notFound().build();
	}

	@ExceptionHandler(NullPointerException.class)
	private ResponseEntity<?> productInformationNullPointerException(NullPointerException e) {
		logger.error(e.getMessage());
		return ResponseEntity.badRequest().build();
	}

	@ExceptionHandler(IllegalArgumentException.class)
	private ResponseEntity<?> productInformationIllegalArgumentException(IllegalArgumentException e) {
		logger.error(e.getMessage());
		return ResponseEntity.badRequest().build();
	}
}