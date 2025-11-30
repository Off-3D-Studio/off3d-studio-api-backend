package com.off3dstudio.off3dbackend.controller;

import com.off3dstudio.off3dbackend.dto.RelatorioClientesDTO;
import com.off3dstudio.off3dbackend.dto.RelatorioFluxoCaixaDTO;
import com.off3dstudio.off3dbackend.dto.RelatorioVendasDTO;
import com.off3dstudio.off3dbackend.service.RelatorioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class RelatorioControllerTest {

    private RelatorioService relatorioService;
    private RelatorioController controller;

    @BeforeEach
    void setup() {
        relatorioService = Mockito.mock(RelatorioService.class);
        controller = new RelatorioController(relatorioService);
    }

    @Test
    void returns_values_from_service() {
        LocalDate ini = LocalDate.of(2025, 11, 1);
        LocalDate fim = LocalDate.of(2025, 11, 30);

        RelatorioVendasDTO vendas = new RelatorioVendasDTO(ini, 2L, new BigDecimal("1500.00"));
        RelatorioFluxoCaixaDTO fluxo = new RelatorioFluxoCaixaDTO(null, 11, 2025, new BigDecimal("1000.00"));
        RelatorioClientesDTO cliente = new RelatorioClientesDTO("A", 2L, new BigDecimal("2000.00"));

        when(relatorioService.gerarRelatorioVendas(ini, fim)).thenReturn(List.of(vendas));
        when(relatorioService.gerarRelatorioFluxoCaixa(2025)).thenReturn(List.of(fluxo));
        when(relatorioService.gerarRelatorioDashboard()).thenReturn(Map.of("totalReceber", new BigDecimal("100")));
        when(relatorioService.gerarRelatorioTopClientes(Mockito.anyInt())).thenReturn(List.of(cliente));

        assertThat(controller.relatorioVendas(ini, fim)).containsExactly(vendas);
        assertThat(controller.relatorioFluxoCaixa(2025)).containsExactly(fluxo);
        assertThat(controller.dashboard()).containsKey("totalReceber");
        assertThat(controller.topClientes(5)).isNotEmpty();
    }
}
