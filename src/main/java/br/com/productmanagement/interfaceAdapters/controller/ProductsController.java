package br.com.productmanagement.interfaceAdapters.controller;

import br.com.productmanagement.entities.Products;
import br.com.productmanagement.interfaceAdapters.gateways.DiscountGateway;
import br.com.productmanagement.interfaceAdapters.gateways.ProductsGateway;
import br.com.productmanagement.interfaceAdapters.presenters.ProductPresenter;
import br.com.productmanagement.interfaceAdapters.presenters.dto.ProductDto;
import br.com.productmanagement.util.pagination.PagedResponse;
import br.com.productmanagement.util.pagination.Pagination;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class ProductsController {

    @Resource
    private ProductsGateway productsGateway;

    @Resource
    private DiscountGateway discountGateway;

    @Resource
    private ProductPresenter productPresenter;

    public PagedResponse<ProductDto> findAll(Pagination pagination){

        Pageable pageable = PageRequest.of(pagination.getPage(), pagination.getPageSize());

        Page<Products> products = productsGateway.findAll(pageable);

        return productPresenter.convertDocuments(products);

    }

    public ProductDto insert(ProductDto dto){

        Products products = productPresenter.convert(dto);

        return productPresenter.convert(productsGateway.insert(products));

    }

}
