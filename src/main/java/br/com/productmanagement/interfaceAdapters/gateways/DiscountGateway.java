package br.com.productmanagement.interfaceAdapters.gateways;

import br.com.productmanagement.frameworks.db.DiscountRepository;
import br.com.productmanagement.entities.Discount;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DiscountGateway {

    @Resource
    private DiscountRepository discountRepository;

    public Page<Discount> findAll(Pageable pageable){

        return discountRepository.findAll(pageable);

    }

    public Discount insert(Discount discount){

        return discountRepository.save(discount);

    }

}
