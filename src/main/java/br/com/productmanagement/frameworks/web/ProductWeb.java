package br.com.productmanagement.frameworks.web;

import br.com.productmanagement.interfaceadapters.controller.ProductController;
import br.com.productmanagement.interfaceadapters.presenters.dto.ProductDto;
import br.com.productmanagement.util.enums.ProductCategory;
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
@Tag(name = "Produtos", description = "Exibe os métodos para cadastro, consulta e atualização de produtos")
public class ProductWeb {

    @Resource
    private ProductController productController;

    @Operation(summary = "Insere um novo produto")
    @PostMapping(value = "/create")
    public ResponseEntity<ProductDto> insert(@Valid @RequestBody ProductDto dto) throws ValidationsException {

        return ResponseEntity.ok(productController.insert(dto));

    }

    @Operation(summary = "Altera algumas informações de um produto")
    @PutMapping(value = "/update/{sku}")
    public ResponseEntity<ProductDto> update(@PathVariable String sku,
                                             @Valid @RequestBody ProductDto dto) throws ValidationsException {

        return ResponseEntity.ok(productController.update(sku, dto));

    }

    @Operation(summary = "Consultar todos os produtos, podendo filtrar por nome, categoria ou fornecedor")
    @GetMapping(value = "/list")
    public ResponseEntity<PagedResponse<ProductDto>> findAll(@Parameter(description = "Default value 10. Max value 1000", example = "10")
                                                             @RequestParam(required = false) Integer pageSize,
                                                             @Parameter(description = "Default value 0", example = "0")
                                                             @RequestParam(required = false) Integer initialPage,
                                                             @Parameter(description = "Nome do produto", example = "creatina")
                                                             @RequestParam(required = false) String name,
                                                             @Parameter(description = "Categoria do produto", example = "saude")
                                                             @RequestParam(required = false) ProductCategory category,
                                                             @Parameter(description = "Fornecedor", example = "max titanium")
                                                             @RequestParam(required = false) String supplier) throws ValidationsException {

        Pagination page = new Pagination(initialPage, pageSize);
        return ResponseEntity.ok(productController.findAll(page, name, category, supplier));

    }

    @Operation(summary = "Procura um produto pelo SKU")
    @GetMapping(value = "/sku/{sku}")
    public ResponseEntity<ProductDto> findBySku(@PathVariable String sku) throws ValidationsException {

        return ResponseEntity.ok(productController.findBySku(sku));

    }

}
