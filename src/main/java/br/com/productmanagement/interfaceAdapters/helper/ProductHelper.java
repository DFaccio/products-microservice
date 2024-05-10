package br.com.productmanagement.interfaceAdapters.helper;

import br.com.productmanagement.entities.Discount;
import br.com.productmanagement.entities.Product;
import br.com.productmanagement.interfaceAdapters.gateways.DiscountGateway;
import br.com.productmanagement.interfaceAdapters.presenters.dto.ProductDto;
import br.com.productmanagement.util.enums.DiscountType;
import br.com.productmanagement.util.enums.Operation;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ProductHelper {

    @Resource
    private DiscountGateway discountGateway;

    Discount discount = new Discount();

    public Discount validadeProductDiscount(Product product) {

        if (product.getDiscount().getDiscountId() != null) {

            discount = isDiscountActive(product.getDiscount());

        }

        if(discount == null){

            String category = product.getProductCategory().toString();

            discount = discountGateway.findByProductCategory(category);

            if(discount != null){

                discount = isDiscountActive(product.getDiscount());

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


        if(!today.isAfter(discount.getDiscountStartDate()) && !today.isBefore(discount.getDiscountFinishDate())){

            return null;

        }

        return  discount;

    }

    public double calculateOrderDiscount(Product product, int orderQuantity, double orderValue){

        double discount = 0.0;

        DiscountType discountType = product.getDiscount().getDiscountType();

        int minimumQuantityToDiscount = product.getDiscount().getMinimumQuantityToDiscount();

        double discountValue = product.getDiscount().getDiscountValue();

        if(minimumQuantityToDiscount <= orderQuantity){

            if(discountType == DiscountType.PERCENTAGE){

                discount = orderValue * (discountValue/100);

            }else{

                discount = discountValue;

            }

        }

        return discount;

    }

    public Product updateProductKeeping(Product product, int productQuantity, int updateQuantity, Operation operation){

        if(operation == Operation.SALE){

            product.setAvailableQuantity(productQuantity - updateQuantity);

        }

        if(operation == Operation.ARRIVAL || operation == Operation.ORDER_CANCELLATION){

            product.setAvailableQuantity(productQuantity + updateQuantity);

        }

        return product;

    }

}
