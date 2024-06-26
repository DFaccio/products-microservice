package br.com.productmanagement.interfaceadapters.gateways;

import br.com.productmanagement.entities.ProductReservation;
import br.com.productmanagement.frameworks.db.ProductReservationRepository;
import br.com.productmanagement.util.enums.ReservationStatus;
import br.com.productmanagement.util.exception.ValidationsException;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductReservationGateway {

    @Resource
    private ProductReservationRepository productReservationRepository;

    public ProductReservation save(ProductReservation productReservation) {

        return productReservationRepository.save(productReservation);

    }

    public ProductReservation findById(UUID id) {

        return productReservationRepository.findById(id).orElse(new ProductReservation());

    }

    public Page<ProductReservation> findAll(Pageable pageable) throws ValidationsException {

        return productReservationRepository.findAll(pageable);

    }
    public Page<ProductReservation> findAllByReservationStatus(Pageable pageable, ReservationStatus reservationStatus) {

        return productReservationRepository.findAllByReservationStatus(pageable, reservationStatus);

    }

    public List<ProductReservation> findAllByReservationStatus(ReservationStatus reservationStatus) {

        return productReservationRepository.findAllByReservationStatus(reservationStatus);

    }

}
