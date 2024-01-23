package com.sanedge.inventoryspringboot.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanedge.inventoryspringboot.domain.request.customer.CreateCustomerRequest;
import com.sanedge.inventoryspringboot.domain.request.customer.UpdateCustomerRequest;
import com.sanedge.inventoryspringboot.domain.response.MessageResponse;
import com.sanedge.inventoryspringboot.exception.ResourceNotFoundException;
import com.sanedge.inventoryspringboot.models.Customer;
import com.sanedge.inventoryspringboot.repository.CustomerRepository;
import com.sanedge.inventoryspringboot.service.CustomerService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public MessageResponse getAllCustomer() {
        try {
            List<Customer> customers = customerRepository.findAll();
            return MessageResponse.builder().data(customers).message("Success").statusCode(200).build();
        } catch (Exception e) {
            log.error("Error while fetching all customers: {}", e.getMessage(), e);
            return MessageResponse.builder().message("Error occurred").statusCode(500).build();
        }
    }

    @Override
    public MessageResponse getCustomer(Long id) {
        try {
            Customer customer = customerRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
            return MessageResponse.builder().data(customer).message("Success").statusCode(200).build();
        } catch (ResourceNotFoundException e) {
            log.warn("Customer not found for id: {}", id);
            return MessageResponse.builder().message("Customer not found").statusCode(404).build();
        } catch (Exception e) {
            log.error("Error while fetching customer: {}", e.getMessage(), e);
            return MessageResponse.builder().message("Error occurred").statusCode(500).build();
        }
    }

    @Override
    public MessageResponse createCustomer(CreateCustomerRequest request) {
        try {
            Customer customer = new Customer();
            customer.setName(request.getName());
            customer.setAlamat(request.getAddress());
            customer.setTelepon(request.getPhone());

            customerRepository.save(customer);
            return MessageResponse.builder().data(customer).message("Success").statusCode(200).build();
        } catch (Exception e) {
            log.error("Error while creating customer: {}", e.getMessage(), e);
            return MessageResponse.builder().message("Error occurred").statusCode(500).build();
        }
    }

    @Override
    public MessageResponse updateCustomer(Long id, UpdateCustomerRequest request) {
        try {
            Customer customer = customerRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
            customer.setName(request.getName());
            customer.setAlamat(request.getAddress());
            customer.setTelepon(request.getPhone());

            customerRepository.save(customer);

            return MessageResponse.builder().data(customer).message("Success").statusCode(200).build();
        } catch (ResourceNotFoundException e) {
            log.warn("Customer not found for id: {}", id);
            return MessageResponse.builder().message("Customer not found").statusCode(404).build();
        } catch (Exception e) {
            log.error("Error while updating customer: {}", e.getMessage(), e);
            return MessageResponse.builder().message("Error occurred").statusCode(500).build();
        }
    }

    @Override
    public MessageResponse deleteCustomer(Long id) {
        try {
            Customer customer = customerRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
            customerRepository.delete(customer);
            return MessageResponse.builder().message("Success").statusCode(200).build();
        } catch (ResourceNotFoundException e) {
            log.warn("Customer not found for id: {}", id);
            return MessageResponse.builder().message("Customer not found").statusCode(404).build();
        } catch (Exception e) {
            log.error("Error while deleting customer: {}", e.getMessage(), e);
            return MessageResponse.builder().message("Error occurred").statusCode(500).build();
        }
    }
}
