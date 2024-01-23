package com.sanedge.inventoryspringboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sanedge.inventoryspringboot.domain.request.customer.CreateCustomerRequest;
import com.sanedge.inventoryspringboot.domain.request.customer.UpdateCustomerRequest;
import com.sanedge.inventoryspringboot.domain.response.MessageResponse;
import com.sanedge.inventoryspringboot.service.CustomerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping("/")
    public ResponseEntity<MessageResponse> findAll() {
        MessageResponse messageResponse = customerService.getAllCustomer();
        return ResponseEntity.status(200).body(messageResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageResponse> findById(@RequestParam("id") Long id) {
        MessageResponse messageResponse = customerService.getCustomer(id);
        return ResponseEntity.status(200).body(messageResponse);
    }

    @PostMapping("/create")
    public ResponseEntity<MessageResponse> create(@Valid @RequestBody CreateCustomerRequest request) {
        MessageResponse messageResponse = customerService.createCustomer(request);
        return ResponseEntity.status(200).body(messageResponse);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<MessageResponse> update(@RequestParam("id") Long id, UpdateCustomerRequest request) {
        MessageResponse messageResponse = customerService.updateCustomer(id, request);
        return ResponseEntity.status(200).body(messageResponse);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<MessageResponse> delete(@RequestParam("id") Long id) {
        MessageResponse messageResponse = customerService.deleteCustomer(id);
        return ResponseEntity.status(200).body(messageResponse);
    }
}
