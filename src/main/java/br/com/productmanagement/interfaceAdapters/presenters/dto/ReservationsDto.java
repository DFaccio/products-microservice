package br.com.productmanagement.interfaceAdapters.presenters.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties({"id"})
public class ReservationsDto extends Dto implements Serializable {

    List<ProductReservationDto> productReservationDto;

}
