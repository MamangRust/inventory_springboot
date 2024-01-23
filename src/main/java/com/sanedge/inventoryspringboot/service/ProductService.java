package com.sanedge.inventoryspringboot.service;

import org.springframework.web.multipart.MultipartFile;

import com.sanedge.inventoryspringboot.domain.request.product.CreateProductRequest;
import com.sanedge.inventoryspringboot.domain.request.product.UpdateProductRequest;
import com.sanedge.inventoryspringboot.domain.response.MessageResponse;

import jakarta.servlet.http.HttpServletResponse;

public interface ProductService {
    public MessageResponse getAllProduct();

    public MessageResponse getProduct(Long id);

    public MessageResponse createProduct(CreateProductRequest request);

    public MessageResponse updateProduct(Long id, UpdateProductRequest request);

    public MessageResponse exportProducts(HttpServletResponse response);

    public MessageResponse importProducts(MultipartFile file);

    public MessageResponse exportProductsToPDF(HttpServletResponse response);

    public MessageResponse deleteProduct(Long id);
}
