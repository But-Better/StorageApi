package com.butbetter.storage.restApi.validator;

public interface Validator<P> {
	/**
	 * method to validate input
	 *
	 * if input is valid, then nothing will happen
	 * if input is in-valid, then an exception will be thrown
	 *
	 * @param object object to validate
	 * @throws RuntimeException thrown, if Validation falied
	 */
	void validate(P object) throws RuntimeException;
}
