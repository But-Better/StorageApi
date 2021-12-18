package com.butbetter.storage.csvImport.repository;

import com.butbetter.storage.csvImport.model.ProductInformationCsv;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FileProductRepository extends JpaRepository<ProductInformationCsv, UUID> {}