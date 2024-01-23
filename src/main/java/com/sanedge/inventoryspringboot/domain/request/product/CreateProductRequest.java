package com.sanedge.inventoryspringboot.domain.request.product;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductRequest {
    private String Name;
    private MultipartFile Image;
    private String qty;
    private Long CategoryId;
}
