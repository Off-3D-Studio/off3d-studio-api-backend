CREATE TABLE pedidos
(
    id               BIGSERIAL PRIMARY KEY,
    cliente          VARCHAR(255)   NOT NULL,
    descricao        TEXT           NOT NULL,
    valor            DECIMAL(19, 2) NOT NULL,
    status           VARCHAR(20)    NOT NULL DEFAULT 'PENDENTE',
    data_criacao     TIMESTAMP      NOT NULL,
    data_atualizacao TIMESTAMP      NOT NULL
);

CREATE INDEX idx_pedidos_status ON pedidos (status);
CREATE INDEX idx_pedidos_cliente ON pedidos (cliente);