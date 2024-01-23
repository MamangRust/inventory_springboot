package com.sanedge.inventoryspringboot.domain.request.productmasuk;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductMasukRequest {
    private String Name;
    private String Qty;
    private Long SupplierId;
    private Long ProductId;
}
