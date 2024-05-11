package br.com.productmanagement.interfaceAdapters.presenters.dto;

import br.com.productmanagement.util.enums.DiscountType;
import br.com.productmanagement.util.enums.ProductCategory;
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
public class DiscountDto extends Dto implements Serializable {

    @Schema(example = "FIAP02")
    private String coupon;

    @NotNull
    @Schema(example = "PERCENTAGE")
    private DiscountType discountType;

    @NotBlank
    @Schema(example = "Adquira 2% de desconto na compra a partir de 3 unidades")
    private String description;

    @NotNull
    @Schema(example = "2.0")
    private double discountValue;

    @NotNull
    @Schema(example = "2024-05-05")
    private LocalDateTime discountStartDate;

    @NotNull
    @Schema(example = "2024-05-25")
    private LocalDateTime discountFinishDate;

    @Schema(example = "3")
    private Integer minimumQuantityToDiscount;

    @Schema(example = "SAUDE")
    private ProductCategory productCategory;

    @NotNull
    @Schema(example = "true")
    private boolean active;

    public DiscountDto(UUID id, String coupon, DiscountType discountType, String description, double discountValue, LocalDateTime discountStartDate, LocalDateTime discountFinishDate, int minimumQuantityToDiscount, ProductCategory productCategory, boolean active) {
        super(id);
        this.coupon = coupon;
        this.discountType = discountType;
        this.description = description;
        this.discountValue = discountValue;
        this.discountStartDate = discountStartDate;
        this.discountFinishDate = discountFinishDate;
        this.minimumQuantityToDiscount = minimumQuantityToDiscount;
        this.productCategory = productCategory;
        this.active = active;
    }

}
