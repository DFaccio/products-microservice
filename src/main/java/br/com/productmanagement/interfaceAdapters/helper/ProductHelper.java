package br.com.productmanagement.interfaceAdapters.helper;

import br.com.productmanagement.entities.Discount;
import br.com.productmanagement.entities.Product;
import br.com.productmanagement.interfaceAdapters.gateways.DiscountGateway;
import br.com.productmanagement.util.enums.DiscountType;
import br.com.productmanagement.util.enums.Operation;
import br.com.productmanagement.util.enums.ProductCategory;
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

        if(discount.getDiscountId() == null){

//            buscar apenas por descontos ativos!!!!!

            Optional<Discount> optional= discountGateway.findByProductCategory(product.getProductCategory());

            discount = optional.get();

            if(discount.getDiscountId() != null){

                discount = isDiscountActive(discount);

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
