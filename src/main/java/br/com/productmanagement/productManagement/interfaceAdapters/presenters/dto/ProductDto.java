package br.com.productmanagement.productManagement.interfaceAdapters.presenters.dto;

import br.com.productmanagement.productManagement.util.enums.ProductCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotEmpty;
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

    @NotEmpty
    @Schema(example = "Creatina Max")
    private String name;

    @NotEmpty
    @Schema(example = "Creatina Max Titanium")
    private String description;

    @NotEmpty
    @Schema(example = "SAUDE")
    private ProductCategory productCategory;

    @NotEmpty
    @Schema(example = "true")
    private boolean available;

    @NotEmpty
    @Schema(example = "10")
    private Integer availableQuantity;

    @NotEmpty
    @Schema(example = "Branco")
    private String color;

    @NotEmpty
    @Schema(example = "https://http2.mlstatic.com/D_NQ_NP_941774-MLA52624902588_112022-O.webp")
    private String imageURL;

    @NotEmpty
    @Schema(example = "Max Titanium")
    private String supplier;

    @NotEmpty
    @Schema(example = "Max Titanium")
    private String brand;

    @NotEmpty
    @Schema(example = "30,00")
    private double price;

    @NotEmpty
    @Schema(example = "12")
    private double productHeight;

    @NotEmpty
    @Schema(example = "13")
    private double productWidth;

    @NotEmpty
    @Schema(example = "13")
    private double productDepth;

    @NotEmpty
    @Schema(example = "300")
    private double productWeight;

    @NotEmpty
    @Schema(example = "15")
    private double packagingHeight;

    @NotEmpty
    @Schema(example = "15")
    private double packagingWidth;

    @NotEmpty
    @Schema(example = "15")
    private double packagingDepth;

    @NotEmpty
    @Schema(example = "350")
    private double packagingWeight;

    @Schema(example = "123e4567-e89b-12d3-a456-426655440000")
    private UUID discount;

}