package br.com.productmanagement.interfaceadapters.controller;

import br.com.productmanagement.entities.Discount;
import br.com.productmanagement.entities.Product;
import br.com.productmanagement.interfaceadapters.gateways.DiscountGateway;
import br.com.productmanagement.interfaceadapters.gateways.ProductGateway;
import br.com.productmanagement.interfaceadapters.helper.ProductHelper;
import br.com.productmanagement.interfaceadapters.presenters.ProductPresenter;
import br.com.productmanagement.interfaceadapters.presenters.dto.ProductDto;
import br.com.productmanagement.usercase.ProductBusiness;
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

    public ProductDto insert(ProductDto dto) throws ValidationsException {

        Product product = productPresenter.convert(dto);

        if(product.getDiscount() != null){

            Optional<Discount> discount = discountGateway.findById(product.getDiscount().getId());

            if(discount.isEmpty()){

                throw new ValidationsException("0101", "Desconto", product.getDiscount().getId().toString());

            }

            product.setDiscount(discount.get());

        }

        product = productBusiness.create(product);

        Product productSave = productGateway.findBySku(product.getSku());

        if(productSave != null){

            product = productBusiness.updateSku(product, productSave);

        }

        product = productGateway.save(product);

        return productPresenter.convert(product);

    }

    public ProductDto update(String sku, ProductDto dto) throws ValidationsException {

        Product product = productPresenter.convert(dto);

        Product productUpd = productGateway.findBySku(sku);

        if(productUpd == null){

            throw new ValidationsException("0100", "SKU", sku);

        }else{

            product = productBusiness.updateSku(product, productUpd);

        }

        if(product.getDiscount() != null){

            Optional<Discount> discount = discountGateway.findById(product.getDiscount().getId());

            if(discount.isEmpty()){

                throw new ValidationsException("0101", "Desconto", product.getDiscount().getId().toString());

            }

            product.setDiscount(discount.get());

        }

        product = productGateway.save(product);

        return productPresenter.convert(product);

    }

    public PagedResponse<ProductDto> findAll(Pagination pagination, String name, ProductCategory category, String supplier) throws ValidationsException {

        Pageable pageable = PageRequest.of(pagination.getPage(), pagination.getPageSize());
        Page<Product> products = null;

        boolean nameFilter = name != null && !name.trim().isEmpty();
        boolean supplierFilter = supplier != null && !supplier.trim().isEmpty();
        boolean categoryFilter = category != null && !String.valueOf(category).trim().isEmpty();

        if (!nameFilter && !supplierFilter && !categoryFilter) {
            products = productGateway.findAll(pageable);
        } else if (nameFilter && supplierFilter && categoryFilter) {
            products = productGateway.findAllByNameAndSupplierAndProductCategory(name, supplier, category, pageable);
        }else if (nameFilter && supplierFilter) {
            products = productGateway.findAllByNameAndSupplier(name, supplier, pageable);
        }else if (nameFilter && categoryFilter) {
            products = productGateway.findAllByNameAndProductCategory(name, category, pageable);
        }else if (categoryFilter && supplierFilter) {
            products = productGateway.findAllBySupplierAndProductCategory(supplier, category, pageable);
        }else if (nameFilter) {
            products = productGateway.findAllByName(name, pageable);
        }else if (supplierFilter) {
            products = productGateway.findAllBySupplier(supplier, pageable);
        }else{
            products = productGateway.findAllByProductCategory(category, pageable);
        }

        if(products.hasContent()) {
            products = productBusiness.checkForAvailableDiscount(products);
        }

        return productPresenter.convertDocuments(products);

    }

    public ProductDto findBySku(String sku) throws ValidationsException {

        Product product = productGateway.findBySku(sku);

        if(product == null){
            throw new ValidationsException("0100", "SKU", sku);
        }

        Discount discount = productHelper.validadeProductDiscount(product);

        product.setDiscount(discount);

        return productPresenter.convert(product);

    }

}
