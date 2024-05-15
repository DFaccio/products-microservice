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

        if(product.getDiscount().getId() != null){
            Optional<Discount> discount = discountGateway.findById(product.getDiscount().getId());
            if(discount.isEmpty()){
                product.setDiscount(null);
            }

        }else{
//            product = setProductWithoutDiscount(product);
            product.setDiscount(null);
        }

        product = productBusiness.create(product);

        Product productSave = productGateway.findBySku(product.getSku());

        if(productSave != null){

            product = productBusiness.updateSku(product, productSave);

        }

        product = productGateway.save(product);

        return product;

    }

    private Product setProductWithoutDiscount(Product product){

        Product productWhithoutDiscount = new Product();

        productWhithoutDiscount.setSku(product.getSku());
        productWhithoutDiscount.setName(product.getName());
        productWhithoutDiscount.setDescription(product.getDescription());
        productWhithoutDiscount.setProductCategory(product.getProductCategory());
        productWhithoutDiscount.setModel(product.getModel());
        productWhithoutDiscount.setAvailable(product.isAvailable());
        productWhithoutDiscount.setAvailableQuantity(product.getAvailableQuantity());
        productWhithoutDiscount.setColor(product.getColor());
        productWhithoutDiscount.setSize(product.getSize());
        productWhithoutDiscount.setImageURL(product.getImageURL());
        productWhithoutDiscount.setSupplier(product.getSupplier());
        productWhithoutDiscount.setBrand(product.getBrand());
        productWhithoutDiscount.setPrice(product.getPrice());
        productWhithoutDiscount.setProductHeight(product.getProductHeight());
        productWhithoutDiscount.setProductWidth(product.getProductWidth());
        productWhithoutDiscount.setProductDepth(product.getProductDepth());
        productWhithoutDiscount.setProductWeight(product.getProductWeight());
        productWhithoutDiscount.setPackagingHeight(product.getPackagingHeight());
        productWhithoutDiscount.setPackagingHeight(product.getPackagingHeight());
        productWhithoutDiscount.setPackagingDepth(product.getPackagingDepth());
        productWhithoutDiscount.setPackagingWeight(product.getPackagingWeight());
        productWhithoutDiscount.setCreationDate(product.getCreationDate());

        return productWhithoutDiscount;

    }
}
