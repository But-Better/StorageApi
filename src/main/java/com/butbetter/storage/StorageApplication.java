package com.butbetter.storage;

import com.butbetter.storage.file_upload.Properties.StorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class StorageApplication {
	public static void main(String[] args) {
		SpringApplication.run(StorageApplication.class, args);
	}
}
