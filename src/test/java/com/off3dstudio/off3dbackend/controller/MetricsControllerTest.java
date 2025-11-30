package com.off3dstudio.off3dbackend.controller;

import com.off3dstudio.off3dbackend.dto.MetricaFluxoCaixaDTO;
import com.off3dstudio.off3dbackend.dto.MetricaVendasMensalDTO;
import com.off3dstudio.off3dbackend.service.MetricsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import com.off3dstudio.off3dbackend.model.Conta;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class MetricsControllerTest {

    private MetricsService metricsService;
    private MetricsController metricsController;

    @BeforeEach
    void setup() {
        metricsService = Mockito.mock(MetricsService.class);
        metricsController = new MetricsController(metricsService);
    }

    @Test
    void endpoints_return_service_values() {
        MetricaVendasMensalDTO vendas = new MetricaVendasMensalDTO(2025, 11, 2L, new BigDecimal("1500.00"));
        MetricaFluxoCaixaDTO fluxo = new MetricaFluxoCaixaDTO(2025, 11, Conta.TipoConta.PAGAR, new BigDecimal("700.00"));

        when(metricsService.getVendasMensais(2025)).thenReturn(List.of(vendas));
        when(metricsService.getFluxoCaixaMensal(2025)).thenReturn(List.of(fluxo));

        when(metricsService.getMetricsByMonth(2025, 11)).thenReturn(Map.of("totalVendas", new BigDecimal("1500")));
        when(metricsService.getMetricsCurrentMonth()).thenReturn(Map.of("m", 1));
        when(metricsService.getMetricsLastMonth()).thenReturn(Map.of("m", 0));
        when(metricsService.getYearlyComparison(2025)).thenReturn(Map.of("crescimentoPercentual", new BigDecimal("100")));

        assertThat(metricsController.getVendasMensais(2025)).containsExactly(vendas);
        assertThat(metricsController.getFluxoCaixaMensal(2025)).containsExactly(fluxo);
        assertThat(metricsController.getMetricsByMonth(2025, 11)).containsKey("totalVendas");
        assertThat(metricsController.getMetricsCurrentMonth()).isNotNull();
        assertThat(metricsController.getMetricsLastMonth()).isNotNull();
        assertThat(metricsController.getYearlyComparison(2025)).containsKey("crescimentoPercentual");
    }
}
