package br.com.productmanagement.helper;

import br.com.productmanagement.entities.Discount;
import br.com.productmanagement.interfaceadapters.presenters.dto.DiscountDto;
import br.com.productmanagement.util.enums.DiscountType;
import br.com.productmanagement.util.enums.ProductCategory;
import br.com.productmanagement.util.time.TimeUtils;
import br.com.productmanagement.utils.Constants;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DiscountTestHelper {

    public Discount newDiscount(){

        Discount discount = new Discount();

        LocalDateTime start = TimeUtils.now().plusDays(Constants.PLUS_1_DAY);
        LocalDateTime finish = TimeUtils.now().plusDays(Constants.PLUS_10_DAYS);

        discount.setDiscountType(DiscountType.PERCENTAGE);
        discount.setDescription("Ganhe 2% de desconto na compra a partir de 3 unidades");;
        discount.setDiscountValue(2);
        discount.setDiscountStartDate(start);
        discount.setDiscountFinishDate(finish);
        discount.setMinimumQuantityToDiscount(3);
        discount.setProductCategory(ProductCategory.SAUDE);
        discount.setActive(true);

        return discount;

    }

    public DiscountDto discountUpd(){

        DiscountDto discountDto = new DiscountDto();

        LocalDateTime start = TimeUtils.now().plusDays(Constants.PLUS_1_DAY);
        LocalDateTime finish = TimeUtils.now().plusDays(Constants.PLUS_10_DAYS);

        discountDto.setDiscountType(DiscountType.PERCENTAGE);
        discountDto.setDescription("Ganhe 5% de desconto na compra a partir de 2 unidades");;
        discountDto.setDiscountValue(5);
        discountDto.setDiscountStartDate(start);
        discountDto.setDiscountFinishDate(finish);
        discountDto.setMinimumQuantityToDiscount(2);
        discountDto.setProductCategory(ProductCategory.SAUDE);
        discountDto.setActive(true);

        return discountDto;

    }

}
