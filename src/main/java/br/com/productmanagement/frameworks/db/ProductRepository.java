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

    Page<Product> findAllByNameContainsAndSupplierContainsAndProductCategory(String name, String supplier, ProductCategory category, Pageable pageable);

    Page<Product> findAllByNameContainsAndSupplierContains(String name, String supplier, Pageable pageable);

    Page<Product> findAllByNameContainsAndProductCategory(String name, ProductCategory category, Pageable pageable);

    Page<Product> findAllBySupplierContainsAndProductCategory(String supplier, ProductCategory category, Pageable pageable);

    Page<Product> findAllByNameContains(String name, Pageable pageable);

    Page<Product> findAllBySupplierContains(String supplier, Pageable pageable);

    Page<Product> findAllByProductCategory(ProductCategory category, Pageable pageable);

}
