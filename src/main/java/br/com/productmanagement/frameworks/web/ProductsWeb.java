package br.com.productmanagement.frameworks.web;

import br.com.productmanagement.interfaceAdapters.controller.ProductsController;
import br.com.productmanagement.interfaceAdapters.presenters.dto.ProductDto;
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

    @Operation(summary = "Consultar todos os produtos")
    @GetMapping
    public ResponseEntity<PagedResponse<ProductDto>> findAll(@Parameter(description = "Default value 10. Max value 1000", example = "10")
                                                             @RequestParam(required = false) Integer pageSize,
                                                             @Parameter(description = "Default value 0", example = "0")
                                                             @RequestParam(required = false) Integer initialPage){

        Pagination page = new Pagination(initialPage, pageSize);
        return ResponseEntity.ok(productsController.findAll(page));

    }

    @Operation(summary = "Insere um novo produto")
    @PutMapping
    public ResponseEntity<ProductDto> insert(@Valid @RequestBody ProductDto dto){

        return ResponseEntity.ok(productsController.insert(dto));
    }

}
