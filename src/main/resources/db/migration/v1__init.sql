CREATE TABLE produtos (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT,
    quantidade_atual INT DEFAULT 0
);

CREATE TABLE movimentacoes_estoque (
    id SERIAL PRIMARY KEY,
    tipo VARCHAR(10) NOT NULL, -- "ENTRADA" ou "SAIDA"
    quantidade INT NOT NULL,
    motivo VARCHAR(255),
    data_movimentacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    produto_id INT NOT NULL REFERENCES produtos(id)
);
