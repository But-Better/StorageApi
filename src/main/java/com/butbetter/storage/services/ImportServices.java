package com.butbetter.storage.services;

import com.butbetter.storage.model.ProductInformation;
import com.butbetter.storage.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ImportServices {

    @Autowired
    private ProductRepository repository;

    public List<ProductInformation> getAll() {
        return repository.findAll();
    }

    public ProductInformation getById(UUID uuid) {
        return repository.getById(uuid);
    }
}
