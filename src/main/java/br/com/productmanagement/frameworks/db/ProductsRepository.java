package br.com.productmanagement.frameworks.db;

import br.com.productmanagement.entities.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductsRepository extends JpaRepository<Products, UUID> {

    Page<Products> findAll(Pageable pageable);

    Products findBySku(String sku);

}
