CREATE TABLE clientes
(
    id            BIGSERIAL PRIMARY KEY,
    nome          VARCHAR(255) NOT NULL,
    email         VARCHAR(255) UNIQUE,
    telefone      VARCHAR(20),
    cpf_cnpj      VARCHAR(18) UNIQUE,
    endereco      VARCHAR(500),
    bairro        VARCHAR(100),
    cidade        VARCHAR(100),
    uf            VARCHAR(2),
    cep           VARCHAR(9),
    data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_clientes_nome ON clientes (nome);
CREATE INDEX idx_clientes_email ON clientes (email);
CREATE INDEX idx_clientes_cpf_cnpj ON clientes (cpf_cnpj);