package com.butbetter.storage.file_upload.Exceptions;

public class StorageFileNotFoundException extends Throwable {

	public StorageFileNotFoundException(String message) {
		super(message);
	}

	public StorageFileNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
