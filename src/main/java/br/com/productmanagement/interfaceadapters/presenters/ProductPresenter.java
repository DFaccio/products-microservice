package br.com.productmanagement.interfaceadapters.presenters;

import br.com.productmanagement.entities.Product;
import br.com.productmanagement.interfaceadapters.presenters.dto.ProductDto;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class ProductPresenter implements Presenter<Product, ProductDto>{

    @Resource
    private DiscountPresenter discountPresenter;

    @Override
    public ProductDto convert(Product document){

        ProductDto productDto = new ProductDto();

        productDto.setId(document.getProductId());
        productDto.setName(document.getName());
        productDto.setSku(document.getSku());
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
        productDto.setCreationDate(document.getCreationDate());
        if(document.getDiscount() != null) {
            productDto.setDiscount(discountPresenter.convert(document.getDiscount()));
        }

        return productDto;

    }

    public Product convert(ProductDto dto){

        Product product = new Product();

        product.setProductId(dto.getId());
        product.setName(dto.getName());
        product.setSku(dto.getSku());
        product.setDescription(dto.getDescription());
        product.setProductCategory(dto.getProductCategory());
        product.setModel(dto.getModel());
        product.setAvailable(dto.isAvailable());
        product.setAvailableQuantity(dto.getAvailableQuantity());
        product.setColor(dto.getColor());
        product.setSize(dto.getSize());
        product.setImageURL(dto.getImageURL());
        product.setSupplier(dto.getSupplier());
        product.setBrand(dto.getBrand());
        product.setPrice(dto.getPrice());
        product.setProductHeight(dto.getProductHeight());
        product.setProductWidth(dto.getProductWidth());
        product.setProductDepth(dto.getProductDepth());
        product.setProductWeight(dto.getProductWeight());
        product.setPackagingHeight(dto.getPackagingHeight());
        product.setPackagingWidth(dto.getPackagingWidth());
        product.setPackagingDepth(dto.getPackagingDepth());
        product.setProductWeight(dto.getPackagingWeight());
        if(dto.getDiscount() != null) {
            product.setDiscount(discountPresenter.convert(dto.getDiscount()));
        }

        return product;

    }

}
