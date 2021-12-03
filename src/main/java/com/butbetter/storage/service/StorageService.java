package com.butbetter.storage.service;

import com.butbetter.storage.controller.ProductInformationNotFoundException;
import com.butbetter.storage.model.ProductInformation;
import com.butbetter.storage.repository.AddressRepository;
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

    private final ProductRepository productRepository;
    private final AddressRepository addressRepository;

    @Autowired
    public StorageService(ProductRepository productRepository, AddressRepository addressRepository) {
        this.productRepository = productRepository;
        this.addressRepository = addressRepository;
    }

    /**
     * Get all {@link ProductInformation} value
     *
     * @return
     */
    public List<ProductInformation> all() {
        return this.productRepository.findAll();
    }

    /**
     * Get One {@link ProductInformation} value
     *
     * @param id
     * @return
     * @throws ProductInformationNotFoundException
     */
    public ProductInformation one(UUID id) throws ProductInformationNotFoundException {
        return this.productRepository.findById(id)
                .orElseThrow(() -> new ProductInformationNotFoundException(couldNotfoundUser, id));
    }

    /**
     * Create a new {@link ProductInformation} value
     *
     * @param productInformation = {@link ProductInformation}
     * @throws NullPointerException = is checked by ValidateANewProductInformation
     */
    public void newProductInformation(@NotNull ProductInformation productInformation) throws NullPointerException {
        this.ValidateANewProductInformation(productInformation);
        this.addressRepository.save(productInformation.getAddress());
        this.productRepository.save(productInformation);
    }

    /**
     * Validate the input of {@link ProductInformation}
     *
     * @param productInformation = {@link ProductInformation}
     * @throws NullPointerException = if Address or DeliveryTime null
     * @throws IllegalArgumentException = if amount smaller then zero
     */
    private void ValidateANewProductInformation(ProductInformation productInformation) throws NullPointerException, IllegalArgumentException {
        if (productInformation.getAddress() == null || productInformation.getDeliveryTime() == null) {
            String message = "Address or DeliveryTime is null";
            logger.error(message);
            throw new NullPointerException(message);
        }

        if (productInformation.getAmount() <= 0) {
            String message = "Number is negative";
            logger.error(message);
            throw new IllegalArgumentException(message);
        }
    }
}
