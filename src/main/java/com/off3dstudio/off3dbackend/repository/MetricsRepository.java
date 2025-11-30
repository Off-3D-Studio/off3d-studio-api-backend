package com.off3dstudio.off3dbackend.repository;

import com.off3dstudio.off3dbackend.dto.MetricaFluxoCaixaDTO;
import com.off3dstudio.off3dbackend.dto.MetricaVendasMensalDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import com.off3dstudio.off3dbackend.model.Pedido;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface MetricsRepository extends JpaRepository<Pedido, Long> {

    // Vendas mensais
    @Query("SELECT new com.off3dstudio.off3dbackend.dto.MetricaVendasMensalDTO(" +
            "YEAR(p.dataCriacao), MONTH(p.dataCriacao), COUNT(p), SUM(p.valor)) " +
            "FROM Pedido p " +
            "WHERE YEAR(p.dataCriacao) = :ano " +
            "GROUP BY YEAR(p.dataCriacao), MONTH(p.dataCriacao) " +
            "ORDER BY MONTH(p.dataCriacao)")
    List<MetricaVendasMensalDTO> findVendasMensais(@Param("ano") int ano);

    // Fluxo de caixa mensal
    @Query("SELECT new com.off3dstudio.off3dbackend.dto.MetricaFluxoCaixaDTO(" +
            "YEAR(c.dataVencimento), MONTH(c.dataVencimento), " +
            "c.tipo, SUM(c.valor)) " +
            "FROM Conta c " +
            "WHERE YEAR(c.dataVencimento) = :ano " +
            "GROUP BY YEAR(c.dataVencimento), MONTH(c.dataVencimento), c.tipo " +
            "ORDER BY MONTH(c.dataVencimento)")
    List<MetricaFluxoCaixaDTO> findFluxoCaixaMensal(@Param("ano") int ano);

    // Total de vendas por mês
    @Query("SELECT COALESCE(SUM(p.valor), 0) FROM Pedido p " +
            "WHERE YEAR(p.dataCriacao) = :ano AND MONTH(p.dataCriacao) = :mes")
    BigDecimal findTotalVendasByMonth(@Param("ano") int ano, @Param("mes") int mes);

    // Contas pagas no mês
    @Query("SELECT COALESCE(SUM(c.valor), 0) FROM Conta c " +
            "WHERE c.status = 'PAGO' " +
            "AND YEAR(c.dataPagamento) = :ano AND MONTH(c.dataPagamento) = :mes")
    BigDecimal findContasPagasByMonth(@Param("ano") int ano, @Param("mes") int mes);

    // Novos clientes no mês
    @Query("SELECT COUNT(DISTINCT p.cliente) FROM Pedido p " +
            "WHERE YEAR(p.dataCriacao) = :ano AND MONTH(p.dataCriacao) = :mes")
    Long countNovosClientesByMonth(@Param("ano") int ano, @Param("mes") int mes);

    // Projetos concluídos no mês
    @Query("SELECT COUNT(pj) FROM Projeto pj " +
            "WHERE pj.status = 'CONCLUIDO' " +
            "AND YEAR(pj.dataTermino) = :ano AND MONTH(pj.dataTermino) = :mes")
    Long countProjetosConcluidosByMonth(@Param("ano") int ano, @Param("mes") int mes);

    // Ticket médio mensal
    @Query("SELECT COALESCE(AVG(p.valor), 0) FROM Pedido p " +
            "WHERE YEAR(p.dataCriacao) = :ano AND MONTH(p.dataCriacao) = :mes")
    BigDecimal findTicketMedioByMonth(@Param("ano") int ano, @Param("mes") int mes);

}
