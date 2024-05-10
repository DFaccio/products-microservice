package br.com.productmanagement.frameworks.db;

import br.com.productmanagement.entities.ProductReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductReservationRepository extends JpaRepository<ProductReservation, UUID> {



}
