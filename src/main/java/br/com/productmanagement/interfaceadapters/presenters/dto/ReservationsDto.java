package br.com.productmanagement.interfaceadapters.presenters.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class ReservationsDto implements Serializable {

    List<ProductReservationDto> reservations;

}
