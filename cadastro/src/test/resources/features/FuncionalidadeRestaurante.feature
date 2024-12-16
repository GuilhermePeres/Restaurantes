# language: pt
Funcionalidade: API - Restaurantes

  Fundo:
    Dado que eu tenho os dados de um restaurante

  Cenário: Cadastrar um novo restaurante
    Quando enviar requisição para cadastrar o restaurante
    Então o restaurante deve ser cadastrado com sucesso

  Cenário: Buscar restaurantes por nome
    Quando enviar requisição para buscar restaurantes por nome
    Então a resposta deve conter os restaurantes buscados por nome

  Cenário: Buscar restaurantes por tipoCozinha
    Quando enviar requisição para buscar restaurantes por tipoCozinha
    Então a resposta deve conter os restaurantes buscados por tipoCozinha

  Cenário: Buscar restaurantes por localizacao
    Quando enviar requisição para buscar restaurantes por localizacao
    Então a resposta deve conter os restaurantes buscados por localizacao

  Cenário: Verificar disponibilidade de lugares
    Dado que eu tenho os dados de um restaurante com id
    Quando enviar requisição para verificar a disponibilidade de lugares
    Então a resposta deve conter o número de lugares disponíveis

  Cenário: Atualizar um restaurante
    Dado que eu tenho os dados de um restaurante com id
    Quando enviar requisição para atualizar o restaurante
    Então a resposta deve conter o restaurante atualizado

  Cenário: Remover um restaurante
    Dado que eu tenho os dados de um restaurante com id
    Quando enviar requisição para remover o restaurante
    Então o restaurante deve ser removido