package br.com.productmanagement.usercase;

import br.com.productmanagement.entities.Product;
import br.com.productmanagement.interfaceAdapters.helper.ProductHelper;
import br.com.productmanagement.util.enums.Operation;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class ProductBusiness {


    @Resource
    private ProductHelper productHelper;

    public Product updateSku(Product product, Product productUpd){

        Product actualProduct = productUpd;

        if(product.getAvailableQuantity() != null
        && product.getAvailableQuantity() > 0 ){

//            int availableQuantity = products.getAvailableQuantity() + productUpd.getAvailableQuantity();
//
//            actualProduct.setAvailableQuantity(availableQuantity);
            actualProduct = productHelper.updateProductKeeping(actualProduct, product.getAvailableQuantity(), productUpd.getAvailableQuantity(), Operation.ARRIVAL);

        }

        if(product.getDiscount() != null){

            actualProduct.setDiscount(product.getDiscount());

        }

        if(product.getPrice() != productUpd.getPrice()){

            actualProduct.setPrice(product.getPrice());

        }

//        ARRUMAR, MESMO ESTANDO IGUAL ESTÁ CAINDO AQUI - usar hashcode?

        if(product.getDescription() != null
        && !StringUtils.equals(product.getDescription(), productUpd.getDescription())){

            actualProduct.setDescription(product.getDescription());

        }

        actualProduct.setAvailable(product.isAvailable());

        return actualProduct;

    }

}
