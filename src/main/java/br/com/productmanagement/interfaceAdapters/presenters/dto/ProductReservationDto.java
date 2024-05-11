package br.com.productmanagement.interfaceAdapters.presenters.dto;

import br.com.productmanagement.util.enums.ReservationStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
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

    private UUID discountId;

    private ReservationStatus reservationStatus;

}
