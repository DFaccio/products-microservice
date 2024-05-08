package br.com.productmanagement.frameworks.web;

import br.com.productmanagement.interfaceAdapters.controller.ProductsController;
import br.com.productmanagement.interfaceAdapters.presenters.dto.ProductDto;
import br.com.productmanagement.interfaceAdapters.presenters.dto.ProductOrderDto;
import br.com.productmanagement.util.exception.ValidationsException;
import br.com.productmanagement.util.pagination.PagedResponse;
import br.com.productmanagement.util.pagination.Pagination;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/products")
@Tag(name = "Produtos", description = "Exibe os métodos para cadastro e controle de produtos")
public class ProductsWeb {

    @Resource
    private ProductsController productsController;

    @Operation(summary = "Insere um novo produto")
    @PostMapping
    public ResponseEntity<ProductDto> insert(@Valid @RequestBody ProductDto dto) throws ValidationsException {

        return ResponseEntity.ok(productsController.insert(dto));

    }

    @Operation(summary = "Consultar todos os produtos")
    @GetMapping
    public ResponseEntity<PagedResponse<ProductDto>> findAll(@Parameter(description = "Default value 10. Max value 1000", example = "10")
                                                             @RequestParam(required = false) Integer pageSize,
                                                             @Parameter(description = "Default value 0", example = "0")
                                                             @RequestParam(required = false) Integer initialPage) throws ValidationsException {

        Pagination page = new Pagination(initialPage, pageSize);
        return ResponseEntity.ok(productsController.findAll(page));

    }

    @Operation(summary = "Procura um produto pelo SKU")
    @GetMapping
    public ResponseEntity<ProductDto> findBySku(@PathVariable String sku) throws ValidationsException {

        return ResponseEntity.ok(productsController.findBySku(sku));

    }

    @Operation(summary = "Atualiza o estoque de produtos a partir de uma nova ordem de compra")
    @PostMapping
    public ResponseEntity<ProductOrderDto> newOrder(@PathVariable String sku, @PathVariable int orderQuantity) throws ValidationsException {

        return ResponseEntity.ok(productsController.updateSkuOnNewOrder(sku, orderQuantity));

    }

    @Operation(summary = "Atualiza o estoque de produtos a partir de uma ordem de compra cancelada")
    @PostMapping
    public void orderCancellation(@PathVariable String sku,@PathVariable int orderQuantity) throws ValidationsException {

        productsController.updateSkuOnOrderCancellation(sku, orderQuantity);

    }

}
