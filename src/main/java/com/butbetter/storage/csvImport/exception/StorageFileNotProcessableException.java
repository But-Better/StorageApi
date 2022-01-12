package com.butbetter.storage.csvImport.exception;

public class StorageFileNotProcessableException extends Throwable {
	public StorageFileNotProcessableException(String message) {
		super(message);
	}

	public StorageFileNotProcessableException(String message, Throwable cause) {
		super(message, cause);
	}
}
