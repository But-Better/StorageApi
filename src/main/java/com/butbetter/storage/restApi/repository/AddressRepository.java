package com.butbetter.storage.restApi.repository;

import com.butbetter.storage.restApi.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, UUID> {}
