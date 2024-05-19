package br.com.productmanagement.interfaceAdapters.presenters;

import br.com.productmanagement.entities.Discount;
import br.com.productmanagement.interfaceAdapters.presenters.dto.DiscountDto;
import org.springframework.stereotype.Component;

@Component
public class DiscountPresenter implements Presenter<Discount, DiscountDto>{

    @Override
    public DiscountDto convert(Discount document){

        DiscountDto discountDto = new DiscountDto();

        discountDto.setId(document.getId());
        discountDto.setCoupon(document.getCoupon());
        discountDto.setDiscountType(document.getDiscountType());
        discountDto.setDescription(document.getDescription());
        discountDto.setDiscountValue(document.getDiscountValue());
        discountDto.setDiscountStartDate(document.getDiscountStartDate());
        discountDto.setDiscountFinishDate(document.getDiscountFinishDate());
        discountDto.setMinimumQuantityToDiscount(document.getMinimumQuantityToDiscount());
        discountDto.setProductCategory(document.getProductCategory());
        discountDto.setActive(document.isActive());

        return discountDto;

    }

    public Discount convert(DiscountDto dto){

        Discount discount = new Discount();

        discount.setId(dto.getId());
        discount.setCoupon(dto.getCoupon());
        discount.setDiscountType(dto.getDiscountType());
        discount.setDescription(dto.getDescription());
        discount.setDiscountValue(dto.getDiscountValue());
        discount.setDiscountStartDate(dto.getDiscountStartDate());
        discount.setDiscountFinishDate(dto.getDiscountFinishDate());
        discount.setMinimumQuantityToDiscount(dto.getMinimumQuantityToDiscount());
        discount.setProductCategory(dto.getProductCategory());
        discount.setActive(dto.isActive());

        return discount;

    }

}
