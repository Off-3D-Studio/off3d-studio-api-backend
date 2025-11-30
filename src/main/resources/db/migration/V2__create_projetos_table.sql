CREATE TABLE projetos
(
    id                    BIGSERIAL PRIMARY KEY,
    nome                  VARCHAR(255) NOT NULL,
    descricao             TEXT,
    status                VARCHAR(20)  NOT NULL DEFAULT 'RASCUNHO',
    data_inicio           DATE,
    data_previsao_termino DATE,
    data_termino          DATE,
    pedido_id             BIGINT,

    CONSTRAINT fk_projeto_pedido FOREIGN KEY (pedido_id) REFERENCES pedidos (id) ON DELETE SET NULL
);

CREATE INDEX idx_projetos_status ON projetos (status);
CREATE INDEX idx_projetos_data_previsao ON projetos (data_previsao_termino);
CREATE INDEX idx_projetos_pedido ON projetos (pedido_id);