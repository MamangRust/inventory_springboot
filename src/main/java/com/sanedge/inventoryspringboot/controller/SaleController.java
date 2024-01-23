package com.sanedge.inventoryspringboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sanedge.inventoryspringboot.domain.request.sale.CreateSaleRequest;
import com.sanedge.inventoryspringboot.domain.request.sale.UpdateSaleRequest;
import com.sanedge.inventoryspringboot.domain.response.MessageResponse;
import com.sanedge.inventoryspringboot.service.SaleService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/sales")
public class SaleController {
    @Autowired
    SaleService saleService;

    @GetMapping("/")
    public ResponseEntity<MessageResponse> findAll() {
        MessageResponse messageResponse = saleService.getAllSale();

        return ResponseEntity.status(HttpStatus.OK).body(messageResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageResponse> findById(@RequestParam("id") Long id) {
        MessageResponse messageResponse = saleService.getSale(id);

        return ResponseEntity.status(HttpStatus.OK).body(messageResponse);
    }

    @PostMapping("/create")
    public ResponseEntity<MessageResponse> create(@Valid @RequestBody CreateSaleRequest request) {
        MessageResponse messageResponse = saleService.createSale(request);

        return ResponseEntity.status(HttpStatus.OK).body(messageResponse);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<MessageResponse> update(@RequestParam("id") Long id,
            @Valid @RequestBody UpdateSaleRequest request) {
        MessageResponse messageResponse = saleService.updateSale(id, request);

        return ResponseEntity.status(HttpStatus.OK).body(messageResponse);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<MessageResponse> delete(@RequestParam("id") Long id) {
        MessageResponse messageResponse = saleService.deleteSale(id);

        return ResponseEntity.status(HttpStatus.OK).body(messageResponse);
    }
}
