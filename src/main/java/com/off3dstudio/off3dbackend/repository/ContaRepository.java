package com.off3dstudio.off3dbackend.repository;

import com.off3dstudio.off3dbackend.dto.RelatorioFluxoCaixaDTO;
import com.off3dstudio.off3dbackend.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ContaRepository extends JpaRepository<Conta, Long> {

    List<Conta> findByTipo(Conta.TipoConta tipo);
    List<Conta> findByStatus(Conta.StatusConta status);
    List<Conta> findByDataVencimentoBetween(LocalDate startDate, LocalDate endDate);

    @Query("SELECT c FROM Conta c WHERE c.dataVencimento BETWEEN :hoje AND :semanaProxima AND c.status = 'PENDENTE'")
    List<Conta> findContasAVencer(@Param("hoje") LocalDate hoje,
                                  @Param("semanaProxima") LocalDate semanaProxima);

    @Query("SELECT c FROM Conta c WHERE c.dataVencimento < CURRENT_DATE AND c.status = 'PENDENTE'")
    List<Conta> findContasVencidas();

    @Query("SELECT COALESCE(SUM(c.valor), 0) FROM Conta c WHERE c.tipo = 'RECEBER' AND c.status = 'PENDENTE'")
    BigDecimal sumContasReceberPendentes();

    @Query("SELECT COALESCE(SUM(c.valor), 0) FROM Conta c WHERE c.tipo = 'PAGAR' AND c.status = 'PENDENTE'")
    BigDecimal sumContasPagarPendentes();

    @Query("SELECT COALESCE(SUM(c.valor), 0) FROM Conta c WHERE c.tipo = 'RECEBER'")
    BigDecimal sumReceber();

    @Query("SELECT COALESCE(SUM(c.valor), 0) FROM Conta c WHERE c.tipo = 'PAGAR'")
    BigDecimal sumPagar();

    @Query("SELECT COALESCE(SUM(c.valor), 0) FROM Conta c WHERE c.status = 'PAGO' AND c.dataPagamento BETWEEN :startDate AND :endDate")
    BigDecimal sumContasPagasNoPeriodo(@Param("startDate") LocalDate startDate,
                                       @Param("endDate") LocalDate endDate);

    @Query("SELECT new com.off3dstudio.off3dbackend.dto.RelatorioFluxoCaixaDTO(" +
            "c.tipo, MONTH(c.dataVencimento), YEAR(c.dataVencimento), SUM(c.valor)) " +
            "FROM Conta c " +
            "WHERE c.dataVencimento BETWEEN :inicio AND :fim " +
            "GROUP BY c.tipo, MONTH(c.dataVencimento), YEAR(c.dataVencimento)")
    List<RelatorioFluxoCaixaDTO> findFluxoCaixaMensal(@Param("inicio") LocalDate inicio,
                                                      @Param("fim") LocalDate fim);

    @Query("SELECT COALESCE(SUM(c.valor), 0) FROM Conta c WHERE c.status = 'PAGO' AND FUNCTION('YEAR', c.dataPagamento) = :year AND FUNCTION('MONTH', c.dataPagamento) = :month")
    BigDecimal sumContasPagasByMonth(@Param("year") int year, @Param("month") int month);

}