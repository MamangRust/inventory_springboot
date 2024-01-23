package com.sanedge.inventoryspringboot.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "sale")
@EqualsAndHashCode(callSuper = true)
public class Sale extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "alamat", nullable = false, columnDefinition = "text")
    private String alamat;

    @Column(name = "email", nullable = false, unique = true, length = 255)
    private String email;

    @Column(name = "telepon", nullable = false, unique = true)
    private String telepon;
}
