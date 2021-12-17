package com.butbetter.storage.controller;

import java.util.UUID;

public class ProductInformationNotFoundException extends Exception {
	public ProductInformationNotFoundException(String message, UUID uuid) {
		super("id" + uuid + " | " + message);
	}
}
