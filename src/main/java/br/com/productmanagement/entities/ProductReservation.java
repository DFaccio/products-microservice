package br.com.productmanagement.entities;

import br.com.productmanagement.util.enums.ReservationStatus;
import jakarta.annotation.Nullable;
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
@Table(name="ProductReservation")
@NoArgsConstructor
@AllArgsConstructor
public class ProductReservation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column
    private UUID reservationId;

    @Column
    private LocalDateTime creationDate;

    @Column
    private LocalDateTime updateDate;

    @Column
    private String sku;

    @Column
    private String name;

    @Column
    private Integer requestedQuantity;

    @Column
    private double reservationValue;

    @Column
    private double appliedDiscountValue;

    @Column
    @Nullable
    private String appliedDiscountId;

    @Column
    @Enumerated(value = EnumType.STRING)
    private ReservationStatus reservationStatus;

}
