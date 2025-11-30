package com.off3dstudio.off3dbackend.dto;

import com.off3dstudio.off3dbackend.model.Pedido;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class DashboardUltimosPedidosDTO {

    private Long pedidoId;
    private String descricao;
    private BigDecimal valor;
    private String status;
    private LocalDateTime dataCriacao;
    private String clienteNome;

    public DashboardUltimosPedidosDTO(Long id, String descricao, BigDecimal valor,
                                      Pedido.StatusPedido status, LocalDateTime dataCriacao, String cliente) {
        this.pedidoId = id;
        this.descricao = descricao;
        this.valor = valor;
        this.status = status.toString();
        this.dataCriacao = dataCriacao;
        this.clienteNome = cliente;
    }
}
