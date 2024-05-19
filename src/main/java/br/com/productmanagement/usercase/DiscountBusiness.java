package br.com.productmanagement.usercase;

import br.com.productmanagement.entities.Discount;
import br.com.productmanagement.interfaceadapters.presenters.dto.DiscountDto;
import br.com.productmanagement.util.exception.ValidationsException;
import br.com.productmanagement.util.time.TimeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DiscountBusiness {

    public Discount create(Discount discount) throws ValidationsException {

        LocalDateTime now = TimeUtils.now();

        if(discount.getDiscountType() == null){

            throw new ValidationsException("0203");

        }

        if(discount.getDescription() == null){

            throw new ValidationsException("0204");

        }

        if(discount.getDiscountStartDate() == null){

            throw new ValidationsException("0205");

        }else if(discount.getDiscountStartDate().isBefore(now)){

            throw new ValidationsException("0206");

        }

        if(discount.getDiscountFinishDate() == null){

            throw new ValidationsException("0207");

        }else if(discount.getDiscountFinishDate().isBefore(now)){

            throw new ValidationsException("0208");

        }

        if(discount.getDiscountFinishDate().isBefore(discount.getDiscountStartDate())){

            throw new ValidationsException("0209");

        }

        discount.setCreationDate(now);

        return discount;

    }

    public Discount update(Discount discount, DiscountDto discountDto) throws ValidationsException {

        LocalDateTime now = TimeUtils.now();

        Discount discountUpd = discount;

        if(discountDto.getDiscountType() != null
        && discountDto.getDiscountType() != discount.getDiscountType()){

            discountUpd.setDiscountType(discountDto.getDiscountType());

        }

        if(discountDto.getDescription() != null
        && !StringUtils.equals(discountDto.getDescription(), discountUpd.getDescription())){

            discountUpd.setDescription(discountDto.getDescription());

        }

        if(discountDto.getDiscountValue() != discountUpd.getDiscountValue()){

            discountUpd.setDiscountValue(discountDto.getDiscountValue());

        }

        if(discountDto.getDiscountStartDate() != null
        && discountDto.getDiscountStartDate() != discountUpd.getDiscountStartDate()){

            if(discountUpd.getDiscountStartDate().isBefore(now)){

                throw new ValidationsException("0206");

            }

            discountUpd.setDiscountStartDate(discountDto.getDiscountStartDate());

        }

        if(discountDto.getDiscountFinishDate() != null
        && discountDto.getDiscountFinishDate() != discountUpd.getDiscountFinishDate()){

            if(discountUpd.getDiscountFinishDate().isBefore(now)){

                throw new ValidationsException("0208");

            }

            discountUpd.setDiscountFinishDate(discountDto.getDiscountFinishDate());

        }

        if(discountUpd.getDiscountFinishDate().isBefore(discountUpd.getDiscountStartDate())){

            throw new ValidationsException("0209");

        }

        if(discountDto.getMinimumQuantityToDiscount() != null
        && discountDto.getMinimumQuantityToDiscount() != discountUpd.getMinimumQuantityToDiscount()){

            discountUpd.setMinimumQuantityToDiscount(discountDto.getMinimumQuantityToDiscount());

        }

        discountUpd.setActive(discountDto.isActive());

        return discountUpd;

    }

}
