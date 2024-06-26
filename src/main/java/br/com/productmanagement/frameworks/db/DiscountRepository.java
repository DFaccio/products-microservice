package br.com.productmanagement.frameworks.db;

import br.com.productmanagement.entities.Discount;
import br.com.productmanagement.util.enums.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, UUID> {

    Discount findByProductCategory(String category);

    Optional<Discount> findByCoupon(String coupon);

    Optional<Discount> findByProductCategory(ProductCategory category);

}
