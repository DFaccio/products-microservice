package br.com.productmanagement.usercase;

import br.com.productmanagement.entities.Discount;
import br.com.productmanagement.interfaceAdapters.presenters.dto.DiscountDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class DiscountBusiness {

    public Discount update(Discount discount, DiscountDto discountDto){

        Discount discountUpd = discount;

        if(discountDto.getDiscountType() != null
        && discountDto.getDiscountType() != discount.getDiscountType()){

            discountUpd.setDiscountType(discountDto.getDiscountType());

        }

//        ARRUMAR, MESMO ESTANDO IGUAL ESTÁ CAINDO AQUI - usar hashcode?

        if(discountDto.getDescription() != null
        && !StringUtils.equals(discountDto.getDescription(), discountUpd.getDescription())){

            discountUpd.setDescription(discountDto.getDescription());

        }

        if(discountDto.getDiscountValue() != discountUpd.getDiscountValue()){

            discountUpd.setDiscountValue(discountDto.getDiscountValue());

        }

        if(discountDto.getDiscountStartDate() != null
        && discountDto.getDiscountStartDate() != discountUpd.getDiscountStartDate()){

            discountUpd.setDiscountStartDate(discountDto.getDiscountStartDate());

        }

        if(discountDto.getDiscountFinishDate() != null
        && discountDto.getDiscountFinishDate() != discountUpd.getDiscountFinishDate()){

            discountUpd.setDiscountFinishDate(discountDto.getDiscountFinishDate());

        }

        if(discountDto.getMinimumQuantityToDiscount() != null
        && discountDto.getMinimumQuantityToDiscount() != discountUpd.getMinimumQuantityToDiscount()){

            discountUpd.setMinimumQuantityToDiscount(discountDto.getMinimumQuantityToDiscount());

        }

        discountUpd.setActive(discountDto.isActive());

        return discountUpd;

    }

}
