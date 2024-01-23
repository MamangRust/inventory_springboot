package com.sanedge.inventoryspringboot.service;

import com.sanedge.inventoryspringboot.domain.request.productmasuk.CreateProductMasukRequest;
import com.sanedge.inventoryspringboot.domain.request.productmasuk.UpdateProductMasukRequest;
import com.sanedge.inventoryspringboot.domain.response.MessageResponse;

public interface ProductMasukService {
    public MessageResponse getAllProductMasuk();

    public MessageResponse getProductMasuk(Long id);

    public MessageResponse createProductMasuk(CreateProductMasukRequest request);

    public MessageResponse updateProductMasuk(Long id, UpdateProductMasukRequest request);

    public MessageResponse deleteProductMasuk(Long id);
}
