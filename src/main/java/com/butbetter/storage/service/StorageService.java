package com.butbetter.storage.service;

import com.butbetter.storage.controller.ProductInformationNotFoundException;
import com.butbetter.storage.model.ProductInformation;
import com.butbetter.storage.repository.AddressRepository;
import com.butbetter.storage.repository.ProductRepository;
import com.butbetter.storage.validator.ProductInformationValidator;
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

    private final ProductRepository productRepository;
    private final AddressRepository addressRepository;

    private final ProductInformationValidator validator;

    @Autowired
    public StorageService(ProductRepository productRepository, AddressRepository addressRepository, ProductInformationValidator validator) {
        this.productRepository = productRepository;
        this.addressRepository = addressRepository;
        this.validator = validator;
    }

    /**
     * Get all {@link ProductInformation} value
     *
     * @return = a list of {@link ProductInformation}
     */
    public List<ProductInformation> all() {
        return this.productRepository.findAll();
    }

    /**
     * Get One {@link ProductInformation} value
     *
     * @param id = type UUID
     * @return = one {@link ProductInformation}
     * @throws ProductInformationNotFoundException = if no {@link ProductInformation} was found
     */
    public ProductInformation one(UUID id) throws ProductInformationNotFoundException {
        return this.productRepository.findById(id)
                .orElseThrow(() -> new ProductInformationNotFoundException("User could not found with the UUID", id));
    }

    /**
     * Create a new {@link ProductInformation} value
     *
     * @param productInformation = {@link ProductInformation}
     * @throws NullPointerException = is checked by validateANewProductInformation
     */
    public void newProductInformation(@NotNull ProductInformation productInformation) throws NullPointerException {
        validator.validateANewProductInformation(productInformation);
        this.addressRepository.save(productInformation.getAddress());
        this.productRepository.save(productInformation);
    }
}
