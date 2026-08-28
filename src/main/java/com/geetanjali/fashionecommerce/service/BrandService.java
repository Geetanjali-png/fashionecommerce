package com.geetanjali.fashionecommerce.service;

import com.geetanjali.fashionecommerce.entity.Brand;
import com.geetanjali.fashionecommerce.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BrandService {

    @Autowired
    private BrandRepository brandRepository;

    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }

    public Optional<Brand> getBrandById(Long id) {
        return brandRepository.findById(id);
    }

    public Brand saveBrand(Brand brand) {
        return brandRepository.save(brand);
    }

    public void deleteBrand(Long id) {
        brandRepository.deleteById(id);
    }

    public Optional<Brand> findByName(String name) {
        return brandRepository.findByName(name);
    }
}