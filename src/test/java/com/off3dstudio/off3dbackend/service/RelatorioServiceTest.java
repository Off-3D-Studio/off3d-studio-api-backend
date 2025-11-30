package com.off3dstudio.off3dbackend.service;

import com.off3dstudio.off3dbackend.dto.RelatorioClientesDTO;
import com.off3dstudio.off3dbackend.dto.RelatorioVendasDTO;
import com.off3dstudio.off3dbackend.repository.ClienteRepository;
import com.off3dstudio.off3dbackend.repository.ContaRepository;
import com.off3dstudio.off3dbackend.repository.PedidoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.sql.Date;
import java.util.List;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class RelatorioServiceTest {

    private PedidoRepository pedidoRepository;
    private ContaRepository contaRepository;
    private ClienteRepository clienteRepository;
    private RelatorioService relatorioService;

    @BeforeEach
    void setup() {
        pedidoRepository = Mockito.mock(PedidoRepository.class);
        contaRepository = Mockito.mock(ContaRepository.class);
        clienteRepository = Mockito.mock(ClienteRepository.class);
        relatorioService = new RelatorioService(pedidoRepository, contaRepository, clienteRepository);
    }

    @Test
    void gerarRelatorioVendas_mapsTupleToDTO() {
        // arrange: create a tuple [java.sql.Date, Long, BigDecimal]
        Date dt = Date.valueOf(LocalDate.of(2025, 11, 1));
        Object[] row = new Object[]{dt, 3L, new BigDecimal("1500.00")};
        when(pedidoRepository.findVendasPorPeriodo(any(LocalDateTime.class), any(LocalDateTime.class)))
            .thenReturn(Collections.<Object[]>singletonList(row));

        // act
        List<RelatorioVendasDTO> result = relatorioService.gerarRelatorioVendas(LocalDate.of(2025, 11, 1), LocalDate.of(2025, 11, 30));

        // assert
        assertThat(result).hasSize(1);
        RelatorioVendasDTO dto = result.get(0);
        assertThat(dto.getData()).isEqualTo(LocalDate.of(2025, 11, 1));
        assertThat(dto.getQuantidadePedidos()).isEqualTo(3L);
        assertThat(dto.getValorTotal()).isEqualByComparingTo(new BigDecimal("1500.00"));
    }

    @Test
    void gerarRelatorioTopClientes_mapsAndLimits() {
        Object[] c1 = new Object[]{"Cliente A", 5L, new BigDecimal("5000.00")};
        Object[] c2 = new Object[]{"Cliente B", 3L, new BigDecimal("2500.00")};
        when(clienteRepository.findTopClientesByPedidosCountAndValor()).thenReturn(List.of(c1, c2));

        List<RelatorioClientesDTO> top1 = relatorioService.gerarRelatorioTopClientes(1);

        assertThat(top1).hasSize(1);
        RelatorioClientesDTO dto = top1.get(0);
        assertThat(dto.getNomeCliente()).isEqualTo("Cliente A");
        assertThat(dto.getTotalPedidos()).isEqualTo(5L);
        assertThat(dto.getValorTotal()).isEqualByComparingTo(new BigDecimal("5000.00"));
    }
}
