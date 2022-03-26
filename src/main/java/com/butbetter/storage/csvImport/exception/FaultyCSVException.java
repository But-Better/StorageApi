package com.butbetter.storage.csvImport.exception;

public class FaultyCSVException extends Exception {
	public FaultyCSVException(String message) {
		super(message);
	}

	public FaultyCSVException(String message, Throwable cause) {
		super(message, cause);
	}
}
