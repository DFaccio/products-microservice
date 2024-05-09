package br.com.productmanagement.frameworks.db;

import br.com.productmanagement.entities.Products;
import br.com.productmanagement.util.enums.ProductCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductsRepository extends JpaRepository<Products, UUID> {

    Page<Products> findAll(Pageable pageable);

    Products findBySku(String sku);

    Page<Products> findAllByNameContainsAndSupplierContainsAndProductCategory(String name, String supplier, ProductCategory category, Pageable pageable);

    Page<Products> findAllByNameContainsAndSupplierContains(String name, String supplier, Pageable pageable);

    Page<Products> findAllByNameContainsAndProductCategory(String name, ProductCategory category, Pageable pageable);

    Page<Products> findAllBySupplierContainsAndProductCategory(String supplier, ProductCategory category, Pageable pageable);

    Page<Products> findAllByNameContains(String name, Pageable pageable);

    Page<Products> findAllBySupplierContains(String supplier, Pageable pageable);

    Page<Products> findAllByProductCategory(ProductCategory category, Pageable pageable);

}
