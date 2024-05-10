package br.com.productmanagement.usercase;

import br.com.productmanagement.entities.Product;
import br.com.productmanagement.interfaceAdapters.helper.ProductHelper;
import br.com.productmanagement.interfaceAdapters.presenters.dto.ProductDto;
import br.com.productmanagement.interfaceAdapters.presenters.dto.ProductReservationDto;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class ProductReservationBusiness {

    @Resource
    private ProductHelper productHelper;

    public boolean checkAvailableQuantity(int productQuantity, int orderQuantity){

        boolean available = false;

        if(productQuantity >= orderQuantity){

            available = true;

        }

        return available;

    }


    public ProductReservationDto returnProductOrderValues(Product product, int orderQuantity){

        ProductReservationDto productReservationDto = new ProductReservationDto();

        productReservationDto.setSku(product.getSku());

        productReservationDto.setName(product.getName());

        productReservationDto.setRequestedQuantity(orderQuantity);

        double orderValue = orderQuantity * product.getPrice();

        productReservationDto.setOrderValue(orderValue);

        double discount = 0.0;

        if(product.getDiscount() != null){

            discount = productHelper.calculateOrderDiscount(product, orderQuantity, orderValue);

        }

        productReservationDto.setApliedDiscount(discount);

        productReservationDto.setDiscountId(product.getDiscount().getDiscountId());

        return productReservationDto;

    }



}
