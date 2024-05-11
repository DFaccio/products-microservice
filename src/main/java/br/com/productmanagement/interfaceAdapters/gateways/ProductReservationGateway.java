package br.com.productmanagement.interfaceAdapters.gateways;

import br.com.productmanagement.entities.Product;
import br.com.productmanagement.entities.ProductReservation;
import br.com.productmanagement.frameworks.db.ProductReservationRepository;
import br.com.productmanagement.util.enums.ReservationStatus;
import br.com.productmanagement.util.exception.ValidationsException;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProductReservationGateway {

    @Resource
    private ProductReservationRepository productReservationRepository;

    public ProductReservation save(ProductReservation productReservation) throws ValidationsException {

        return productReservationRepository.save(productReservation);

    }

    public ProductReservation findById(UUID id){

        return productReservationRepository.findById(id).orElse(new ProductReservation());

    }

    public Page<ProductReservation> findAll(Pageable pageable) throws ValidationsException {

        return productReservationRepository.findAll(pageable);

    }
    public Page<ProductReservation> findAllByReservationStatus(Pageable pageable, ReservationStatus reservationStatus) throws ValidationsException {

        return productReservationRepository.findAllByReservationStatus(pageable, reservationStatus);

    }

}
