package com.off3dstudio.off3dbackend.controller;

import com.off3dstudio.off3dbackend.dto.RelatorioClientesDTO;
import com.off3dstudio.off3dbackend.dto.RelatorioFluxoCaixaDTO;
import com.off3dstudio.off3dbackend.dto.RelatorioVendasDTO;
import com.off3dstudio.off3dbackend.service.RelatorioService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/relatorios")
@RequiredArgsConstructor
public class RelatorioController {

    private final RelatorioService relatorioService;

    @GetMapping("/vendas")
    public List<RelatorioVendasDTO> relatorioVendas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {
        return relatorioService.gerarRelatorioVendas(inicio, fim);
    }

    @GetMapping("/fluxo-caixa/{ano}")
    public List<RelatorioFluxoCaixaDTO> relatorioFluxoCaixa(@PathVariable int ano) {
        return relatorioService.gerarRelatorioFluxoCaixa(ano);
    }

    @GetMapping("/dashboard")
    public Map<String, BigDecimal> dashboard() {
        return relatorioService.gerarRelatorioDashboard();
    }

    @GetMapping("/clientes-top")
    public List<RelatorioClientesDTO> topClientes(@RequestParam(defaultValue = "10") int limit) {
        return relatorioService.gerarRelatorioTopClientes(limit);
    }

}
