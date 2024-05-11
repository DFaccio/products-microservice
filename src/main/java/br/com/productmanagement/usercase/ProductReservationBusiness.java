package br.com.productmanagement.usercase;

import br.com.productmanagement.entities.Product;
import br.com.productmanagement.entities.ProductReservation;
import br.com.productmanagement.interfaceAdapters.helper.ProductHelper;
import br.com.productmanagement.interfaceAdapters.presenters.dto.ProductReservationDto;
import br.com.productmanagement.util.enums.ReservationStatus;
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

        ProductReservation productReservation = new ProductReservation();

        LocalDateTime dateTime = LocalDateTime.now();

        productReservation.setCreationDate(dateTime);

        productReservation.setUpdateDate(dateTime);

        productReservation.setSku(product.getSku());

        productReservation.setName(product.getName());

        productReservation.setRequestedQuantity(requestedQuantity);

        double reservationValue = calculateReservationValue(product, requestedQuantity);

        productReservation.setReservationValue(reservationValue);

        productReservation.setReservationStatus(ReservationStatus.CREATED);

        product.setDiscount(productHelper.validadeProductDiscount(product));

        if(product.getDiscount() != null){

            productReservation.setDiscountId(product.getDiscount().getDiscountId());

            productReservation.setAppliedDiscount(productHelper.calculateOrderDiscount(product, requestedQuantity, reservationValue));

        }

        return productReservation;

    }

    public ProductReservation updateReservation(ProductReservation productReservation, Product product, int requestedQuantity){

        productReservation.setUpdateDate(LocalDateTime.now());

        productReservation.setRequestedQuantity(requestedQuantity);

        double reservationValue = calculateReservationValue(product, requestedQuantity);

        productReservation.setReservationValue(reservationValue);

        product.setDiscount(productHelper.validadeProductDiscount(product));

        if(product.getDiscount() != null){

            productReservation.setAppliedDiscount(productHelper.calculateOrderDiscount(product, requestedQuantity, reservationValue));

            if(productReservation.getAppliedDiscount() > 0){

                productReservation.setDiscountId(product.getDiscount().getDiscountId());

            }else{

                productReservation.setDiscountId(null);

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