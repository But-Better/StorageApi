package com.butbetter.storage.restApi.service;

import com.butbetter.storage.restApi.controller.ProductInformationNotFoundException;
import com.butbetter.storage.restApi.model.ProductInformation;
import com.butbetter.storage.restApi.validator.IProductInformationValidator;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

public interface IStorageService {
	/**
	 * Get all {@link ProductInformation} value
	 *
	 * @return = a list of {@link ProductInformation}
	 */
	List<ProductInformation> all();

	/**
	 * Get One {@link ProductInformation} value
	 *
	 * @param id = type UUID
	 *
	 * @return = one {@link ProductInformation}
	 *
	 * @throws ProductInformationNotFoundException = if no {@link ProductInformation} was found
	 */
	ProductInformation one(UUID id) throws ProductInformationNotFoundException;

	/**
	 * Create a new {@link ProductInformation} value
	 *
	 * @param productInformation = {@link ProductInformation}
	 *
	 * @throws NullPointerException = is checked by {@link IProductInformationValidator}
	 */
	void newProductInformation(@NotNull ProductInformation productInformation) throws NullPointerException;
}
