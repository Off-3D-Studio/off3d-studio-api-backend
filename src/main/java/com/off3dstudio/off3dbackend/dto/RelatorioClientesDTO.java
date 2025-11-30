package com.off3dstudio.off3dbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class RelatorioClientesDTO {

    private String nomeCliente;
    private Long totalPedidos;
    private BigDecimal valorTotal;

}
