package br.com.productmanagement.interfaceadapters.presenters.dto;

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
public class ProductDto extends Dto implements Serializable {

    @Schema(example = "SAU-MAXT-CRE-MAX-BRA-300")
    private String sku;

    @NotBlank
    @Schema(example = "Creatina")
    private String name;

    @NotBlank
    @Schema(example = "Creatina Max Titanium, mais energia para o seu treino")
    private String description;

    @NotNull
    @Schema(example = "SAUDE")
    private ProductCategory productCategory;

    @NotBlank
    @Schema(example = "MAX")
    private String model;

    @NotNull
    @Schema(example = "true")
    private boolean available;

    @NotNull
    @Schema(example = "10")
    private Integer availableQuantity;

    @NotBlank
    @Schema(example = "Branco")
    private String color;

    @NotBlank
    @Schema(example = "300")
    private String size;

    @NotBlank
    @Schema(example = "https://http2.mlstatic.com/D_NQ_NP_941774-MLA52624902588_112022-O.webp")
    private String imageURL;

    @NotBlank
    @Schema(example = "Max Titanium")
    private String supplier;

    @NotBlank
    @Schema(example = "Max Titanium")
    private String brand;

    @NotNull
    @Schema(example = "30.00")
    private double price;

    @NotNull
    @Schema(example = "12.3")
    private double productHeight;

    @NotNull
    @Schema(example = "13.5")
    private double productWidth;

    @NotNull
    @Schema(example = "13.5")
    private double productDepth;

    @NotNull
    @Schema(example = "300.0")
    private double productWeight;

    @NotNull
    @Schema(example = "15.1")
    private double packagingHeight;

    @NotNull
    @Schema(example = "15.1")
    private double packagingWidth;

    @NotNull
    @Schema(example = "15.1")
    private double packagingDepth;

    @NotNull
    @Schema(example = "350.7")
    private double packagingWeight;

    private LocalDateTime creationDate;

    private DiscountDto discount;

    public ProductDto(UUID id, String name, String description, ProductCategory productCategory, String model, boolean available, Integer availableQuantity, String color, String size, String imageURL, String supplier, String brand, double price, double productHeight, double productWidth, double productDepth, double productWeight, double packagingHeight, double packagingWidth, double packagingDepth, double packagingWeight, String sku, DiscountDto discount) {
        super(id);
        this.name = name;
        this.description = description;
        this.productCategory = productCategory;
        this.model = model;
        this.available = available;
        this.availableQuantity = availableQuantity;
        this.color = color;
        this.size = size;
        this.imageURL = imageURL;
        this.supplier = supplier;
        this.brand = brand;
        this.price = price;
        this.productHeight = productHeight;
        this.productWidth = productWidth;
        this.productDepth = productDepth;
        this.productWeight = productWeight;
        this.packagingHeight = packagingHeight;
        this.packagingWidth = packagingWidth;
        this.packagingDepth = packagingDepth;
        this.packagingWeight = packagingWeight;
        this.sku = sku;
        this.discount = discount;
    }

}