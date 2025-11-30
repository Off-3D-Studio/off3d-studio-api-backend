package com.off3dstudio.off3dbackend.repository;

import com.off3dstudio.off3dbackend.model.Pedido;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PedidoRepositoryTest {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Test
    void saveAndFindByPeriodo_and_vendasPorPeriodo() {
        LocalDateTime now = LocalDateTime.of(2025, 11, 10, 10, 0);

        Pedido a = new Pedido();
        a.setCliente("A"); a.setDescricao("A"); a.setValor(new BigDecimal("100.00")); a.setStatus(Pedido.StatusPedido.CONCLUIDO);
        a.setDataCriacao(now);

        Pedido b = new Pedido();
        b.setCliente("B"); b.setDescricao("B"); b.setValor(new BigDecimal("300.00")); b.setStatus(Pedido.StatusPedido.CONCLUIDO);
        b.setDataCriacao(now.plusDays(1));

        pedidoRepository.saveAll(List.of(a, b));

        LocalDate start = LocalDate.of(2025, 11, 9);
        LocalDate end = LocalDate.of(2025, 11, 11);
        LocalDateTime inicio = start.atStartOfDay();
        LocalDateTime fim = end.atTime(23, 59, 59);

        List<Pedido> period = pedidoRepository.findByPeriodo(inicio, fim);
        assertThat(period).hasSizeGreaterThanOrEqualTo(2);

        List<Object[]> vendas = pedidoRepository.findVendasPorPeriodo(inicio, fim);
        // Each array: [date, count, sum]
        assertThat(vendas).isNotEmpty();

        boolean found = vendas.stream().anyMatch(r -> r.length == 3 && r[1] instanceof Long && r[2] instanceof BigDecimal);
        assertThat(found).isTrue();
    }
}
