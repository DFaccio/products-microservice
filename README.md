# Serviço de gerenciamento de estoque de produtos - Desafio Tech Challenge - Módulo 4

Este repositório refere-se ao microsserviço de produtos. No total, o projeto envolve 4 microsserviços, sendo eles:

1. Cliente
2. **Produto** *(este)*
3. Pedido
4. Logística

Este microsserviço é responsável pelo processamento dos produtos, desde a criação até o controle de estoque. Isto inclui:

* Cadastrar desconto
* Cadastrar um produto
* Atualizar um produto (incluir desconto, atualizar status, quantidade disponível, descrição e preço)
* Criar reserva de produtos
* Alterar reserva de produtos (atualizar quantidade, cancelar, confirmar)
* Incluir produtos em lote via arquivo csv (processamento batch)

## Tecnologias

* Spring Boot para a estrutura do serviço
* Spring Data JPA para manipulação de dados dos produtos
* PostgreSQL para persistência 
* Spring Batch para carga de dados de produtos

## [SWAGGER](http://localhost:7078/swagger-ui/index.html#/)
## [PGAdmin](http://localhost:16543/browser/)

## Desenvolvedores

- [Aydan Amorim](https://github.com/AydanAmorim)
- [Danilo Faccio](https://github.com/DFaccio)
- [Erick Ribeiro](https://github.com/erickmatheusribeiro)
- [Isabela França](https://github.com/fysabelah)