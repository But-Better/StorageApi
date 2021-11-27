package com.butbetter.storage.FileUpload.Exceptions;

import java.io.IOException;

public class StorageException extends IOException {
	public StorageException(String s) {
		super(s);
	}

	public StorageException(String s, Throwable e) {
		super(s, e);
	}
}
