package br.com.productmanagement.interfaceAdapters.controller;

import br.com.productmanagement.entities.Discount;
import br.com.productmanagement.entities.Product;
import br.com.productmanagement.interfaceAdapters.gateways.DiscountGateway;
import br.com.productmanagement.interfaceAdapters.gateways.ProductGateway;
import br.com.productmanagement.interfaceAdapters.helper.ProductHelper;
import br.com.productmanagement.interfaceAdapters.presenters.ProductPresenter;
import br.com.productmanagement.interfaceAdapters.presenters.dto.ProductDto;
import br.com.productmanagement.usercase.ProductBusiness;
import br.com.productmanagement.util.SkuGenerator;
import br.com.productmanagement.util.enums.ProductCategory;
import br.com.productmanagement.util.exception.ValidationsException;
import br.com.productmanagement.util.pagination.PagedResponse;
import br.com.productmanagement.util.pagination.Pagination;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ProductController {

    @Resource
    private ProductGateway productGateway;

    @Resource
    private DiscountGateway discountGateway;

    @Resource
    private ProductPresenter productPresenter;

    @Resource
    private ProductBusiness productBusiness;

    @Resource
    private ProductHelper productHelper;

    @Resource
    private SkuGenerator skuGenerator;

    public ProductDto insert(ProductDto dto) throws ValidationsException {

        Product product = productPresenter.convert(dto);

        String sku;

        if(product.getSku() == null) {

            sku = skuGenerator.generateSku(product.getProductCategory().toString(),
                    product.getBrand(),
                    product.getName(),
                    product.getModel(),
                    product.getColor(),
                    product.getSize());

            product.setSku(sku);

        }else{

            sku = product.getSku();

        }

        Product productSave = productGateway.findBySku(sku);

        if(productSave != null){

            product = productBusiness.updateSku(product, productSave);

        }

        product = productGateway.save(product);

        if(product.getDiscount() != null){
            Optional<Discount> discount = discountGateway.findById(product.getDiscount().getDiscountId());
            product.setDiscount(discount.get());
        }
        return productPresenter.convert(product);

    }

    public ProductDto update(String sku, ProductDto dto) throws ValidationsException {

        Product product = productPresenter.convert(dto);

        Product productUpd = productGateway.findBySku(sku);

        if(productUpd != null){

            product = productBusiness.updateSku(product, productUpd);

        }else{

            throw new ValidationsException("0100", "SKU", sku);

        }

        product = productGateway.save(product);

        return productPresenter.convert(product);

    }

    public PagedResponse<ProductDto> findAll(Pagination pagination, String name, ProductCategory category, String supplier) throws ValidationsException {

        Pageable pageable = PageRequest.of(pagination.getPage(), pagination.getPageSize());
        Page<Product> products = null;

        boolean nameFilter = name != null && !name.trim().isEmpty();
        boolean supplierFilter = supplier != null && !supplier.trim().isEmpty();
        boolean categoryFilter = category != null;

//        ARRUMAR OS FILTROS QUE NÃO ESTÃO RETORNANDO

        if (!nameFilter && !supplierFilter && !categoryFilter) {
            products = productGateway.findAll(pageable);
        }
        if (nameFilter && supplierFilter && categoryFilter) {
            products = productGateway.findAllByNameAndSupplierAndProductCategory(name, supplier, category, pageable);
        }
        if (nameFilter && supplierFilter) {
            products = productGateway.findAllByNameAndSupplier(name, supplier, pageable);
        }
        if (nameFilter && categoryFilter) {
            products = productGateway.findAllByNameAndProductCategory(name, category, pageable);
        }
        if (categoryFilter && supplierFilter) {
            products = productGateway.findAllBySupplierAndProductCategory(supplier, category, pageable);
        }
        if (nameFilter && !supplierFilter && !categoryFilter) {
            products = productGateway.findAllByName(name, pageable);
        }
        if (!nameFilter && supplierFilter && !categoryFilter) {
            products = productGateway.findAllBySupplier(supplier, pageable);
        }
        if (!nameFilter && !supplierFilter && categoryFilter) {
            products = productGateway.findAllByProductCategory(category, pageable);
        }

//        ARRUMAR O SET DA OCORRENCIA

//        if(products.getPageable().getPageSize() > 0){
//
//            int i = 0;
//
//            for(Products prodDis : products.getContent()){
//
//                Discount discount = new Discount();
//                discount = productHelper.validadeProductDiscount(products.getContent().get(i));
//                prodDis.setDiscount(discount);
//                products.toSet().stream().toList().add(i, prodDis);
//                i++;
//
//            }
//
//        }

        return productPresenter.convertDocuments(products);

    }

    public ProductDto findBySku(String sku) throws ValidationsException {

        Product product = productGateway.findBySku(sku);

        if(product == null){
            throw new ValidationsException("0100", "SKU", sku);
        }

        Discount discount = new Discount();

        discount = productHelper.validadeProductDiscount(product);

        product.setDiscount(discount);

        return productPresenter.convert(product);

    }

}
