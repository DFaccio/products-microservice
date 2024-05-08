package br.com.productmanagement.interfaceAdapters.helper;

import br.com.productmanagement.entities.Discount;
import br.com.productmanagement.entities.Products;
import br.com.productmanagement.interfaceAdapters.gateways.DiscountGateway;
import br.com.productmanagement.interfaceAdapters.presenters.dto.ProductDto;
import br.com.productmanagement.util.enums.DiscountType;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ProductDiscountHelper {

    @Resource
    private DiscountGateway discountGateway;

    Discount discount = new Discount();

    public Discount validadeProductDiscount(Products products) {

        if (products.getDiscount().getDiscountId() != null) {

            discount = isDiscountActive(products.getDiscount());

        }

        if(discount == null){

            String category = products.getProductCategory().toString();

            discount = discountGateway.findByProductCategory(category);

            if(discount != null){

                discount = isDiscountActive(products.getDiscount());

            }

        }

        return discount;

    }


    private Discount isDiscountActive(Discount discount) {

        if(discount.isActive()){

            discount = validateDiscountAvailability(discount);

        }else{

            return null;

        }

        return  discount;

    }

    private Discount validateDiscountAvailability(Discount discount) {

        LocalDateTime today = LocalDateTime.now();


        if(!today.isAfter(discount.getDiscountStartDate()) && today.isBefore(discount.getDiscountFinishDate())){

            return null;

        }

        return  discount;

    }

    public double calculateOrderDiscount(ProductDto productDto, int orderQuantity, double orderValue){

        double discount = 0.0;

        DiscountType discountType = productDto.getDiscount().getDiscountType();

        int minimumQuantityToDiscount = productDto.getDiscount().getMinimumQuantityToDiscount();

        double discountValue = productDto.getDiscount().getDiscountValue();

        if(minimumQuantityToDiscount <= orderQuantity){

            if(discountType == DiscountType.PERCENTAGE){

                discount = orderValue * (discountValue/100);

            }else{

                discount = discountValue;

            }

        }

        return discount;

    }

}
