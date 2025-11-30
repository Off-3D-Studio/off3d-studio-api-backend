package com.off3dstudio.off3dbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class MetricaVendasMensalDTO {

    private Integer ano;
    private Integer mes;
    private Long quantidadePedidos;
    private BigDecimal valorTotal;

    public String getMesAno() {
        return mes + "/" + ano;
    }
}
