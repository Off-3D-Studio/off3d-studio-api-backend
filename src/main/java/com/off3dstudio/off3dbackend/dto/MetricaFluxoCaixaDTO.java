package com.off3dstudio.off3dbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import com.off3dstudio.off3dbackend.model.Conta;

@Data
@AllArgsConstructor
public class MetricaFluxoCaixaDTO {

    private Integer ano;
    private Integer mes;
    private Conta.TipoConta tipo; // PAGAR ou RECEBER
    private BigDecimal valorTotal;

}
