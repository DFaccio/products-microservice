package br.com.productmanagement.batch;

import br.com.productmanagement.entities.Product;
import br.com.productmanagement.interfaceAdapters.gateways.DiscountGateway;
import br.com.productmanagement.interfaceAdapters.gateways.ProductGateway;
import br.com.productmanagement.usercase.ProductBusiness;
import jakarta.annotation.Resource;
import org.springframework.batch.item.ItemProcessor;

public class ProductProcessor implements ItemProcessor<Product, Product> {

    @Resource
    private ProductGateway productGateway;

    @Resource
    private DiscountGateway discountGateway;

    @Resource
    private ProductBusiness productBusiness;

    @Override
    public Product process(Product product) throws Exception {

//        if(product.getDiscount().getId() != null){
//            Optional<Discount> discount = discountGateway.findById(product.getDiscount().getId());
//            if(discount.isEmpty()){
//                product.setDiscount(null);
//            }
//
//        }else{
////            product = setProductWithoutDiscount(product);
//            product.setDiscount(null);
//        }

        product = productBusiness.create(product);

        Product productSave = productGateway.findBySku(product.getSku());

        if(productSave != null){

            product = productBusiness.updateSku(product, productSave);

        }

        product = productGateway.save(product);

        return product;

    }

}
