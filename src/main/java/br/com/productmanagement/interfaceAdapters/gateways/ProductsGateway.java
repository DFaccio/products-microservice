package br.com.productmanagement.interfaceAdapters.gateways;

import br.com.productmanagement.frameworks.db.ProductsRepository;
import br.com.productmanagement.entities.Products;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductsGateway {

    @Resource
    private ProductsRepository productsRepository;

    public Page<Products> findAll(Pageable pageable) {

        return productsRepository.findAll(pageable);

    }

    public Products insert(Products products){

        return productsRepository.save(products);

    }

}
