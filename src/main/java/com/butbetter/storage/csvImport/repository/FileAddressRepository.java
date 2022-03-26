package com.butbetter.storage.csvImport.repository;

import com.butbetter.storage.csvImport.model.AddressCsv;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FileAddressRepository extends JpaRepository<AddressCsv, UUID> {

}
