package com.off3dstudio.off3dbackend.service;

import com.off3dstudio.off3dbackend.dto.MetricaFluxoCaixaDTO;
import com.off3dstudio.off3dbackend.dto.MetricaVendasMensalDTO;
import com.off3dstudio.off3dbackend.repository.MetricsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MetricsService {

    private final MetricsRepository metricsRepository;

    public List<MetricaVendasMensalDTO> getVendasMensais(int ano) {
        return metricsRepository.findVendasMensais(ano);
    }

    public List<MetricaFluxoCaixaDTO> getFluxoCaixaMensal(int ano) {
        return metricsRepository.findFluxoCaixaMensal(ano);
    }

    public Map<String, Object> getMetricsByMonth(int ano, int mes) {
        Map<String, Object> metrics = new HashMap<>();

        metrics.put("totalVendas", metricsRepository.findTotalVendasByMonth(ano, mes));
        metrics.put("contasPagas", metricsRepository.findContasPagasByMonth(ano, mes));
        metrics.put("novosClientes", metricsRepository.countNovosClientesByMonth(ano, mes));
        metrics.put("projetosConcluidos", metricsRepository.countProjetosConcluidosByMonth(ano, mes));
        metrics.put("ticketMedio", metricsRepository.findTicketMedioByMonth(ano, mes));

        // Calcula lucro bruto (vendas - contas pagas)
        BigDecimal vendas = metricsRepository.findTotalVendasByMonth(ano, mes);
        BigDecimal contasPagas = metricsRepository.findContasPagasByMonth(ano, mes);
        metrics.put("lucroBruto", vendas.subtract(contasPagas));

        return metrics;
    }

    public Map<String, Object> getMetricsCurrentMonth() {
        LocalDate now = LocalDate.now();
        return getMetricsByMonth(now.getYear(), now.getMonthValue());
    }

    public Map<String, Object> getMetricsLastMonth() {
        LocalDate lastMonth = LocalDate.now().minusMonths(1);
        return getMetricsByMonth(lastMonth.getYear(), lastMonth.getMonthValue());
    }

    public Map<String, Object> getYearlyComparison(int ano) {
        Map<String, Object> comparison = new HashMap<>();

        // Comparativo com ano anterior
        List<MetricaVendasMensalDTO> vendasAtual = metricsRepository.findVendasMensais(ano);
        List<MetricaVendasMensalDTO> vendasAnterior = metricsRepository.findVendasMensais(ano - 1);

        comparison.put("vendasAnoAtual", vendasAtual);
        comparison.put("vendasAnoAnterior", vendasAnterior);

        // Crescimento anual
        BigDecimal totalAtual = vendasAtual.stream()
                .map(MetricaVendasMensalDTO::getValorTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalAnterior = vendasAnterior.stream()
                .map(MetricaVendasMensalDTO::getValorTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal crescimento = totalAnterior.compareTo(BigDecimal.ZERO) > 0 ?
                totalAtual.subtract(totalAnterior)
                        .divide(totalAnterior, new MathContext(4, RoundingMode.HALF_UP))
                        .multiply(BigDecimal.valueOf(100)) :
                BigDecimal.valueOf(100);

        comparison.put("crescimentoPercentual", crescimento);

        return comparison;
    }

}
