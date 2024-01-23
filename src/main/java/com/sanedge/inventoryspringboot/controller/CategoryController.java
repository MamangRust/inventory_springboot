package com.sanedge.inventoryspringboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sanedge.inventoryspringboot.domain.request.category.CreateCategoryRequest;
import com.sanedge.inventoryspringboot.domain.request.category.UpdateCategoryRequest;
import com.sanedge.inventoryspringboot.domain.response.MessageResponse;
import com.sanedge.inventoryspringboot.service.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping("/")
    public ResponseEntity<MessageResponse> getAllCategory() {
        MessageResponse messageResponse = categoryService.getAllCategory();

        return ResponseEntity.status(HttpStatus.OK).body(messageResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageResponse> getCategory(Long id) {
        MessageResponse messageResponse = categoryService.getCategory(id);

        return ResponseEntity.status(HttpStatus.OK).body(messageResponse);
    }

    @GetMapping("/create")
    public ResponseEntity<MessageResponse> createCategory(RequestEntity<CreateCategoryRequest> request) {
        MessageResponse messageResponse = categoryService.createCategory(request.getBody());

        return ResponseEntity.status(HttpStatus.OK).body(messageResponse);
    }

    @GetMapping("/update/{id}")
    public ResponseEntity<MessageResponse> updateCategory(@RequestParam("id") Long id,
            @Valid @RequestBody UpdateCategoryRequest request) {
        MessageResponse messageResponse = categoryService.updateCategory(id, request);

        return ResponseEntity.status(HttpStatus.OK).body(messageResponse);
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<MessageResponse> deleteCategory(@RequestParam("id") Long id) {
        MessageResponse messageResponse = categoryService.deleteCategory(id);

        return ResponseEntity.status(HttpStatus.OK).body(messageResponse);
    }
}
