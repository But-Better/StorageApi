package com.butbetter.storage.FileUpload.Properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("storage")
public class StorageProperties {

	/**
	 * Folder location for storing files
	 */
	private String location = "csv-dir";

	public StorageProperties() {}

	public StorageProperties(String customLocation) {
		location = customLocation;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}
