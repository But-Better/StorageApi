package com.butbetter.storage.restApi.controller;

import java.util.UUID;

public class ProductInformationNotFoundException extends Exception {
	public ProductInformationNotFoundException(String message, UUID uuid) {
		super("id" + uuid + " | " + message);
	}
}
