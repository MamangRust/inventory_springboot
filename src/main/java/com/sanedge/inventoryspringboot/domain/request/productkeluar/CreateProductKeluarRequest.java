package com.sanedge.inventoryspringboot.domain.request.productkeluar;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductKeluarRequest {
    private String Qty;
    private Long ProductId;
    private Long CategoryId;
}
