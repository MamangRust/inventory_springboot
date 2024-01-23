package com.sanedge.inventoryspringboot.service;

import com.sanedge.inventoryspringboot.domain.request.sale.CreateSaleRequest;
import com.sanedge.inventoryspringboot.domain.request.sale.UpdateSaleRequest;
import com.sanedge.inventoryspringboot.domain.response.MessageResponse;

public interface SaleService {
    public MessageResponse getAllSale();

    public MessageResponse getSale(Long id);

    public MessageResponse createSale(CreateSaleRequest request);

    public MessageResponse updateSale(Long id, UpdateSaleRequest request);

    public MessageResponse deleteSale(Long id);
}
