package com.geetanjali.fashionecommerce.service;

import com.geetanjali.fashionecommerce.entity.ProductVariant;
import com.geetanjali.fashionecommerce.repository.ProductVariantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductVariantService {

    @Autowired
    private ProductVariantRepository productVariantRepository;

    public List<ProductVariant> getAllVariants() {
        return productVariantRepository.findAll();
    }

    public Optional<ProductVariant> getVariantById(Long id) {
        return productVariantRepository.findById(id);
    }

    public List<ProductVariant> getVariantsByProductId(Long productId) {
        return productVariantRepository.findByProductId(productId);
    }

    public ProductVariant saveVariant(ProductVariant variant) {
        return productVariantRepository.save(variant);
    }

    public void deleteVariant(Long id) {
        productVariantRepository.deleteById(id);
    }
}