package br.com.productmanagement.unit;

import br.com.productmanagement.entities.Product;
import br.com.productmanagement.helper.ProductTestHelper;
import br.com.productmanagement.usercase.ProductBusiness;
import br.com.productmanagement.util.exception.ValidationsException;
import br.com.productmanagement.utils.TestUtils;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class ProductBusinessTest extends TestUtils {

    @Resource
    private ProductBusiness productBusiness;

    @Resource
    private ProductTestHelper productTestHelper;

    @Test
    public void createProductTest() throws ValidationsException {

        Product product = productTestHelper.newProduct();

        product = productBusiness.create(product);

        assertNotNull(product);

    }

    @Test
    public void updateSkuTest() throws ValidationsException {

        Product product = productTestHelper.newProduct();
        Product productUpd = productTestHelper.productUpd();

        product = productBusiness.updateSku(product, productUpd);

        assertNotNull(product);

    }

    @Test
    public void checkForAvailableDiscountTest(){

        Page<Product> product = productTestHelper.newProductPage();

        product = productBusiness.checkForAvailableDiscount(product);

        assertNotNull(product);

    }

}
