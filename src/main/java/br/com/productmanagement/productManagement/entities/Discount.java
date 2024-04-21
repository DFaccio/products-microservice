package br.com.productmanagement.productManagement.entities;

import br.com.productmanagement.productManagement.util.enums.DiscountType;
import br.com.productmanagement.productManagement.util.enums.ProductCategory;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name="Discount")
@Data
public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column
    private UUID discountId;

    @Column
    private String coupon;

    @Column
    private DiscountType discountType;

    @Column
    private String description;

    @Column
    private double discountValue;

    @Column
    private LocalDate discountStartDate;

    @Column
    private LocalDate discountFinishDate;

    @Column
    private int minimumQuantityToDiscount;

    @Column
    private ProductCategory productCategory;

    @Column
    private boolean active;

}
