package br.com.productmanagement.interfaceadapters.presenters;

import br.com.productmanagement.entities.ProductReservation;
import br.com.productmanagement.interfaceadapters.presenters.dto.ProductReservationDto;
import org.springframework.stereotype.Component;

@Component
public class ProductReservationPresenter implements Presenter<ProductReservation, ProductReservationDto>{

    public ProductReservationDto convert(ProductReservation document){

        ProductReservationDto productReservationDto = new ProductReservationDto();

        productReservationDto.setId(document.getReservationId());
        productReservationDto.setCreationDate(document.getCreationDate());
        productReservationDto.setUpdateDate(document.getUpdateDate());
        productReservationDto.setSku(document.getSku());
        productReservationDto.setRequestedQuantity(document.getRequestedQuantity());
        productReservationDto.setName(document.getName());
        productReservationDto.setReservationValue(document.getReservationValue());
        productReservationDto.setAppliedDiscount(document.getAppliedDiscountValue());
        productReservationDto.setDiscountId(document.getAppliedDiscountId());
        productReservationDto.setReservationStatus(document.getReservationStatus());

        return productReservationDto;

    }

    public ProductReservation convert(ProductReservationDto dto){

        ProductReservation productReservation = new ProductReservation();

        productReservation.setReservationId(dto.getId());
        productReservation.setCreationDate(dto.getCreationDate());
        productReservation.setUpdateDate(dto.getUpdateDate());
        productReservation.setSku(dto.getSku());
        productReservation.setName(dto.getName());
        productReservation.setRequestedQuantity(dto.getRequestedQuantity());
        productReservation.setReservationValue(dto.getReservationValue());
        productReservation.setAppliedDiscountValue(dto.getAppliedDiscount());
        productReservation.setAppliedDiscountId(dto.getDiscountId());
        productReservation.setReservationStatus(dto.getReservationStatus());


        return productReservation;

    }

}
