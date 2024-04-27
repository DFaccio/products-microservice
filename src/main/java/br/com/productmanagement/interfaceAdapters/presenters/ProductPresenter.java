package br.com.productmanagement.interfaceAdapters.presenters;

import br.com.productmanagement.entities.Products;
import br.com.productmanagement.interfaceAdapters.presenters.dto.ProductDto;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class ProductPresenter implements Presenter<Products, ProductDto>{

    @Resource
    private DiscountPresenter discountPresenter;

    @Override
    public ProductDto convert(Products document){

        ProductDto productDto = new ProductDto();

        productDto.setId(document.getProductId());
        productDto.setName(document.getName());
        productDto.setDescription(document.getDescription());
        productDto.setProductCategory(document.getProductCategory());
        productDto.setModel(document.getModel());
        productDto.setAvailable(document.isAvailable());
        productDto.setAvailableQuantity(document.getAvailableQuantity());
        productDto.setColor(document.getColor());
        productDto.setSize(document.getSize());
        productDto.setImageURL(document.getImageURL());
        productDto.setSupplier(document.getSupplier());
        productDto.setBrand(document.getBrand());
        productDto.setPrice(document.getPrice());
        productDto.setProductHeight(document.getProductHeight());
        productDto.setProductWidth(document.getProductWidth());
        productDto.setProductDepth(document.getProductDepth());
        productDto.setProductWeight(document.getProductWeight());
        productDto.setPackagingHeight(document.getPackagingHeight());
        productDto.setPackagingWidth(document.getPackagingWidth());
        productDto.setPackagingDepth(document.getPackagingDepth());
        productDto.setPackagingWeight(document.getPackagingWeight());
        productDto.setSku(document.getSku());
        productDto.setDiscount(discountPresenter.convert(document.getDiscount()));

        return productDto;

    }

    public Products convert(ProductDto dto){

        Products products = new Products();

        products.setProductId(dto.getId());
        products.setName(dto.getName());
        products.setDescription(dto.getDescription());
        products.setProductCategory(dto.getProductCategory());
        products.setModel(dto.getModel());
        products.setAvailable(dto.isAvailable());
        products.setAvailableQuantity(dto.getAvailableQuantity());
        products.setColor(dto.getColor());
        products.setSize(dto.getSize());
        products.setImageURL(dto.getImageURL());
        products.setSupplier(dto.getSupplier());
        products.setBrand(dto.getBrand());
        products.setPrice(dto.getPrice());
        products.setProductHeight(dto.getProductHeight());
        products.setProductWidth(dto.getProductWidth());
        products.setProductDepth(dto.getProductDepth());
        products.setProductWeight(dto.getProductWeight());
        products.setPackagingHeight(dto.getPackagingHeight());
        products.setPackagingWidth(dto.getPackagingWidth());
        products.setPackagingDepth(dto.getPackagingDepth());
        products.setProductWeight(dto.getPackagingWeight());
        products.setSku(dto.getSku());
        products.setDiscount(discountPresenter.convert(dto.getDiscount()));

        return products;

    }

}
