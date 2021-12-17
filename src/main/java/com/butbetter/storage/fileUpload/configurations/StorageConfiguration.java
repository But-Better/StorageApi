package com.butbetter.storage.fileUpload.configurations;

import com.butbetter.storage.fileUpload.IFileStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageConfiguration {

	private final Logger logger = LoggerFactory.getLogger(StorageConfiguration.class);

	@Bean
	CommandLineRunner init(IFileStorageService fileStorageService) {
		return (args) -> {
			logger.info("initialize Storage");
			fileStorageService.deleteAll();
			fileStorageService.init();
			logger.info("done Storage initialization");
		};
	}
}

