package br.com.productmanagement.interfaceAdapters.controller;

import br.com.productmanagement.entities.Product;
import br.com.productmanagement.entities.ProductReservation;
import br.com.productmanagement.interfaceAdapters.gateways.ProductGateway;
import br.com.productmanagement.interfaceAdapters.gateways.ProductReservationGateway;
import br.com.productmanagement.interfaceAdapters.helper.ProductHelper;
import br.com.productmanagement.interfaceAdapters.presenters.dto.ProductDto;
import br.com.productmanagement.interfaceAdapters.presenters.dto.ProductReservationDto;
import br.com.productmanagement.interfaceAdapters.presenters.dto.ReservationsDto;
import br.com.productmanagement.usercase.ProductReservationBusiness;
import br.com.productmanagement.util.enums.Operation;
import br.com.productmanagement.util.enums.ReservationStatus;
import br.com.productmanagement.util.exception.ValidationsException;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

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

    public ReservationsDto newReservation(ReservationsDto reservationsDto) throws ValidationsException {

        ReservationsDto reservations = new ReservationsDto();

        List<ProductReservationDto> productReservationDtoList = reservationsDto.getProductReservationDto();

        for(ProductReservationDto productReservationDto : productReservationDtoList){

            ProductReservationDto productReservation = new ProductReservationDto();

            String sku = productReservationDto.getSku();

            Product product = productGateway.findBySku(sku);

            int productDisponibility = product.getAvailableQuantity();
            int requestedQuantity = productReservationDto.getRequestedQuantity();

            productReservation.setProductDisponibility(productDisponibility);

            if(!productReservationBusiness.checkAvailableQuantity(productDisponibility, requestedQuantity)){

                productReservation.setReservationStatus(ReservationStatus.FAILED);

            }else{

                productReservation.setReservationStatus(ReservationStatus.PENDING);

                productReservation = productReservationBusiness.returnProductOrderValues(product, requestedQuantity);

            }

            productReservationDtoList.add(productReservation);

        }

        reservations.setProductReservationDto(productReservationDtoList);


        return reservations;

    }

    public ReservationsDto reservationUpdate(ReservationsDto reservationsDto) throws ValidationsException {

//        ler lista recebida, realizar o que está abaixo para cada ocorrência

        ProductDto productDto = findBySku(sku);

        int productQuantity = productDto.getAvailableQuantity();

        if(!productReservationBusiness.checkAvailableQuantity(productQuantity, orderQuantity)){
            throw new ValidationsException("0101", "produto", sku);
        }

        ProductReservationDto productOrderDto = productReservationBusiness.returnProductOrderValues(productDto, orderQuantity);

        Product product = productPresenter.convert(productDto);

        product = productHelper.updateProductKeeping(product, productQuantity, orderQuantity, Operation.SALE);

        productReservationGateway.save(product);

        return productOrderDto;

    }

    public ReservationsDto reservationConfirmation(ReservationsDto reservationsDto) throws ValidationsException {

//        ler lista recebida, realizar o que está abaixo para cada ocorrência

        ProductDto productDto = findBySku(sku);

        int productQuantity = productDto.getAvailableQuantity();

        if(!productReservationBusiness.checkAvailableQuantity(productQuantity, orderQuantity)){
            throw new ValidationsException("0101", "produto", sku);
        }

        ProductReservationDto productOrderDto = productReservationBusiness.returnProductOrderValues(productDto, requestedQuantity);

        ProductReservation productReservation = productPresenter.convert(productDto);

        productReservation = productHelper.updateProductKeeping(productReservation, productQuantity, requestedQuantity, Operation.SALE);

        productReservationGateway.save(productReservation);

        return productOrderDto;

    }

    public void reservationCancellation(ReservationsDto reservationsDto) throws ValidationsException {

//        ler lista recebida, realizar o que está abaixo para cada ocorrência

        ProductReservation productReservation = productGateway.findBySku(sku);

        int productQuantity = product.getAvailableQuantity();

        productReservation = productHelper.updateProductKeeping(productReservation, productQuantity, orderQuantity, Operation.ORDER_CANCELLATION);

        productReservationGateway.save(productReservation);

    }


}
