package com.off3dstudio.off3dbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class PedidoDTO {

    private Long id;
    private String clienteNome;
    private String descricao;
    private BigDecimal valor;
    private String status;
    private LocalDateTime dataCriacao;

}
