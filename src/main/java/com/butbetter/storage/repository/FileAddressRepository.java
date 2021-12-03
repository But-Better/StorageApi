package com.butbetter.storage.repository;

import com.butbetter.storage.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FileAddressRepository extends JpaRepository<Address, UUID> {

}
