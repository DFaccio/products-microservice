package br.com.productmanagement.interfaceAdapters.presenters;

import br.com.productmanagement.entities.Discount;
import br.com.productmanagement.interfaceAdapters.presenters.dto.DiscountDto;
import org.springframework.stereotype.Component;

@Component
public class DiscountPresenter implements Presenter<Discount, DiscountDto>{

    @Override
    public DiscountDto convert(Discount document){

    return new DiscountDto(
            document.getDiscountId(),
            document.getCoupon(),
            document.getDiscountType(),
            document.getDescription(),
            document.getDiscountValue(),
            document.getDiscountStartDate(),
            document.getDiscountFinishDate(),
            document.getMinimumQuantityToDiscount(),
            document.getProductCategory(),
            document.isActive()
    );

    }

    public Discount convert(DiscountDto dto){

        return new Discount(
                dto.getCoupon(),
                dto.getDiscountType(),
                dto.getDescription(),
                dto.getDiscountValue(),
                dto.getDiscountStartDate(),
                dto.getDiscountFinishDate(),
                dto.getMinimumQuantityToDiscount(),
                dto.getProductCategory(),
                dto.isActive()
        );
    }

}
