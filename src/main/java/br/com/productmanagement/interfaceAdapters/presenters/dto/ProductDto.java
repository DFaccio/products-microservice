package br.com.productmanagement.interfaceAdapters.presenters.dto;

import br.com.productmanagement.util.enums.ProductCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class ProductDto extends Dto implements Serializable {

    @NotBlank
    @Schema(example = "Creatina Max")
    private String name;

    @NotBlank
    @Schema(example = "Creatina Max Titanium")
    private String description;

    @NotNull
    @Schema(example = "SAUDE")
    private ProductCategory productCategory;

    @NotNull
    @Schema(example = "true")
    private boolean available;

    @NotBlank
    @Schema(example = "10")
    private Integer availableQuantity;

    @NotBlank
    @Schema(example = "Branco")
    private String color;

    @NotBlank
    @Schema(example = "https://http2.mlstatic.com/D_NQ_NP_941774-MLA52624902588_112022-O.webp")
    private String imageURL;

    @NotBlank
    @Schema(example = "Max Titanium")
    private String supplier;

    @NotBlank
    @Schema(example = "Max Titanium")
    private String brand;

    @NotBlank
    @Schema(example = "30,00")
    private double price;

    @NotBlank
    @Schema(example = "12")
    private double productHeight;

    @NotBlank
    @Schema(example = "13")
    private double productWidth;

    @NotBlank
    @Schema(example = "13")
    private double productDepth;

    @NotBlank
    @Schema(example = "300")
    private double productWeight;

    @NotBlank
    @Schema(example = "15")
    private double packagingHeight;

    @NotBlank
    @Schema(example = "15")
    private double packagingWidth;

    @NotBlank
    @Schema(example = "15")
    private double packagingDepth;

    @NotBlank
    @Schema(example = "350")
    private double packagingWeight;

    private DiscountDto discountDto;

    public ProductDto(UUID productId, String name, String description, ProductCategory productCategory, boolean available, Integer availableQuantity, String color, String imageURL, String supplier, String brand, double price, double productHeight, double productWidth, double productDepth, double productWeight, double packagingHeight, double packagingWidth, double packagingDepth, double packagingWeight) {
    }

}