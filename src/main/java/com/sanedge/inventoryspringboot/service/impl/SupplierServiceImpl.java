package com.sanedge.inventoryspringboot.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanedge.inventoryspringboot.domain.request.supplier.CreateSupplierRequest;
import com.sanedge.inventoryspringboot.domain.request.supplier.UpdateSupplierRequest;
import com.sanedge.inventoryspringboot.domain.response.MessageResponse;
import com.sanedge.inventoryspringboot.exception.ResourceNotFoundException;
import com.sanedge.inventoryspringboot.models.Supplier;
import com.sanedge.inventoryspringboot.repository.SupplierRepository;
import com.sanedge.inventoryspringboot.service.SupplierService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SupplierServiceImpl implements SupplierService {
    private final SupplierRepository supplierRepository;

    @Autowired
    public SupplierServiceImpl(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @Override
    public MessageResponse getAllSupplier() {
        try {
            List<Supplier> suppliers = supplierRepository.findAll();
            return MessageResponse.builder().data(suppliers).message("Success").statusCode(200).build();
        } catch (Exception e) {
            log.error("Error while fetching all suppliers: {}", e.getMessage(), e);
            return MessageResponse.builder().message("Error occurred").statusCode(500).build();
        }
    }

    @Override
    public MessageResponse getSupplier(Long id) {
        try {
            Supplier supplier = supplierRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Supplier not found"));
            return MessageResponse.builder().data(supplier).message("Success").statusCode(200).build();
        } catch (ResourceNotFoundException e) {
            log.warn("Supplier not found for id: {}", id);
            return MessageResponse.builder().message("Supplier not found").statusCode(404).build();
        } catch (Exception e) {
            log.error("Error while fetching supplier: {}", e.getMessage(), e);
            return MessageResponse.builder().message("Error occurred").statusCode(500).build();
        }
    }

    @Override
    public MessageResponse createSupplier(CreateSupplierRequest request) {
        try {
            Supplier supplier = Supplier.builder()
                    .name(request.getName())
                    .alamat(request.getAddress())
                    .telepon(request.getPhone())
                    .email(request.getEmail())
                    .build();
            supplierRepository.save(supplier);
            return MessageResponse.builder().data(supplier).message("Success").statusCode(200).build();
        } catch (Exception e) {
            log.error("Error while creating supplier: {}", e.getMessage(), e);
            return MessageResponse.builder().message("Error occurred").statusCode(500).build();
        }
    }

    @Override
    public MessageResponse updateSupplier(Long id, UpdateSupplierRequest request) {
        try {
            Supplier supplier = supplierRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Supplier not found"));
            supplier.setName(request.getName());
            supplier.setEmail(request.getEmail());
            supplier.setAlamat(request.getAddress());
            supplier.setTelepon(request.getPhone());

            supplierRepository.save(supplier);
            return MessageResponse.builder().data(supplier).message("Success").statusCode(200).build();
        } catch (ResourceNotFoundException e) {
            log.warn("Supplier not found for id: {}", id);
            return MessageResponse.builder().message("Supplier not found").statusCode(404).build();
        } catch (Exception e) {
            log.error("Error while updating supplier: {}", e.getMessage(), e);
            return MessageResponse.builder().message("Error occurred").statusCode(500).build();
        }
    }

    @Override
    public MessageResponse deleteSupplier(Long id) {
        try {
            Supplier supplier = supplierRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Supplier not found"));
            supplierRepository.delete(supplier);
            return MessageResponse.builder().message("Success").statusCode(200).build();
        } catch (ResourceNotFoundException e) {
            log.warn("Supplier not found for id: {}", id);
            return MessageResponse.builder().message("Supplier not found").statusCode(404).build();
        } catch (Exception e) {
            log.error("Error while deleting supplier: {}", e.getMessage(), e);
            return MessageResponse.builder().message("Error occurred").statusCode(500).build();
        }
    }
}
