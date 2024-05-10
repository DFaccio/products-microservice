package br.com.productmanagement.entities;

import br.com.productmanagement.util.enums.ReservationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
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
    private String sku;

    @Column
    private String name;

    @Column
    private Integer requestedQuantity;

    @Column
    private double orderValue;

    @Column
    private double apliedDiscount;

    @Column
    private UUID discountId;

    @Column
    @Enumerated(value = EnumType.STRING)
    private ReservationStatus reservationStatus;

}
