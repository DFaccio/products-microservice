package br.com.productmanagement.entities;

import br.com.productmanagement.util.enums.DiscountType;
import br.com.productmanagement.util.enums.ProductCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name="Discount")
@NoArgsConstructor
@AllArgsConstructor
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

}
