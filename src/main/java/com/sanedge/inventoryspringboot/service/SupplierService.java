package com.sanedge.inventoryspringboot.service;

import com.sanedge.inventoryspringboot.domain.request.supplier.CreateSupplierRequest;
import com.sanedge.inventoryspringboot.domain.request.supplier.UpdateSupplierRequest;
import com.sanedge.inventoryspringboot.domain.response.MessageResponse;

public interface SupplierService {
    public MessageResponse getAllSupplier();

    public MessageResponse getSupplier(Long id);

    public MessageResponse createSupplier(CreateSupplierRequest request);

    public MessageResponse updateSupplier(Long id, UpdateSupplierRequest request);

    public MessageResponse deleteSupplier(Long id);
}
