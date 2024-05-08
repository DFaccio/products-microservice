package br.com.productmanagement.interfaceAdapters.gateways;

import br.com.productmanagement.frameworks.db.ProductsRepository;
import br.com.productmanagement.entities.Products;
import br.com.productmanagement.util.exception.ValidationsException;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

}
