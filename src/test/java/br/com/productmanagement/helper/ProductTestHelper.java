package br.com.productmanagement.helper;

import br.com.productmanagement.entities.Product;
import br.com.productmanagement.interfaceadapters.presenters.dto.ProductDto;
import br.com.productmanagement.util.SkuGenerator;
import br.com.productmanagement.util.enums.ProductCategory;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class ProductTestHelper {

    @Resource
    private SkuGenerator skuGenerator;

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

        String category = product.getProductCategory().toString();
        String brand = product.getBrand();
        String name = product.getName();
        String model = product.getModel();
        String color = product.getColor();
        String size = product.getSize();

        String sku = skuGenerator.generateSku(category, brand, name, model, color, size);

        product.setSku(sku);

        return product;

    }

    public ProductDto newProductDto(){

        ProductDto productDto = new ProductDto();

        productDto.setName("Creatina Creapure Pote 250g Max Titanium Sabor Sem sabor");
        productDto.setDescription("Creatina Monohidratada. Não contém glúten");
        productDto.setProductCategory(ProductCategory.SAUDE);
        productDto.setModel("Creatina");
        productDto.setAvailable(true);
        productDto.setAvailableQuantity(50);
        productDto.setColor("Sem sabor");
        productDto.setSize("250");
        productDto.setImageURL("https://http2.mlstatic.com/D_NQ_NP_644384-MLU75987424605_042024-O.webp");
        productDto.setSupplier("Max Titanium");
        productDto.setBrand("Max Titanium");
        productDto.setPrice(29.90);
        productDto.setProductHeight(15.3);
        productDto.setProductWidth(12.5);
        productDto.setProductDepth(12.5);
        productDto.setProductWeight(250);
        productDto.setPackagingHeight(16);
        productDto.setPackagingWidth(15);
        productDto.setPackagingDepth(15);
        productDto.setPackagingWeight(305);

        return productDto;

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

    public ProductDto productUpdDto(){

        ProductDto productDto = new ProductDto();

        productDto.setName("Creatina Creapure Pote 250g Max Titanium Sabor Sem sabor");
        productDto.setDescription("Creatina Monohidratada. Não contém glúten");
        productDto.setProductCategory(ProductCategory.SAUDE);
        productDto.setModel("Creatina");
        productDto.setAvailable(true);
        productDto.setAvailableQuantity(50);
        productDto.setColor("Sem sabor");
        productDto.setSize("250");
        productDto.setImageURL("https://http2.mlstatic.com/D_NQ_NP_644384-MLU75987424605_042024-O.webp");
        productDto.setSupplier("Max Titanium");
        productDto.setBrand("Max Titanium");
        productDto.setPrice(29.90);
        productDto.setProductHeight(15.3);
        productDto.setProductWidth(12.5);
        productDto.setProductDepth(12.5);
        productDto.setProductWeight(250);
        productDto.setPackagingHeight(16);
        productDto.setPackagingWidth(15);
        productDto.setPackagingDepth(15);
        productDto.setPackagingWeight(305);

        return productDto;

    }

}
