package com.sanedge.inventoryspringboot.domain.request.sale;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateSaleRequest {
    private String name;
    private String address;
    private String email;
    private String phone;
}
