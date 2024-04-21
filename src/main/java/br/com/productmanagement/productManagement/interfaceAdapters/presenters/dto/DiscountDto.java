package br.com.productmanagement.productManagement.interfaceAdapters.presenters.dto;

import br.com.productmanagement.productManagement.util.enums.DiscountType;
import br.com.productmanagement.productManagement.util.enums.ProductCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class DiscountDto extends Dto implements Serializable {

    @Schema(example = "FIAP02")
    private String coupon;

    @NotEmpty
    @Schema(example = "PERCENTAGE")
    private DiscountType discountType;

    @NotEmpty
    @Schema(example = "Adquira 2% de desconto na compra a partir de 3 unidades")
    private String description;

    @Schema(example = "2")
    private double discountValue;

    @Schema(example = "2024-05-05")
    private LocalDate discountStartDate;

    @Schema(example = "2024-05-25")
    private LocalDate discountFinishDate;

    @Schema(example = "3")
    private int minimumQuantityToDiscount;

    @Schema(example = "SAUDE")
    private ProductCategory productCategory;

    @NotEmpty
    @Schema(example = "true")
    private boolean active;

}
