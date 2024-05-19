package br.com.productmanagement.entities;

import br.com.productmanagement.util.enums.ProductCategory;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name="Product")
@AllArgsConstructor
@NoArgsConstructor
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column
    private UUID productId;

    @Column
    private String sku;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    @Enumerated(value = EnumType.STRING)
    private ProductCategory productCategory;

    @Column
    private String model;

    @Column
    private boolean available;

    @Column
    private Integer availableQuantity;

    @Column
    private String color;

    @Column
    private String size;

    @Column
    private String imageURL;

    @Column
    private String supplier;

    @Column
    private String brand;

    @Column
    private double price;

    @Column
    private double productHeight;

    @Column
    private double productWidth;

    @Column
    private double productDepth;

    @Column
    private double productWeight;

    @Column
    private double packagingHeight;

    @Column
    private double packagingWidth;

    @Column
    private double packagingDepth;

    @Column
    private double packagingWeight;

    @Column
    private LocalDateTime creationDate;

    @Nullable
    @ManyToOne
    @JoinColumn(name = "discount_id")
    private Discount discount;

}
