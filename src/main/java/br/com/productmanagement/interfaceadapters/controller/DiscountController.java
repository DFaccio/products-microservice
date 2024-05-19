package br.com.productmanagement.interfaceadapters.controller;

import br.com.productmanagement.entities.Discount;
import br.com.productmanagement.interfaceadapters.gateways.DiscountGateway;
import br.com.productmanagement.interfaceadapters.presenters.DiscountPresenter;
import br.com.productmanagement.interfaceadapters.presenters.dto.DiscountDto;
import br.com.productmanagement.usercase.DiscountBusiness;
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
    private DiscountBusiness discountBusiness;

    @Resource
    private DiscountPresenter discountPresenter;

    public DiscountDto insert(DiscountDto dto) throws ValidationsException {

        Discount discount = discountPresenter.convert(dto);

        if(discount.getProductCategory() != null){

            Optional<Discount> optional = discountGateway.findByProductCategory(discount.getProductCategory());

            if(optional.isPresent()){

                throw new ValidationsException("0201");

            }

        }

        if(discount.getCoupon() != null){

            Optional<Discount> optional = discountGateway.findByCoupon(discount.getCoupon());

            if(optional.isPresent()){

                throw new ValidationsException("0202");

            }

        }

        discount = discountBusiness.create(discount);

        discount = discountGateway.save(discount);

        return discountPresenter.convert(discount);

    }

    public DiscountDto update(UUID id, DiscountDto dto) throws ValidationsException {

        Optional<Discount> optional = discountGateway.findById(id);

        if(optional.isEmpty()){

            throw new ValidationsException("0200", "Desconto", id.toString());

        }

        Discount discountUpd = discountBusiness.update(optional.get(), dto);

        discountUpd = discountGateway.save(discountUpd);

        return discountPresenter.convert(discountGateway.save(discountUpd));

    }

    public PagedResponse<DiscountDto> findAll(Pagination pagination){

        Pageable pageable = PageRequest.of(pagination.getPage(), pagination.getPageSize());

        Page<Discount> discount = discountGateway.findAll(pageable);

        return discountPresenter.convertDocuments(discount);

    }

    public DiscountDto findByCoupon(String coupon) throws ValidationsException {

        Optional<Discount> optional = discountGateway.findByCoupon(coupon);

        if(optional.isEmpty()){

            throw new ValidationsException("0200", "Cupom", coupon);

        }

        Discount discount = optional.get();

        return discountPresenter.convert(discount);

    }

    public DiscountDto findById(UUID id) throws ValidationsException {

        Optional<Discount> optional = discountGateway.findById(id);

        if(optional.isEmpty()){

            throw new ValidationsException("0200", "Desconto", id.toString());

        }

        Discount discount = optional.get();

        return discountPresenter.convert(discount);

    }

    public DiscountDto findByProductCategory(ProductCategory category) throws ValidationsException {

        Optional<Discount> optional = discountGateway.findByProductCategory(category);

        if(optional.isEmpty()){

            throw new ValidationsException("0200", "Desconto", category.toString());

        }

        Discount discount = optional.get();

        return discountPresenter.convert(discount);

    }

}
