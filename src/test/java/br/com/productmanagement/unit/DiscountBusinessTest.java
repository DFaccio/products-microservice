package br.com.productmanagement.unit;

import br.com.productmanagement.entities.Discount;
import br.com.productmanagement.helper.DiscountTestHelper;
import br.com.productmanagement.interfaceadapters.presenters.dto.DiscountDto;
import br.com.productmanagement.usercase.DiscountBusiness;
import br.com.productmanagement.util.exception.ValidationsException;
import br.com.productmanagement.utils.TestUtils;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class DiscountBusinessTest extends TestUtils {

    @Resource
    private DiscountBusiness discountBusiness;

    @Resource
    private DiscountTestHelper discountTestHelper;

    @Test
    public void createTest() throws ValidationsException {

        Discount discount= discountTestHelper.createDiscount();

        discount = discountBusiness.create(discount);

        assertNotNull(discount);

    }

    @Test
    public void updateTest() throws ValidationsException {

        Discount discount= discountTestHelper.createDiscount();

        DiscountDto discountDto= discountTestHelper.discountUpd();

        discount = discountBusiness.update(discount, discountDto);

        assertNotNull(discount);

    }

}
