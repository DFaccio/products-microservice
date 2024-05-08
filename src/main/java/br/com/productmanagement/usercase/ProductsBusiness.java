package br.com.productmanagement.usercase;

import br.com.productmanagement.entities.Products;
import br.com.productmanagement.interfaceAdapters.helper.ProductDiscountHelper;
import br.com.productmanagement.interfaceAdapters.presenters.dto.ProductDto;
import br.com.productmanagement.interfaceAdapters.presenters.dto.ProductOrderDto;
import br.com.productmanagement.util.enums.UpdateType;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class ProductsBusiness {


    @Resource
    private ProductDiscountHelper productDiscountHelper;

    public Products updateSku(Products products, Products productUpd){

        Products actualProduct = productUpd;

        if(products.getAvailableQuantity() > 0 ){

//            int availableQuantity = products.getAvailableQuantity() + productUpd.getAvailableQuantity();
//
//            actualProduct.setAvailableQuantity(availableQuantity);
            actualProduct = updateProductKeeping(products, products.getAvailableQuantity(), productUpd.getAvailableQuantity(), UpdateType.SELL);

        }

        if(products.getDiscount().getDiscountId() != null){

            actualProduct.setDiscount(products.getDiscount());

        }

        if(products.getPrice() != productUpd.getPrice()){

            actualProduct.setPrice(products.getPrice());

        }

        actualProduct.setAvailable(products.isAvailable());

        return actualProduct;

    }

    public ProductOrderDto returnProductOrderValues(ProductDto productDto, int orderQuantity){

        ProductOrderDto productOrderDto = new ProductOrderDto();

        productOrderDto.setSku(productDto.getSku());

        productOrderDto.setName(productDto.getName());

        productOrderDto.setOrderQuantity(orderQuantity);

        double orderValue = orderQuantity * productDto.getPrice();

        productOrderDto.setOrderValue(orderValue);

        double discount = 0.0;

        if(productDto.getDiscount() != null){

            discount = productDiscountHelper.calculateOrderDiscount(productDto, orderQuantity, orderValue);

        }

        productOrderDto.setApliedDiscount(discount);

        productOrderDto.setDiscountId(productDto.getDiscount().getId());

        return productOrderDto;

    }


    public boolean checkAvailableQuantity(int productQuantity, int orderQuantity){

        boolean available = false;

        if(productQuantity >= orderQuantity){
            available = true;
        }

        return available;

    }
    public Products updateProductKeeping(Products products, int productQuantity, int updateQuantity, UpdateType updateType){

        if(updateType == UpdateType.SELL){

            products.setAvailableQuantity(productQuantity - updateQuantity);

        }

        if(updateType == UpdateType.ARRIVAL || updateType == UpdateType.ORDER_CANCELLATION){

            products.setAvailableQuantity(productQuantity + updateQuantity);

        }

        return products;

    }

}
