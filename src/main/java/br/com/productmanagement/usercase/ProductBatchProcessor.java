package br.com.productmanagement.usercase;

import br.com.productmanagement.entities.Product;
import br.com.productmanagement.interfaceadapters.gateways.ProductGateway;
import jakarta.annotation.Resource;
import org.springframework.batch.item.ItemProcessor;

public class ProductBatchProcessor implements ItemProcessor<Product, Product> {

    @Resource
    private ProductGateway productGateway;

    @Resource
    private ProductBusiness productBusiness;

    @Override
    public Product process(Product product) throws Exception {

        product = productBusiness.create(product);

        Product productSave = productGateway.findBySku(product.getSku());

        if(productSave != null){

            product = productBusiness.updateSku(product, productSave);

        }

        product = productGateway.save(product);

        return product;

    }

}
