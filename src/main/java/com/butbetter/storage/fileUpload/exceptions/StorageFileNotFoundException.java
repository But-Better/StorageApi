package com.butbetter.storage.fileUpload.exceptions;

public class StorageFileNotFoundException extends Throwable {

	public StorageFileNotFoundException(String message) {
		super(message);
	}

	public StorageFileNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
