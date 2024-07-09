package br.com.productmanagement.util.config.documentation;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    private static final String GATEWAY_URL = "http://localhost:7071/order-management-system/product-microservice";

    @Bean
    public OpenAPI apiDocConfig() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Microsserviço para controle de estoque de produtos")
                                .description("APIs para cadastro e atualização de produtos, descontos e reservas")
                                .version("1.0.0")
                ).addServersItem(new Server().url(GATEWAY_URL));
    }
}