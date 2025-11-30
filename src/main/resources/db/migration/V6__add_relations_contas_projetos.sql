-- Adiciona relação entre contas e pedidos (se não existir)
ALTER TABLE contas
    ADD COLUMN IF NOT EXISTS pedido_id BIGINT;

-- A constraint FK_CONTA_PEDIDO já existe, então NÃO execute esta linha:
-- ALTER TABLE contas ADD CONSTRAINT fk_contas_pedido FOREIGN KEY (pedido_id) REFERENCES pedidos (id) ON DELETE SET NULL;

CREATE INDEX IF NOT EXISTS idx_contas_pedido_id ON contas (pedido_id);

-- Adiciona relação entre contas e projetos (se não existir)
ALTER TABLE contas
    ADD COLUMN IF NOT EXISTS projeto_id BIGINT;

ALTER TABLE contas
    ADD CONSTRAINT IF NOT EXISTS fk_contas_projeto
        FOREIGN KEY (projeto_id) REFERENCES projetos (id) ON DELETE SET NULL;

CREATE INDEX IF NOT EXISTS idx_contas_projeto_id ON contas (projeto_id);

-- Adiciona relação entre projetos e pedidos (se não existir)
ALTER TABLE projetos
    ADD COLUMN IF NOT EXISTS pedido_id BIGINT;

-- A constraint FK_PROJETO_PEDIDO já existe, então NÃO execute esta linha:
-- ALTER TABLE projetos ADD CONSTRAINT fk_projetos_pedido FOREIGN KEY (pedido_id) REFERENCES pedidos (id) ON DELETE SET NULL;

CREATE INDEX IF NOT EXISTS idx_projetos_pedido_id ON projetos (pedido_id);
