package com.butbetter.storage.customConverter;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;

public class BeanOffsetDateTimeConverter<T, I> extends AbstractBeanField<T, I> {

	private final Logger logger = LoggerFactory.getLogger(BeanOffsetDateTimeConverter.class);

	/**
	 * converts String to OffsetDateTime, by using the build in OffsetDateTime parser
	 * @param value value to parse
	 * @return Offset DateTime parsed
	 * @throws CsvDataTypeMismatchException thrown, if the default OffsetDateTime converter,
	 * couldn't convert the given String
	 *
	 * @see AbstractBeanField
	 */
	@Override
	protected Object convert(String value) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
		try {
			return OffsetDateTime.parse(value);
		} catch (DateTimeParseException ex) {
			String message = "couldn't convert from " + value +
					" to OffsetDateTime via in-build parser";
			logger.error(message);
			throw new CsvDataTypeMismatchException(message);
		}
	}
}
