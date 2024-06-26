package br.com.productmanagement.frameworks.db;

import br.com.productmanagement.entities.ProductReservation;
import br.com.productmanagement.util.enums.ReservationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductReservationRepository extends JpaRepository<ProductReservation, UUID> {

    Page<ProductReservation> findAllByReservationStatus(Pageable pageable, ReservationStatus reservationStatus);

    List<ProductReservation> findAllByReservationStatus(ReservationStatus reservationStatus);

}
