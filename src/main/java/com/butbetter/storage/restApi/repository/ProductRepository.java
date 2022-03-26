package com.butbetter.storage.restApi.repository;

import com.butbetter.storage.restApi.model.ProductInformation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<ProductInformation, UUID> {}
