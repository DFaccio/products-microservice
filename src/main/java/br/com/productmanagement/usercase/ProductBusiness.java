package br.com.productmanagement.usercase;

import br.com.productmanagement.entities.Product;
import br.com.productmanagement.interfaceAdapters.helper.ProductHelper;
import br.com.productmanagement.interfaceAdapters.presenters.dto.ProductDto;
import br.com.productmanagement.util.SkuGenerator;
import br.com.productmanagement.util.enums.Operation;
import br.com.productmanagement.util.exception.ValidationsException;
import br.com.productmanagement.util.pagination.PagedResponse;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ProductBusiness {


    @Resource
    private ProductHelper productHelper;

    @Resource
    private SkuGenerator skuGenerator;

    public Product create(Product product) throws ValidationsException {

        String sku;

        if(product.getSku() == null) {

            sku = skuGenerator.generateSku(product.getProductCategory().toString(),
                    product.getBrand(),
                    product.getName(),
                    product.getModel(),
                    product.getColor(),
                    product.getSize());

            product.setSku(sku);

        }

        if(product.getCreationDate() == null) {
            product.setCreationDate(LocalDateTime.now());
        }

        return product;

    }


    public Product updateSku(Product product, Product productUpd){

        Product actualProduct = productUpd;

        if(product.getAvailableQuantity() != null
        && product.getAvailableQuantity() > 0 ){

            actualProduct = productHelper.updateProductKeeping(actualProduct, product.getAvailableQuantity(), productUpd.getAvailableQuantity(), Operation.ARRIVAL);

        }

        if(product.getDiscount() != null){

            actualProduct.setDiscount(product.getDiscount());

        }

        if(product.getPrice() != productUpd.getPrice()){

            actualProduct.setPrice(product.getPrice());

        }

        if(product.getDescription() != null
        && !StringUtils.equals(product.getDescription(), productUpd.getDescription())){

            actualProduct.setDescription(product.getDescription());

        }

        actualProduct.setAvailable(product.isAvailable());

        return actualProduct;

    }

    public Page<Product> checkForAvailableDiscount(Page<Product> product){

        if(product.hasContent()){

            product = product.map(prod -> {

                prod.setDiscount(productHelper.validadeProductDiscount(prod));

                return prod;

            });

        }

        return product;

    }

}
