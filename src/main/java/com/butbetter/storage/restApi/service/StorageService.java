package com.butbetter.storage.restApi.service;

import com.butbetter.storage.restApi.controller.ProductInformationNotFoundException;
import com.butbetter.storage.restApi.model.ProductInformation;
import com.butbetter.storage.restApi.repository.AddressRepository;
import com.butbetter.storage.restApi.repository.ProductRepository;
import com.butbetter.storage.restApi.validator.IProductInformationValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Service
public class StorageService implements IStorageService {

	private static final Logger logger = LoggerFactory.getLogger(StorageService.class);

	private final ProductRepository productRepository;
	private final AddressRepository addressRepository;

	private final IProductInformationValidator validator;

	@Autowired
	public StorageService(ProductRepository productRepository, AddressRepository addressRepository, IProductInformationValidator validator) {
		this.productRepository = productRepository;
		this.addressRepository = addressRepository;
		this.validator = validator;
	}

	public List<ProductInformation> all() {
		return this.productRepository.findAll();
	}

	public ProductInformation one(UUID id) throws ProductInformationNotFoundException {
		return this.productRepository.findById(id).orElseThrow(() -> new ProductInformationNotFoundException("User could not found with the UUID", id));
	}

	public void newProductInformation(@NotNull ProductInformation productInformation) throws NullPointerException {
		validator.validate(productInformation);
		this.addressRepository.save(productInformation.getAddress());
		this.productRepository.save(productInformation);
	}
}
