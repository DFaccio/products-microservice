package br.com.productmanagement.batch;

import br.com.productmanagement.entities.Discount;
import br.com.productmanagement.entities.Product;
import br.com.productmanagement.interfaceAdapters.gateways.DiscountGateway;
import br.com.productmanagement.interfaceAdapters.gateways.ProductGateway;
import br.com.productmanagement.interfaceAdapters.helper.ProductHelper;
import br.com.productmanagement.interfaceAdapters.presenters.ProductPresenter;
import br.com.productmanagement.usercase.ProductBusiness;
import br.com.productmanagement.util.SkuGenerator;
import br.com.productmanagement.util.exception.ValidationsException;
import jakarta.annotation.Resource;
import org.springframework.batch.item.ItemProcessor;

import java.time.LocalDateTime;
import java.util.Optional;

public class ProductProcessor implements ItemProcessor<Product, Product> {

    @Resource
    private ProductGateway productGateway;

    @Resource
    private DiscountGateway discountGateway;

    @Resource
    private ProductPresenter productPresenter;

    @Resource
    private ProductBusiness productBusiness;

    @Resource
    private ProductHelper productHelper;

    @Resource
    private SkuGenerator skuGenerator;

    @Override
    public Product process(Product product) throws Exception {

        if(product.getDiscount() != null){
            Optional<Discount> discount = discountGateway.findById(product.getDiscount().getId());
            if(discount.isEmpty()){
                product.setDiscount(new Discount());
            }

        }

        product = productBusiness.create(product);

        Product productSave = productGateway.findBySku(product.getSku());

        if(productSave != null){

            product = productBusiness.updateSku(product, productSave);

        }

        product = productGateway.save(product);

        return product;

    }

}
