package com.butbetter.storage.csvImport.customConverter;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;

public class BeanOffsetDateTimeConverter<T, I> extends AbstractBeanField<T, I> {

	private final Logger logger = LoggerFactory.getLogger(BeanOffsetDateTimeConverter.class);

	/**
	 * converts String to OffsetDateTime
	 *
	 * @throws CsvDataTypeMismatchException thrown, if the default OffsetDateTime converter, couldn't convert the given
	 *                                      String
	 * @see AbstractBeanField
	 */
	@Override
	public final OffsetDateTime convert(String value) throws CsvDataTypeMismatchException {
		try {
			return OffsetDateTime.parse(value);
		} catch (DateTimeParseException ex) {
			String message = "couldn't convert from " + value + " to OffsetDateTime via in-build parser";
			logger.error(message);
			throw new CsvDataTypeMismatchException(message);
		}
	}
}
