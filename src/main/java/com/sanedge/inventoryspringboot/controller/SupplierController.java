package com.sanedge.inventoryspringboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sanedge.inventoryspringboot.domain.request.supplier.CreateSupplierRequest;
import com.sanedge.inventoryspringboot.domain.request.supplier.UpdateSupplierRequest;
import com.sanedge.inventoryspringboot.domain.response.MessageResponse;
import com.sanedge.inventoryspringboot.service.SupplierService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/supplier")
public class SupplierController {
    @Autowired
    SupplierService supplierService;

    @GetMapping("/")
    public ResponseEntity<MessageResponse> findAll() {
        MessageResponse messageResponse = supplierService.getAllSupplier();
        return ResponseEntity.status(200).body(messageResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageResponse> findById(@RequestParam("id") Long id) {
        MessageResponse messageResponse = supplierService.getSupplier(id);
        return ResponseEntity.status(200).body(messageResponse);
    }

    @PostMapping("/create")
    public ResponseEntity<MessageResponse> create(@Valid @RequestBody CreateSupplierRequest request) {
        MessageResponse messageResponse = supplierService.createSupplier(request);
        return ResponseEntity.status(200).body(messageResponse);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<MessageResponse> update(@RequestParam("id") Long id, UpdateSupplierRequest request) {
        MessageResponse messageResponse = supplierService.updateSupplier(id, request);
        return ResponseEntity.status(200).body(messageResponse);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<MessageResponse> delete(@RequestParam("id") Long id) {
        MessageResponse messageResponse = supplierService.deleteSupplier(id);
        return ResponseEntity.status(200).body(messageResponse);
    }
}
