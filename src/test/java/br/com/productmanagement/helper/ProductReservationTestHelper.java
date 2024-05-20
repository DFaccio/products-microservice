package br.com.productmanagement.helper;

import br.com.productmanagement.entities.ProductReservation;
import br.com.productmanagement.interfaceadapters.presenters.dto.ProductReservationDto;
import br.com.productmanagement.interfaceadapters.presenters.dto.ReservationsDto;
import br.com.productmanagement.util.enums.ReservationStatus;
import br.com.productmanagement.util.time.TimeUtils;
import br.com.productmanagement.utils.Constants;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class ProductReservationTestHelper {

    public ProductReservation newReservation(){

        ProductReservation productReservation = new ProductReservation();

        productReservation.setReservationId(Constants.RESERVATION_ID);
        productReservation.setCreationDate(TimeUtils.now());
        productReservation.setUpdateDate(TimeUtils.now());
        productReservation.setSku("SAU-MAXT-CRE-CREA-SEMS-250");
        productReservation.setName("Creatina Creapure Pote 250g Max Titanium Sabor Sem sabor");
        productReservation.setRequestedQuantity(1);
        productReservation.setReservationValue(29.90);
        productReservation.setReservationStatus(ReservationStatus.CREATED);

        return productReservation;

    }

    public ReservationsDto newReservationDto(){

        ReservationsDto reservationsDto = new ReservationsDto();
        List<ProductReservationDto> productReservationsDto = new ArrayList<>();
        ProductReservationDto productReservationDto = new ProductReservationDto();

        productReservationDto.setSku("SAU-MAXT-CRE-CREA-SEMS-250");
        productReservationDto.setRequestedQuantity(2);

        productReservationsDto.add(productReservationDto);
        reservationsDto.setReservations(productReservationsDto);

        return reservationsDto;

    }

    public ReservationsDto updateReservationDto(UUID uuid){

        ReservationsDto reservationsDto = new ReservationsDto();
        List<ProductReservationDto> productReservationsDto = new ArrayList<>();
        ProductReservationDto productReservationDto = new ProductReservationDto();

        productReservationDto.setId(uuid);
        productReservationDto.setRequestedQuantity(2);

        productReservationsDto.add(productReservationDto);
        reservationsDto.setReservations(productReservationsDto);

        return reservationsDto;

    }

    public ReservationsDto reservationConfirmCancelDto(UUID uuid){

        ReservationsDto reservationsDto = new ReservationsDto();
        List<ProductReservationDto> productReservationsDto = new ArrayList<>();
        ProductReservationDto productReservationDto = new ProductReservationDto();

        productReservationDto.setId(uuid);

        productReservationsDto.add(productReservationDto);
        reservationsDto.setReservations(productReservationsDto);

        return reservationsDto;

    }

}
