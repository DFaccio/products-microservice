package br.com.productmanagement.helper;

import br.com.productmanagement.entities.ProductReservation;
import br.com.productmanagement.usercase.ProductReservationBusiness;
import br.com.productmanagement.util.enums.ReservationStatus;
import br.com.productmanagement.util.time.TimeUtils;
import br.com.productmanagement.utils.Constants;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.util.Objects;

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

}
