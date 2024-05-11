package br.com.productmanagement.frameworks.web;

import br.com.productmanagement.interfaceAdapters.controller.ProductReservationController;
import br.com.productmanagement.interfaceAdapters.presenters.dto.ProductReservationDto;
import br.com.productmanagement.interfaceAdapters.presenters.dto.ReservationsDto;
import br.com.productmanagement.util.enums.ReservationStatus;
import br.com.productmanagement.util.exception.ValidationsException;
import br.com.productmanagement.util.pagination.PagedResponse;
import br.com.productmanagement.util.pagination.Pagination;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/reservation")
@Tag(name = "Reserva", description = "Exibe os métodos para cadastro, consulta e atualização de reservas de produtos")
public class ProductReservationWeb {

    @Resource
    private ProductReservationController productReservationController;


    @Operation(summary = "Atualiza o estoque de produtos a partir de uma ou mais reservas novas")
    @PostMapping(value = "/create")
    public ResponseEntity<ReservationsDto> newReservation(@Valid @RequestBody ReservationsDto reservationsDto) throws ValidationsException {

        return ResponseEntity.ok(productReservationController.newReservation(reservationsDto));

    }


    @Operation(summary = "Consulta uma reserva por id")
    @GetMapping(value = "/id/{id}")
    public ResponseEntity<ProductReservationDto> findById(@PathVariable UUID id) throws ValidationsException {

        return ResponseEntity.ok(productReservationController.findById(id));

    }

    @Operation(summary = "Consulta todas as reservas, podendo filtrar por status")
    @GetMapping(value = "/list")
    public ResponseEntity<PagedResponse<ProductReservationDto>> findAll(@Parameter(description = "Default value 10. Max value 1000", example = "10")
                                                             @RequestParam(required = false) Integer pageSize,
                                                             @Parameter(description = "Default value 0", example = "0")
                                                             @RequestParam(required = false) Integer initialPage,
                                                             @Parameter(description = "Status da reserva", example = "CONFIRMED")
                                                             @RequestParam(required = false)ReservationStatus reservationStatus) throws ValidationsException {

        Pagination page = new Pagination(initialPage, pageSize);
        return ResponseEntity.ok(productReservationController.findAll(page, reservationStatus));

    }


    @Operation(summary = "Atualiza uma reserva ou mais reservas e o estoque de produtos a partir de uma mudança em uma ou mais reservas")
    @PutMapping(value = "/update")
    public ResponseEntity<ReservationsDto> reservationUpdate(@Valid @RequestBody ReservationsDto reservationsDto) throws ValidationsException {

        return ResponseEntity.ok(productReservationController.reservationUpdate(reservationsDto));

    }

    @Operation(summary = "Atualiza o status de uma ou mais reservas de produtos")
    @PutMapping(value = "/confirmation")
    public ResponseEntity<ReservationsDto> reservationConfirmation(@Valid @RequestBody ReservationsDto reservationsDto) throws ValidationsException {

        return ResponseEntity.ok(productReservationController.reservationConfirmation(reservationsDto));

    }

    @Operation(summary = "Atualiza o estoque de produtos a partir do cancelamento de uma reserva ou mais reservas")
    @PutMapping(value = "/cancellation")
    public ResponseEntity<ReservationsDto> reservationCancellation(@Valid @RequestBody ReservationsDto reservationsDto) throws ValidationsException {

        return ResponseEntity.ok(productReservationController.reservationCancellation(reservationsDto));

    }

}
