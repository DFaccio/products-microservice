package br.com.productmanagement.frameworks.db;

import br.com.productmanagement.entities.Product;
import br.com.productmanagement.util.enums.ProductCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    Page<Product> findAll(Pageable pageable);

    Product findBySku(String sku);

    Page<Product> findByNameContainingIgnoreCaseAndSupplierContainingIgnoreCaseAndProductCategory(String name, String supplier, ProductCategory category, Pageable pageable);

    Page<Product> findByNameContainingIgnoreCaseAndSupplierContainingIgnoreCase(String name, String supplier, Pageable pageable);

    Page<Product> findByNameContainingIgnoreCaseAndProductCategory(String name, ProductCategory category, Pageable pageable);

    Page<Product> findBySupplierContainingIgnoreCaseAndProductCategory(String supplier, ProductCategory category, Pageable pageable);

    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<Product> findBySupplierContainingIgnoreCase(String supplier, Pageable pageable);

    Page<Product> findByProductCategory(ProductCategory category, Pageable pageable);

}
