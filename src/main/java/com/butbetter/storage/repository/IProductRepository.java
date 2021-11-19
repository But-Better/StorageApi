package com.butbetter.storage.repository;

import com.butbetter.storage.model.ProductInformation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IProductRepository extends JpaRepository<ProductInformation, UUID> {
}
