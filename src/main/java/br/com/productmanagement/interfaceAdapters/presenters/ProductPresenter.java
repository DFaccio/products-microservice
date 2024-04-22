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

        return new ProductDto(
                document.getProductId(),
                document.getName(),
                document.getDescription(),
                document.getProductCategory(),
                document.isAvailable(),
                document.getAvailableQuantity(),
                document.getColor(),
                document.getImageURL(),
                document.getSupplier(),
                document.getBrand(),
                document.getPrice(),
                document.getProductHeight(),
                document.getProductWidth(),
                document.getProductDepth(),
                document.getProductWeight(),
                document.getPackagingHeight(),
                document.getPackagingWidth(),
                document.getPackagingDepth(),
                document.getPackagingWeight()
        );

    }

    public Products convert(ProductDto dto){

        return new Products(
                dto.getId(),
                dto.getName(),
                dto.getDescription(),
                dto.getProductCategory(),
                dto.isAvailable(),
                dto.getAvailableQuantity(),
                dto.getColor(),
                dto.getImageURL(),
                dto.getSupplier(),
                dto.getBrand(),
                dto.getPrice(),
                dto.getProductHeight(),
                dto.getProductWidth(),
                dto.getProductDepth(),
                dto.getProductWeight(),
                dto.getPackagingHeight(),
                dto.getPackagingWidth(),
                dto.getPackagingDepth(),
                dto.getPackagingWeight(),
                discountPresenter.convert(dto.getDiscountDto())
        );

    }

}
