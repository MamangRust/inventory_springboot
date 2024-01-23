package com.sanedge.inventoryspringboot.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanedge.inventoryspringboot.domain.request.category.CreateCategoryRequest;
import com.sanedge.inventoryspringboot.domain.request.category.UpdateCategoryRequest;
import com.sanedge.inventoryspringboot.domain.response.MessageResponse;
import com.sanedge.inventoryspringboot.exception.ResourceNotFoundException;
import com.sanedge.inventoryspringboot.models.Category;
import com.sanedge.inventoryspringboot.repository.CategoryRepository;
import com.sanedge.inventoryspringboot.service.CategoryService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public MessageResponse getAllCategory() {
        try {
            List<Category> categories = categoryRepository.findAll();
            return MessageResponse.builder().data(categories).message("Success").statusCode(200).build();
        } catch (Exception e) {
            log.error("Error while fetching all categories: {}", e.getMessage(), e);
            return MessageResponse.builder().message("Error occurred").statusCode(500).build();
        }
    }

    @Override
    public MessageResponse getCategory(Long id) {
        try {
            Category category = categoryRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
            return MessageResponse.builder().data(category).message("Success").statusCode(200).build();
        } catch (ResourceNotFoundException e) {
            log.warn("Category not found for id: {}", id);
            return MessageResponse.builder().message("Category not found").statusCode(404).build();
        } catch (Exception e) {
            log.error("Error while fetching category: {}", e.getMessage(), e);
            return MessageResponse.builder().message("Error occurred").statusCode(500).build();
        }
    }

    @Override
    public MessageResponse createCategory(CreateCategoryRequest request) {
        try {
            Category category = new Category();
            category.setName(request.getName());
            categoryRepository.save(category);
            return MessageResponse.builder().data(category).message("Success").statusCode(200).build();
        } catch (Exception e) {
            log.error("Error while creating category: {}", e.getMessage(), e);
            return MessageResponse.builder().message("Error occurred").statusCode(500).build();
        }
    }

    @Override
    public MessageResponse updateCategory(Long id, UpdateCategoryRequest request) {
        try {
            Category category = categoryRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
            category.setName(request.getName());
            categoryRepository.save(category);
            return MessageResponse.builder().data(category).message("Success").statusCode(200).build();
        } catch (ResourceNotFoundException e) {
            log.warn("Category not found for id: {}", id);
            return MessageResponse.builder().message("Category not found").statusCode(404).build();
        } catch (Exception e) {
            log.error("Error while updating category: {}", e.getMessage(), e);
            return MessageResponse.builder().message("Error occurred").statusCode(500).build();
        }
    }

    @Override
    public MessageResponse deleteCategory(Long id) {
        try {
            Category category = categoryRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
            categoryRepository.delete(category);
            return MessageResponse.builder().message("Success").statusCode(200).build();
        } catch (ResourceNotFoundException e) {
            log.warn("Category not found for id: {}", id);
            return MessageResponse.builder().message("Category not found").statusCode(404).build();
        } catch (Exception e) {
            log.error("Error while deleting category: {}", e.getMessage(), e);
            return MessageResponse.builder().message("Error occurred").statusCode(500).build();
        }
    }
}
