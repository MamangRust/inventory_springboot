package com.sanedge.inventoryspringboot.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sanedge.inventoryspringboot.models.ProductKeluar;

@Repository
public interface ProductKeluarRepository extends JpaRepository<ProductKeluar, Long> {
    Optional<ProductKeluar> findById(Long id);
}
