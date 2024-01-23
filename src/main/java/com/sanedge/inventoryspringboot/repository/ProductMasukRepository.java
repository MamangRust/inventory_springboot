package com.sanedge.inventoryspringboot.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sanedge.inventoryspringboot.models.ProductMasuk;

@Repository
public interface ProductMasukRepository extends JpaRepository<ProductMasuk, Long> {
    Optional<ProductMasuk> findById(Long id);
}
