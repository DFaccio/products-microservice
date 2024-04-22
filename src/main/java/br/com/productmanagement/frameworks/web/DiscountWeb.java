package br.com.productmanagement.frameworks.web;

import br.com.productmanagement.interfaceAdapters.controller.DiscountController;
import br.com.productmanagement.interfaceAdapters.presenters.dto.DiscountDto;
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
@RequestMapping(value = "/discounts")
@Tag(name = "Descontos", description = "Exibe os métodos para cadastro e controle de descontos")
public class DiscountWeb {

    @Resource
    private DiscountController discountController;

    @Operation(summary = "Consultar todos os descontos")
    @GetMapping
    public ResponseEntity<PagedResponse<DiscountDto>> findAll(@Parameter(description = "Default value 10. Max value 1000", example = "10")
                                                              @RequestParam(required = false) Integer pageSize,
                                                              @Parameter(description = "Default value 0", example = "0")
                                                              @RequestParam(required = false) Integer initialPage){

        Pagination page = new Pagination(initialPage, pageSize);
        return ResponseEntity.ok(discountController.findAll(page));

    }

    @Operation(summary = "Insere um novo desconto")
    @PutMapping
    public ResponseEntity<DiscountDto> insert(@Valid @RequestBody DiscountDto dto){

        return ResponseEntity.ok(discountController.insert(dto));

    }

}
