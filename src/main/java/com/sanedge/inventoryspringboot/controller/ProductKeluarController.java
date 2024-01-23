package com.sanedge.inventoryspringboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sanedge.inventoryspringboot.domain.request.productkeluar.CreateProductKeluarRequest;
import com.sanedge.inventoryspringboot.domain.request.productkeluar.UpdateProductKeluarRequest;
import com.sanedge.inventoryspringboot.domain.response.MessageResponse;
import com.sanedge.inventoryspringboot.service.ProductKeluarService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/product-keluar")
public class ProductKeluarController {
    @Autowired
    private ProductKeluarService productKeluarService;

    @GetMapping("/")
    public ResponseEntity<MessageResponse> findAll() {
        MessageResponse messageResponse = productKeluarService.getAllProductKeluar();
        return ResponseEntity.status(200).body(messageResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageResponse> findById(@RequestParam("id") Long id) {
        MessageResponse messageResponse = productKeluarService.getProductKeluar(id);
        return ResponseEntity.status(200).body(messageResponse);
    }

    @PostMapping("/create")
    public ResponseEntity<MessageResponse> create(@Valid @RequestBody CreateProductKeluarRequest request) {
        MessageResponse messageResponse = productKeluarService.createProductKeluar(request);
        return ResponseEntity.status(200).body(messageResponse);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<MessageResponse> update(@RequestParam("id") Long id, UpdateProductKeluarRequest request) {
        MessageResponse messageResponse = productKeluarService.updateProductKeluar(id, request);
        return ResponseEntity.status(200).body(messageResponse);
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<MessageResponse> delete(@RequestParam("id") Long id) {
        MessageResponse messageResponse = productKeluarService.deleteProductKeluar(id);
        return ResponseEntity.status(200).body(messageResponse);
    }
}
