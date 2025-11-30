package com.off3dstudio.off3dbackend.repository;

import com.off3dstudio.off3dbackend.dto.RelatorioFluxoCaixaDTO;
import com.off3dstudio.off3dbackend.dto.RelatorioVendasDTO;
import com.off3dstudio.off3dbackend.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RelatorioRepository extends JpaRepository<Pedido, Long> {

    // Relatório de vendas por período
    @Query("SELECT function('date', p.dataCriacao), COUNT(p), SUM(p.valor) " +
            "FROM Pedido p " +
            "WHERE p.dataCriacao BETWEEN :inicio AND :fim " +
            "GROUP BY function('date', p.dataCriacao) " +
            "ORDER BY function('date', p.dataCriacao)")
    List<Object[]> findVendasPorPeriodo(@Param("inicio") LocalDateTime inicio,
                                                  @Param("fim") LocalDateTime fim);

    // Fluxo de caixa mensal
    @Query("SELECT new com.off3dstudio.off3dbackend.dto.RelatorioFluxoCaixaDTO(" +
            "c.tipo, MONTH(c.dataVencimento), YEAR(c.dataVencimento), SUM(c.valor)) " +
            "FROM Conta c " +
            "WHERE c.dataVencimento BETWEEN :inicio AND :fim " +
            "GROUP BY c.tipo, MONTH(c.dataVencimento), YEAR(c.dataVencimento)")
    List<RelatorioFluxoCaixaDTO> findFluxoCaixaMensal(@Param("inicio") LocalDate inicio,
                                                      @Param("fim") LocalDate fim);

}
