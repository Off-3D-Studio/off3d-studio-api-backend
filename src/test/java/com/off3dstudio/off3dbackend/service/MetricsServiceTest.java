package com.off3dstudio.off3dbackend.service;

import com.off3dstudio.off3dbackend.dto.MetricaVendasMensalDTO;
import com.off3dstudio.off3dbackend.repository.MetricsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class MetricsServiceTest {

    private MetricsRepository metricsRepository;
    private MetricsService metricsService;

    @BeforeEach
    void setup() {
        metricsRepository = Mockito.mock(MetricsRepository.class);
        metricsService = new MetricsService(metricsRepository);
    }

    @Test
    void getMetricsByMonth_computesLucroBruto() {
        when(metricsRepository.findTotalVendasByMonth(2025, 11)).thenReturn(new BigDecimal("2000.00"));
        when(metricsRepository.findContasPagasByMonth(2025, 11)).thenReturn(new BigDecimal("500.00"));
        when(metricsRepository.countNovosClientesByMonth(2025, 11)).thenReturn(10L);
        when(metricsRepository.countProjetosConcluidosByMonth(2025, 11)).thenReturn(2L);
        when(metricsRepository.findTicketMedioByMonth(2025, 11)).thenReturn(new BigDecimal("200.00"));

        Map<String, Object> metrics = metricsService.getMetricsByMonth(2025, 11);

        assertThat(metrics.get("totalVendas")).isEqualTo(new BigDecimal("2000.00"));
        assertThat(metrics.get("contasPagas")).isEqualTo(new BigDecimal("500.00"));
        assertThat(metrics.get("lucroBruto")).isEqualTo(new BigDecimal("1500.00"));
        assertThat(metrics.get("novosClientes")).isEqualTo(10L);
        assertThat(metrics.get("projetosConcluidos")).isEqualTo(2L);
    }

    @Test
    void getYearlyComparison_returnsCrescimentoPercentual() {
        MetricaVendasMensalDTO v1 = new MetricaVendasMensalDTO(2025, 1, 1L, new BigDecimal("1000.00"));
        MetricaVendasMensalDTO v2 = new MetricaVendasMensalDTO(2025, 2, 1L, new BigDecimal("1000.00"));
        MetricaVendasMensalDTO old1 = new MetricaVendasMensalDTO(2024, 1, 1L, new BigDecimal("500.00"));
        when(metricsRepository.findVendasMensais(2025)).thenReturn(List.of(v1, v2));
        when(metricsRepository.findVendasMensais(2024)).thenReturn(List.of(old1));

        Map<String, Object> comparison = metricsService.getYearlyComparison(2025);

        // totalAtual = 2000, totalAnterior = 500 => crescimento = ((2000 - 500)/500) * 100 = 300%
        assertThat(comparison.get("crescimentoPercentual")).isEqualTo(new BigDecimal("300"));
    }
}
