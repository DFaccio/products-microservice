# Serviço de gerenciamento de estoque de produtos - Desafio Tech Challenge - Módulo 4

### Este repositório refere-se ao microsserviço de produtos. No total, o projeto envolve 4 microsserviços, sendo eles:

1. Cliente
2. **Produto** *(este)*
3. Pedido
4. Logística

### Este microsserviço é responsável pelo processamento dos produtos, desde a criação até o controle de estoque. Isto inclui:

* Cadastrar desconto
* Alterar desconto
* Cadastrar produto
* Atualizar produto (incluir desconto, atualizar status, quantidade disponível, descrição e preço)
* Criar reserva de produtos
* Alterar reserva de produtos (atualizar quantidade, cancelar, confirmar)
* Incluir produtos em lote via arquivo csv (processamento batch)

### Tecnologias

* Spring Boot para a estrutura do serviço
* Spring Data JPA para manipulação de dados dos produtos
* PostgreSQL para persistência 
* Spring Batch para carga de dados de produtos

### [SWAGGER](http://localhost:7077/swagger-ui/index.html#/)
### [PGAdmin](http://localhost:16543/browser/)

### Desenvolvedores

- [Aydan Amorim](https://github.com/AydanAmorim)
- [Danilo Faccio](https://github.com/DFaccio)
- [Erick Ribeiro](https://github.com/erickmatheusribeiro)
- [Isabela França](https://github.com/fysabelah)

### Instruções

    Para criar descontos, produtos e reservas de produtos, usar os padrões de payload de exemplo contidos no arquivo payloads.md, na raiz do projeto.
    
    É gerado um SKU para os produtos cadastrados. Ao incluir um produto já existente, com os mesmos atributos como categoria, marca, nome, modelo, cor e tamanho, será gerado o SKU e vereificado se o mesmo já está cadastrado. Caso positivo, não será criado um novo produto, mas o existente será atualizado com as demais informações, como descrição, quantidade disponível, disponibilidade, preço e desconto.
    Para criar um produto com desconto, ou atualizar o desconto em um produto existente, precisa criar o desconto primeiro.
    
    O serviço só permite criar um desconto por categoria de produto.
    Para desconto específico para um produto, não informar a categoria e atualizar o produto com o id do desconto criado especificamente para ele.
    Para cupom de desconto é verificado se já foi criado um cupom com o mesmo nome, não permitindo duplicidade.
    Pode-se criar desconto de valor ou de porcentagem. Eles são verificados ao realizar a reserva de um produto, de acordo com a categoria e se é por valor ou porcentagem, retornando o valor calculado em cima do valor do produto e da quantidade solicitada.

    Para produtos que não possuem desconto específico, a aplicação verificará se existe um desconto para a categoria do produto.
    Toda  verificação de desconto também valida se o desconto está ativo e dentro do período de vigência cadastrado.
    Ao buscar por produtos, a aplicação verifica se há desconto ativo e dentro do período de vigência cadastrado para retornar informações de desconto para todos os produtos elegíveis.

    Ao criar uma reserva, a quantidade solicitada já é removida do estoque do produto.
    Ao atualizar uma reserva, se a quantidade for maior, é verificada a disponibilidade do produto em estoque, retornando UNAVAILABLE caso a quantidade solicitada seja maior que a disponível, e a diferença não é removida do estoque.
    Se aquantidade alterada for para menor, a diferença é devolvida para o estoque.
    Ao cancelar uma reserva, ela não poderá mais ser confirmada, sendo necessário criar uma nova reserva.
    Uma reserva confirmada poderá ser cancelada, devolvendo a quantidade reservada ao estoque.
    As reservas no status CREATED expiram após 20 minutos da sua última atualização caso não sejam confirmadas, e a quantidade reservada será devolvida ao estoque.
    Reservas expiradas não podem ser confirmadas, alteradas ou canceladas.

    Para carga de produtos, pode-se utilizar o arquivo products.csv na raiz do projeto, e enviar o arquivo pelo endpoint /productBatch/importProducts

    É possível listar todos os produtos, com ou sem filtro. O filtro permite retornar produtos por nome, categoria ou fornecedor.

    É possível listar reservas, com ou sem filtro. O filtro permite retornar reservas por status CREATED, CONFIRMED, CANCELLED, EXPIRED ou UNAVAILABLE.