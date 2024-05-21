package br.com.productmanagement.interfaceadapters.presenters.dto;

import br.com.productmanagement.util.enums.ReservationStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductReservationDto extends Dto implements Serializable {

    private LocalDateTime creationDate;

    private LocalDateTime updateDate;

    @Schema(example = "SAU-MAXT-CRE-MAX-BRA-000")
    private String sku;

    private Integer productDisponibility;

    private String name;

    private double productValue;

    @Schema(example = "10")
    private Integer requestedQuantity;

    private double reservationValue;

    private double appliedDiscount;

    private String discountId;

    private ReservationStatus reservationStatus;

}
