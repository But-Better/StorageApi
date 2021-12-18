package com.butbetter.storage.csvImport.customConverter;

import com.butbetter.storage.csvImport.model.AddressCsv;
import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * custom Bean Converter from csv to AddressCsv
 *
 * @see AbstractBeanField
 */
public class BeanAddressCsvConverter<T, I> extends AbstractBeanField<T, I> {

	private static final String NAME_IDENTIFIER = "name=";
	private static final String COMPANY_NAME_IDENTIFIER = "companyName=";
	private static final String STREET_IDENTIFIER = "street=";
	private static final String CITY_IDENTIFIER = "city=";
	private static final String POSTCODE_IDENTIFIER = "postCode=";
	private static final String COUNTRY_IDENTIFIER = "country=";
	private static final String VALUE_PACKAGE = "'";
	private final Logger logger = LoggerFactory.getLogger(BeanAddressCsvConverter.class);
	private final Pattern check_pattern = Pattern.compile("(Address.)" + NAME_IDENTIFIER + ".+?" + COMPANY_NAME_IDENTIFIER + ".+?" + STREET_IDENTIFIER + ".+?" + CITY_IDENTIFIER + ".+?" + POSTCODE_IDENTIFIER + ".+?" + COUNTRY_IDENTIFIER);


	private final Pattern name_pattern = Pattern.compile("(" + NAME_IDENTIFIER + VALUE_PACKAGE + ").+?(" + VALUE_PACKAGE + ")");
	private final Pattern company_name_pattern = Pattern.compile("(" + COMPANY_NAME_IDENTIFIER + VALUE_PACKAGE + ").+?(" + VALUE_PACKAGE + ")");
	private final Pattern street_pattern = Pattern.compile("(" + STREET_IDENTIFIER + VALUE_PACKAGE + ").+?(" + VALUE_PACKAGE + ")");
	private final Pattern city_pattern = Pattern.compile("(" + CITY_IDENTIFIER + VALUE_PACKAGE + ").+?(" + VALUE_PACKAGE + ")");
	private final Pattern postCode_pattern = Pattern.compile("(" + POSTCODE_IDENTIFIER + VALUE_PACKAGE + ").+?(" + VALUE_PACKAGE + ")");
	private final Pattern country_pattern = Pattern.compile("(" + COUNTRY_IDENTIFIER + VALUE_PACKAGE + ").+?(" + VALUE_PACKAGE + ")");

	/**
	 * this converter was written to convert a String to an AddressCsv Object
	 * <p>
	 * this conversion has 3 Steps: 1) check, if the given String even contains enough/the right information and is
	 * given in the right format
	 * <p>
	 * an example for a correct String might look like this: ```Address{uuid=null, name='a', companyName='a',
	 * street='a', city='a', postCode='a', country='a'}```
	 * <p>
	 * 2) matching and pulling of all the needed information, out if the given String where we now are quite sure, that
	 * it contains the information it needs
	 * <p>
	 * this process has to work with the constraints, the Java Regex Library put's on itself, since it's very easy to
	 * overwrite a match. Because of that, the method ```String getGroup(Matcher matcher, int i)``` is needed to
	 * carefully get them out by checking first, so it's loaded in and can be extracted (for more information on that
	 * problem, read here: https://stackoverflow.com/questions/5674268/no-match-found-when-using-matchers-group-method#5674321)
	 * <p>
	 * 3) takes the cleaned Strings from the now extracted information and puts them into the now ready AddressCsv
	 * Constructor
	 *
	 * @param value String of AddressCsv Object
	 *
	 * @return AddressCsv parsed from given String
	 *
	 * @throws CsvDataTypeMismatchException thrown, if the given String wasn't in the right format, and therefore cannot
	 *                                      be converted to the needed Object
	 * @see AbstractBeanField
	 */
	@Override
	public final Object convert(String value) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
		// Pattern check, if the String contains the needed value and is in the right format
		if (!check_pattern.matcher(value).find()) {
			String message = "given Input-String could not be validated (Input: " + value + ")";
			logger.error(message);
			throw new CsvDataTypeMismatchException(message);
		}

		// Matching of all needed information
		Matcher name_matcher = name_pattern.matcher(value);
		Matcher company_name_matcher = company_name_pattern.matcher(value);
		Matcher street_matcher = street_pattern.matcher(value);
		Matcher city_matcher = city_pattern.matcher(value);
		Matcher postCode_matcher = postCode_pattern.matcher(value);
		Matcher country_matcher = country_pattern.matcher(value);

		String name = getCleanValue(getGroup(name_matcher, 0), NAME_IDENTIFIER + VALUE_PACKAGE, VALUE_PACKAGE);

		String company_name = getCleanValue(getGroup(company_name_matcher, 0), COMPANY_NAME_IDENTIFIER + VALUE_PACKAGE, VALUE_PACKAGE);

		String street = getCleanValue(getGroup(street_matcher, 0), STREET_IDENTIFIER + VALUE_PACKAGE, VALUE_PACKAGE);

		String city = getCleanValue(getGroup(city_matcher, 0), CITY_IDENTIFIER + VALUE_PACKAGE, VALUE_PACKAGE);

		String postCode = getCleanValue(getGroup(postCode_matcher, 0), POSTCODE_IDENTIFIER + VALUE_PACKAGE, VALUE_PACKAGE);

		String country = getCleanValue(getGroup(country_matcher, 0), COUNTRY_IDENTIFIER + VALUE_PACKAGE, VALUE_PACKAGE);

		return new AddressCsv(name, company_name, street, city, postCode, country);
	}

	/**
	 * method for getting a specific Group in a matched pattern, if there is one
	 *
	 * @param matcher matcher with the pattern and group
	 * @param i       group id
	 *
	 * @return matched String
	 */
	private String getGroup(Matcher matcher, int i) throws CsvConstraintViolationException {
		if (matcher.find()) {
			return matcher.group(i);
		}
		String message = "Pattern: " + matcher.pattern().toString() + " couldn't find values that matches itself, " + "the given String might be broken";
		logger.error(message);
		throw new CsvConstraintViolationException(message);
	}

	/**
	 * cleaning values of prefix and suffix (if the prefix or suffix doesn't exist, it's going to be ignored)
	 *
	 * @param original String to clean
	 * @param prefix   deletable prefix
	 * @param suffix   deletable suffix
	 *
	 * @return String cleaned of prefix and suffix
	 */
	private String getCleanValue(String original, String prefix, String suffix) {
		return original.replace(prefix, "").replace(suffix, "");
	}
}
