package br.com.productmanagement.unit;

import br.com.productmanagement.entities.Product;
import br.com.productmanagement.entities.ProductReservation;
import br.com.productmanagement.helper.ProductReservationTestHelper;
import br.com.productmanagement.helper.ProductTestHelper;
import br.com.productmanagement.usercase.ProductReservationBusiness;
import br.com.productmanagement.util.enums.ReservationStatus;
import br.com.productmanagement.util.exception.ValidationsException;
import br.com.productmanagement.utils.TestUtils;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ProductReservationBusinessTest extends TestUtils {

    @Resource
    private ProductReservationBusiness productReservationBusiness;

    @Resource
    private ProductTestHelper productTestHelper;

    @Resource
    private ProductReservationTestHelper productReservationTestHelper;

    @Test
    public void checkAvailableQuantityTest(){

        int productQuantity = 10;
        int requestedQuantity = 4;

        boolean available = productReservationBusiness.checkAvailableQuantity(productQuantity,requestedQuantity);

        Assertions.assertTrue(available);

    }

    @Test
    public void newReservationTest(){

        Product product = productTestHelper.newProduct();

        int requestedQuantity = 4;

        ProductReservation productReservation = productReservationBusiness.newReservation(product, requestedQuantity);

        assertNotNull(productReservation);

    }

    @Test
    public void updateReservationTest() throws ValidationsException {

        Product product = productTestHelper.newProduct();

        int requestedQuantity = 4;

        ProductReservation productReservation = productReservationTestHelper.newReservation();

        productReservation = productReservationBusiness.updateReservation(productReservation, product, requestedQuantity);

        assertNotNull(productReservation);

    }

    @Test
    public void reservationConfirmationTest() throws ValidationsException {

        ProductReservation productReservation = productReservationTestHelper.newReservation();

        productReservation = productReservationBusiness.reservationConfirmation(productReservation);

        assertNotNull(productReservation);

    }

    @Test
    public void calculateReservationValueTest(){

        Product product = productTestHelper.newProduct();

        int requestedQuantity = 4;

        double expected = requestedQuantity * product.getPrice();

        double reservationValue = productReservationBusiness.calculateReservationValue(product, requestedQuantity);

        assertEquals(expected, reservationValue);

    }

}
