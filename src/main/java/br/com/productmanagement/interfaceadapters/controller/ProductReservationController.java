package br.com.productmanagement.interfaceadapters.controller;

import br.com.productmanagement.entities.Product;
import br.com.productmanagement.entities.ProductReservation;
import br.com.productmanagement.interfaceadapters.gateways.ProductGateway;
import br.com.productmanagement.interfaceadapters.gateways.ProductReservationGateway;
import br.com.productmanagement.interfaceadapters.helper.ProductHelper;
import br.com.productmanagement.interfaceadapters.presenters.ProductReservationPresenter;
import br.com.productmanagement.interfaceadapters.presenters.dto.ProductReservationDto;
import br.com.productmanagement.interfaceadapters.presenters.dto.ReservationsDto;
import br.com.productmanagement.usercase.ProductReservationBusiness;
import br.com.productmanagement.util.enums.Operation;
import br.com.productmanagement.util.enums.ReservationStatus;
import br.com.productmanagement.util.exception.ValidationsException;
import br.com.productmanagement.util.pagination.PagedResponse;
import br.com.productmanagement.util.pagination.Pagination;
import br.com.productmanagement.util.time.TimeUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
@EnableScheduling
@Slf4j
public class ProductReservationController {

    @Resource
    private ProductReservationGateway productReservationGateway;

    @Resource
    private ProductReservationBusiness productReservationBusiness;

    @Resource
    private ProductGateway productGateway;

    @Resource
    private ProductHelper productHelper;

    @Resource
    private ProductReservationPresenter productReservationPresenter;

    private static final Integer EXPIRATION = 20;

    public PagedResponse<ProductReservationDto> findAll(Pagination pagination, ReservationStatus reservationStatus) throws ValidationsException {

        Pageable pageable = PageRequest.of(pagination.getPage(), pagination.getPageSize());
        Page<ProductReservation> productReservation;

        boolean reservationStatusFilter = reservationStatus != null;

        if (reservationStatusFilter) {

            productReservation = productReservationGateway.findAllByReservationStatus(pageable, reservationStatus);

        }else{

            productReservation = productReservationGateway.findAll(pageable);

        }

        return productReservationPresenter.convertDocuments(productReservation);

    }

    public ProductReservationDto findById(UUID id){

        ProductReservation productReservation = productReservationGateway.findById(id);

        return productReservationPresenter.convert(productReservation);

    }

    public ReservationsDto newReservation(ReservationsDto reservationsDto) throws ValidationsException {

        ReservationsDto reservations = new ReservationsDto();

        List<ProductReservationDto> productReservationDtoList = new ArrayList<>();

        for(ProductReservationDto productReservationDto : reservationsDto.getReservations()){

            ProductReservationDto productReservation;

            ProductReservation newReservation = new ProductReservation();

            String sku;

            int requestedQuantity;

            if(productReservationDto.getSku() != null){
                sku = productReservationDto.getSku();
            }else{
                throw new ValidationsException("0300");
            }

            if(productReservationDto.getRequestedQuantity() != null){
                 requestedQuantity = productReservationDto.getRequestedQuantity();
            }else{
                throw new ValidationsException("0301");
            }

            Product product = productGateway.findBySku(sku);

            int productDisponibility = product.getAvailableQuantity();


            if(!productReservationBusiness.checkAvailableQuantity(productDisponibility, requestedQuantity)){

                newReservation = productReservationBusiness.reservationUnavailable(newReservation, productReservationDto);

            }else{

                newReservation = productReservationBusiness.newReservation(product, requestedQuantity);

                newReservation = productReservationGateway.save(newReservation);

                product = productHelper.updateProductKeeping(product, productDisponibility, requestedQuantity, Operation.RESERVATION);

                product = productGateway.save(product);

            }

            productReservation = productReservationPresenter.convert(newReservation);

            productReservation.setProductDisponibility(product.getAvailableQuantity());

            productReservation.setProductValue(product.getPrice());

            productReservationDtoList.add(productReservation);

        }

        reservations.setReservations(productReservationDtoList);

        return reservations;

    }

    public ReservationsDto reservationUpdate(ReservationsDto reservationsDto) throws ValidationsException {

        ReservationsDto reservations = new ReservationsDto();

        List<ProductReservationDto> productReservationDtoList = new ArrayList<>();

        for(ProductReservationDto productReservationDto : reservationsDto.getReservations()){

            ProductReservationDto productReservation;

            UUID id;

            int requestedQuantity;

            if(productReservationDto.getId() != null){
                id = productReservationDto.getId();
            }else{
                throw new ValidationsException("0302");
            }

            if(productReservationDto.getRequestedQuantity() != null){
                requestedQuantity = productReservationDto.getRequestedQuantity();
            }else{
                throw new ValidationsException("0301");
            }

            ProductReservation updReservation = productReservationGateway.findById(id);

            if(updReservation.getReservationStatus() == ReservationStatus.CONFIRMED){

                throw new ValidationsException("0304");

            }else if(updReservation.getReservationStatus() == ReservationStatus.EXPIRED){

                throw new ValidationsException("0307", "Reserva", updReservation.getReservationId().toString());

            }

            String sku = updReservation.getSku();

            Product product = productGateway.findBySku(sku);

            int productDisponibility = product.getAvailableQuantity();

            int reservedQuantity = updReservation.getRequestedQuantity();

            int updStockQuantity;

            boolean increaseReservation = true;

            boolean updStockAndReservation = true;

            if(requestedQuantity == reservedQuantity) {

                updStockAndReservation = false;

            }

            if(requestedQuantity > reservedQuantity){

                updStockQuantity = requestedQuantity - reservedQuantity;

                if(!productReservationBusiness.checkAvailableQuantity(productDisponibility, updStockQuantity)) {

                    updReservation = productReservationBusiness.reservationUnavailable(updReservation, productReservationDto);

                    updStockAndReservation = false;

                }

            }else{

                updStockQuantity = reservedQuantity - requestedQuantity;

                increaseReservation = false;

            }

            if(updStockAndReservation){

                updReservation = productReservationBusiness.updateReservation(updReservation, product, requestedQuantity);

                updReservation = productReservationGateway.save(updReservation);

                if(increaseReservation){

                    product = productHelper.updateProductKeeping(product, productDisponibility, updStockQuantity, Operation.RESERVATION);

                }else{

                    product = productHelper.updateProductKeeping(product, productDisponibility, updStockQuantity, Operation.RESERVATION_GIVEUP);

                }

                product = productGateway.save(product);

            }

            productReservation = productReservationPresenter.convert(updReservation);

            productReservation.setProductDisponibility(product.getAvailableQuantity());

            productReservation.setProductValue(product.getPrice());

            productReservationDtoList.add(productReservation);

        }

        reservations.setReservations(productReservationDtoList);

        return reservations;

    }

    public ReservationsDto reservationConfirmation(ReservationsDto reservationsDto) throws ValidationsException {

        ReservationsDto reservations = new ReservationsDto();

        List<ProductReservationDto> productReservationDtoList = new ArrayList<>();

        for(ProductReservationDto productReservationDto : reservationsDto.getReservations()){

            ProductReservationDto productReservation;

            UUID id;

            if(productReservationDto.getId() != null){
                id = productReservationDto.getId();
            }else{
                throw new ValidationsException("0302");
            }

            ProductReservation updReservation = productReservationGateway.findById(id);

            updReservation = productReservationBusiness.reservationConfirmation(updReservation);

            updReservation = productReservationGateway.save(updReservation);

            productReservation = productReservationPresenter.convert(updReservation);

            productReservationDtoList.add(productReservation);

        }

        reservations.setReservations(productReservationDtoList);

        return reservations;

    }

    public ReservationsDto reservationCancellation(ReservationsDto reservationsDto) throws ValidationsException {

        ReservationsDto reservations = new ReservationsDto();

        List<ProductReservationDto> productReservationDtoList = new ArrayList<>();

        for(ProductReservationDto productReservationDto : reservationsDto.getReservations()){

            ProductReservationDto productReservation;

            UUID id;

            if(productReservationDto.getId() != null){
                id = productReservationDto.getId();
            }else{
                throw new ValidationsException("0302");
            }

            ProductReservation updReservation = productReservationGateway.findById(id);

            if(updReservation.getReservationStatus() == ReservationStatus.EXPIRED){

                throw new ValidationsException("0308", "Reserva", updReservation.getReservationId().toString());

            }

            String sku = updReservation.getSku();

            Product product = productGateway.findBySku(sku);

            int productDisponibility = product.getAvailableQuantity();

            int reservedQuantity = updReservation.getRequestedQuantity();

            updReservation.setUpdateDate(TimeUtils.now());

            updReservation.setReservationStatus(ReservationStatus.CANCELLED);

            productReservationGateway.save(updReservation);

            product = productHelper.updateProductKeeping(product, productDisponibility, reservedQuantity, Operation.RESERVATION_CANCELLATION);

            product = productGateway.save(product);

            productReservation = productReservationPresenter.convert(updReservation);

            productReservation.setProductDisponibility(product.getAvailableQuantity());

            productReservation.setProductValue(product.getPrice());

            productReservationDtoList.add(productReservation);

        }

        reservations.setReservations(productReservationDtoList);

        return reservations;

    }

    @Scheduled(fixedRateString = "${time.to.expire.created.reservations}", timeUnit = TimeUnit.MINUTES)
    public void reservationExpirationCheck() {
        log.info("Verificação de reservas a expirar iniciada");

        List<ProductReservation> productReservation = productReservationGateway.findAllByReservationStatus(ReservationStatus.CREATED);

        productReservation.forEach(reservation -> {

            Duration duration = Duration.between(reservation.getUpdateDate(), TimeUtils.now());
            long minutes = duration.toMinutes() % 60;

            if (minutes > EXPIRATION) {

                log.info("Reserva " + reservation.getReservationId() + " expirada após 20 minutos sem alterações!");
                reservation.setReservationStatus(ReservationStatus.EXPIRED);
                reservation.setUpdateDate(TimeUtils.now());

                productReservationGateway.save(reservation);

                retrieveProductKeepingByReservationExpiration(reservation);

            }
        });

        log.info("Verificação de reservas a expirar concluída");

    }

    public void retrieveProductKeepingByReservationExpiration(ProductReservation productReservation){

        String sku = productReservation.getSku();

        Product product = productGateway.findBySku(sku);

        int productDisponibility = product.getAvailableQuantity();

        int reservedQuantity = productReservation.getRequestedQuantity();

        product = productHelper.updateProductKeeping(product, productDisponibility, reservedQuantity, Operation.RESERVATION_EXPIRATION);

        productGateway.save(product);

    }

}
