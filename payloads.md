# Exemplos de payload

## Cadastrar e atualizar produto 

### Sem desconto

    {
        "name": "Creatina Creapure Pote 250g Max Titanium Sabor Sem sabor",
        "description": "Creatina Monohidratada. Não contém glúten",
        "productCategory": "SAUDE",
        "model": "Max Titanium",
        "available": true,
        "availableQuantity": 10,
        "color": "Branco",
        "size": "250",
        "imageURL": "https://http2.mlstatic.com/D_NQ_NP_941774-MLA52624902588_112022-O.webp",
        "supplier": "Max Titanium",
        "brand": "Max Titanium",
        "price": 29.90,
        "productHeight": 12.3,
        "productWidth": 13.5,
        "productDepth": 13.5,
        "productWeight": 250,
        "packagingHeight": 15.1,
        "packagingWidth": 15.1,
        "packagingDepth": 15.1,
        "packagingWeight": 305
    }

### Com desconto específico (criar o desconto antes)

    {
        "name": "Creatina Creapure Pote 250g Max Titanium Sabor Sem sabor",
        "description": "Creatina Monohidratada. Não contém glúten",
        "productCategory": "SAUDE",
        "model": "Max Titanium",
        "available": true,
        "availableQuantity": 10,
        "color": "Branco",
        "size": "250",
        "imageURL": "https://http2.mlstatic.com/D_NQ_NP_941774-MLA52624902588_112022-O.webp",
        "supplier": "Max Titanium",
        "brand": "Max Titanium",
        "price": 29.90,
        "productHeight": 12.3,
        "productWidth": 13.5,
        "productDepth": 13.5,
        "productWeight": 250,
        "packagingHeight": 15.1,
        "packagingWidth": 15.1,
        "packagingDepth": 15.1,
        "packagingWeight": 305,
        "discount": {
            "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6"
        }
    }

## Cadastrar e atualizar desconto

### Desconto para categoria

    {
        "discountType": "PERCENTAGE",
        "description": "Adquira 5% de desconto na compra a partir de 3 unidades em qualquer produto de SAUDE",
        "discountValue": 5,
        "discountStartDate": "2024-05-20T00:00:00Z",
        "discountFinishDate": "2024-07-30T23:59:59Z",
        "minimumQuantityToDiscount": 3,
        "productCategory": "SAUDE",
        "active": true
    }

### Cupom de desconto

    {
        "coupon": "FIAP02",
        "discountType": "PERCENTAGE",
        "description": "Adquira 2% de desconto usando o cupom FIAP02",
        "discountValue": 2,
        "discountStartDate": "2024-05-20T00:00:00Z",
        "discountFinishDate": "2024-07-30T23:59:59Z",
        "active": true
    }

### Desconto específico para vincular a um ou mais produtos

    {
        "discountType": "VALUE",
        "description": "Adquira 5 reais de desconto na compra a partir de 3 unidades",
        "discountValue": 2,
        "discountStartDate": "2024-05-20T00:00:00Z",
        "discountFinishDate": "2024-07-30T23:59:59Z",
        "minimumQuantityToDiscount": 3,
        "active": true
    }

## Criar e alterar reservas

### Criar reservas de produtos (passar uma lista com o SKU e quantidade)

    {
        "reservations": [
            {
                "sku": "SAU-MAXT-CRE-MAX-BRA-000",
                "requestedQuantity": 2
            },
            {
                "sku": "SAU-MAXT-CRE-MAX-BRA-000",
                "requestedQuantity": 2
            }
        ]
    }

### Atualizar quantidade de reserva (passar uma lista com id de reserva e quantidade)

    {
        "reservations": [
            {
                "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
                "requestedQuantity": 3
            },
            {
                "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
                "requestedQuantity": 0
            }
        ]
    }

### Confirmar e cancelar reservas

    {
        "reservations": [
            {
                "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6"
            },
            {
                "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6"
            }
        ]
    }