package com.butbetter.storage.FileUpload.Configurations;

import com.butbetter.storage.FileUpload.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageConfiguration {

	private final Logger logger = LoggerFactory.getLogger(StorageConfiguration.class);

	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
			logger.info("initialize Storage");
			storageService.deleteAll();
			storageService.init();
			logger.info("done Storage initialization");
		};
	}
}

