package com.butbetter.storage.repository;

import com.butbetter.storage.model.ProductInformation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FileProductRepository extends JpaRepository<ProductInformation, UUID> {}