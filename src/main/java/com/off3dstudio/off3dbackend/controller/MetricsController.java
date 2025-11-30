package com.off3dstudio.off3dbackend.controller;

import com.off3dstudio.off3dbackend.dto.MetricaFluxoCaixaDTO;
import com.off3dstudio.off3dbackend.dto.MetricaVendasMensalDTO;
import com.off3dstudio.off3dbackend.service.MetricsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/api/metrics")
@RequiredArgsConstructor
public class MetricsController {

    private final MetricsService metricsService;

    @GetMapping("/vendas/{ano}")
    public List<MetricaVendasMensalDTO> getVendasMensais(@PathVariable int ano) {
        return metricsService.getVendasMensais(ano);
    }

    @GetMapping("/fluxo-caixa/{ano}")
    public List<MetricaFluxoCaixaDTO> getFluxoCaixaMensal(@PathVariable int ano) {
        return metricsService.getFluxoCaixaMensal(ano);
    }

    @GetMapping("/mes/{ano}/{mes}")
    public Map<String, Object> getMetricsByMonth(
            @PathVariable int ano,
            @PathVariable int mes) {
        return metricsService.getMetricsByMonth(ano, mes);
    }

    @GetMapping("/mes-atual")
    public Map<String, Object> getMetricsCurrentMonth() {
        return metricsService.getMetricsCurrentMonth();
    }

    @GetMapping("/mes-anterior")
    public Map<String, Object> getMetricsLastMonth() {
        return metricsService.getMetricsLastMonth();
    }

    @GetMapping("/comparativo/{ano}")
    public Map<String, Object> getYearlyComparison(@PathVariable int ano) {
        return metricsService.getYearlyComparison(ano);
    }

}
