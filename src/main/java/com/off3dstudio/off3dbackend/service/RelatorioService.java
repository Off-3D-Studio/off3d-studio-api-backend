package com.off3dstudio.off3dbackend.service;

import com.off3dstudio.off3dbackend.dto.RelatorioClientesDTO;
import com.off3dstudio.off3dbackend.dto.RelatorioFluxoCaixaDTO;
import com.off3dstudio.off3dbackend.dto.RelatorioVendasDTO;
import com.off3dstudio.off3dbackend.repository.ClienteRepository;
import com.off3dstudio.off3dbackend.repository.ContaRepository;
import com.off3dstudio.off3dbackend.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RelatorioService {

    private final PedidoRepository pedidoRepository;
    private final ContaRepository contaRepository;
    private final ClienteRepository clienteRepository;

    public List<RelatorioVendasDTO> gerarRelatorioVendas(LocalDate inicio, LocalDate fim) {
        LocalDateTime inicioDateTime = inicio.atStartOfDay();
        LocalDateTime fimDateTime = fim.atTime(23, 59, 59);

        List<Object[]> raw = pedidoRepository.findVendasPorPeriodo(inicioDateTime, fimDateTime);

        return raw.stream()
                .map(obj -> {
                    // obj[0] = date (could be java.sql.Date, Timestamp or java.time.LocalDate)
                    Object dateObj = obj[0];
                    java.time.LocalDate date = null;
                    if (dateObj instanceof java.sql.Date) {
                        date = ((java.sql.Date) dateObj).toLocalDate();
                    } else if (dateObj instanceof java.sql.Timestamp) {
                        date = ((java.sql.Timestamp) dateObj).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
                    } else if (dateObj instanceof java.time.LocalDate) {
                        date = (java.time.LocalDate) dateObj;
                    } else if (dateObj instanceof java.util.Date) {
                        date = ((java.util.Date) dateObj).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
                    }

                    Long quantidade = (Long) obj[1];
                    java.math.BigDecimal valor = (java.math.BigDecimal) obj[2];

                    return new RelatorioVendasDTO(date, quantidade, valor);
                })
                .collect(Collectors.toList());
    }

    public List<RelatorioFluxoCaixaDTO> gerarRelatorioFluxoCaixa(int ano) {
        LocalDate inicio = LocalDate.of(ano, 1, 1);
        LocalDate fim = LocalDate.of(ano, 12, 31);

        return contaRepository.findFluxoCaixaMensal(inicio, fim);
    }

    public Map<String, BigDecimal> gerarRelatorioDashboard() {
        Map<String, BigDecimal> dashboard = new HashMap<>();

        dashboard.put("totalReceber", contaRepository.sumContasReceberPendentes());
        dashboard.put("totalPagar", contaRepository.sumContasPagarPendentes());
        dashboard.put("saldo", dashboard.get("totalReceber").subtract(dashboard.get("totalPagar")));
        dashboard.put("vendasMes", pedidoRepository.findTotalVendasMesAtual());

        return dashboard;
    }

    public List<RelatorioClientesDTO> gerarRelatorioTopClientes(int limit) {
        List<Object[]> results = clienteRepository.findTopClientesByPedidosCountAndValor();
        return results.stream()
                .limit(limit)
                .map(obj -> new RelatorioClientesDTO(
                        (String) obj[0],           // nomeCliente
                        (Long) obj[1],             // totalPedidos
                        (BigDecimal) obj[2]        // valorTotal
                ))
                .collect(Collectors.toList());
    }


}
