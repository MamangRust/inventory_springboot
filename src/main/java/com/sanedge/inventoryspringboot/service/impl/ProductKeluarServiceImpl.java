package com.sanedge.inventoryspringboot.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanedge.inventoryspringboot.domain.request.productkeluar.CreateProductKeluarRequest;
import com.sanedge.inventoryspringboot.domain.request.productkeluar.UpdateProductKeluarRequest;
import com.sanedge.inventoryspringboot.domain.response.MessageResponse;
import com.sanedge.inventoryspringboot.exception.ResourceNotFoundException;
import com.sanedge.inventoryspringboot.models.Category;
import com.sanedge.inventoryspringboot.models.Product;
import com.sanedge.inventoryspringboot.models.ProductKeluar;
import com.sanedge.inventoryspringboot.repository.CategoryRepository;
import com.sanedge.inventoryspringboot.repository.ProductKeluarRepository;
import com.sanedge.inventoryspringboot.repository.ProductRepository;
import com.sanedge.inventoryspringboot.service.ProductKeluarService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProductKeluarServiceImpl implements ProductKeluarService {
    private final ProductKeluarRepository productKeluarRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ProductKeluarServiceImpl(ProductKeluarRepository productKeluarRepository,
            ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productKeluarRepository = productKeluarRepository;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public MessageResponse getAllProductKeluar() {
        try {
            List<ProductKeluar> productKeluars = productKeluarRepository.findAll();

            return MessageResponse.builder().data(productKeluars)
                    .message("Success").statusCode(200).build();
        } catch (Exception e) {
            log.error("Error while fetching all product keluar: {}", e.getMessage(), e);
            return MessageResponse.builder().message("Error occurred").statusCode(500).build();
        }
    }

    @Override
    public MessageResponse getProductKeluar(Long id) {
        try {
            ProductKeluar productKeluar = productKeluarRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Product keluar not found"));
            return MessageResponse.builder().data(productKeluar).message("Success").statusCode(200).build();
        } catch (ResourceNotFoundException e) {
            log.warn("Product keluar not found for id: {}", id);
            return MessageResponse.builder().message("Product keluar not found").statusCode(404).build();
        } catch (Exception e) {
            log.error("Error while fetching product keluar: {}", e.getMessage(), e);
            return MessageResponse.builder().message("Error occurred").statusCode(500).build();
        }
    }

    @Override
    public MessageResponse createProductKeluar(CreateProductKeluarRequest request) {
        try {
            Product product = productRepository.findById(request.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

            ProductKeluar productKeluar = new ProductKeluar();

            productKeluar.setCategory(category);
            productKeluar.setProduct(product);

            productKeluarRepository.save(productKeluar);

            return MessageResponse.builder().data(productKeluar).message("Success").statusCode(200).build();

        } catch (Exception e) {
            log.error("Error while creating product keluar: {}", e.getMessage(), e);
            return MessageResponse.builder().message("Product not found").statusCode(404).build();
        }
    }

    @Override
    public MessageResponse updateProductKeluar(Long id, UpdateProductKeluarRequest request) {
        try {
            ProductKeluar productKeluar = productKeluarRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Product keluar not found"));
            Product product = productRepository.findById(request.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

            productKeluar.setCategory(category);
            productKeluar.setProduct(product);

            productKeluarRepository.save(productKeluar);

            return MessageResponse.builder().data(productKeluar).message("Success").statusCode(200).build();

        } catch (Exception e) {
            log.error("Error while updating product keluar: {}", e.getMessage(), e);
            return MessageResponse.builder().message("Error occurred").statusCode(500).build();
        }
    }

    @Override
    public MessageResponse deleteProductKeluar(Long id) {
        try {
            ProductKeluar productKeluar = productKeluarRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Product keluar not found"));
            productKeluarRepository.delete(productKeluar);
            return MessageResponse.builder().message("Success").statusCode(200).build();
        } catch (Exception e) {
            log.error("Error while deleting product keluar: {}", e.getMessage(), e);
            return MessageResponse.builder().message("Error occurred").statusCode(500).build();
        }
    }
}
