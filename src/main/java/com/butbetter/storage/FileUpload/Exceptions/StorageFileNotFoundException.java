package com.butbetter.storage.FileUpload.Exceptions;

public class StorageFileNotFoundException extends Throwable {

	public StorageFileNotFoundException(String message) {
		super(message);
	}

	public StorageFileNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
