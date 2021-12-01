package com.butbetter.storage.controller;

import com.butbetter.storage.service.StorageService;
import com.butbetter.storage.model.ProductInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("storage/v1")
public class StorageController {

    private static final Logger logger = LoggerFactory.getLogger(StorageController.class);

    @Autowired
    private StorageService storageService;

    @GetMapping("/productInformation")
    public ResponseEntity<List<ProductInformation>> all() {
        List<ProductInformation> list = this.storageService.all();
        logger.info(Arrays.toString(list.toArray()));
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/ProductInformation/{id}")
    public ResponseEntity<ProductInformation> one(@PathVariable UUID id) throws ProductInformationNotFoundException {
        ProductInformation productInformation = this.storageService.one(id);
        logger.info(String.valueOf(productInformation));
        return ResponseEntity.ok().body(productInformation);
    }

    @PostMapping("/productInformation")
    public ResponseEntity<?> newProductInformation(@RequestBody ProductInformation productInformation) {
        logger.info(String.valueOf(productInformation));
        this.storageService.newProductInformation(productInformation);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(ProductInformationNotFoundException.class)
    public ResponseEntity<?> productInformationNotFoundException(ProductInformationNotFoundException e) {
        logger.error(e.getMessage());
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<?> productInformationHasOneNullPointer(NullPointerException e) {
        logger.error(e.getMessage());
        return ResponseEntity.badRequest().build();
    }
}