package br.com.productmanagement.interfaceAdapters.controller;

import br.com.productmanagement.entities.Discount;
import br.com.productmanagement.interfaceAdapters.gateways.DiscountGateway;
import br.com.productmanagement.interfaceAdapters.presenters.DiscountPresenter;
import br.com.productmanagement.interfaceAdapters.presenters.dto.DiscountDto;
import br.com.productmanagement.usercase.DiscountBusiness;
import br.com.productmanagement.util.MessageUtil;
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

//      arrumar retorno de erro

        Discount discount = discountPresenter.convert(dto);

        if(discount.getProductCategory() != null){

            Optional<Discount> optional = discountGateway.findByProductCategory(discount.getProductCategory());

            if(optional.isPresent()){

                new IllegalArgumentException(MessageUtil.getMessage("0201"));
//                throw new ValidationsException("0201", "categoria", discount.getProductCategory().toString());

            }

        }

//      arrumar retorno de erro

        if(discount.getCoupon() != null){

            Optional<Discount> optional = discountGateway.findByCoupon(discount.getCoupon());

            if(optional.isPresent()){

                new IllegalArgumentException(MessageUtil.getMessage("0202"));
//                throw new ValidationsException("0202", "cupom", discount.getCoupon());

            }

        }

        discount = discountGateway.save(discount);

        return discountPresenter.convert(discount);

    }

    public DiscountDto update(UUID id, DiscountDto dto) throws ValidationsException {

        Optional<Discount> optional = discountGateway.findById(id);

        if(optional.isEmpty()){
            throw new ValidationsException("0200", "desconto", id.toString());
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

    //    ARRUMAR MENSAGEM DE NÃO ENCONTRADO

    public DiscountDto findByCoupon(String coupon) throws ValidationsException {

        Optional<Discount> optional = discountGateway.findByCoupon(coupon);

        if(optional.isEmpty()){
//            throw new IllegalArgumentException(MessageUtil.getMessage("0200", "cupom", coupon));
            throw new ValidationsException("0200", "cupom", coupon);
        }

        Discount discount = optional.get();

        return discountPresenter.convert(discount);

    }

    //    ARRUMAR MENSAGEM DE NÃO ENCONTRADO

    public DiscountDto findById(UUID id) throws ValidationsException {

        Optional<Discount> optional = discountGateway.findById(id);

        if(optional.isEmpty()){
            throw new ValidationsException("0200", "desconto", id.toString());
        }

        Discount discount = optional.get();

        return discountPresenter.convert(discount);

    }

    //    ARRUMAR MENSAGEM DE NÃO ENCONTRADO

    public DiscountDto findByProductCategory(ProductCategory category) throws ValidationsException {

        Optional<Discount> optional = discountGateway.findByProductCategory(category);

        if(optional.isEmpty()){
            throw new ValidationsException("0200", "desconto", category.toString());
        }

        Discount discount = optional.get();

        return discountPresenter.convert(discount);


    }

}
