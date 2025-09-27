
## Integrantes

- Hellen Marinho Cordeiro 558841
- Heloisa Alves de Mesquita 559145

## Projeto

O Projeto Diamante é uma aplicação de gerenciamento de estoque desenvolvida para um Sebo, com o objetivo de auxiliar o proprietário no controle eficiente de seus produtos e movimentações.
A aplicação permite cadastrar produtos, editar informações, excluir itens e registrar entradas e saídas de estoque.

## Funcionalidades

- Cadastro de produtos com nome, descrição e quantidade disponível.
- Listagem de produtos cadastrados.
- Edição e exclusão de produtos.
- Registro de movimentações de estoque (entrada e saída).
- Controle histórico de movimentações por produto.

## Endpoints Disponíveis

Produtos
- GET /produtos/ — Redireciona para a lista de produtos.
- GET /produtos/listar — Exibe todos os produtos cadastrados.
- GET /produtos/novo — Formulário para cadastrar um novo produto.
- POST /produtos/salvar — Salva um novo produto ou atualiza um existente.
- GET /produtos/editar/{id} — Formulário para editar um produto existente.
- POST /produtos/excluir/{id} — Exclui um produto pelo ID.

Movimentações
- GET /movimentacoes/nova?produtoId={id} — Formulário para registrar uma movimentação de entrada ou saída para um produto específico.
- POST /movimentacoes/salvar — Salva uma movimentação de estoque.
- GET /movimentacoes/listar — Lista todas as movimentações registradas.

## Como rodar a aplicação

Crie um arquivo na raiz .env:

```bash
  GOOGLE_CLIENT_ID=376861506229-sstu92t6lcp31iehgh4vqnii4416pf21.apps.googleusercontent.com
  GOOGLE_CLIENT_SECRET=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
  ADMIN_EMAIL=helo.alvz@gmail.com
```

Acesse no navegador:

```bash
  http://localhost:8080
```

