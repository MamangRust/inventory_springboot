package com.sanedge.inventoryspringboot.domain.request.productmasuk;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProductMasukRequest {
    private String Name;
    private String qty;
    private Long SupplierId;
    private Long ProductId;
}
