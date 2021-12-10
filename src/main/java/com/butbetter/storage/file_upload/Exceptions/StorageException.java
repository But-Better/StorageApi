package com.butbetter.storage.file_upload.Exceptions;

import java.io.IOException;

public class StorageException extends IOException {
	public StorageException(String s) {
		super(s);
	}

	public StorageException(String s, Throwable e) {
		super(s, e);
	}
}
