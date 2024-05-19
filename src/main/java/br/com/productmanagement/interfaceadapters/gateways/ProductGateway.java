package br.com.productmanagement.interfaceadapters.gateways;

import br.com.productmanagement.entities.Product;
import br.com.productmanagement.frameworks.db.ProductRepository;
import br.com.productmanagement.util.enums.ProductCategory;
import br.com.productmanagement.util.exception.ValidationsException;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductGateway {

    @Resource
    private ProductRepository productRepository;

    public Product save(Product product) throws ValidationsException {

        return productRepository.save(product);

    }

    public Page<Product> findAll(Pageable pageable) throws ValidationsException {

        return productRepository.findAll(pageable);

    }

    public Product findBySku(String sku) throws ValidationsException {

        return productRepository.findBySku(sku);

    }

    public Page<Product> findAllByNameAndSupplierAndProductCategory(String name, String supplier, ProductCategory category, Pageable pageable){

        return productRepository.findByNameContainingIgnoreCaseAndSupplierContainingIgnoreCaseAndProductCategory(name, supplier, category, pageable);

    }

    public Page<Product> findAllByNameAndSupplier(String name, String supplier, Pageable pageable){

        return productRepository.findByNameContainingIgnoreCaseAndSupplierContainingIgnoreCase(name, supplier, pageable);

    }

    public Page<Product> findAllByNameAndProductCategory(String name, ProductCategory category, Pageable pageable){

        return productRepository.findByNameContainingIgnoreCaseAndProductCategory(name, category, pageable);

    }

    public Page<Product> findAllBySupplierAndProductCategory(String supplier, ProductCategory category, Pageable pageable){

        return productRepository.findBySupplierContainingIgnoreCaseAndProductCategory(supplier, category, pageable);

    }

    public Page<Product> findAllByName(String name, Pageable pageable){

        return productRepository.findByNameContainingIgnoreCase(name, pageable);

    }

    public Page<Product> findAllBySupplier(String supplier, Pageable pageable){

        return productRepository.findBySupplierContainingIgnoreCase(supplier, pageable);

    }

    public Page<Product> findAllByProductCategory(ProductCategory category, Pageable pageable){

        return productRepository.findByProductCategory(category, pageable);

    }

}
