package br.com.productmanagement.interfaceAdapters.controller;

import br.com.productmanagement.entities.Discount;
import br.com.productmanagement.interfaceAdapters.gateways.DiscountGateway;
import br.com.productmanagement.interfaceAdapters.presenters.DiscountPresenter;
import br.com.productmanagement.interfaceAdapters.presenters.dto.DiscountDto;
import br.com.productmanagement.util.enums.ProductCategory;
import br.com.productmanagement.util.exception.ValidationsException;
import br.com.productmanagement.util.pagination.PagedResponse;
import br.com.productmanagement.util.pagination.Pagination;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class DiscountController {

    @Resource
    private DiscountGateway discountGateway;

    @Resource
    private DiscountPresenter discountPresenter;

    public DiscountDto insert(DiscountDto dto){

        Discount discount = discountPresenter.convert(dto);

        return discountPresenter.convert(discountGateway.insert(discount));

    }

    public PagedResponse<DiscountDto> findAll(Pagination pagination){

        Pageable pageable = PageRequest.of(pagination.getPage(), pagination.getPageSize());

        Page<Discount> discount = discountGateway.findAll(pageable);

        return discountPresenter.convertDocuments(discount);

    }

    public DiscountDto findByCoupon(String coupon) throws ValidationsException {

        Optional<Discount> optional = discountGateway.findByCoupon(coupon);

        if(optional.isEmpty()){
            throw new ValidationsException("0200", "cupom", coupon);
        }

        Discount discount = optional.get();

        return discountPresenter.convert(discount);

    }

    public DiscountDto findById(UUID id) throws ValidationsException {

        Optional<Discount> optional = discountGateway.findById(id);

        if(optional.isEmpty()){
            throw new ValidationsException("0200", "desconto", id.toString());
        }

        Discount discount = optional.get();

        return discountPresenter.convert(discount);

    }

    public DiscountDto findByProductCategory(ProductCategory category) throws ValidationsException {

        Optional<Discount> optional = discountGateway.findByProductCategory(category);

        if(optional.isEmpty()){
            throw new ValidationsException("0200", "desconto", category.toString());
        }

        Discount discount = optional.get();

        return discountPresenter.convert(discount);


    }

}
