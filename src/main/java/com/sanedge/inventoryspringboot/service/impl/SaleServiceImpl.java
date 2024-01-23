package com.sanedge.inventoryspringboot.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanedge.inventoryspringboot.domain.request.sale.CreateSaleRequest;
import com.sanedge.inventoryspringboot.domain.request.sale.UpdateSaleRequest;
import com.sanedge.inventoryspringboot.domain.response.MessageResponse;
import com.sanedge.inventoryspringboot.exception.ResourceNotFoundException;
import com.sanedge.inventoryspringboot.models.Sale;
import com.sanedge.inventoryspringboot.repository.SaleRepository;
import com.sanedge.inventoryspringboot.service.SaleService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SaleServiceImpl implements SaleService {
    private final SaleRepository saleRepository;

    @Autowired
    public SaleServiceImpl(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    @Override
    public MessageResponse getAllSale() {
        try {
            return MessageResponse.builder().data(saleRepository.findAll()).message("Success").statusCode(200).build();
        } catch (Exception e) {
            log.error("Error while fetching all sales: {}", e.getMessage(), e);
            return MessageResponse.builder().message("Error occurred").statusCode(500).build();
        }
    }

    @Override
    public MessageResponse getSale(Long id) {
        try {
            return MessageResponse.builder().data(saleRepository.findById(id).get()).message("Success").statusCode(200)
                    .build();
        } catch (Exception e) {
            log.error("Error while fetching sale: {}", e.getMessage(), e);
            return MessageResponse.builder().message("Error occurred").statusCode(500).build();
        }
    }

    public MessageResponse createSale(CreateSaleRequest request) {
        try {

            Sale sale = new Sale();

            sale.setName(request.getName());
            sale.setTelepon(request.getPhone());
            sale.setEmail(request.getEmail());
            sale.setAlamat(request.getAddress());

            saleRepository.save(sale);
            return MessageResponse.builder().data(sale).message("Success").statusCode(200).build();
        } catch (Exception e) {
            log.error("Error while creating sale: {}", e.getMessage(), e);
            return MessageResponse.builder().message("Error occurred").statusCode(500).build();
        }
    }

    public MessageResponse updateSale(Long id, UpdateSaleRequest request) {
        try {
            Sale sale = saleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Sale not found"));
            sale.setName(request.getName());
            sale.setTelepon(request.getPhone());
            sale.setEmail(request.getEmail());
            sale.setAlamat(request.getAddress());
            saleRepository.save(sale);
            return MessageResponse.builder().data(sale).message("Success").statusCode(200).build();
        } catch (Exception e) {
            log.error("Error while updating sale: {}", e.getMessage(), e);
            return MessageResponse.builder().message("Error occurred").statusCode(500).build();
        }
    }

    @Override
    public MessageResponse deleteSale(Long id) {
        try {
            Sale sale = saleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Sale not found"));

            saleRepository.delete(sale);

            return MessageResponse.builder().message("Success").statusCode(200).build();
        } catch (Exception e) {
            log.error("Error while deleting sale: {}", e.getMessage(), e);
            return MessageResponse.builder().message("Error occurred").statusCode(500).build();
        }
    }
}
