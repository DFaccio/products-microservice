package br.com.productmanagement.interfaceadapters.gateways;

import br.com.productmanagement.entities.Discount;
import br.com.productmanagement.frameworks.db.DiscountRepository;
import br.com.productmanagement.util.enums.ProductCategory;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class DiscountGateway {

    @Resource
    private DiscountRepository discountRepository;

    public Discount save(Discount discount){

        return discountRepository.save(discount);

    }

    public Page<Discount> findAll(Pageable pageable){

        return discountRepository.findAll(pageable);

    }

    public Discount findByProductCategory(String category){

        return discountRepository.findByProductCategory(category);

    }

    public Optional<Discount> findByCoupon(String coupon){

        return discountRepository.findByCoupon(coupon);

    }

    public Optional<Discount> findById(UUID id){

        return discountRepository.findById(id);

    }

    public Optional<Discount> findByProductCategory(ProductCategory category){

        return discountRepository.findByProductCategory(category);

    }

}
