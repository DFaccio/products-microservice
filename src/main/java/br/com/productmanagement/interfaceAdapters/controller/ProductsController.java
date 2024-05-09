package br.com.productmanagement.interfaceAdapters.controller;

import br.com.productmanagement.entities.Discount;
import br.com.productmanagement.entities.Products;
import br.com.productmanagement.interfaceAdapters.gateways.DiscountGateway;
import br.com.productmanagement.interfaceAdapters.gateways.ProductsGateway;
import br.com.productmanagement.interfaceAdapters.helper.ProductDiscountHelper;
import br.com.productmanagement.interfaceAdapters.presenters.ProductPresenter;
import br.com.productmanagement.interfaceAdapters.presenters.dto.ProductDto;
import br.com.productmanagement.interfaceAdapters.presenters.dto.ProductOrderDto;
import br.com.productmanagement.usercase.ProductsBusiness;
import br.com.productmanagement.util.SkuGenerator;
import br.com.productmanagement.util.enums.Operation;
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
public class ProductsController {

    @Resource
    private ProductsGateway productsGateway;

    @Resource
    private DiscountGateway discountGateway;

    @Resource
    private ProductPresenter productPresenter;

    @Resource
    private ProductsBusiness productsBusiness;

    @Resource
    private ProductDiscountHelper productDiscountHelper;

    @Resource
    private SkuGenerator skuGenerator;

    public ProductDto insert(ProductDto dto) throws ValidationsException {

        Products products = productPresenter.convert(dto);

        String sku;

        if(products.getSku() == null) {

            sku = skuGenerator.generateSku(products.getProductCategory().toString(),
                    products.getBrand(),
                    products.getName(),
                    products.getModel(),
                    products.getColor(),
                    products.getSize());

            products.setSku(sku);

        }else{

            sku = products.getSku();

        }

        Products productSave = productsGateway.findBySku(sku);

        if(productSave != null){

            products = productsBusiness.updateSku(products, productSave);

        }

        products = productsGateway.save(products);

        if(products.getDiscount() != null){
            Optional<Discount> discount = discountGateway.findById(products.getDiscount().getDiscountId());
            products.setDiscount(discount.get());
        }
        return productPresenter.convert(products);

    }

    public ProductDto update(String sku, ProductDto dto) throws ValidationsException {

        Products products = productPresenter.convert(dto);

        Products productUpd = productsGateway.findBySku(sku);

        if(productUpd != null){

            products = productsBusiness.updateSku(products, productUpd);

        }else{

            throw new ValidationsException("0100", "SKU", sku);

        }

        products = productsGateway.save(products);

        return productPresenter.convert(products);

    }

    public PagedResponse<ProductDto> findAll(Pagination pagination, String name, ProductCategory category, String supplier) throws ValidationsException {

        Pageable pageable = PageRequest.of(pagination.getPage(), pagination.getPageSize());
        Page<Products> products = null;

        boolean nameFilter = name != null && !name.trim().isEmpty();
        boolean supplierFilter = supplier != null && !supplier.trim().isEmpty();
        boolean categoryFilter = category != null;

//        ARRUMAR OS FILTROS QUE NÃO ESTÃO RETORNANDO

        if (!nameFilter && !supplierFilter && !categoryFilter) {
            products = productsGateway.findAll(pageable);
        }
        if (nameFilter && supplierFilter && categoryFilter) {
            products = productsGateway.findAllByNameAndSupplierAndProductCategory(name, supplier, category, pageable);
        }
        if (nameFilter && supplierFilter) {
            products = productsGateway.findAllByNameAndSupplier(name, supplier, pageable);
        }
        if (nameFilter && categoryFilter) {
            products = productsGateway.findAllByNameAndProductCategory(name, category, pageable);
        }
        if (categoryFilter && supplierFilter) {
            products = productsGateway.findAllBySupplierAndProductCategory(supplier, category, pageable);
        }
        if (nameFilter && !supplierFilter && !categoryFilter) {
            products = productsGateway.findAllByName(name, pageable);
        }
        if (!nameFilter && supplierFilter && !categoryFilter) {
            products = productsGateway.findAllBySupplier(supplier, pageable);
        }
        if (!nameFilter && !supplierFilter && categoryFilter) {
            products = productsGateway.findAllByProductCategory(category, pageable);
        }

//        ARRUMAR O SET DA OCORRENCIA

//        if(products.getPageable().getPageSize() > 0){
//
//            int i = 0;
//
//            for(Products prodDis : products.getContent()){
//
//                Discount discount = new Discount();
//                discount = productDiscountHelper.validadeProductDiscount(products.getContent().get(i));
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

        Products products = productsGateway.findBySku(sku);

        if(products == null){
            throw new ValidationsException("0100", "SKU", sku);
        }

        Discount discount = new Discount();

        discount = productDiscountHelper.validadeProductDiscount(products);

        products.setDiscount(discount);

//        se tiver discountId, chama findById do discount, depois chama método pra validar o desconto
//        se não tiver discountId ou não estiver válido, chama o findByCategory, depois chama método pra validar o desconto
//        ProductDto productDto = productsBusiness.validadeProductDiscount(products.get());

        return productPresenter.convert(products);

    }

    public ProductOrderDto newOrderRequest(String sku, int orderQuantity) throws ValidationsException {

        ProductDto productDto = findBySku(sku);

        int productQuantity = productDto.getAvailableQuantity();

        if(!productsBusiness.checkAvailableQuantity(productQuantity, orderQuantity)){
            throw new ValidationsException("0101", "produto", sku);
        }

        ProductOrderDto productOrderDto = productsBusiness.returnProductOrderValues(productDto, orderQuantity);

        return productOrderDto;

    }

    public ProductOrderDto updateSkuOnNewOrder(String sku, int orderQuantity) throws ValidationsException {

        ProductDto productDto = findBySku(sku);

        int productQuantity = productDto.getAvailableQuantity();

        if(!productsBusiness.checkAvailableQuantity(productQuantity, orderQuantity)){
            throw new ValidationsException("0101", "produto", sku);
        }

        ProductOrderDto productOrderDto = productsBusiness.returnProductOrderValues(productDto, orderQuantity);

        Products products = productPresenter.convert(productDto);

        products = productsBusiness.updateProductKeeping(products, productQuantity, orderQuantity, Operation.SALE);

        productsGateway.save(products);

        return productOrderDto;

    }

    public void updateSkuOnOrderCancellation(String sku, int orderQuantity) throws ValidationsException {

        Products products = productsGateway.findBySku(sku);

        int productQuantity = products.getAvailableQuantity();

        products = productsBusiness.updateProductKeeping(products, productQuantity, orderQuantity, Operation.ORDER_CANCELLATION);

        productsGateway.save(products);

    }

}
