package br.com.productmanagement.helper;

import br.com.productmanagement.entities.Product;
import br.com.productmanagement.util.enums.ProductCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class ProductTestHelper {

    public Product newProduct(){

        Product product = new Product();

        product.setName("Creatina Creapure Pote 250g Max Titanium Sabor Sem sabor");
        product.setDescription("Creatina Monohidratada. Não contém glúten");
        product.setProductCategory(ProductCategory.SAUDE);
        product.setModel("Creatina");
        product.setAvailable(true);
        product.setAvailableQuantity(50);
        product.setColor("Sem sabor");
        product.setSize("250");
        product.setImageURL("https://http2.mlstatic.com/D_NQ_NP_644384-MLU75987424605_042024-O.webp");
        product.setSupplier("Max Titanium");
        product.setBrand("Max Titanium");
        product.setPrice(29.90);
        product.setProductHeight(15.3);
        product.setProductWidth(12.5);
        product.setProductDepth(12.5);
        product.setProductWeight(250);
        product.setPackagingHeight(16);
        product.setPackagingWidth(15);
        product.setPackagingDepth(15);
        product.setPackagingWeight(305);

        return product;

    }

    public Page<Product> newProductPage(){

        Product product = newProduct();

        List<Product> products = Collections.singletonList(product);
        Pageable pageable = PageRequest.of(0, 10);

        return new PageImpl<>(products, pageable, products.size());

    }

    public Product productUpd(){

        Product product = new Product();

        product.setName("Creatina Creapure Pote 250g Max Titanium Sabor Sem sabor");
        product.setDescription("Creatina Monohidratada. Não contém glúten");
        product.setProductCategory(ProductCategory.SAUDE);
        product.setModel("Creatina");
        product.setAvailable(true);
        product.setAvailableQuantity(50);
        product.setColor("Sem sabor");
        product.setSize("250");
        product.setImageURL("https://http2.mlstatic.com/D_NQ_NP_644384-MLU75987424605_042024-O.webp");
        product.setSupplier("Max Titanium");
        product.setBrand("Max Titanium");
        product.setPrice(29.90);
        product.setProductHeight(15.3);
        product.setProductWidth(12.5);
        product.setProductDepth(12.5);
        product.setProductWeight(250);
        product.setPackagingHeight(16);
        product.setPackagingWidth(15);
        product.setPackagingDepth(15);
        product.setPackagingWeight(305);

        return product;

    }


}
