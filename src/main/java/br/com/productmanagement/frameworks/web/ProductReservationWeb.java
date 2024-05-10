package br.com.productmanagement.frameworks.web;

import br.com.productmanagement.interfaceAdapters.controller.ProductReservationController;
import br.com.productmanagement.interfaceAdapters.presenters.dto.ProductReservationDto;
import br.com.productmanagement.interfaceAdapters.presenters.dto.ReservationsDto;
import br.com.productmanagement.util.exception.ValidationsException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/reservation")
@Tag(name = "Reserva", description = "Exibe os métodos para controle de reservas de produtos")
public class ProductReservationWeb {

    @Resource
    private ProductReservationController productReservationController;


    @Operation(summary = "Atualiza o estoque de produtos a partir de uma nova ordem de compra")
    @PostMapping(value = "/create")
    public ResponseEntity<ReservationsDto> newReservation(@Valid @RequestBody ReservationsDto reservationsDto) throws ValidationsException {

        return ResponseEntity.ok(productReservationController.newReservation(reservationsDto));

    }

    @Operation(summary = "Atualiza o estoque de produtos a partir de uma nova ordem de compra")
    @PutMapping(value = "/update")
    public ResponseEntity<ReservationsDto> reservationUpdate(@Valid @RequestBody ReservationsDto reservationsDto) throws ValidationsException {

        return ResponseEntity.ok(productReservationController.reservationUpdate(reservationsDto));

    }

    @Operation(summary = "Atualiza o estoque de produtos a partir de uma nova ordem de compra")
    @PutMapping(value = "/confirmation")
    public ResponseEntity<ReservationsDto> reservationConfirmation(@Valid @RequestBody ReservationsDto reservationsDto) throws ValidationsException {

        return ResponseEntity.ok(productReservationController.reservationConfirmation(reservationsDto));

    }

    @Operation(summary = "Atualiza o estoque de produtos a partir de uma ordem de compra cancelada")
    @PutMapping(value = "/cancellation")
    public void reservationCancellation(@Valid @RequestBody ReservationsDto reservationsDto) throws ValidationsException {

        productReservationController.reservationCancellation(reservationsDto);

    }

}
