package br.com.productmanagement.interfaceAdapters.controller;

import br.com.productmanagement.entities.Product;
import br.com.productmanagement.entities.ProductReservation;
import br.com.productmanagement.interfaceAdapters.gateways.ProductGateway;
import br.com.productmanagement.interfaceAdapters.gateways.ProductReservationGateway;
import br.com.productmanagement.interfaceAdapters.helper.ProductHelper;
import br.com.productmanagement.interfaceAdapters.presenters.ProductReservationPresenter;
import br.com.productmanagement.interfaceAdapters.presenters.dto.ProductReservationDto;
import br.com.productmanagement.interfaceAdapters.presenters.dto.ReservationsDto;
import br.com.productmanagement.usercase.ProductReservationBusiness;
import br.com.productmanagement.util.enums.Operation;
import br.com.productmanagement.util.enums.ReservationStatus;
import br.com.productmanagement.util.exception.ValidationsException;
import br.com.productmanagement.util.pagination.PagedResponse;
import br.com.productmanagement.util.pagination.Pagination;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
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

            String sku = productReservationDto.getSku();

            Product product = productGateway.findBySku(sku);

            int productDisponibility = product.getAvailableQuantity();

            int requestedQuantity = productReservationDto.getRequestedQuantity();

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

            ProductReservation updReservation = productReservationGateway.findById(productReservationDto.getId());

            String sku = updReservation.getSku();

            Product product = productGateway.findBySku(sku);

            int productDisponibility = product.getAvailableQuantity();

            int reservedQuantity = updReservation.getRequestedQuantity();

            int requestedQuantity = productReservationDto.getRequestedQuantity();

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

            ProductReservation updReservation = productReservationGateway.findById(productReservationDto.getId());

            updReservation.setUpdateDate(LocalDateTime.now());

            updReservation.setReservationStatus(ReservationStatus.CONFIRMED);

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

            ProductReservation updReservation = productReservationGateway.findById(productReservationDto.getId());

            String sku = updReservation.getSku();

            Product product = productGateway.findBySku(sku);

            int productDisponibility = product.getAvailableQuantity();

            int reservedQuantity = updReservation.getRequestedQuantity();

            updReservation.setUpdateDate(LocalDateTime.now());

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

}
