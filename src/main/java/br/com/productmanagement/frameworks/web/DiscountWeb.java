package br.com.productmanagement.frameworks.web;

import br.com.productmanagement.interfaceAdapters.controller.DiscountController;
import br.com.productmanagement.interfaceAdapters.presenters.dto.DiscountDto;
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

import java.util.UUID;

@RestController
@RequestMapping(value = "/discount")
@Tag(name = "Descontos", description = "Exibe os métodos para cadastro, consulta e atualização de descontos")
public class DiscountWeb {

    @Resource
    private DiscountController discountController;

    @Operation(summary = "Insere um novo desconto")
    @PostMapping(value = "/create")
    public ResponseEntity<DiscountDto> insert(@Valid @RequestBody DiscountDto dto) throws ValidationsException {

        return ResponseEntity.ok(discountController.insert(dto));

    }

    @Operation(summary = "Insere um novo desconto")
    @PutMapping(value = "/update/{id}")
    public ResponseEntity<DiscountDto> update(@PathVariable UUID id,
                                              @Valid @RequestBody DiscountDto dto) throws ValidationsException {

        return ResponseEntity.ok(discountController.update(id, dto));

    }

    @Operation(summary = "Consultar todos os descontos")
    @GetMapping(value = "/list")
    public ResponseEntity<PagedResponse<DiscountDto>> findAll(@Parameter(description = "Default value 10. Max value 1000", example = "10")
                                                              @RequestParam(required = false) Integer pageSize,
                                                              @Parameter(description = "Default value 0", example = "0")
                                                              @RequestParam(required = false) Integer initialPage){

        Pagination page = new Pagination(initialPage, pageSize);
        return ResponseEntity.ok(discountController.findAll(page));

    }

    @Operation(summary = "Consulta desconto por id")
    @GetMapping(value = "/id/{id}")
    public ResponseEntity<DiscountDto> findById(@PathVariable UUID id) throws ValidationsException {

        return ResponseEntity.ok(discountController.findById(id));

    }

    @Operation(summary = "Consulta desconto por cupom")
    @GetMapping(value = "/coupon/{coupon}")
    public ResponseEntity<DiscountDto> findByCoupon(@PathVariable String coupon) throws ValidationsException {

        return ResponseEntity.ok(discountController.findByCoupon(coupon));

    }

    @Operation(summary = "Consulta desconto por categoria de produto")
    @GetMapping(value = "/category/{category}")
    public ResponseEntity<DiscountDto> findByProductCategory(@PathVariable ProductCategory category) throws ValidationsException {

        return ResponseEntity.ok(discountController.findByProductCategory(category));

    }

}
