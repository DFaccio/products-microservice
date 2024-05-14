package br.com.productmanagement.interfaceAdapters.helper;

import br.com.productmanagement.entities.Discount;
import br.com.productmanagement.entities.Product;
import br.com.productmanagement.interfaceAdapters.gateways.DiscountGateway;
import br.com.productmanagement.util.enums.DiscountType;
import br.com.productmanagement.util.enums.Operation;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class ProductHelper {

    @Resource
    private DiscountGateway discountGateway;

    public Discount validadeProductDiscount(Product product) {

        Discount discount = new Discount();

        if (product.getDiscount() != null) {

            discount = isDiscountActive(product.getDiscount());

        }

        if(discount.getId() == null){

            Optional<Discount> optional= discountGateway.findByProductCategory(product.getProductCategory());

            if(optional.isPresent()){

                discount = optional.get();

                if(discount.getId() != null){

                    discount = isDiscountActive(discount);

                }

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

    public double calculateOrderDiscount(Discount discount, int orderQuantity, double orderValue){

        double appliableDiscount = 0.0;

        DiscountType discountType = discount.getDiscountType();

        int minimumQuantityToDiscount = discount.getMinimumQuantityToDiscount();

        double discountValue = discount.getDiscountValue();

        if(minimumQuantityToDiscount <= orderQuantity){

            if(discountType == DiscountType.PERCENTAGE){

                appliableDiscount = orderValue * (discountValue/100);

            }else{

                appliableDiscount = discountValue;

            }

        }

        return appliableDiscount;

    }

    public Product updateProductKeeping(Product product, int productQuantity, int updateQuantity, Operation operation){

        if(operation == Operation.RESERVATION){

            Integer updQuantity = productQuantity - updateQuantity;

            product.setAvailableQuantity(updQuantity);

            if(updQuantity == 0){

                product.setAvailable(false);

            }

        }

        if(operation == Operation.ARRIVAL || operation == Operation.RESERVATION_CANCELLATION || operation == Operation.RESERVATION_GIVEUP){

            product.setAvailableQuantity(productQuantity + updateQuantity);

            if(!product.isAvailable()){

                product.setAvailable(true);

            }

        }

        return product;

    }

}
