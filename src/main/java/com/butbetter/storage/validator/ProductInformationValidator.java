package com.butbetter.storage.validator;

import com.butbetter.storage.model.ProductInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ProductInformationValidator {

	private final Logger logger = LoggerFactory.getLogger(ProductInformationValidator.class);

	/**
	 * Validate the input of {@link ProductInformation}
	 *
	 * @param productInformation = {@link ProductInformation}
	 * @throws NullPointerException     = if Address or DeliveryTime null
	 * @throws IllegalArgumentException = if amount smaller then zero
	 */
	public void validateANewProductInformation(ProductInformation productInformation) throws NullPointerException, IllegalArgumentException {
		if (productInformation.getAddress() == null || productInformation.getDeliveryTime() == null) {
			String message = "Address or DeliveryTime is null";
			logger.error(message);
			throw new NullPointerException(message);
		}

		if (productInformation.getAmount() < 0) {
			String message = "Number is negative";
			logger.error(message);
			throw new IllegalArgumentException(message);
		}
	}
}
