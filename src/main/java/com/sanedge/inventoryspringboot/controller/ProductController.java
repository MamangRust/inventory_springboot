package com.sanedge.inventoryspringboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sanedge.inventoryspringboot.domain.request.product.CreateProductRequest;
import com.sanedge.inventoryspringboot.domain.request.product.UpdateProductRequest;
import com.sanedge.inventoryspringboot.domain.response.MessageResponse;
import com.sanedge.inventoryspringboot.service.ProductService;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    ProductService productService;

    @GetMapping("/")
    public ResponseEntity<MessageResponse> findAll() {
        MessageResponse messageResponse = productService.getAllProduct();
        return ResponseEntity.status(200).body(messageResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageResponse> findById(@RequestParam("id") Long id) {
        MessageResponse messageResponse = productService.getProduct(id);
        return ResponseEntity.status(200).body(messageResponse);
    }

    @PostMapping("/create")
    public ResponseEntity<MessageResponse> create(@RequestParam("file") MultipartFile myfile,
            @RequestParam("name") String name,
            @RequestParam("qty") String qty, @RequestParam("categoryId") Long categoryId) {
        CreateProductRequest request = new CreateProductRequest();

        request.setImage(myfile);
        request.setName(name);
        request.setQty(qty);
        request.setCategoryId(categoryId);

        MessageResponse messageResponse = productService.createProduct(request);
        return ResponseEntity.status(200).body(messageResponse);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<MessageResponse> update(@RequestParam("id") Long id,
            @RequestParam("file") MultipartFile myfile,
            @RequestParam("name") String name,
            @RequestParam("qty") String qty, @RequestParam("categoryId") Long categoryId) {
        UpdateProductRequest request = new UpdateProductRequest();

        request.setImage(myfile);
        request.setName(name);
        request.setQty(qty);
        request.setCategoryId(categoryId);

        MessageResponse messageResponse = productService.updateProduct(id, request);

        return ResponseEntity.status(200).body(messageResponse);

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<MessageResponse> delete(@RequestParam("id") Long id) {
        MessageResponse messageResponse = productService.deleteProduct(id);
        return ResponseEntity.status(200).body(messageResponse);
    }

}
