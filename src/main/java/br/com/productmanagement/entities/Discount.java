package br.com.productmanagement.entities;

import br.com.productmanagement.util.enums.DiscountType;
import br.com.productmanagement.util.enums.ProductCategory;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name="Discount")
public class Discount implements Serializable {

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
    private LocalDateTime discountStartDate;

    @Column
    private LocalDateTime discountFinishDate;

    @Column
    private int minimumQuantityToDiscount;

    @Column
    private ProductCategory productCategory;

    @Column
    private boolean active;

    public Discount(String coupon, DiscountType discountType, String description, double discountValue, LocalDate discountStartDate, LocalDate discountFinishDate, int minimumQuantityToDiscount, ProductCategory productCategory, boolean active) {
    }
}
