-- Adiciona a coluna cliente_id na tabela pedidos
ALTER TABLE pedidos
    ADD COLUMN cliente_id BIGINT;

-- Adiciona a constraint de foreign key
ALTER TABLE pedidos
    ADD CONSTRAINT fk_pedidos_cliente
        FOREIGN KEY (cliente_id) REFERENCES clientes (id) ON DELETE SET NULL;

-- Cria índice para melhor performance
CREATE INDEX idx_pedidos_cliente_id ON pedidos (cliente_id);

-- Atualiza os pedidos existentes (se aplicável)
-- UPDATE pedidos SET cliente_id = [id_do_cliente] WHERE id = [id_do_pedido];