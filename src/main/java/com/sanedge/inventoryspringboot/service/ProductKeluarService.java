package com.sanedge.inventoryspringboot.service;

import com.sanedge.inventoryspringboot.domain.request.productkeluar.CreateProductKeluarRequest;
import com.sanedge.inventoryspringboot.domain.request.productkeluar.UpdateProductKeluarRequest;
import com.sanedge.inventoryspringboot.domain.response.MessageResponse;

public interface ProductKeluarService {
    public MessageResponse getAllProductKeluar();

    public MessageResponse getProductKeluar(Long id);

    public MessageResponse createProductKeluar(CreateProductKeluarRequest request);

    public MessageResponse updateProductKeluar(Long id, UpdateProductKeluarRequest request);

    public MessageResponse deleteProductKeluar(Long id);
}
