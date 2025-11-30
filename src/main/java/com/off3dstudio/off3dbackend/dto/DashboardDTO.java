package com.off3dstudio.off3dbackend.dto;

import com.off3dstudio.off3dbackend.model.Projeto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardDTO {

    private BigDecimal totalReceber;
    private BigDecimal totalPagar;
    private BigDecimal saldo;
    private Long pedidosPendentes;
    private Long projetosAndamento;
    private Long totalClientes;
    private List<DashboardUltimosPedidosDTO> ultimosPedidos;
    private List<Projeto> projetosProximosVencimento;

}
