package br.com.productmanagement.frameworks.web;

import br.com.productmanagement.interfaceadapters.controller.ProductBatchController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@RestController
@RequestMapping(value = "/productBatch")
@Tag(name = "Carga de produtos", description = "Endpoint para envio de aquivo para carga de produtos")
public class ProductBatchWeb {

    @Resource
    private ProductBatchController productBatchController;

    @Operation(summary = "Envio de arquivo csv para carga de produtos em lote")
    @PostMapping(value = "/importProducts", consumes= MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> importProducts(@RequestParam("file") MultipartFile file) {

        return productBatchController.scheduleProductJob(file);

    }

}
