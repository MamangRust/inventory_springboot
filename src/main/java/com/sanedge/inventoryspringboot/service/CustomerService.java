package com.sanedge.inventoryspringboot.service;

import com.sanedge.inventoryspringboot.domain.request.customer.CreateCustomerRequest;
import com.sanedge.inventoryspringboot.domain.request.customer.UpdateCustomerRequest;
import com.sanedge.inventoryspringboot.domain.response.MessageResponse;

public interface CustomerService {
    public MessageResponse getAllCustomer();

    public MessageResponse getCustomer(Long id);

    public MessageResponse createCustomer(CreateCustomerRequest request);

    public MessageResponse updateCustomer(Long id, UpdateCustomerRequest request);

    public MessageResponse deleteCustomer(Long id);
}
