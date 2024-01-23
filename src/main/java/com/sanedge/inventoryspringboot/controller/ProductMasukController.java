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

import com.sanedge.inventoryspringboot.domain.request.productmasuk.CreateProductMasukRequest;
import com.sanedge.inventoryspringboot.domain.request.productmasuk.UpdateProductMasukRequest;
import com.sanedge.inventoryspringboot.domain.response.MessageResponse;
import com.sanedge.inventoryspringboot.service.ProductMasukService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/product-masuk")
public class ProductMasukController {
    @Autowired
    private ProductMasukService productMasukService;

    @GetMapping("/")
    public ResponseEntity<MessageResponse> findAll() {
        MessageResponse messageResponse = productMasukService.getAllProductMasuk();
        return ResponseEntity.status(200).body(messageResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageResponse> findById(@RequestParam("id") Long id) {
        MessageResponse messageResponse = productMasukService.getProductMasuk(id);
        return ResponseEntity.status(200).body(messageResponse);
    }

    @PostMapping("/create")
    public ResponseEntity<MessageResponse> create(@Valid @RequestBody CreateProductMasukRequest request) {
        MessageResponse messageResponse = productMasukService.createProductMasuk(request);
        return ResponseEntity.status(200).body(messageResponse);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<MessageResponse> update(@RequestParam("id") Long id, UpdateProductMasukRequest request) {
        MessageResponse messageResponse = productMasukService.updateProductMasuk(id, request);
        return ResponseEntity.status(200).body(messageResponse);
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<MessageResponse> delete(@RequestParam("id") Long id) {
        MessageResponse messageResponse = productMasukService.deleteProductMasuk(id);
        return ResponseEntity.status(200).body(messageResponse);
    }
}
