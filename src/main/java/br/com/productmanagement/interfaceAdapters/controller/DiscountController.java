package br.com.productmanagement.interfaceAdapters.controller;

import br.com.productmanagement.entities.Discount;
import br.com.productmanagement.interfaceAdapters.gateways.DiscountGateway;
import br.com.productmanagement.interfaceAdapters.presenters.DiscountPresenter;
import br.com.productmanagement.interfaceAdapters.presenters.dto.DiscountDto;
import br.com.productmanagement.util.pagination.PagedResponse;
import br.com.productmanagement.util.pagination.Pagination;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class DiscountController {

    @Resource
    private DiscountGateway discountGateway;

    @Resource
    private DiscountPresenter discountPresenter;

    public PagedResponse<DiscountDto> findAll(Pagination pagination){

        Pageable pageable = PageRequest.of(pagination.getPage(), pagination.getPageSize());

        Page<Discount> discount = discountGateway.findAll(pageable);

        return discountPresenter.convertDocuments(discount);

    }

    public DiscountDto insert(DiscountDto dto){

        Discount discount = discountPresenter.convert(dto);

        return discountPresenter.convert(discountGateway.insert(discount));

    }

}
