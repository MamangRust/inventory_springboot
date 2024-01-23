package com.sanedge.inventoryspringboot.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanedge.inventoryspringboot.domain.request.productmasuk.CreateProductMasukRequest;
import com.sanedge.inventoryspringboot.domain.request.productmasuk.UpdateProductMasukRequest;
import com.sanedge.inventoryspringboot.domain.response.MessageResponse;
import com.sanedge.inventoryspringboot.exception.ResourceNotFoundException;
import com.sanedge.inventoryspringboot.models.Product;
import com.sanedge.inventoryspringboot.models.ProductMasuk;
import com.sanedge.inventoryspringboot.models.Supplier;
import com.sanedge.inventoryspringboot.repository.ProductMasukRepository;
import com.sanedge.inventoryspringboot.repository.ProductRepository;
import com.sanedge.inventoryspringboot.repository.SupplierRepository;
import com.sanedge.inventoryspringboot.service.ProductMasukService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProductMasukServiceImpl implements ProductMasukService {
    private final ProductMasukRepository productMasukRepository;
    private final ProductRepository productRepository;
    private final SupplierRepository supplierRepository;

    @Autowired
    public ProductMasukServiceImpl(ProductMasukRepository productMasukRepository, ProductRepository productRepository,
            SupplierRepository supplierRepository) {
        this.productMasukRepository = productMasukRepository;
        this.productRepository = productRepository;
        this.supplierRepository = supplierRepository;
    }

    @Override
    public MessageResponse getAllProductMasuk() {
        try {
            List<ProductMasuk> productMasuks = productMasukRepository.findAll();
            return MessageResponse.builder().data(productMasuks).message("Success").statusCode(200).build();
        } catch (Exception e) {
            log.error("Error while fetching all productMasuks: {}", e.getMessage(), e);
            return MessageResponse.builder().message("Error occurred").statusCode(500).build();
        }
    }

    @Override
    public MessageResponse getProductMasuk(Long id) {
        try {
            ProductMasuk productMasuk = productMasukRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("ProductMasuk not found"));
            return MessageResponse.builder().data(productMasuk).message("Success").statusCode(200).build();
        } catch (ResourceNotFoundException e) {
            log.warn("ProductMasuk not found for id: {}", id);
            return MessageResponse.builder().message("ProductMasuk not found").statusCode(404).build();
        } catch (Exception e) {
            log.error("Error while fetching productMasuk: {}", e.getMessage(), e);
            return MessageResponse.builder().message("Error occurred").statusCode(500).build();
        }
    }

    @Override
    public MessageResponse createProductMasuk(CreateProductMasukRequest request) {
        try {
            Product product = productRepository.findById(request.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

            Supplier supplier = supplierRepository.findById(request.getSupplierId())
                    .orElseThrow(() -> new ResourceNotFoundException("Supplier not found"));

            ProductMasuk productMasuk = new ProductMasuk();

            productMasuk.setName(request.getName());
            productMasuk.setQty(request.getQty());
            productMasuk.setProduct(product);
            productMasuk.setSupplier(supplier);

            productMasukRepository.save(productMasuk);
            return MessageResponse.builder().data(productMasuk).message("Success").statusCode(200).build();
        } catch (Exception e) {
            log.error("Error while creating productMasuk: {}", e.getMessage(), e);
            return MessageResponse.builder().message("Error occurred").statusCode(500).build();
        }
    }

    @Override
    public MessageResponse updateProductMasuk(Long id, UpdateProductMasukRequest request) {
        try {
            ProductMasuk productMasuk = productMasukRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("ProductMasuk not found"));

            Product product = productRepository.findById(request.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

            Supplier supplier = supplierRepository.findById(request.getSupplierId())
                    .orElseThrow(() -> new ResourceNotFoundException("Supplier not found"));

            productMasuk.setName(request.getName());
            productMasuk.setQty(request.getQty());
            productMasuk.setProduct(product);
            productMasuk.setSupplier(supplier);

            productMasukRepository.save(productMasuk);
            return MessageResponse.builder().data(productMasuk).message("Success").statusCode(200).build();
        } catch (Exception e) {
            log.error("Error while updating productMasuk: {}", e.getMessage(), e);
            return MessageResponse.builder().message("Error occurred").statusCode(500).build();
        }
    }

    @Override
    public MessageResponse deleteProductMasuk(Long id) {
        try {
            ProductMasuk productMasuk = productMasukRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("ProductMasuk not found"));
            productMasukRepository.delete(productMasuk);
            return MessageResponse.builder().message("Success").statusCode(200).build();
        } catch (Exception e) {
            log.error("Error while deleting productMasuk: {}", e.getMessage(), e);
            return MessageResponse.builder().message("Error occurred").statusCode(500).build();
        }
    }
}
