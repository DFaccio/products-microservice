package br.com.productmanagement.interfaceAdapters.gateways;

import br.com.productmanagement.entities.Products;
import br.com.productmanagement.frameworks.db.ProductsRepository;
import br.com.productmanagement.util.enums.ProductCategory;
import br.com.productmanagement.util.exception.ValidationsException;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductsGateway {

    @Resource
    private ProductsRepository productsRepository;

    public Products save(Products products) throws ValidationsException {

        return productsRepository.save(products);

    }

    public Page<Products> findAll(Pageable pageable) throws ValidationsException {

        return productsRepository.findAll(pageable);

    }

    public Products findBySku(String sku) throws ValidationsException {

        return productsRepository.findBySku(sku);

    }

    public Page<Products> findAllByNameAndSupplierAndProductCategory(String name, String supplier, ProductCategory category, Pageable pageable){

        return productsRepository.findAllByNameContainsAndSupplierContainsAndProductCategory(name, supplier, category, pageable);

    }

    public Page<Products> findAllByNameAndSupplier(String name, String supplier, Pageable pageable){

        return productsRepository.findAllByNameContainsAndSupplierContains(name, supplier, pageable);

    }

    public Page<Products> findAllByNameAndProductCategory(String name, ProductCategory category, Pageable pageable){

        return productsRepository.findAllByNameContainsAndProductCategory(name, category, pageable);

    }

    public Page<Products> findAllBySupplierAndProductCategory(String supplier, ProductCategory category, Pageable pageable){

        return productsRepository.findAllBySupplierContainsAndProductCategory(supplier, category, pageable);

    }

    public Page<Products> findAllByName(String name, Pageable pageable){

        return productsRepository.findAllByNameContains(name, pageable);

    }

    public Page<Products> findAllBySupplier(String supplier, Pageable pageable){

        return productsRepository.findAllBySupplierContains(supplier, pageable);

    }

    public Page<Products> findAllByProductCategory(ProductCategory category, Pageable pageable){

        return productsRepository.findAllByProductCategory(category, pageable);

    }

}
