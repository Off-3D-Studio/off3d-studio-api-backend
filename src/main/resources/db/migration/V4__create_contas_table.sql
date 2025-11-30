CREATE TABLE contas
(
    id              BIGSERIAL PRIMARY KEY,
    descricao       VARCHAR(255)   NOT NULL,
    valor           DECIMAL(19, 2) NOT NULL,
    tipo            VARCHAR(10)    NOT NULL CHECK (tipo IN ('PAGAR', 'RECEBER')),
    status          VARCHAR(10)    NOT NULL DEFAULT 'PENDENTE' CHECK (status IN ('PENDENTE', 'PAGO', 'ATRASADO')),
    data_vencimento DATE           NOT NULL,
    data_pagamento  DATE,
    data_criacao    TIMESTAMP               DEFAULT CURRENT_TIMESTAMP,
    pedido_id       BIGINT,

    CONSTRAINT fk_conta_pedido FOREIGN KEY (pedido_id) REFERENCES pedidos (id) ON DELETE SET NULL
);

CREATE INDEX idx_contas_tipo ON contas (tipo);
CREATE INDEX idx_contas_status ON contas (status);
CREATE INDEX idx_contas_data_vencimento ON contas (data_vencimento);
CREATE INDEX idx_contas_pedido ON contas (pedido_id);