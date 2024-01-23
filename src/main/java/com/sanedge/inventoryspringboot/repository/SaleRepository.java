package com.sanedge.inventoryspringboot.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sanedge.inventoryspringboot.models.Sale;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
    Optional<Sale> findById(Long id);
}
