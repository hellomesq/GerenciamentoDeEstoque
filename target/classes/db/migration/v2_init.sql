CREATE TABLE usuarios (
    id SERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    nome VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    senha VARCHAR(255) -- pode ser NULL se não usar senha
);
