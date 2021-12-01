package com.butbetter.storage.service;

import com.butbetter.storage.controller.ProductInformationNotFoundException;
import com.butbetter.storage.model.ProductInformation;
import com.butbetter.storage.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Service
public class StorageService {

    private static final Logger logger = LoggerFactory.getLogger(StorageService.class);
    private static final String couldNotfoundUser = "User not found with the UUID";

    @Autowired
    private ProductRepository productRepository;

    public List<ProductInformation> all() {
        return this.productRepository.findAll();
    }

    public ProductInformation one(UUID id) throws ProductInformationNotFoundException {
        return this.productRepository.findById(id)
                .orElseThrow(() -> new ProductInformationNotFoundException(couldNotfoundUser, id));
    }

    public void newProductInformation(@NotNull ProductInformation productInformation) throws NullPointerException {
        this.ValidateANewProductInformation(productInformation);
        this.productRepository.save(productInformation);
    }

    private void ValidateANewProductInformation(ProductInformation productInformation) throws NullPointerException {
        if (productInformation.getAddress() == null
                || productInformation.getAmount() >= 0
                || productInformation.getDeliveryTime() == null
        ) {
            throw new NullPointerException();
        }
    }
}
