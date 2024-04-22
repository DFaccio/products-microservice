package br.com.productmanagement.entities;

import br.com.productmanagement.util.enums.ProductCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@Entity
@Table(name="Products")
@AllArgsConstructor
@NoArgsConstructor
public class Products implements Serializable {

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
    @JoinColumn(name = "discount_id")
    private Discount discount;

}
