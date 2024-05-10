package br.com.productmanagement.interfaceAdapters.gateways;

import br.com.productmanagement.entities.Product;
import br.com.productmanagement.entities.ProductReservation;
import br.com.productmanagement.frameworks.db.ProductReservationRepository;
import br.com.productmanagement.util.exception.ValidationsException;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class ProductReservationGateway {

    @Resource
    private ProductReservationRepository productReservationRepository;

    public ProductReservation save(ProductReservation productReservation) throws ValidationsException {

        return productReservationRepository.save(productReservation);

    }

}
