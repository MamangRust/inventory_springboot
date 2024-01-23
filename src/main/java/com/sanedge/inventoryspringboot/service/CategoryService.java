package com.sanedge.inventoryspringboot.service;

import com.sanedge.inventoryspringboot.domain.request.category.CreateCategoryRequest;
import com.sanedge.inventoryspringboot.domain.request.category.UpdateCategoryRequest;
import com.sanedge.inventoryspringboot.domain.response.MessageResponse;

public interface CategoryService {
    public MessageResponse getAllCategory();

    public MessageResponse getCategory(Long id);

    public MessageResponse createCategory(CreateCategoryRequest request);

    public MessageResponse updateCategory(Long id, UpdateCategoryRequest request);

    public MessageResponse deleteCategory(Long id);
}
