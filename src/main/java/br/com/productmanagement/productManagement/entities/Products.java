package br.com.productmanagement.productManagement.entities;

import br.com.productmanagement.productManagement.util.enums.ProductCategory;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name="Products")
@Data
public class Products {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column
    private UUID productId;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private ProductCategory productCategory;

    @Column
    private boolean available;

    @Column
    private Integer availableQuantity;

    @Column
    private String color;

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

    @OneToOne
    @JoinColumn(name = "discount_Id")
    private Discount discount;

}
