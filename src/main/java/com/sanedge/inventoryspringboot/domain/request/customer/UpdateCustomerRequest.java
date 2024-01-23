package com.sanedge.inventoryspringboot.domain.request.customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateCustomerRequest {
    private String name;
    private String address;
    private String phone;
}
