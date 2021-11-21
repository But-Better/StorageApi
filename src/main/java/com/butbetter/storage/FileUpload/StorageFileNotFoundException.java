package com.butbetter.storage.FileUpload;

public class StorageFileNotFoundException extends Throwable {
	public StorageFileNotFoundException() {
		super();
	}

	public StorageFileNotFoundException(String message) {
		super(message);
	}

	public StorageFileNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
