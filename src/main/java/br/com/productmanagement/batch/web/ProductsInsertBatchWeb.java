package br.com.productmanagement.batch.web;

import br.com.productmanagement.batch.controller.ProductsInsertBatchController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Controller
@RequestMapping(value = "/productsInsertBatch")
@Tag(name = "Carga de produtos", description = "Endpoint para envio de aquivo para carga de produtos")
public class ProductsInsertBatchWeb {

    @Resource
    private ProductsInsertBatchController productsInsertBatchController;

    @Operation(summary = "Envio de arquivo csv para carga de produtos em lote")
    @PostMapping("/importProducts")
    public ResponseEntity importProducts(MultipartFile file,
                                         @Parameter(description = "Data e hora que o arquivo deverá ser executado." +
                                                 " Não preencher se quiser que a execução seja imediata."
                                                 , example = "2024-05-31T15:00:00.000000")
                                         @RequestParam(required = false) LocalDateTime timeToSchedule) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Favor anexar um arquivo.");
        }

        return productsInsertBatchController.scheduleProductJob(file, timeToSchedule);

    }

}
