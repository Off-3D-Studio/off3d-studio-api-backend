package com.off3dstudio.off3dbackend.dto;

import com.off3dstudio.off3dbackend.model.Conta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RelatorioFluxoCaixaDTO {

    private Conta.TipoConta tipo;
    private Integer mes;
    private Integer ano;
    private BigDecimal valor;

}
