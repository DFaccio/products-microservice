package br.com.productmanagement.interfaceAdapters.presenters.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class ProductOrderDto {

    @NotBlank
    @Schema(example = "SAU-MAXT-CRE-MAX-BRA-000")
    private String sku;

    private String name;

    @NotNull
    @Schema(example = "10")
    private Integer orderQuantity;

    private double orderValue;

    private double apliedDiscount;

    private UUID discountId;

}
