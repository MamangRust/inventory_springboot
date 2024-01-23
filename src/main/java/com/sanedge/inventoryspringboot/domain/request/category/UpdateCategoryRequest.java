package com.sanedge.inventoryspringboot.domain.request.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateCategoryRequest {
    private String name;
}
