package br.com.productmanagement.usercase;

import br.com.productmanagement.entities.Discount;
import br.com.productmanagement.entities.Product;
import br.com.productmanagement.entities.ProductReservation;
import br.com.productmanagement.interfaceAdapters.helper.ProductHelper;
import br.com.productmanagement.interfaceAdapters.presenters.dto.ProductReservationDto;
import br.com.productmanagement.util.enums.ReservationStatus;
import br.com.productmanagement.util.time.TimeUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ProductReservationBusiness {

    @Resource
    private ProductHelper productHelper;

    public boolean checkAvailableQuantity(int productQuantity, int requestedQuantity){

        boolean available = false;

        if(productQuantity >= requestedQuantity){

            available = true;

        }

        return available;

    }


    public ProductReservation newReservation(Product product, int requestedQuantity){

        Discount discount;

        ProductReservation productReservation = new ProductReservation();

        LocalDateTime dateTime = TimeUtils.now();

        productReservation.setCreationDate(dateTime);

        productReservation.setUpdateDate(dateTime);

        productReservation.setSku(product.getSku());

        productReservation.setName(product.getName());

        productReservation.setRequestedQuantity(requestedQuantity);

        double reservationValue = calculateReservationValue(product, requestedQuantity);

        productReservation.setReservationValue(reservationValue);

        productReservation.setReservationStatus(ReservationStatus.CREATED);

        discount = productHelper.validadeProductDiscount(product);

        if(discount.getId() != null){

            productReservation.setAppliedDiscountValue(productHelper.calculateOrderDiscount(discount, requestedQuantity, reservationValue));

            if(productReservation.getAppliedDiscountValue() > 0.0){

                productReservation.setAppliedDiscountId(discount.getId().toString());

            }else{

                productReservation.setAppliedDiscountId(null);

            }

        }

        return productReservation;

    }

    public ProductReservation updateReservation(ProductReservation productReservation, Product product, int requestedQuantity){

        Discount discount;

        productReservation.setUpdateDate(TimeUtils.now());

        productReservation.setRequestedQuantity(requestedQuantity);

        double reservationValue = calculateReservationValue(product, requestedQuantity);

        productReservation.setReservationValue(reservationValue);

        discount = productHelper.validadeProductDiscount(product);

        if(product.getDiscount() != null){

            productReservation.setAppliedDiscountValue(productHelper.calculateOrderDiscount(discount, requestedQuantity, reservationValue));

            if(productReservation.getAppliedDiscountValue() > 0){

                productReservation.setAppliedDiscountId(discount.getId().toString());

            }else{

                productReservation.setAppliedDiscountId(null);

            }

        }

        return productReservation;

    }

    public double calculateReservationValue(Product product, int requestedQuantity){

        return requestedQuantity * product.getPrice();

    }

    public ProductReservation reservationUnavailable(ProductReservation productReservation, ProductReservationDto productReservationDto){

        productReservation.setSku(productReservation.getSku());
        productReservation.setRequestedQuantity(productReservationDto.getRequestedQuantity());
        productReservation.setReservationStatus(ReservationStatus.UNAVAILABLE);

        return productReservation;

    }

}
